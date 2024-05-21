package com.example.financeservice.exception.category;

public class CategoryInvalidTypeException extends RuntimeException {

    public CategoryInvalidTypeException() {
        super("Invalid category type!");
    }

    public CategoryInvalidTypeException(String message) {
        super(message);
    }
}
