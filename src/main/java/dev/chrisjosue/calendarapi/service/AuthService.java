package dev.chrisjosue.calendarapi.service;

import dev.chrisjosue.calendarapi.dto.auth.AuthDto;
import dev.chrisjosue.calendarapi.dto.custom.AuthResponse;
import dev.chrisjosue.calendarapi.dto.user.UserDto;

public interface AuthService {
    AuthResponse signUp(UserDto userDto);
    AuthResponse signIn(AuthDto authDto);
    AuthResponse renew(String user);
}
