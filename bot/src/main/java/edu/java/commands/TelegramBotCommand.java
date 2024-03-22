package edu.java.commands;

public interface TelegramBotCommand {
    String getName();

    String getDescription();

    String execute(String text, long chatId);
}
