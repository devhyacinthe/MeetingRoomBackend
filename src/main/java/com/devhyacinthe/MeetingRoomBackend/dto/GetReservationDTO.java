package com.devhyacinthe.MeetingRoomBackend.dto;

import com.devhyacinthe.MeetingRoomBackend.entity.Room;
import com.devhyacinthe.MeetingRoomBackend.entity.User;
import com.devhyacinthe.MeetingRoomBackend.enums.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public class GetReservationDTO {

    private String reservationNum;

    private Room room;

    private User user;

    private ReservationStatus status;

    private LocalDate dateReservation;

    private LocalTime startTime, endTime;

    public String getReservationNum() {
        return reservationNum;
    }

    public void setReservationNum(String reservationNum) {
        this.reservationNum = reservationNum;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
