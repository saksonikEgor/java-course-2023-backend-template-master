package edu.java.handler.parser;

import edu.java.commands.TelegramBotCommand;
import edu.java.commands.TelegramBotCommandType;
import java.util.Map;
import java.util.Optional;

public class TwoWordMessageExecutor extends MessageExecutor {
    private final Map<TelegramBotCommandType, TelegramBotCommand> typeToTwoWordCommand;
    private final String telegramBotName;

    public TwoWordMessageExecutor(
        MessageExecutor next,
        Map<String, TelegramBotCommandType> nameToType,
        Map<TelegramBotCommandType, TelegramBotCommand> typeToTwoWordCommand, String telegramBotName
    ) {
        super(next, nameToType);
        this.typeToTwoWordCommand = typeToTwoWordCommand;
        this.telegramBotName = telegramBotName;
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public Optional<String> parse(String[] words, long chatId) {
        try {
            String commandName;
            String url;

            switch (words.length) {
                case 2 -> {
                    commandName = words[0];
                    url = words[1];
                }
                case 3 -> {
                    if (!words[0].equals("@" + telegramBotName)) {
                        return next == null ? Optional.empty() : next.parse(words, chatId);
                    }

                    commandName = words[1];
                    url = words[2];
                }
                default -> {
                    return next == null ? Optional.empty() : next.parse(words, chatId);
                }
            }

            TelegramBotCommandType type = nameToType.get(commandName);
            TelegramBotCommand command = typeToTwoWordCommand.get(type);

            return Optional.ofNullable(command.execute(url, chatId));
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }
}
