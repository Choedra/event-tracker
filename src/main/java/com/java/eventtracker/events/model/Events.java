package com.java.eventtracker.events.model;

import com.java.eventtracker.users.model.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Events {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Column(name = "event_date")  // Column name in the database
    private LocalDateTime eventDate; // Date and time of the event

    @ManyToOne
    @JoinColumn(name = "user_Id", nullable = false) // Ensure this matches your database schema
    private Users users;

}
