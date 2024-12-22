package com.java.eventtracker.events.mapper;

import com.java.eventtracker.events.model.Events;
import com.java.eventtracker.events.model.EventsDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventsMapper {
    /**
     * Maps the Event entity to EventDTO.
     *
     * @param event The Event entity.
     * @return The EventDTO.
     */
    public static EventsDTO toDTO(Events event) {
        EventsDTO dto = new EventsDTO();
        BeanUtils.copyProperties(event, dto);
        return dto;
    }

    /**
     * Maps a list of Event entities to a list of EventDTOs.
     *
     * @param events The list of Event entities.
     * @return The list of EventDTOs.
     */
    public static List<EventsDTO> toDTO(List<Events> events) {
        return events.stream()
                .map(EventsMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Maps an Optional<Event> to an Optional<EventDTO>.
     *
     * @param events The Optional Event entity.
     * @return The Optional EventDTO.
     */
    public static Optional<EventsDTO> toDTO(Optional<Events> events) {
        return events.map(EventsMapper::toDTO);
    }

    /**
     * Maps an EventDTO to an Event entity.
     *
     * @param dto The EventDTO.
     * @return The Event entity.
     */
    public static Events toEntity(EventsDTO dto) {
        Events event = new Events();
        BeanUtils.copyProperties(dto, event);
        return event;
    }
}
