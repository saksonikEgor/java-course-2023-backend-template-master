package edu.java.exception;

public class UserIsAlreadyRegisteredException extends RuntimeException {
    public UserIsAlreadyRegisteredException(String message) {
        super(message);
    }
}
