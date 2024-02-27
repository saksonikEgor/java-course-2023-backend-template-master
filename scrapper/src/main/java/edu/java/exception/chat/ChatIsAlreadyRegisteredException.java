package edu.java.exception.chat;

public class ChatIsAlreadyRegisteredException extends RuntimeException {
    public ChatIsAlreadyRegisteredException(String message) {
        super(message);
    }
}
