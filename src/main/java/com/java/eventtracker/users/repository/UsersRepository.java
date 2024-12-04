package com.java.eventtracker.users.repository;

import com.java.eventtracker.users.model.Users;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {

    /**
     * Fetches the user information by email id
     *
     * @param emailId The email id of the user
     * @return The optional valued user object.
     */
    Optional<Users> findByEmail(@NonNull String emailId);

    /**
     * Checks if the email is already in the database or not.
     *
     * @param email The email id of teh user.
     * @return The flag indicating whether the email exits in the system or not.
     */
    boolean existsByEmail(String email);
}
