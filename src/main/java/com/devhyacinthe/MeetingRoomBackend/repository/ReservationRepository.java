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
}
