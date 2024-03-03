package edu.java.commands;

import com.pengrad.telegrambot.model.Message;

public interface TelegramBotCommand {
    String getName();

    String getDescription();

    String execute(Message message);
}
