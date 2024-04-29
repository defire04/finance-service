package com.example.financeservice.exception.category;

public class CategoryDoesNotBelongToThisUserException extends RuntimeException {

    public CategoryDoesNotBelongToThisUserException() {
        super("Category does not belong to this user");
    }

    public CategoryDoesNotBelongToThisUserException(String message) {
        super(message);
    }
}
