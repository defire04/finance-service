package com.example.financeservice.exception.piggy;

public class PiggyBankDoesNotBelongToThisUserException extends RuntimeException {

    public PiggyBankDoesNotBelongToThisUserException() {
        super("Piggy bank does not belong to this user");
    }

    public PiggyBankDoesNotBelongToThisUserException(String message) {
        super(message);
    }
}
