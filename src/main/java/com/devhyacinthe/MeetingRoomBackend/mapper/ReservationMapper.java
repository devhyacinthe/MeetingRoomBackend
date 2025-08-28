package com.devhyacinthe.MeetingRoomBackend.mapper;

import com.devhyacinthe.MeetingRoomBackend.dto.GetReservationDTO;
import com.devhyacinthe.MeetingRoomBackend.dto.ReservationDTO;
import com.devhyacinthe.MeetingRoomBackend.dto.UpdateReservationStatusDTO;
import com.devhyacinthe.MeetingRoomBackend.entity.Reservation;
import com.devhyacinthe.MeetingRoomBackend.enums.ReservationStatus;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReservationMapper {
    Reservation toEntity(ReservationDTO reservationDTO);
    void updateReservationFromDto(ReservationDTO dto, @MappingTarget Reservation entity);
    void updateReservationStatusFromDto(ReservationStatus status, @MappingTarget Reservation entity);
    GetReservationDTO toGetReservationDTO(Reservation reservation);
}
