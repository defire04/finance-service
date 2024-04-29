package com.example.financeservice.exception.balance;

public class BalanceAlreadyExitsException extends RuntimeException {

    public BalanceAlreadyExitsException() {
        super("Balance for this user already registered");
    }

    public BalanceAlreadyExitsException(String message) {
        super(message);
    }
}
