package edu.java.handler;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface InputHandler {
    SendMessage handle(Update update);
}
