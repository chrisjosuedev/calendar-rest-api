package dev.chrisjosue.calendarapi.service.impl;

import dev.chrisjosue.calendarapi.dto.event.EventDto;
import dev.chrisjosue.calendarapi.model.EventEntity;
import dev.chrisjosue.calendarapi.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    @Override
    public EventEntity save(EventDto entity) {
        return null;
    }

    @Override
    public EventEntity update(EventDto entity, String s) {
        return null;
    }

    @Override
    public void delete(String s) {

    }

    @Override
    public EventEntity findById(String s) {
        return null;
    }

    @Override
    public List<EventEntity> findAll() {
        return null;
    }
}
