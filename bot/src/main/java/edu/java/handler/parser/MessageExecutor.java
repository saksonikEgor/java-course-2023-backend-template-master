package edu.java.handler.parser;

import edu.java.commands.TelegramBotCommandType;
import java.util.Map;
import java.util.Optional;

public abstract class MessageExecutor {
    protected final MessageExecutor next;
    protected final Map<String, TelegramBotCommandType> nameToType;

    protected MessageExecutor(
        MessageExecutor next,
        Map<String, TelegramBotCommandType> nameToType
    ) {
        this.next = next;
        this.nameToType = nameToType;
    }

    public abstract Optional<String> parse(String[] words, long chatId);
}
