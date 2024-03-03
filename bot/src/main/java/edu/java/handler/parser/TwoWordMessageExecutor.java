package edu.java.handler.parser;

import edu.java.commands.TelegramBotCommand;
import edu.java.commands.TelegramBotCommandType;
import java.util.Map;
import java.util.Optional;

public class TwoWordMessageExecutor extends MessageExecutor {
    private final Map<TelegramBotCommandType, TelegramBotCommand> typeToTwoWordCommand;

    public TwoWordMessageExecutor(
        MessageExecutor next,
        Map<String, TelegramBotCommandType> nameToType,
        Map<TelegramBotCommandType, TelegramBotCommand> typeToTwoWordCommand
    ) {
        super(next, nameToType);
        this.typeToTwoWordCommand = typeToTwoWordCommand;
    }

    @Override
    public Optional<String> parse(String[] words, long chatId) {
        if (words.length != 2) {
            return next == null ? Optional.empty() : next.parse(words, chatId);
        }

        try {
            String commandName = words[0];
            String url = words[1];

            TelegramBotCommandType type = nameToType.get(commandName);
            TelegramBotCommand command = typeToTwoWordCommand.get(type);

            return Optional.ofNullable(command.execute(url, chatId));
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }
}
