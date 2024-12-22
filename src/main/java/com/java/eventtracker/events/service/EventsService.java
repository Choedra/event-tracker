package com.java.eventtracker.events.service;

import com.java.eventtracker.events.model.Events;
import com.java.eventtracker.events.repository.EventsRepository;
import com.java.eventtracker.users.model.Users;
import com.java.eventtracker.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EventsService {

    @Autowired
    private EventsRepository eventRepository;

    @Autowired
    private UsersRepository usersRepository;

    public Events saveEvent(Events events, Long userId) {
        // Fetch the user
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate the event date
        if (events.getEventDate() == null || events.getEventDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Invalid event date");
        }

        // Associate the event with the user
        events.setUsers(users);

        // Save and return the event
        return eventRepository.save(events);
    }
}
