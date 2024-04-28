package com.example.financeservice.exception.user;

import lombok.Getter;

@Getter
public class KeycloakUserNotFountException extends RuntimeException {

    public KeycloakUserNotFountException(String username) {
        super("Cant find user with username " + username + "in keycloak");

    }
}
