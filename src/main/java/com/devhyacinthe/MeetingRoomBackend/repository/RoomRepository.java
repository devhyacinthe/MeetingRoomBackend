package com.devhyacinthe.MeetingRoomBackend.repository;

import com.devhyacinthe.MeetingRoomBackend.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

}
