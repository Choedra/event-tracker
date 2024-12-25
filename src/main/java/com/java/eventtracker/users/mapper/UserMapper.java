package com.java.eventtracker.users.mapper;

import com.java.eventtracker.users.model.User;
import com.java.eventtracker.users.model.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    /**
     * Maps the users to users dto.
     *
     * @param user The user entity.
     * @return Returns the user entity.
     */
    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto, "password");
        return dto;
    }

    /**
     * Maps the list of users to users dto
     *
     * @param users The list of user entity.
     * @return The list of users dto.
     */
    public static List<UserDTO> toDTO(List<User> users) {
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Maps the optional user to optional users dto.
     *
     * @param users The user entity.
     * @return The optional user dto.
     */
    public static Optional<UserDTO> toDTO(Optional<User> users) {
        return users.map(UserMapper::toDTO);
    }

    /**
     * Maps the users dto  to the users entity.
     *
     * @param dto The users dto.
     * @return The user entity.
     */
    public static User toEntity(UserDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        return user;
    }
}
