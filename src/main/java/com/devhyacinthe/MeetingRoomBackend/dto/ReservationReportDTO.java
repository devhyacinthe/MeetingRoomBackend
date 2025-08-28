package com.devhyacinthe.MeetingRoomBackend.dto;

import com.devhyacinthe.MeetingRoomBackend.enums.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationReportDTO {
    private String reservationNum;

    private String roomName, userName;

    private ReservationStatus status;

    private LocalDate dateReservation;

    private LocalTime startTime, endTime;

    public ReservationReportDTO(String reservationNum, String roomName, String userName, ReservationStatus status, LocalDate dateReservation, LocalTime startTime, LocalTime endTime) {
        this.reservationNum = reservationNum;
        this.roomName = roomName;
        this.userName = userName;
        this.status = status;
        this.dateReservation = dateReservation;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getReservationNum() {
        return reservationNum;
    }

    public void setReservationNum(String reservationNum) {
        this.reservationNum = reservationNum;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
