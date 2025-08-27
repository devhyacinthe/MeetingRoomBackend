package com.devhyacinthe.MeetingRoomBackend.controller;

import com.devhyacinthe.MeetingRoomBackend.dto.AuthRequest;
import com.devhyacinthe.MeetingRoomBackend.dto.AuthResponse;
import com.devhyacinthe.MeetingRoomBackend.dto.RegisterResponse;
import com.devhyacinthe.MeetingRoomBackend.mapper.UserMapper;
import com.devhyacinthe.MeetingRoomBackend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Authentification")
@RequestMapping("/api/auth")
//@SecurityRequirement(name = "bearerAuth")
public class AuthController {
    private final AuthService authService;
    private final UserMapper userMapper;


    public AuthController(AuthService authService, UserMapper userMapper) {
        this.authService = authService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    @Operation(summary = "S'inscrire")
    public RegisterResponse register(@RequestBody AuthRequest request)  {
        return authService.register(userMapper.toEntity(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Se connecter")
    public AuthResponse login(@RequestBody AuthRequest request) {
        String token = authService.login(request);
        return new AuthResponse(token);
    }
}
