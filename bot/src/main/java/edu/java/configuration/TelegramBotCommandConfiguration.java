package edu.java.configuration;

import edu.java.commands.TelegramBotCommand;
import edu.java.commands.TelegramBotCommandInfo;
import edu.java.commands.TelegramBotCommandType;
import java.util.Map;
import edu.java.commands.TelegramBotHelpCommand;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Configuration
public class TelegramBotCommandConfiguration {
    private static final String HELP_COMMAND_NAME = "/help";
    private static final String HELP_COMMAND_DEFINITION = " -> показать все доступные команды";
    private static final String LIST_COMMAND_NAME = "/list";
    private static final String LIST_COMMAND_DEFINITION = " -> показать список отслеживаемые ссылок";
    private static final String START_COMMAND_NAME = "/start";
    private static final String START_COMMAND_DEFINITION = " -> зарегестрировать пользователя";
    private static final String TRACK_COMMAND_NAME = "/track";
    private static final String TRACK_COMMAND_DEFINITION = " -> начать отслеживать ссылки";
    private static final String UNTRACK_COMMAND_NAME = "/untrack";
    private static final String UNTRACK_COMMAND_DEFINITION = " -> прекратить отслеживать ссылки";

    private final Map<TelegramBotCommandType, TelegramBotCommandInfo> infoByType = Map.of(
        TelegramBotCommandType.HELP, new TelegramBotCommandInfo(HELP_COMMAND_NAME, HELP_COMMAND_DEFINITION),
        TelegramBotCommandType.LIST, new TelegramBotCommandInfo(LIST_COMMAND_NAME, LIST_COMMAND_DEFINITION),
        TelegramBotCommandType.START, new TelegramBotCommandInfo(START_COMMAND_NAME, START_COMMAND_DEFINITION),
        TelegramBotCommandType.TRACK, new TelegramBotCommandInfo(TRACK_COMMAND_NAME, TRACK_COMMAND_DEFINITION),
        TelegramBotCommandType.UNTRACK, new TelegramBotCommandInfo(UNTRACK_COMMAND_NAME, UNTRACK_COMMAND_DEFINITION)
    );

    private final Map<String, TelegramBotCommandType> typeByName = Map.of(
        HELP_COMMAND_NAME, TelegramBotCommandType.HELP,
        LIST_COMMAND_NAME, TelegramBotCommandType.LIST,
        START_COMMAND_NAME, TelegramBotCommandType.START,
        TRACK_COMMAND_NAME, TelegramBotCommandType.TRACK,
        UNTRACK_COMMAND_NAME, TelegramBotCommandType.UNTRACK
    );
}
