package com.java.eventtracker.events.controller;

import com.java.eventtracker.events.model.Events;
import com.java.eventtracker.events.service.EventsService;
import com.java.eventtracker.utils.RestHelper;
import com.java.eventtracker.utils.RestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/events")
public class EventsController {

    private final EventsService eventService;

    public EventsController(EventsService eventService) {
        this.eventService = eventService;
    }

    /**
     * Creates a new event for a specific user on a selected day.
     *
     * @param events  The event details to be saved.
     * @param userId  The ID of the user to associate with the event.
     * @return The saved event details as a response.
     */
    @PostMapping("/{userId}")
    public ResponseEntity<RestResponse> saveEvent(
            @Validated @RequestBody Events events,
            @PathVariable Long userId) {

        try {
            // Save the event
            Events savedEvent = eventService.saveEvent(events, userId);

            // Prepare the response
            HashMap<String, Object> response = new HashMap<>();
            response.put("event", savedEvent);

            return RestHelper.responseSuccess(response);
        } catch (IllegalArgumentException e) {
            return RestHelper.responseError(e.getMessage());
        } catch (Exception e) {
            return RestHelper.responseError("Error saving event: " + e.getMessage());
        }
    }
}
