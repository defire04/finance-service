package com.example.financeservice.exception.piggy;

public class PiggyBankDoesNotExistException extends RuntimeException {

    public PiggyBankDoesNotExistException() {
        super("Piggy bank does not exist");
    }

    public PiggyBankDoesNotExistException(String message) {
        super(message);
    }
}
