package com.todoapp.exception;

import lombok.Getter;

@Getter
public class IllegalOperationException extends RuntimeException {
    private final String message;
    public IllegalOperationException(String message) {
        super();
        this.message = message;
    }
}
