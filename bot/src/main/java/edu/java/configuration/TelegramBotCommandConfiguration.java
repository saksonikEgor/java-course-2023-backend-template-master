package edu.java.configuration;

import edu.java.commands.TelegramBotCommandInfo;
import edu.java.commands.TelegramBotCommandType;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramBotCommandConfiguration {
    private static final String HELP_COMMAND_NAME = "/help";
    private static final String LIST_COMMAND_NAME = "/list";
    private static final String START_COMMAND_NAME = "/start";
    private static final String TRACK_COMMAND_NAME = "/track";
    private static final String UNTRACK_COMMAND_NAME = "/untrack";

    private static final String HELP_COMMAND_DEFINITION = "Показать все доступные команды";
    private static final String LIST_COMMAND_DEFINITION = "Показать список отслеживаемые ссылок";
    private static final String START_COMMAND_DEFINITION = "Зарегестрировать пользователя";
    private static final String TRACK_COMMAND_DEFINITION = "Начать отслеживать ссылки";
    private static final String UNTRACK_COMMAND_DEFINITION = "Прекратить отслеживать ссылки";

    private static final String TICK = "✅";
    private static final String CROSS = "❌";

    @SuppressWarnings("MultipleStringLiterals")
    private static final String HELP_COMMAND_SUCCESSFUL_RESPONSE =
        "Бот предназначен для отслеживания изменений по ссылкам.\n"
            + "На данный момент реализован функционал для сайтов [GitHub](https://github.com/) и "
            + "[StackOverflow](https://stackoverflow.com/) \n\n"
            + HELP_COMMAND_NAME + " -> " + HELP_COMMAND_DEFINITION + "\n"
            + LIST_COMMAND_NAME + " -> " + LIST_COMMAND_DEFINITION + "\n"
            + START_COMMAND_NAME + " -> " + START_COMMAND_DEFINITION;
    private static final String HELP_COMMAND_UNSUCCESSFUL_RESPONSE = "Не получилось вывести все доступные команды";
    private static final String LIST_COMMAND_SUCCESSFUL_RESPONSE = "Список отслеживаемых ссылок:\n";
    private static final String LIST_COMMAND_UNSUCCESSFUL_RESPONSE = "Список отслеживаемых ссылок пуст, добавьте "
        + "отслеживаемые ссылки командой " + TRACK_COMMAND_NAME;
    private static final String
        START_COMMAND_SUCCESSFUL_RESPONSE = TICK + "Вы успешно зарегестрировались, для просмотра "
        + "всех доступных команд введите " + HELP_COMMAND_NAME;
    private static final String START_COMMAND_UNSUCCESSFUL_RESPONSE = CROSS + "Не получилось вас зарегестрировать";
    private static final String TRACK_COMMAND_SUCCESSFUL_RESPONSE = TICK + "Введенная вами ссылка теперь отслеживается";
    private static final String
        TRACK_COMMAND_UNSUCCESSFUL_RESPONSE = CROSS + "Не получилось добавить ссылку для отслеживания";
    private static final String UNTRACK_COMMAND_SUCCESSFUL_RESPONSE =
        TICK + "Введенная вами ссылка перестала быть отслеживаемой";
    private static final String
        UNTRACK_COMMAND_UNSUCCESSFUL_RESPONSE = CROSS + "Не получилось прекратить отслеживание ссылки";

    @Bean
    public String wrongInputMessage() {
        return CROSS + "Некорректная команда, для просморта доступных команд введите " + HELP_COMMAND_NAME;
    }

    @Bean
    public String fatalExceptionMessage() {
        return CROSS + "Не удалось выполнить запрос. Ошибка на стороне сервера, попробуйте позже.";
    }

    @Bean
    public String cross() {
        return CROSS;
    }

    @Bean
    public Map<TelegramBotCommandType, TelegramBotCommandInfo> typeToInfo() {
        return Map.of(
            TelegramBotCommandType.HELP, new TelegramBotCommandInfo(
                HELP_COMMAND_NAME,
                HELP_COMMAND_DEFINITION,
                HELP_COMMAND_SUCCESSFUL_RESPONSE,
                HELP_COMMAND_UNSUCCESSFUL_RESPONSE
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
    }

    @Bean
    public Map<String, TelegramBotCommandType> nameToType() {
        return Map.of(
            HELP_COMMAND_NAME, TelegramBotCommandType.HELP,
            LIST_COMMAND_NAME, TelegramBotCommandType.LIST,
            START_COMMAND_NAME, TelegramBotCommandType.START,
            TRACK_COMMAND_NAME, TelegramBotCommandType.TRACK,
            UNTRACK_COMMAND_NAME, TelegramBotCommandType.UNTRACK
        );
    }
}
