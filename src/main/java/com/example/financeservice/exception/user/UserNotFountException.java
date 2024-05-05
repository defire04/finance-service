package com.example.financeservice.exception.user;

import lombok.Getter;

@Getter
public class UserNotFountException extends RuntimeException {

    public UserNotFountException(String username) {
        super("Cant find user with username " + username);

    }
}
