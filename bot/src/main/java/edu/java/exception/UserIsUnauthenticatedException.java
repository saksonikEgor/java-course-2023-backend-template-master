package edu.java.exception;

public class UserIsUnauthenticatedException extends RuntimeException {
    public UserIsUnauthenticatedException(String message) {
        super(message);
    }
}
