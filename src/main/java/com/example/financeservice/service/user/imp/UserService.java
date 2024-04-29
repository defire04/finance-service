package com.example.financeservice.service.user.imp;

import com.example.financeservice.model.user.User;
import com.example.financeservice.repository.user.UserRepository;
import com.example.financeservice.service.base.imp.BaseEntityService;
import com.example.financeservice.service.user.IUserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends BaseEntityService<User, UserRepository> implements IUserService {

    public UserService(UserRepository repository) {
        super(repository);
    }


    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }



}
