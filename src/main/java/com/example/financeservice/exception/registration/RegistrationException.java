package com.example.financeservice.exception.registration;

import lombok.Getter;

@Getter
public class RegistrationException extends RuntimeException {
    private final int statusCode;
    private final String errorMessage;

    public RegistrationException(int statusCode, String errorMessage) {
        super(errorMessage);
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }
}