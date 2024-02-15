package edu.java.commands;

import com.pengrad.telegrambot.model.Message;

public sealed interface TelegramBotCommand
    permits TelegramBotHelpCommand, TelegramBotListCommand, TelegramBotStartCommand,
    TelegramBotTrackCommand, TelegramBotUntrackCommand {
    String getName();

    String getDescription();

    String execute(Message message);
}
