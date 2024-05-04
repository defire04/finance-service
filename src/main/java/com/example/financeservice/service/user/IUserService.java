package com.example.financeservice.service.user;

import com.example.financeservice.model.user.User;
import com.example.financeservice.service.base.IBaseEntityService;

import java.util.Optional;

public interface IUserService extends IBaseEntityService<User> {

    /**
     * Finds a user by their username.
     *
     * @param username The username to search for
     * @return An optional containing the user if found, or empty otherwise
     */
    public Optional<User> findByUsername(String username);
}
