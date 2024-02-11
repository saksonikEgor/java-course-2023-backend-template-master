package edu.java.configuration;

import edu.java.commands.TelegramBotCommandInfo;
import edu.java.commands.TelegramBotCommandType;
import java.util.Map;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

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

    private static final String HELP_COMMAND_SUCCESSFUL_RESPONSE = HELP_COMMAND_NAME + HELP_COMMAND_DEFINITION + "\n" +
        LIST_COMMAND_NAME + LIST_COMMAND_DEFINITION + "\n" +
        START_COMMAND_NAME + START_COMMAND_DEFINITION + "\n" +
        TRACK_COMMAND_NAME + TRACK_COMMAND_DEFINITION + "\n" +
        UNTRACK_COMMAND_NAME + UNTRACK_COMMAND_DEFINITION;
    private static final String HELP_COMMAND_UNSUCCESSFUL_RESPONSE = "Не получилось вывести все доступные команды";
    private static final String LIST_COMMAND_SUCCESSFUL_RESPONSE = "Список отслеживаемых ссылок:\n";
    private static final String LIST_COMMAND_UNSUCCESSFUL_RESPONSE = "Список отслеживаемых ссылок пуст, добавьте " +
        "отслеживаемые ссылки командой /track";
    private static final String START_COMMAND_SUCCESSFUL_RESPONSE = "Вы успешно зарегестрировались, для просмотра " +
        "всех доступных команд введите /help";
    private static final String START_COMMAND_UNSUCCESSFUL_RESPONSE = "Не получилось вас зарегестрировать";
    private static final String TRACK_COMMAND_SUCCESSFUL_RESPONSE = "Добавленная вами ссылка теперь отслеживается";
    private static final String TRACK_COMMAND_UNSUCCESSFUL_RESPONSE = "Не получилось добавить ссылку для отслеживания";
    private static final String UNTRACK_COMMAND_SUCCESSFUL_RESPONSE = "Ссылка больше не отслеживается";
    private static final String UNTRACK_COMMAND_UNSUCCESSFUL_RESPONSE = "Не получилось прекратить отслеживание ссылки";

    private final String wrongInputMessage = "Некорректная команда, для просморта доступных команд введите /help";

    private final Map<TelegramBotCommandType, TelegramBotCommandInfo> infoByType = Map.of(
        TelegramBotCommandType.HELP, new TelegramBotCommandInfo(
            HELP_COMMAND_NAME, HELP_COMMAND_DEFINITION,
            HELP_COMMAND_SUCCESSFUL_RESPONSE,
            HELP_COMMAND_SUCCESSFUL_RESPONSE
        ),
        TelegramBotCommandType.LIST, new TelegramBotCommandInfo(
            LIST_COMMAND_NAME,
            LIST_COMMAND_DEFINITION,
            LIST_COMMAND_SUCCESSFUL_RESPONSE,
            LIST_COMMAND_UNSUCCESSFUL_RESPONSE
        ),
        TelegramBotCommandType.START, new TelegramBotCommandInfo(
            START_COMMAND_NAME,
            START_COMMAND_DEFINITION,
            START_COMMAND_SUCCESSFUL_RESPONSE,
            START_COMMAND_UNSUCCESSFUL_RESPONSE
        ),
        TelegramBotCommandType.TRACK, new TelegramBotCommandInfo(
            TRACK_COMMAND_NAME,
            TRACK_COMMAND_DEFINITION,
            TRACK_COMMAND_SUCCESSFUL_RESPONSE,
            TRACK_COMMAND_UNSUCCESSFUL_RESPONSE
        ),
        TelegramBotCommandType.UNTRACK, new TelegramBotCommandInfo(
            UNTRACK_COMMAND_NAME,
            UNTRACK_COMMAND_DEFINITION,
            UNTRACK_COMMAND_SUCCESSFUL_RESPONSE,
            UNTRACK_COMMAND_UNSUCCESSFUL_RESPONSE
        )
    );

    private final Map<String, TelegramBotCommandType> typeByName = Map.of(
        HELP_COMMAND_NAME, TelegramBotCommandType.HELP,
        LIST_COMMAND_NAME, TelegramBotCommandType.LIST,
        START_COMMAND_NAME, TelegramBotCommandType.START,
        TRACK_COMMAND_NAME, TelegramBotCommandType.TRACK,
        UNTRACK_COMMAND_NAME, TelegramBotCommandType.UNTRACK
    );
}
