package com.example.financeservice.exception.balance;

public class NotEnoughAmountForThisTransaction extends RuntimeException {

    public NotEnoughAmountForThisTransaction() {
        super("Not enough amount for this transaction");
    }

    public NotEnoughAmountForThisTransaction(String message) {
        super(message);
    }
}