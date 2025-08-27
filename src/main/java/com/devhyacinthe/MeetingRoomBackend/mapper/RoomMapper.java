package com.devhyacinthe.MeetingRoomBackend.mapper;

import com.devhyacinthe.MeetingRoomBackend.dto.GetRoomDTO;
import com.devhyacinthe.MeetingRoomBackend.dto.RoomDTO;
import com.devhyacinthe.MeetingRoomBackend.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoomMapper {
    Room toEntity(RoomDTO roomDTO);
    void updateRoomFromDto(RoomDTO dto, @MappingTarget Room entity);
    GetRoomDTO toGetRoomDTO(Room room);
}
