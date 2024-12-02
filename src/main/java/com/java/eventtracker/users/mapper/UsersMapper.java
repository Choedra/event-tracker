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
     * Maps the user to user dto.
     *
     * @param user The user entity.
     * @return Returns the user DTO.
     */
    public static UsersDTO toDTO(Users user) {
        UsersDTO dto = new UsersDTO();
        BeanUtils.copyProperties(user, dto, "password");
        return dto;
    }

    /**
     * Maps the list of users to user DTO.
     *
     * @param users The list of user entities.
     * @return The list of user DTOs.
     */
    public static List<UsersDTO> toDTO(List<Users> users) {
        return users.stream()
                .map(UsersMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Maps the optional user to optional user DTO.
     *
     * @param user The user entity.
     * @return The optional user DTO.
     */
    public static Optional<UsersDTO> toDTO(Optional<Users> user) {
        return user.map(UsersMapper::toDTO);
    }

    /**
     * Maps the user DTO to the user entity.
     *
     * @param dto The user DTO.
     * @return The user entity.
     */
    public static Users toEntity(UsersDTO dto) {
        Users user = new Users();
        BeanUtils.copyProperties(dto, user);
        return user;
    }
}
