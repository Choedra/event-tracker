package com.java.eventtracker.users.service;

import com.java.eventtracker.auth.helper.UserInfoDetails;
import com.java.eventtracker.constants.UserConstants;
import com.java.eventtracker.exception.GlobalExceptionWrapper;
import com.java.eventtracker.users.mapper.UsersMapper;
import com.java.eventtracker.users.model.Users;
import com.java.eventtracker.users.model.UsersDTO;
import com.java.eventtracker.users.repository.UsersRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService implements IUsersService{

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<Users> findAll() { return usersRepository.findAll(); }

    @Override
    public Users save(@NonNull Users users) {
        users.setPassword(encoder.encode(users.getPassword()));
        users.setRoles("USER");
        return usersRepository.save(users);
    }

    public UsersDTO fetchSelfInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserInfoDetails) authentication.getPrincipal()).getUsername();
        return  findByEmail(email).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException("User not found."));
    }

    public Optional<UsersDTO> findByEmail(@NonNull String emailId) {
        Optional<Users> instructor = this.usersRepository.findByEmail(emailId);
        return UsersMapper.toDTO(instructor);
    }

    @Override
    public String update(@NonNull Users users) {
        if (usersRepository.existsById(users.getId())) {
            usersRepository.save(users);
            return UserConstants.UPDATE_SUCCESSFUL;
        } else {
            return UserConstants.NOT_FOUND;
        }
    }

    //get by id
    @Override
    public Users findById(long id) throws Exception {
        return usersRepository.findById(id)
                .orElseThrow(() -> new Exception(UserConstants.NOT_FOUND));
    }

    @Override
    public String deleteById(long id) {
        if (usersRepository.existsById(id)) {
            usersRepository.deleteById(id);
            return UserConstants.DELETE_SUCCESSFUL;
        } else {
            return UserConstants.NOT_FOUND;
        }
    }
}
