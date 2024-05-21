package com.example.financeservice.exception.balance.transaction;

public class TransactionCantHaveNegativeNumberException extends RuntimeException {

    public TransactionCantHaveNegativeNumberException() {
        super("Transaction cannot have a negative number");
    }

    public TransactionCantHaveNegativeNumberException(String message) {
        super(message);
    }
}

