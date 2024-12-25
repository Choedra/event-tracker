package com.java.eventtracker.events.service;

import com.java.eventtracker.events.mapper.EventsMapper;
import com.java.eventtracker.events.model.Events;
import com.java.eventtracker.events.model.EventsDTO;
import com.java.eventtracker.events.repository.EventsRepository;
import com.java.eventtracker.users.mapper.UserMapper;
import com.java.eventtracker.users.model.UserDTO;
import com.java.eventtracker.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventsService implements IEventService{

    @Autowired
    private EventsRepository eventRepository;

    @Autowired
    private UserService usersService;

    @Override
    public List<EventsDTO> findAll() {
        UserDTO authenticatedUser = usersService.fetchSelfInfo();
        List<Events> events = eventRepository.findByUserId(authenticatedUser.getId());
        return EventsMapper.toDTO(events);
    }

    @Override
    public EventsDTO save(Events events) {
        UserDTO user = usersService.fetchSelfInfo();

        // Associate the event with the user
        events.setUsers(UserMapper.toEntity(user));

        // Save and return the event
        Events savedEvents = eventRepository.save(events);
        return EventsMapper.toDTO(savedEvents);
    }

    @Override
    public EventsDTO findById(long id) {
        Events events = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with ID: " + id));
        return EventsMapper.toDTO(events);
    }

    @Override
    public String update(long id, Events entity) {
        return "";
    }

    @Override
    public String deleteById(long id) {
        eventRepository.deleteById(id);
        return String.format("% deleted successfully", "Event");
    }
}
