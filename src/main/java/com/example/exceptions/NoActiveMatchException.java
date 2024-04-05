package com.example.exceptions;

public class NoActiveMatchException extends RuntimeException {
    public NoActiveMatchException(String message) {
        super(message);
    }
}
