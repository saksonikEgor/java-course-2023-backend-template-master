package edu.java.commands;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;

public sealed interface TelegramBotCommand
    permits TelegramBotStartCommand,
    TelegramBotHelpCommand, TelegramBotTrackCommand,
    TelegramBotUntrackCommand, TelegramBotListCommand {
    String getName();

    String getDescription();

    SendMessage execute(Message message);
}
