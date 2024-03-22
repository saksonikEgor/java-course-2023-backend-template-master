package edu.java.handler.parser;

import edu.java.commands.TelegramBotCommand;
import edu.java.commands.TelegramBotCommandType;
import java.util.Map;
import java.util.Optional;

public class OneWordMessageExecutor extends MessageExecutor {
    private final Map<TelegramBotCommandType, TelegramBotCommand> typeToOneWordCommand;

    public OneWordMessageExecutor(
        MessageExecutor next,
        Map<String, TelegramBotCommandType> nameToType,
        Map<TelegramBotCommandType, TelegramBotCommand> typeToOneWordCommand
    ) {
        super(next, nameToType);
        this.typeToOneWordCommand = typeToOneWordCommand;
    }

    @Override
    public Optional<String> parse(String[] words, long chatId) {
        if (words.length != 1) {
            return next == null ? Optional.empty() : next.parse(words, chatId);
        }

        try {
            String commandName = words[0];

            TelegramBotCommandType type = nameToType.get(commandName);
            TelegramBotCommand command = typeToOneWordCommand.get(type);
            String resultText = command.execute(commandName, chatId);

            return Optional.ofNullable(resultText);
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }
}
