package dev.chrisjosue.calendarapi.service.imp;

import dev.chrisjosue.calendarapi.dto.auth.AuthDto;
import dev.chrisjosue.calendarapi.dto.custom.AuthResponse;
import dev.chrisjosue.calendarapi.dto.user.UserDto;
import dev.chrisjosue.calendarapi.model.UserEntity;
import dev.chrisjosue.calendarapi.repository.AuthRepository;
import dev.chrisjosue.calendarapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {
    private final AuthRepository authRepository;

    @Override
    public AuthResponse signUp(UserDto userDto) {
        Optional<UserEntity> emailExists = authRepository.findByEmail(userDto.getEmail());

        if (emailExists.isPresent()) throw new RuntimeException("Email already exists");

        UserEntity newUser = UserEntity.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();

        var userSaved = authRepository.save(newUser);
        return AuthResponse.builder()
                .uid(userSaved.getId())
                .name(userSaved.getName())
                // temporal TODO: JWT Implementation
                .token(UUID.randomUUID().toString())
                .build();
    }

    @Override
    public AuthResponse signIn(AuthDto authDto) {
        return null;
    }
}
