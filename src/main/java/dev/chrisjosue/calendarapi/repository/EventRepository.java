package dev.chrisjosue.calendarapi.repository;

import dev.chrisjosue.calendarapi.model.EventEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends MongoRepository<EventEntity, String> {}
