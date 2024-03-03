package edu.java.commands;

public record TelegramBotCommandInfo(
    String commandName,
    String commandDefinition,
    String successfulResponse,
    String unSuccessfulResponse
) {
}
