package com.example.financeservice.handler.exeption;

import com.example.financeservice.exception.registration.RegistrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RegistrationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleRegistrationException(RegistrationException ex) {

        Map<String, String> response = new HashMap<>();
        response.put("error_msg", ex.getErrorMessage());
        response.put("error_name", ex.getClass().getSimpleName());
        response.put("error_status_from_keycloak", String.valueOf(ex.getStatusCode()));

        log.error(ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }
}
