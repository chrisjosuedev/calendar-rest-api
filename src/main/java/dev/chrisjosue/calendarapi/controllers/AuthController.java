package dev.chrisjosue.calendarapi.controllers;

import dev.chrisjosue.calendarapi.dto.auth.AuthDto;
import dev.chrisjosue.calendarapi.dto.custom.AuthResponse;
import dev.chrisjosue.calendarapi.dto.custom.ResponseHandler;
import dev.chrisjosue.calendarapi.dto.user.UserDto;
import dev.chrisjosue.calendarapi.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Secure and user-friendly registration and login processes to manage your events effectively.")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Sign In User",
            description = """
                    Allows users to log in and obtain an authentication token. Provide valid credentials to access protected API endpoints.\s
                    The token received should be included in the Authorization Header for future requests.
                    """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Login OK.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))}),
            @ApiResponse(responseCode = "400",
                    description = "User Information is incorrect.",
                    content = @Content)
    })
    @PostMapping("/signin")
    public ResponseEntity<Object> signIn(@Valid @RequestBody AuthDto authDto) {
        return ResponseHandler.responseHandler(true, HttpStatus.OK, authService.signIn(authDto));
    }

    @Operation(summary = "Sign Up User",
            description = """
                    Register new users using this endpoint. Provide user details, including name, email and password to create a new account. 
                    Upon successful registration, users can log in to access the API and manage events.
                    """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "User CREATED.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))}),
            @ApiResponse(responseCode = "400",
                    description = "User Information is incorrect.",
                    content = @Content)
    })
    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@Valid @RequestBody UserDto userDto) {
        return ResponseHandler.responseHandler(true, HttpStatus.CREATED, authService.signUp(userDto));
    }

    @Operation(summary = "Renew User JWT Token",
            description = """
                    Renew an existing token by providing your current token in the Authorization Header.
                    This endpoint returns a new token allowing you to stay authenticated without needing to log in again.
                    """,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Token OK.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))}),
            @ApiResponse(responseCode = "403",
                    description = "Token is incorrect.",
                    content = @Content)
    })
    @GetMapping("/renew")
    public ResponseEntity<Object> renewToken(Principal principal) {
        return ResponseHandler.responseHandler(true, HttpStatus.OK, authService.renew(principal.getName()));
    }
}
