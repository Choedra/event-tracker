package com.java.eventtracker.users.service;

import com.java.eventtracker.Auth.helper.UserInfoDetails;
import com.java.eventtracker.users.mapper.UsersMapper;
import com.java.eventtracker.utils.constants.UserConstants;
import com.java.eventtracker.users.model.Users;
import com.java.eventtracker.users.model.UsersDTO;
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
    public List<UsersDTO> findAll() {
        List<Users> users = this.usersRepository.findAll();
        return UsersMapper.toDTO(users);
    }

    @Override
    public UsersDTO save(@NonNull Users users) {
        //Check if same user already exists during signup
        if (usersRepository.existsByEmail(users.getEmail())) {
            throw new GlobalExceptionWrapper.BadRequestException(DUPLICATE_EMAIL_MESSAGE);
        }

        users.setPassword(encoder.encode(users.getPassword()));
        users.setRoles("USER");
        Users savedUsers = this.usersRepository.save(users);

        return UsersMapper.toDTO(savedUsers);
    }

    @Override
    public UsersDTO findById(long id) {
        Users users = this.usersRepository.findById(id).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE, USERS.toLowerCase())));
        return UsersMapper.toDTO(users);
    }

    @Override
    public String update(long id, Users entity) {
        return "";
    }

    @Override
    public String deleteById(long id) {
        return "";
    }


    @Override
    public UsersDTO fetchSelfInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserInfoDetails) authentication.getPrincipal()).getUsername();
        return  findByEmail(email).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE, USERS.toLowerCase())));
    }

    public Optional<UsersDTO> findByEmail(@NonNull String emailId) {
        Optional<Users> user = this.usersRepository.findByEmail(emailId);
        return UsersMapper.toDTO(user);
    }
}
