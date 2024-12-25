package com.java.eventtracker.events.controller;

import com.java.eventtracker.events.model.Events;
import com.java.eventtracker.events.model.EventsDTO;
import com.java.eventtracker.events.service.EventsService;
import com.java.eventtracker.users.service.IUsersService;
import com.java.eventtracker.users.service.UsersService;
import com.java.eventtracker.utils.RestHelper;
import com.java.eventtracker.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
public class EventsController {

    @Autowired
    private EventsService eventService;

    /**
     * Creates a new event for a specific user on a selected day.
     *
     * @param events  The event details to be saved.
     * @return The saved event details as a response.
     */
    @PostMapping
    public ResponseEntity<RestResponse> saveEvent(
            @Validated @RequestBody Events events) {

        EventsDTO savedEvent = eventService.save(events);
        // Prepare the response
        HashMap<String, Object> response = new HashMap<>();
        response.put("event", savedEvent);
        return RestHelper.responseSuccess(response);
    }
    /**
     * Deletes an event by its ID.
     *
     * @param eventId The ID of the event to delete.
     * @return A response indicating success or failure.
     */
    @DeleteMapping("/{eventId}")
    public ResponseEntity<RestResponse> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteById(eventId);
        // Prepare the response as a Map
        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "Event deleted successfully.");
        return RestHelper.responseSuccess(response);
    }

    /**
     * Retrieves an event by its ID.
     *
     * @param eventId The ID of the event to retrieve.
     * @return The event details as a response.
     */
    @GetMapping("/{eventId}")
    public ResponseEntity<RestResponse> findEventById(@PathVariable Long eventId) {
        EventsDTO event = eventService.findById(eventId);
        // Prepare the response
        HashMap<String, Object> response = new HashMap<>();
        response.put("event", event);
        return RestHelper.responseSuccess(response);
    }

    @GetMapping
    public ResponseEntity<RestResponse> findAll() {
        List<EventsDTO> events = eventService.findAll();
        // Prepare the response
        HashMap<String, Object> response = new HashMap<>();
        response.put("events", events);
        return RestHelper.responseSuccess(response);
    }

}
