package com.java.eventtracker.events.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventsDTO {
    @Id // Mark this field as the primary key
    private long id; // Unique identifier for the event

    private String title; // Title of the event
    private String description; // Description of the event
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime eventDate; // Date and time of the event

    private long userId; // User associated with the event
}
