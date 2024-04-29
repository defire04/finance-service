package com.example.financeservice.exception.category;

public class CategoryWithThisNameAlreadyUserByCurrentUserException extends RuntimeException {

    public CategoryWithThisNameAlreadyUserByCurrentUserException() {
        super("Category with this name already used for this user");
    }

    public CategoryWithThisNameAlreadyUserByCurrentUserException(String message) {
        super(message);
    }

}
