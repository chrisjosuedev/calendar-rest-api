package dev.chrisjosue.calendarapi.service;

import dev.chrisjosue.calendarapi.common.EntityService;
import dev.chrisjosue.calendarapi.dto.event.EventDto;
import dev.chrisjosue.calendarapi.model.EventEntity;

public interface EventService extends EntityService<EventEntity, EventDto, String> {}
