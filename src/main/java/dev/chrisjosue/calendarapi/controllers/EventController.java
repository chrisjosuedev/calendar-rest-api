package dev.chrisjosue.calendarapi.controllers;

import dev.chrisjosue.calendarapi.dto.custom.ResponseHandler;
import dev.chrisjosue.calendarapi.dto.event.EventDto;
import dev.chrisjosue.calendarapi.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<Object> getEvents() {
        return ResponseHandler.responseHandler(true,
                HttpStatus.OK,
                eventService.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> createEvent(Principal principal, @Valid @RequestBody EventDto eventDto) {
        return ResponseHandler.responseHandler(true,
                HttpStatus.CREATED,
                eventService.save(eventDto, principal.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEvent(Principal principal, @PathVariable("id") String id, @Valid @RequestBody EventDto eventDto) {
        return ResponseHandler.responseHandler(true,
                HttpStatus.OK,
                eventService.update(eventDto, id, principal.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEvent(Principal principal, @PathVariable("id") String id) {
        eventService.delete(id, principal.getName());
        return ResponseHandler.responseHandler(true,
                HttpStatus.OK,
                null);
    }
}
