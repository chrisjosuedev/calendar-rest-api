package dev.chrisjosue.calendarapi.security.impl;

import dev.chrisjosue.calendarapi.security.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    private final String secretKey;

    public JwtServiceImpl(@Value("${SECRET_KEY}") String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new LinkedHashMap<>(), userDetails);
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
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
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * signIn Key
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
