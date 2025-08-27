package com.devhyacinthe.MeetingRoomBackend.service;

import com.devhyacinthe.MeetingRoomBackend.dto.GetRoomDTO;
import com.devhyacinthe.MeetingRoomBackend.dto.RoomDTO;
import com.devhyacinthe.MeetingRoomBackend.entity.Room;
import com.devhyacinthe.MeetingRoomBackend.mapper.RoomMapper;
import com.devhyacinthe.MeetingRoomBackend.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepo;
    private final RoomMapper roomMapper;

    public RoomService(RoomRepository roomRepo, RoomMapper roomMapper) {
        this.roomRepo = roomRepo;
        this.roomMapper = roomMapper;
    }

    public Room createRoom(RoomDTO roomDto) {
        Room room = new Room(roomDto.getName(), roomDto.getCapacity(), roomDto.getEquipments(), roomDto.getLocation(), roomDto.getStatus());
        return roomRepo.save(room);
    };

    public List<GetRoomDTO> getAllRooms() {
        return new ArrayList<>(roomRepo.findAll()
                .stream()
                .map(roomMapper::toGetRoomDTO)
                .toList());
    }

    public Room getOneRoomById(Long id) {
        return roomRepo.findById(id).orElseThrow();
    }

    public Room updateOneRoomById(Long id, RoomDTO roomDto){
        Room room = roomRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Salle introuvable avec id: " + id));
        roomMapper.updateRoomFromDto(roomDto, room);
        return roomRepo.save(room);
    }

    public void deleteOneRoomById(Long id) {
        roomRepo.deleteById(id);
    }
}
