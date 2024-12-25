package com.java.eventtracker.users.service;

import com.java.eventtracker.Auth.helper.UserInfoDetails;
import com.java.eventtracker.users.mapper.UsersMapper;
import com.java.eventtracker.users.model.User;
import com.java.eventtracker.users.model.UserDTO;
import com.java.eventtracker.users.repository.UsersRepository;
import com.java.eventtracker.utils.exception.GlobalExceptionWrapper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.java.eventtracker.utils.constants.UserConstants.*;

@Service
public class UsersService implements IUsersService{

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<UserDTO> findAll() {
        List<User> users = this.usersRepository.findAll();
        return UsersMapper.toDTO(users);
    }

    @Override
    public UserDTO save(@NonNull User user) {
        //Check if same user already exists during signup
        if (usersRepository.existsByEmail(user.getEmail())) {
            throw new GlobalExceptionWrapper.BadRequestException(DUPLICATE_EMAIL_MESSAGE);
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles("USER");
        User savedUser = this.usersRepository.save(user);

        return UsersMapper.toDTO(savedUser);
    }

    @Override
    public UserDTO findById(long id) {
        User user = this.usersRepository.findById(id).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE, USERS.toLowerCase())));
        return UsersMapper.toDTO(user);
    }

    @Override
    public String update(long id, User entity) {
        return "";
    }

    @Override
    public String deleteById(long id) {
        return "";
    }


    @Override
    public UserDTO fetchSelfInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserInfoDetails) authentication.getPrincipal()).getUsername();
        return  findByEmail(email).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE, USERS.toLowerCase())));
    }

    public Optional<UserDTO> findByEmail(@NonNull String emailId) {
        Optional<User> user = this.usersRepository.findByEmail(emailId);
        return UsersMapper.toDTO(user);
    }
}
