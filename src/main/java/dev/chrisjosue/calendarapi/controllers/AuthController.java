package dev.chrisjosue.calendarapi.controllers;

import dev.chrisjosue.calendarapi.dto.auth.AuthDto;
import dev.chrisjosue.calendarapi.dto.custom.ResponseHandler;
import dev.chrisjosue.calendarapi.dto.user.UserDto;
import dev.chrisjosue.calendarapi.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<Object> signIn(@Valid @RequestBody AuthDto authDto) {
        return ResponseHandler.responseHandler(true, HttpStatus.OK, authService.signIn(authDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@Valid @RequestBody UserDto userDto) {
        return ResponseHandler.responseHandler(true, HttpStatus.CREATED, authService.signUp(userDto));
    }

    @GetMapping("/renew")
    public ResponseEntity<Object> renewToken(Principal principal) {
        return ResponseHandler.responseHandler(true, HttpStatus.OK, authService.renew(principal.getName()));
    }
}
