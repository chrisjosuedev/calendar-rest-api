package dev.chrisjosue.calendarapi.security.impl;

import dev.chrisjosue.calendarapi.security.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    private final String secretKey;
    private final static int EXPIRATION_IN_HOURS = 1;

    public JwtServiceImpl(@Value("${SECRET_KEY}") String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new LinkedHashMap<>(), userDetails);
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        Date issuedAt = new Date(System.currentTimeMillis());
        return Jwts
                .builder()
                .header()
                .type("JWT")
                .and()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(issuedAt)
                .expiration(new Date(
                        issuedAt.getTime() + (EXPIRATION_IN_HOURS * 60 * 60 * 1000)
                ))
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    @Override
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        var isExpired = getExpirationDate(token).before(new Date());
        var username = getUsername(token);
        return (username.equals(userDetails.getUsername()) && !isExpired);
    }

    @Override
    public String getUsername(String token) {
        return getClaims(token, Claims::getSubject);
    }

    @Override
    public Date getExpirationDate(String token) {
        return getClaims(token, Claims::getExpiration);
    }

    /**
     * Extract a specific claim from a token.
     */
    private <T> T getClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extract all claims from token
     */
    private Claims getAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * signIn Key
     */
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
