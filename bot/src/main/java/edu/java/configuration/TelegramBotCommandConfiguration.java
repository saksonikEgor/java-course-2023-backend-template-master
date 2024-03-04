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

    private static final String HELP_COMMAND_DEFINITION = " -> показать все доступные команды";
    private static final String LIST_COMMAND_DEFINITION = " -> показать список отслеживаемые ссылок";
    private static final String START_COMMAND_DEFINITION = " -> зарегестрировать пользователя";
    private static final String TRACK_COMMAND_DEFINITION = " -> начать отслеживать ссылки";
    private static final String UNTRACK_COMMAND_DEFINITION = " -> прекратить отслеживать ссылки";

    private static final String TICK = "✅";
    private static final String CROSS = "❌";

    private static final String HELP_COMMAND_SUCCESSFUL_RESPONSE = HELP_COMMAND_NAME + HELP_COMMAND_DEFINITION + "\n"
        + LIST_COMMAND_NAME + LIST_COMMAND_DEFINITION + "\n"
        + START_COMMAND_NAME + START_COMMAND_DEFINITION + "\n"
        + TRACK_COMMAND_NAME + TRACK_COMMAND_DEFINITION + "\n"
        + UNTRACK_COMMAND_NAME + UNTRACK_COMMAND_DEFINITION;
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
    public String notRegisteredErrorMessage() {
        return CROSS + "Вы не зарегестрированы. Для регистрации введите " + START_COMMAND_NAME;
    }

    @Bean
    public String wrongLinkFormatMessage() {
        return CROSS + "Некорректный формат ссылки, попробуйте еще раз";
    }

    @Bean
    public String successfulTrackingMessage() {
        return TICK + "Введенная вами ссылка теперь отслеживается. Для просмотра всех отслеживаемых ссылок введите "
            + LIST_COMMAND_NAME;
    }

    @Bean
    public String successfulUntrackingMessage() {
        return TICK +
            "Введенная вами ссылка перестала быть отслеживаемой. Для просмотра всех отслеживаемых ссылок введите "
            + LIST_COMMAND_NAME;
    }

    @Bean
    public String linkIsAlreadyTrackedMessage() {
        return CROSS +
            "Введенная вами ссылка уже была добавлена ранее. Для просмотра всех отслеживаемых ссылок введите "
            + LIST_COMMAND_NAME;
    }

    @Bean
    public String linkIsNotTrackingMessage() {
        return CROSS + "Введенная вами ссылка не отслеживалась. Для просмотра всех отслеживаемых ссылок введите "
            + LIST_COMMAND_NAME;
    }

    @Bean
    public String fatalExceptionMessage() {
        return CROSS + "Не удалось выполнить запрос. Ошибка на стороне сервера, попробуйте позже.";
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
