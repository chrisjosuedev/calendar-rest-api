package dev.chrisjosue.calendarapi.utils.helpers.impl;

import dev.chrisjosue.calendarapi.model.Token;
import dev.chrisjosue.calendarapi.model.UserEntity;
import dev.chrisjosue.calendarapi.repository.TokenRepository;
import dev.chrisjosue.calendarapi.utils.exceptions.MyBusinessException;
import dev.chrisjosue.calendarapi.utils.helpers.TokenHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenHelperImpl implements TokenHelper {
    private final TokenRepository tokenRepository;

    @Override
    public void revokeAllTokensByUser(UserEntity user) {
        List<Token> allTokens = tokenRepository.findAllByUserEntityIdAndRevokedIsFalseOrExpiredIsFalse(user.getId());
        if (allTokens.isEmpty()) return;
        allTokens.forEach((token) -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(allTokens);
    }

    @Override
    public void revokeToken(String jwt) {
        tokenRepository.findByToken(jwt)
                .ifPresentOrElse(
                        (token) -> {
                            token.setRevoked(true);
                            token.setExpired(true);
                            tokenRepository.save(token);
                        }, () -> {
                            throw new MyBusinessException("Token is invalid.", HttpStatus.BAD_REQUEST);
                        });
    }
}
