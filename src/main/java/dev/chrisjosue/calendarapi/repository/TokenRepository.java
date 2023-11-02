package dev.chrisjosue.calendarapi.repository;

import dev.chrisjosue.calendarapi.model.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {
    List<Token> findAllByUserId(String uid);
    Optional<Token> findByToken(String token);
}
