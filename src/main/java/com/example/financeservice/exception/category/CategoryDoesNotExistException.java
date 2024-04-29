package com.example.financeservice.exception.category;

public class CategoryDoesNotExistException extends RuntimeException{

    public CategoryDoesNotExistException() {
        super("Category does not exist");
    }

    public CategoryDoesNotExistException(String message) {
        super(message);
    }
}
