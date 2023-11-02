package dev.chrisjosue.calendarapi.utils.helpers;

import dev.chrisjosue.calendarapi.model.UserEntity;

public interface TokenHelper {
    void revokeAllTokensByUser(UserEntity user);
    void revokeToken(String jwt);
}
