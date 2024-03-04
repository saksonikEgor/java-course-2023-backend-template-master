package edu.java.configuration;

import edu.java.commands.TelegramBotCommand;
import edu.java.commands.TelegramBotCommandType;
import edu.java.commands.TelegramBotHelpCommand;
import edu.java.commands.TelegramBotListCommand;
import edu.java.commands.TelegramBotStartCommand;
import edu.java.commands.TelegramBotTrackCommand;
import edu.java.commands.TelegramBotUntrackCommand;
import edu.java.handler.parser.MessageExecutor;
import edu.java.handler.parser.OneWordMessageExecutor;
import edu.java.handler.parser.TwoWordMessageExecutor;
import java.util.Map;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ChatInputHandlerConfiguration {
    private final Map<TelegramBotCommandType, TelegramBotCommand> typeToOneWordCommand;
    private final Map<TelegramBotCommandType, TelegramBotCommand> typeToTwoWordCommand;
    private final Map<String, TelegramBotCommandType> nameToType;
    private final String telegramBotName;

    public ChatInputHandlerConfiguration(
        TelegramBotHelpCommand helpCommand,
        TelegramBotListCommand listCommand,
        TelegramBotStartCommand startCommand,
        TelegramBotTrackCommand trackCommand,
        TelegramBotUntrackCommand untrackCommand,
        Map<String, TelegramBotCommandType> nameToType,
        String telegramBotName
    ) {
        this.telegramBotName = telegramBotName;
        typeToOneWordCommand = Map.of(
            TelegramBotCommandType.HELP, helpCommand,
            TelegramBotCommandType.LIST, listCommand,
            TelegramBotCommandType.START, startCommand
        );
        typeToTwoWordCommand = Map.of(
            TelegramBotCommandType.TRACK, trackCommand,
            TelegramBotCommandType.UNTRACK, untrackCommand
        );
        this.nameToType = nameToType;
    }

    @Bean
    public MessageExecutor chainOfParsers() {
        return new OneWordMessageExecutor(
            new TwoWordMessageExecutor(null, nameToType, typeToTwoWordCommand, telegramBotName),
            nameToType,
            typeToOneWordCommand
        );
    }
}
