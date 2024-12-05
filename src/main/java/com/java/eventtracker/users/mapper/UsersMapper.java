package com.java.eventtracker.users.mapper;

import com.java.eventtracker.users.model.Users;
import com.java.eventtracker.users.model.UsersDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UsersMapper {
    /**
     * Maps the users to users dto.
     *
     * @param users The user entity.
     * @return Returns the user entity.
     */
    public static UsersDTO toDTO(Users users) {
        UsersDTO dto = new UsersDTO();
        BeanUtils.copyProperties(users, dto, "password");
        return dto;
    }

    /**
     * Maps the list of users to users dto
     *
     * @param users The list of user entity.
     * @return The list of users dto.
     */
    public static List<UsersDTO> toDTO(List<Users> users) {
        return users.stream()
                .map(UsersMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Maps the optional user to optional users dto.
     *
     * @param users The user entity.
     * @return The optional user dto.
     */
    public static Optional<UsersDTO> toDTO(Optional<Users> users) {
        return users.map(UsersMapper::toDTO);
    }

    /**
     * Maps the users dto  to the users entity.
     *
     * @param dto The users dto.
     * @return The user entity.
     */
    public static Users toEntity(UsersDTO dto) {
        Users users = new Users();
        BeanUtils.copyProperties(dto, users);
        return users;
    }
}
