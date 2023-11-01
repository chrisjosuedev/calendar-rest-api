package dev.chrisjosue.calendarapi.service.impl;

import dev.chrisjosue.calendarapi.dto.auth.AuthDto;
import dev.chrisjosue.calendarapi.dto.custom.AuthResponse;
import dev.chrisjosue.calendarapi.dto.user.UserDto;
import dev.chrisjosue.calendarapi.enums.TokenType;
import dev.chrisjosue.calendarapi.model.Token;
import dev.chrisjosue.calendarapi.model.UserEntity;
import dev.chrisjosue.calendarapi.repository.AuthRepository;
import dev.chrisjosue.calendarapi.repository.TokenRepository;
import dev.chrisjosue.calendarapi.security.JwtService;
import dev.chrisjosue.calendarapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final AuthRepository authRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    @Override
    public AuthResponse signUp(UserDto userDto) {
        Optional<UserEntity> emailExists = authRepository.findByEmail(userDto.getEmail());

        if (emailExists.isPresent()) throw new RuntimeException("Email already exists");

        UserEntity newUser = UserEntity.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();

        UserEntity userSaved = authRepository.save(newUser);
        Token newToken = saveToken(jwtService.generateToken(userSaved), userSaved);
        Token tokenSaved = tokenRepository.save(newToken);

        return AuthResponse.builder()
                .uid(userSaved.getId())
                .name(userSaved.getName())
                .token(tokenSaved.getToken())
                .build();
    }

    @Override
    public AuthResponse signIn(AuthDto authDto) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(
                authDto.getEmail(),
                authDto.getPassword()
        ));
        UserEntity userFound = authRepository.findByEmail(authDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found."));

        revokeAllTokens(userFound);
        Token newToken = saveToken(jwtService.generateToken(userFound), userFound);
        Token tokenSaved = tokenRepository.save(newToken);

        return AuthResponse.builder()
                .uid(userFound.getId())
                .name(userFound.getName())
                .token(tokenSaved.getToken())
                .build();
    }

    private Token saveToken(String jwt, UserEntity user) {
        return Token.builder()
                .token(jwt)
                .expired(false)
                .revoked(false)
                .type(TokenType.BEARER)
                .userEntity(user)
                .build();
    }

    private void revokeAllTokens (UserEntity user) {
        List<Token> allTokens = tokenRepository.findAllByUserEntityIdAndRevokedIsFalseOrExpiredIsFalse(user.getId());
        if (allTokens.isEmpty()) return;

        allTokens.forEach((token) -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(allTokens);
    }
}
