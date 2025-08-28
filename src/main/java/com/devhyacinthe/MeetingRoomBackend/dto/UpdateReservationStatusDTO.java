package com.devhyacinthe.MeetingRoomBackend.dto;

import com.devhyacinthe.MeetingRoomBackend.enums.ReservationStatus;

public class UpdateReservationStatusDTO {
    private ReservationStatus status;

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}
