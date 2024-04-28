package com.example.financeservice.service.user;

import com.example.financeservice.model.user.User;
import com.example.financeservice.service.base.IBaseEntityService;

import java.util.Optional;

public interface IUserService extends IBaseEntityService<User> {

    public Optional<User> findByUsername(String username);
}
