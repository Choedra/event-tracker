package com.java.eventtracker.events.repository;

import com.java.eventtracker.events.model.Events;
import com.java.eventtracker.users.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface EventsRepository extends JpaRepository<Events, Long> {

    // Correct method using 'eventDate' field name
    boolean existsByUsersAndEventDate(Users users, LocalDateTime eventDate);
}
