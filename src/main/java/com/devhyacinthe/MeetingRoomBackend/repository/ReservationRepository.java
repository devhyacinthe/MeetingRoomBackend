package com.devhyacinthe.MeetingRoomBackend.repository;

import com.devhyacinthe.MeetingRoomBackend.entity.Reservation;
import com.devhyacinthe.MeetingRoomBackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser(User user);

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.room.id = :roomId " +
            "AND r.dateReservation = :dateReservation " +
            "AND ( (r.startTime < :endTime AND r.endTime > :startTime) )")
    List<Reservation> findConflictingReservations(@Param("roomId") Long roomId,
                                                  @Param("dateReservation") LocalDate dateReservation,
                                                  @Param("startTime") LocalTime startTime,
                                                  @Param("endTime") LocalTime endTime);

    // Toutes les réservations pour une salle spécifique
    List<Reservation> findByRoomId(Long roomId);

    // Toutes les réservations pour un jour donné
    List<Reservation> findByDateReservation(LocalDate dateReservation);

    // Toutes les réservations pour une salle à une date donnée
    List<Reservation> findByRoomIdAndDateReservation(Long roomId, LocalDate dateReservation);

    @Query("SELECT r FROM Reservation r WHERE r.room.id = :roomId AND r.dateReservation BETWEEN :start AND :end")
    List<Reservation> findByRoomAndDateBetween(
            @Param("roomId") Long roomId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );
}
