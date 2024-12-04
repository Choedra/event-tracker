package com.java.eventtracker.users.service;


import com.java.eventtracker.auth.helper.UserInfoDetails;
import com.java.eventtracker.users.mapper.UsersMapper;
import com.java.eventtracker.utils.exception.GlobalExceptionWrapper;
import com.java.eventtracker.users.model.Users;
import com.java.eventtracker.users.model.UsersDTO;
import com.java.eventtracker.users.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
            throw new com.java.eventtracker.utils.exception.GlobalExceptionWrapper.BadRequestException(DUPLICATE_EMAIL_MESSAGE);
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

    public UsersDTO fetchSelfInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserInfoDetails) authentication.getPrincipal()).getUsername();
        return  findByEmail(email).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE, USERS.toLowerCase())));
    }

    public Optional<UsersDTO> findByEmail(@NonNull String emailId) {
        Optional<Users> instructor = this.usersRepository.findByEmail(emailId);
        return UsersMapper.toDTO(instructor);
    }

    @Override
    public String update(long id, @NonNull Users entity) {
        UsersDTO authenticatedUser = fetchSelfInfo();
        Users usersEntity = UsersMapper.toEntity(authenticatedUser);

        //Allow update by admin to the instructor info.
        if(Arrays.stream(authenticatedUser.getRoles().split(",")).anyMatch(role -> role.trim().equalsIgnoreCase("ADMIN"))){
            usersEntity = UsersMapper.toEntity(findById(id));
        }

        usersEntity.setEmail(entity.getEmail());
        usersEntity.setUsername(entity.getUsername());


        this.usersRepository.save(usersEntity);
        return String.format(UPDATED_SUCCESSFULLY_MESSAGE, USERS);
    }

    @Override
    @Transactional
    public String deleteById(long id) {
        UsersDTO authenticatedUser = fetchSelfInfo();
        Users usersEntity = UsersMapper.toEntity(authenticatedUser);

        //Allow to delete by admin to the instructor info.
        if(Arrays.stream(authenticatedUser.getRoles().split(",")).anyMatch(role -> role.trim().equalsIgnoreCase("ADMIN"))){
            usersEntity = UsersMapper.toEntity(findById(id));
        }

        this.usersRepository.deleteById(usersEntity.getId());
        return String.format(DELETED_SUCCESSFULLY_MESSAGE, USERS);
    }
}
