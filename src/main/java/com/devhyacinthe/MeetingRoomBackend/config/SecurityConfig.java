package com.devhyacinthe.MeetingRoomBackend.config;

import com.devhyacinthe.MeetingRoomBackend.enums.Role;
import com.devhyacinthe.MeetingRoomBackend.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Cette classe permet de définir les règles de spring security et de voir les endpoints autorisés ou pas
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // endpoints publics
                        .requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // ADMIN : gestion complète des salles
                        .requestMatchers("/api/admin/rooms/**").hasRole(Role.ADMIN.name())

                        .requestMatchers("/api/rooms/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())

                        // UTILISATEUR et ADMIN: consulter les salles et créer des réservations
                        .requestMatchers("/api/reservations/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())

                        .requestMatchers("/api/admin/reservations/**").hasRole(Role.ADMIN.name())

                        // tout le reste nécessite authentification
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
