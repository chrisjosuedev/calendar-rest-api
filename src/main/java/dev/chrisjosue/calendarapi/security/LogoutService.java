package dev.chrisjosue.calendarapi.security;

import dev.chrisjosue.calendarapi.utils.helpers.TokenHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final TokenHelper tokenHelper;

    @Override
    public void logout(HttpServletRequest req, HttpServletResponse res, Authentication auth) {
        final String authHeader = req.getHeader("Authorization");
        final String jwt;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) return;
        // Extract JWT from Header
        jwt = authHeader.substring(7);

        // Revoke token
        tokenHelper.revokeToken(jwt);
    }
}
