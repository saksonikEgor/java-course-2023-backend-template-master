package edu.java.commands;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;

public interface TelegramBotCommand {
    String getName();

    String getDescription();

    SendMessage execute(Message message);
}
