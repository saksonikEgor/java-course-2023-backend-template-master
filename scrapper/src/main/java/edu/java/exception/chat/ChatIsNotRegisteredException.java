package edu.java.exception.chat;

public class ChatIsNotRegisteredException extends RuntimeException {
    public ChatIsNotRegisteredException(String message) {
        super(message);
    }
}
