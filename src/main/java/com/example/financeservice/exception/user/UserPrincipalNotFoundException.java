package com.example.financeservice.exception.user;

public class UserPrincipalNotFoundException extends RuntimeException{

    public UserPrincipalNotFoundException() {
        super("User principal not valid");
    }

    public UserPrincipalNotFoundException(String message) {
        super(message);
    }
}
