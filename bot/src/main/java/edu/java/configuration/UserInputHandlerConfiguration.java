package edu.java.configuration;

import edu.java.commands.TelegramBotCommand;
import edu.java.commands.TelegramBotCommandType;
import edu.java.commands.TelegramBotHelpCommand;
import edu.java.commands.TelegramBotListCommand;
import edu.java.commands.TelegramBotStartCommand;
import edu.java.commands.TelegramBotTrackCommand;
import edu.java.commands.TelegramBotUntrackCommand;
import java.util.Map;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class UserInputHandlerConfiguration {
    private final Map<TelegramBotCommandType, TelegramBotCommand> typeToCommand;

    public UserInputHandlerConfiguration(
        TelegramBotHelpCommand helpCommand,
        TelegramBotListCommand listCommand,
        TelegramBotStartCommand startCommand,
        TelegramBotTrackCommand trackCommand,
        TelegramBotUntrackCommand untrackCommand
    ) {
        typeToCommand = Map.of(
            TelegramBotCommandType.HELP, helpCommand,
            TelegramBotCommandType.LIST, listCommand,
            TelegramBotCommandType.START, startCommand,
            TelegramBotCommandType.TRACK, trackCommand,
            TelegramBotCommandType.UNTRACK, untrackCommand
        );
    }
}
