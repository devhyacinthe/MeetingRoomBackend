package com.devhyacinthe.MeetingRoomBackend.service;

import com.devhyacinthe.MeetingRoomBackend.dto.GetReservationDTO;
import com.devhyacinthe.MeetingRoomBackend.dto.ReservationDTO;
import com.devhyacinthe.MeetingRoomBackend.dto.UpdateReservationStatusDTO;
import com.devhyacinthe.MeetingRoomBackend.entity.Reservation;
import com.devhyacinthe.MeetingRoomBackend.entity.Room;
import com.devhyacinthe.MeetingRoomBackend.entity.User;
import com.devhyacinthe.MeetingRoomBackend.enums.ReservationStatus;
import com.devhyacinthe.MeetingRoomBackend.exception.ConflictException;
import com.devhyacinthe.MeetingRoomBackend.exception.NotFoundException;
import com.devhyacinthe.MeetingRoomBackend.mapper.ReservationMapper;
import com.devhyacinthe.MeetingRoomBackend.repository.ReservationRepository;
import com.devhyacinthe.MeetingRoomBackend.repository.RoomRepository;
import com.devhyacinthe.MeetingRoomBackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    private ReservationRepository reservationRepo;
    private ReservationMapper reservationMapper;
    private UserService userService;
    private MailService mailService;
    private RoomRepository roomRepo;
    private UserRepository userRepo;

    public ReservationService(ReservationRepository reservationRepo, ReservationMapper reservationMapper, UserService userService, MailService mailService, UserRepository userRepo, RoomRepository roomRepo){
        this.reservationRepo = reservationRepo;
        this.reservationMapper = reservationMapper;
        this.userService = userService;
        this.mailService = mailService;
        this.userRepo = userRepo;
        this.roomRepo = roomRepo;
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

}
