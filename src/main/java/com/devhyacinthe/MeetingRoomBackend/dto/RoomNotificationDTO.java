package com.devhyacinthe.MeetingRoomBackend.dto;

import com.devhyacinthe.MeetingRoomBackend.enums.ReservationStatus;
import com.devhyacinthe.MeetingRoomBackend.enums.RoomStatus;

public class RoomNotificationDTO {

    private String  name;

    private RoomStatus status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }
}
