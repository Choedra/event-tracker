package com.java.eventtracker.users.service;

import com.java.eventtracker.Auth.helper.UserInfoDetails;
import com.java.eventtracker.users.mapper.UserMapper;
import com.java.eventtracker.users.model.User;
import com.java.eventtracker.users.model.UserDTO;
import com.java.eventtracker.users.repository.UserRepository;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> findAll() {
        List<User> users = this.userRepository.findAll();
        return UserMapper.toDTO(users);
    }

    @Override
    public UserDTO save(@NonNull User user) {
        //Check if same user already exists during signup
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new GlobalExceptionWrapper.BadRequestException(DUPLICATE_EMAIL_MESSAGE);
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles("USER");
        User savedUser = this.userRepository.save(user);

        return UserMapper.toDTO(savedUser);
    }

    @Override
    public UserDTO findById(long id) {
        User user = this.userRepository.findById(id).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE, USER.toLowerCase())));
        return UserMapper.toDTO(user);
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
    public String updateEntity(User user) {
        this.userRepository.save(user);
        return String.format(UPDATED_SUCCESSFULLY_MESSAGE, USER);
    }
    @Override
    public UserDTO fetchSelfInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserInfoDetails) authentication.getPrincipal()).getUsername();
        User selectedUser = findByEmail(email).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE,
                        USER.toLowerCase())));
        return UserMapper.toDTO(selectedUser);
    }

    public Optional<User> findByEmail(@NonNull String emailId) {
        return this.userRepository.findByEmail(emailId);
    }
}
