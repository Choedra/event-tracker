package com.java.eventtracker.events.repository;

import com.java.eventtracker.events.model.Events;
import com.java.eventtracker.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventsRepository extends JpaRepository<Events, Long> {

    List<Events> findByUserId(long userId);
}
