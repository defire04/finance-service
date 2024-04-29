package com.example.financeservice.exception.balance;

public class BalanceNotRegisterException extends RuntimeException {

    public BalanceNotRegisterException() {
        super("Balance not registered for this user");
    }

    public BalanceNotRegisterException(String message) {
        super(message);
    }
}
