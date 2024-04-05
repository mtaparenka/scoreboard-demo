package com.example.exceptions;

public class MatchAlreadyStartedException extends RuntimeException {
    public MatchAlreadyStartedException(String message) {
        super(message);
    }
}
