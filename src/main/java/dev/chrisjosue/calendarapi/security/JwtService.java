package dev.chrisjosue.calendarapi.security;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;

public interface JwtService {
    Boolean isTokenValid(String token, UserDetails userDetails);
    String generateToken(UserDetails userDetails);
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);
    String getUsername(String token);
    Date getExpirationDate(String token);
}
