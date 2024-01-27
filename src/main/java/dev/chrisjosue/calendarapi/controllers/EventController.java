package dev.chrisjosue.calendarapi.controllers;

import dev.chrisjosue.calendarapi.dto.custom.ResponseHandler;
import dev.chrisjosue.calendarapi.dto.event.EventDto;
import dev.chrisjosue.calendarapi.model.EventEntity;
import dev.chrisjosue.calendarapi.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Tag(name = "Event Management", description = "Provide a comprehensive set of functionalities for managing and coordinating events in a collaborative environment.")
public class EventController {
    private final EventService eventService;

    @Operation(summary = "Get All Events.",
            description = "Retrieve a list of all events using this endpoint.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "List of User events.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventEntity.class))}),
            @ApiResponse(responseCode = "403",
                    description = "User without authentication",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<Object> getEvents() {
        return ResponseHandler.responseHandler(true,
                HttpStatus.OK,
                eventService.findAll());
    }

    @Operation(summary = "Create a new Event.",
            description = "Create a new event with this endpoint. Provide event details such as the title, notes (optional), start, end.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Event created successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventEntity.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Event Information is incorrect.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "User without authentication",
                    content = @Content),
    })
    @PostMapping
    public ResponseEntity<Object> createEvent(Principal principal, @Valid @RequestBody EventDto eventDto) {
        return ResponseHandler.responseHandler(true,
                HttpStatus.CREATED,
                eventService.save(eventDto, principal.getName()));
    }

    @Operation(summary = "Update an Event.",
            description = "Update an existing event by specifying the event's unique identifier (id). Only the user who created the event can modify it.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Event updated successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventEntity.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Event Information is incorrect.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "User does not have permissions to update the event.",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Event does not exists.",
                    content = @Content),
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEvent(Principal principal, @PathVariable("id") String id, @Valid @RequestBody EventDto eventDto) {
        return ResponseHandler.responseHandler(true,
                HttpStatus.OK,
                eventService.update(eventDto, id, principal.getName()));
    }

    @Operation(summary = "Delete an Event.",
            description = """
                    Delete an event by specifying the event's unique identifier (id).\s
                    This action is only allowed for the user who created the event.
                    """,
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Event deleted successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventEntity.class))}),
            @ApiResponse(responseCode = "403",
                    description = "User without authentication",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "User does not have permissions to remove the event.",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Event does not exists.",
                    content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEvent(Principal principal, @PathVariable("id") String id) {
        eventService.delete(id, principal.getName());
        return ResponseHandler.responseHandler(true,
                HttpStatus.OK,
                null);
    }
}
