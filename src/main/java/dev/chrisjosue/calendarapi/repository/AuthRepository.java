package dev.chrisjosue.calendarapi.repository;

import dev.chrisjosue.calendarapi.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);
}
