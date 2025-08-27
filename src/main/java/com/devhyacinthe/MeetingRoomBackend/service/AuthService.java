package com.devhyacinthe.MeetingRoomBackend.service;

import com.devhyacinthe.MeetingRoomBackend.dto.AuthRequest;
import com.devhyacinthe.MeetingRoomBackend.dto.RegisterResponse;
import com.devhyacinthe.MeetingRoomBackend.entity.User;
import com.devhyacinthe.MeetingRoomBackend.enums.Role;
import com.devhyacinthe.MeetingRoomBackend.exception.ConflictException;
import com.devhyacinthe.MeetingRoomBackend.mapper.UserMapper;
import com.devhyacinthe.MeetingRoomBackend.repository.UserRepository;
import com.devhyacinthe.MeetingRoomBackend.security.JwtUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;

    public AuthService(UserRepository userRepo, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.userMapper = userMapper;
    }

    public RegisterResponse register(User user)  {
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new ConflictException("Email déjà utilisé !");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepo.save(user);
        return userMapper.toRegisterResponseDTO(user);
    }

    public String login(AuthRequest request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new ConflictException("Email ou mot de passe incorrect"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ConflictException("Email ou mot de passe incorrect");
        }

        String token = jwtUtils.generateToken(user.getEmail());
        System.out.println("🔑 TOKEN GENERE = " + token);
        return token;
    }
}
