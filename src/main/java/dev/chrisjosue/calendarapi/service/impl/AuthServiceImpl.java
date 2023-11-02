package dev.chrisjosue.calendarapi.service.impl;

import dev.chrisjosue.calendarapi.utils.exceptions.MyBusinessException;
import dev.chrisjosue.calendarapi.utils.helpers.TokenHelper;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final AuthRepository authRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final TokenHelper tokenHelper;

    @Override
    public AuthResponse signUp(UserDto userDto) {
        Optional<UserEntity> emailExists = authRepository.findByEmail(userDto.getEmail());

        if (emailExists.isPresent()) throw new RuntimeException("Email already exists");

        var newUser = UserEntity.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();

        var userSaved = authRepository.save(newUser);
        var newToken = saveToken(jwtService.generateToken(userSaved), userSaved);
        var tokenSaved = tokenRepository.save(newToken);

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
        var userFound = authRepository.findByEmail(authDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found."));

        // Revoke All Token from Current User
        tokenHelper.revokeAllTokensByUser(userFound);

        // Generate new Token
        var newToken = saveToken(jwtService.generateToken(userFound), userFound);
        var tokenSaved = tokenRepository.save(newToken);

        return AuthResponse.builder()
                .uid(userFound.getId())
                .name(userFound.getName())
                .token(tokenSaved.getToken())
                .build();
    }

    @Override
    public AuthResponse renew(String user) {
        try {
            // Find User
            var userLogged = authRepository.findByEmail(user)
                    .orElseThrow(() -> new MyBusinessException("User does not exists.", HttpStatus.NOT_FOUND));

            // Revoke All Token
            tokenHelper.revokeAllTokensByUser(userLogged);

            // Generate new Token
            var newToken = saveToken(jwtService.generateToken(userLogged), userLogged);
            var tokenSaved = tokenRepository.save(newToken);

            return AuthResponse.builder()
                    .uid(userLogged.getId())
                    .name(userLogged.getName())
                    .token(tokenSaved.getToken())
                    .build();
        } catch (RuntimeException ex) {
            throw new MyBusinessException(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private Token saveToken(String jwt, UserEntity user) {
        return Token.builder()
                .token(jwt)
                .expired(false)
                .revoked(false)
                .type(TokenType.BEARER)
                .user(user)
                .build();
    }
}
