package edu.java.exception;

public class ChatIsNotExistException extends RuntimeException {
    public ChatIsNotExistException(String message) {
        super(message);
    }
}
