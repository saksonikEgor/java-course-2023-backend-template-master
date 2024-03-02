package edu.java.exception.chat;

public class ChatIsNotExistException extends RuntimeException {
    public ChatIsNotExistException(String message) {
        super(message);
    }
}
