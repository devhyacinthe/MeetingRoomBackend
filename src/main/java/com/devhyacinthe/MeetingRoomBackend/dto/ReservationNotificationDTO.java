package com.devhyacinthe.MeetingRoomBackend.dto;

import com.devhyacinthe.MeetingRoomBackend.enums.RoomStatus;

import java.time.LocalDateTime;

public class ReservationNotificationDTO {

    private Long roomId;

    private RoomStatus status;

    private Long reservationId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public ReservationNotificationDTO() {

    }

    public ReservationNotificationDTO(Long roomId, RoomStatus status, Long reservationId, LocalDateTime startTime, LocalDateTime endTime) {
        this.roomId = roomId;
        this.status = status;
        this.reservationId = reservationId;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
