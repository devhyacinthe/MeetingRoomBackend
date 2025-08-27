package com.devhyacinthe.MeetingRoomBackend.mapper;

import com.devhyacinthe.MeetingRoomBackend.dto.AuthRequest;
import com.devhyacinthe.MeetingRoomBackend.dto.AuthResponse;
import com.devhyacinthe.MeetingRoomBackend.dto.RegisterResponse;
import com.devhyacinthe.MeetingRoomBackend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    AuthRequest toAuthRequestDTO(User user);
    User toEntity(AuthRequest dto);

    RegisterResponse toRegisterResponseDTO(User user);
}
