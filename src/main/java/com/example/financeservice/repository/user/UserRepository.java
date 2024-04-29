package com.example.financeservice.repository.user;

import com.example.financeservice.model.user.User;
import com.example.financeservice.repository.BaseEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseEntityRepository<User> {

    public Optional<User> findByUsername(String username);


}
