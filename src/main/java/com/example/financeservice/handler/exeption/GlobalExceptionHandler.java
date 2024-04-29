package com.example.financeservice.handler.exeption;

import com.example.financeservice.dto.exception.ExceptionResponseDTO;
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
    public ResponseEntity<ExceptionResponseDTO> handleRegistrationException(RegistrationException ex) {
        log.error(ex.getMessage());

        return ResponseEntity.status(ex.getStatusCode()).body( new ExceptionResponseDTO()
                .setExceptionName(ex.getClass().getSimpleName())
                .setErrorStatusFromKeycloak(HttpStatus.valueOf(ex.getStatusCode()))
                .setErrorMsg(ex.getErrorMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> handleAllException(Exception ex) {

        log.error(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ExceptionResponseDTO()
                .setExceptionName(ex.getClass().getSimpleName())
                .setErrorMsg(ex.getMessage()));
    }
}
