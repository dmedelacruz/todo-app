package com.todoapp.exception;

import lombok.Getter;

@Getter
public class RecordNotFoundException extends RuntimeException {
    private final String message;
    public RecordNotFoundException(String message) {
        super();
        this.message = message;
    }
}
