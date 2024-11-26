package com.java.eventtracker.users.service;

import com.java.eventtracker.constants.UserConstants;
import com.java.eventtracker.users.model.Users;
import com.java.eventtracker.users.repository.UsersRepository;
import lombok.NonNull;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService implements IUsersService{
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<Users> findAll() { return usersRepository.findAll(); }

    @Override
    public Users save(@NonNull Users users) {

        return usersRepository.save(users);
    }

    @Override
    public Users findById(long id) throws Exception {
        return usersRepository.findById(id)
                .orElseThrow(()->new Exception(UserConstants.NOT_FOUND));
    }

    @Override
    public String update(@NonNull Users users) {
        usersRepository.save(users);
        return UserConstants.UPDATE_SUCCESSFUL;
    }

    @Override
    public String deleteById(long id) {

        usersRepository.deleteById(id);
        return UserConstants.DELETE_SUCCESSFUL;
    }
}
