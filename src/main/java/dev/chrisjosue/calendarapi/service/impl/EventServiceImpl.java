package dev.chrisjosue.calendarapi.service.impl;

import dev.chrisjosue.calendarapi.dto.event.EventDto;
import dev.chrisjosue.calendarapi.model.EventEntity;
import dev.chrisjosue.calendarapi.model.UserEntity;
import dev.chrisjosue.calendarapi.repository.AuthRepository;
import dev.chrisjosue.calendarapi.repository.EventRepository;
import dev.chrisjosue.calendarapi.service.EventService;
import dev.chrisjosue.calendarapi.utils.exceptions.MyBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private UserEntity userLogged;
    private final AuthRepository authRepository;
    private final EventRepository eventRepository;

    @Override
    public EventEntity save(EventDto eventDto, String username) {
        try {
            var newEvent = EventEntity.builder()
                    .title(eventDto.getTitle())
                    .notes((eventDto.getNotes() == null ? "" : eventDto.getNotes()))
                    .start(eventDto.getStart())
                    .end(eventDto.getEnd())
                    .user(getUserLogged(username))
                    .build();
            return eventRepository.save(newEvent);
        } catch (RuntimeException ex) {
            throw new MyBusinessException(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public EventEntity update(EventDto eventDto, String uid, String username) {
        try {
            userLogged = getUserLogged(username);
            var eventToUpdate = eventRepository.findById(uid)
                    .orElse(null);
            if (eventToUpdate == null)
                throw new MyBusinessException("Event does not exists.", HttpStatus.NOT_FOUND);
            if (!belongsToUser(eventToUpdate, userLogged.getId()))
                throw new MyBusinessException("You does not have permissions to update this event.", HttpStatus.FORBIDDEN);
            /*
             * Update Event Entity with New Values
             */
            if (eventDto.getNotes() != null) eventToUpdate.setNotes(eventDto.getNotes());
            eventToUpdate.setTitle(eventDto.getTitle());
            eventToUpdate.setStart(eventDto.getStart());
            eventToUpdate.setEnd(eventDto.getEnd());

            return eventRepository.save(eventToUpdate);
        } catch (RuntimeException ex) {
            throw new MyBusinessException(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void delete(String uid, String username) {
        try {
            userLogged = getUserLogged(username);
            var eventToDelete = eventRepository.findById(uid)
                    .orElse(null);
            if (eventToDelete == null)
                throw new MyBusinessException("Event does not exists.", HttpStatus.NOT_FOUND);
            if (!belongsToUser(eventToDelete, userLogged.getId()))
                throw new MyBusinessException("You does not have permissions to remove this event.", HttpStatus.FORBIDDEN);

            eventRepository.deleteById(uid);
        } catch (RuntimeException ex) {
            throw new MyBusinessException(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<EventEntity> findAll() {
        return eventRepository.findAll();
    }

    /**
     * Find User Logged
     */
    private UserEntity getUserLogged(String username) {
        return authRepository.findByEmail(username)
                .orElseThrow(() -> new MyBusinessException("User not found.", HttpStatus.NOT_FOUND));
    }

    /*
     * Event by Given User Id
     */
    private boolean belongsToUser(EventEntity event, String userId) {
        return (event.getUser().getId()).equals(userId);
    }
}
