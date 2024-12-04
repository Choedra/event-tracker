package com.java.eventtracker.users.service;

import com.java.eventtracker.users.model.Users;
import com.java.eventtracker.users.model.UsersDTO;
import com.java.eventtracker.utils.IGenericCrudService;

public interface IUsersService extends IGenericCrudService<Users, UsersDTO> {
    /**
     * Fetches the authenticated users info.
     *
     * @return The users dto
     */
    UsersDTO fetchSelfInfo();
}
