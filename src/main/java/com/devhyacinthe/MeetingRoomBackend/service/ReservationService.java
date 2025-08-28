package com.devhyacinthe.MeetingRoomBackend.service;

import com.devhyacinthe.MeetingRoomBackend.dto.*;
import com.devhyacinthe.MeetingRoomBackend.entity.Reservation;
import com.devhyacinthe.MeetingRoomBackend.entity.Room;
import com.devhyacinthe.MeetingRoomBackend.entity.User;
import com.devhyacinthe.MeetingRoomBackend.enums.ReservationStatus;
import com.devhyacinthe.MeetingRoomBackend.enums.RoomStatus;
import com.devhyacinthe.MeetingRoomBackend.exception.ConflictException;
import com.devhyacinthe.MeetingRoomBackend.exception.NotFoundException;
import com.devhyacinthe.MeetingRoomBackend.mapper.ReservationMapper;
import com.devhyacinthe.MeetingRoomBackend.repository.ReservationRepository;
import com.devhyacinthe.MeetingRoomBackend.repository.RoomRepository;
import com.devhyacinthe.MeetingRoomBackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepo;
    private final ReservationMapper reservationMapper;
    private final UserService userService;
    private final MailService mailService;
    private final RoomRepository roomRepo;
    private final UserRepository userRepo;
    private final NotificationService notificationService;

    public ReservationService(ReservationRepository reservationRepo, ReservationMapper reservationMapper, UserService userService, MailService mailService, UserRepository userRepo, RoomRepository roomRepo, NotificationService notificationService){
        this.reservationRepo = reservationRepo;
        this.reservationMapper = reservationMapper;
        this.userService = userService;
        this.mailService = mailService;
        this.userRepo = userRepo;
        this.roomRepo = roomRepo;
        this.notificationService = notificationService;
    }


    public Reservation createReservation(ReservationDTO reservationDto) {
        // Vérifier si la salle existe
        Room room = roomRepo.findById(reservationDto.getRoomId())
                .orElseThrow(() -> new NotFoundException("Salle non trouvée"));

        // Vérifier si l'utilisateur existe
        User user = userRepo.findById(userService.getAuthenticatedUser().getId())
                .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé"));

        // Vérifier les chevauchements
        List<Reservation> conflits = reservationRepo.findConflictingReservations(
                reservationDto.getRoomId(), reservationDto.getDateReservation(), reservationDto.getStartTime(), reservationDto.getEndTime());

        if (!conflits.isEmpty()) {
            throw new ConflictException("La salle est déjà réservée sur ce créneau horaire.");
        }

        // Je génère le code de réservation
        String code = generateCode(6);

        // Créer une nouvelle réservation en EN_ATTENTE
        Reservation reservation = new Reservation();
        reservation.setReservationNum(code);
        reservation.setRoom(room);
        reservation.setUser(user);
        reservation.setDateReservation(reservationDto.getDateReservation());
        reservation.setStartTime(reservationDto.getStartTime());
        reservation.setEndTime(reservationDto.getEndTime());
        reservation.setStatus(ReservationStatus.PENDING);

        return reservationRepo.save(reservation);
    }


    public List<GetReservationDTO>  getAllUserReservations() {
        User authenticatedUser = userService.getAuthenticatedUser();
        return new ArrayList<>(
                reservationRepo.findByUser(authenticatedUser)
                .stream()
                .map(reservationMapper::toGetReservationDTO)
                .toList());
    }

    public List<GetReservationDTO>  getAllReservations() {
        return new ArrayList<>(
                reservationRepo.findAll()
                        .stream()
                        .map(reservationMapper::toGetReservationDTO)
                        .toList());
    }

    public Reservation confirmReservationById(Long id) {
        Reservation reservation = reservationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation introuvable avec id: " + id));
        reservationMapper.updateReservationStatusFromDto(ReservationStatus.CONFIRMED, reservation);

        // envoi notification
        ReservationNotificationDTO dto = new ReservationNotificationDTO(
                reservation.getRoom().getId(),
                RoomStatus.BUSY,
                reservation.getId(),
                reservation.getDateReservation().atTime(reservation.getStartTime()),
                reservation.getDateReservation().atTime(reservation.getEndTime())
        );
        notificationService.notifyRoomChange(dto);

        /*mailService.sendReservationEmail(
                reservation.getUser().getEmail(),
                "✅ Confirmation de réservation",
                "Votre réservation pour la salle " + reservation.getRoom().getName() + " est confirmée le " + reservation.getDateReservation()
        );*/
        return reservationRepo.save(reservation);
    }

    public Reservation cancelReservationById(Long id) {
        Reservation reservation = reservationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation introuvable avec id: " + id));
        reservationMapper.updateReservationStatusFromDto(ReservationStatus.CANCELLED, reservation);

        // envoi notification
        ReservationNotificationDTO dto = new ReservationNotificationDTO(
                reservation.getRoom().getId(),
                RoomStatus.FREE,
                reservation.getId(),
                reservation.getDateReservation().atTime(reservation.getStartTime()),
                reservation.getDateReservation().atTime(reservation.getEndTime())
        );
        notificationService.notifyRoomChange(dto);


        /*mailService.sendReservationEmail(
                reservation.getUser().getEmail(),
                "❌ Annulation de votre réservation",
                "Votre réservation pour la salle " + reservation.getRoom().getName() + " a été annulée"
        );*/
        return reservationRepo.save(reservation);
    }


    private static final String ALPHANUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private static String generateCode(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(ALPHANUM.length());
            sb.append(ALPHANUM.charAt(index));
        }
        return sb.toString();
    }

    private List<Reservation> findByRoomAndWeek(Long roomId, int week, int year) {
        LocalDate firstDayOfWeek = LocalDate.ofYearDay(year, 1)
                .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, week)
                .with(DayOfWeek.MONDAY);

        LocalDate lastDayOfWeek = firstDayOfWeek.plusDays(6);

        return reservationRepo.findByRoomAndDateBetween(roomId, firstDayOfWeek, lastDayOfWeek);
    }

    public List<CalendarEventDTO> getReservationsAsCalendar(Long roomId, LocalDate dateReservation) {
        List<Reservation> reservations = reservationRepo.findByRoomIdAndDateReservation(roomId, dateReservation);

        return reservations.stream().map(r -> {
            String color = switch (r.getStatus()) {
                case PENDING -> "#f6c23e"; // jaune
                case CONFIRMED -> "#1cc88a"; // vert
                case CANCELLED -> "#e74a3b";   // rouge
                default -> "#36b9cc";          // bleu par défaut
            };

            return new CalendarEventDTO(
                    "Salle " + r.getRoom().getName() + " - " + r.getUser().getUsername(),
                    r.getDateReservation().atTime(r.getStartTime()).toString(),
                    r.getDateReservation().atTime(r.getEndTime()).toString(),
                    color
            );
        }).toList();
    }

    public List<CalendarEventDTO> getReservationsForWeek(Long roomId, int week, int year) {
        List<Reservation> reservations = findByRoomAndWeek(roomId, week, year);

        return reservations.stream().map(r -> {
            String color = switch (r.getStatus()) {
                case PENDING -> "#f6c23e";
                case CONFIRMED -> "#1cc88a";
                case CANCELLED -> "#e74a3b";
                default -> "#36b9cc";
            };

            return new CalendarEventDTO(
                    "Salle " + r.getRoom().getName() + " - " + r.getUser().getUsername(),
                    r.getDateReservation().atTime(r.getStartTime()).toString(),
                    r.getDateReservation().atTime(r.getEndTime()).toString(),
                    color
            );
        }).toList();
    }

}
