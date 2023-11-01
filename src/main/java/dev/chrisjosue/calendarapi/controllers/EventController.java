package dev.chrisjosue.calendarapi.controllers;

import dev.chrisjosue.calendarapi.dto.custom.ResponseHandler;
import dev.chrisjosue.calendarapi.dto.event.EventDto;
import dev.chrisjosue.calendarapi.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<Object> getEvents() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEventById(@PathVariable("id") String id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<Object> createEvent(@Valid @RequestBody EventDto eventDto) {
        return ResponseHandler.responseHandler(true, HttpStatus.CREATED, eventDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEvent(@PathVariable("id") String id, @Valid @RequestBody EventDto eventDto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEvent(@PathVariable("id") String id) {
        return null;
    }
}
