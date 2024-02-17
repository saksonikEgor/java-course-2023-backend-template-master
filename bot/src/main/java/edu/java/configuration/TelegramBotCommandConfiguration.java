package edu.java.configuration;

import edu.java.commands.TelegramBotCommandInfo;
import edu.java.commands.TelegramBotCommandType;
import java.util.Map;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class TelegramBotCommandConfiguration {
    private static final String HELP_COMMAND_DEFINITION = " -> показать все доступные команды";
    private static final String LIST_COMMAND_DEFINITION = " -> показать список отслеживаемые ссылок";
    private static final String START_COMMAND_DEFINITION = " -> зарегестрировать пользователя";
    private static final String TRACK_COMMAND_DEFINITION = " -> начать отслеживать ссылки";
    private static final String UNTRACK_COMMAND_DEFINITION = " -> прекратить отслеживать ссылки";
    private static final String TICK = "✅";
    private static final String CROSS = "❌";
    private final String helpCommandName = "/help";
    private final String listCommandName = "/list";
    private final String startCommandName = "/start";
    private final String trackCommandName = "/track";
    private final String untrackCommandName = "/untrack";
    private final String helpCommandSuccessfulResponse = helpCommandName + HELP_COMMAND_DEFINITION + "\n" +
        listCommandName + LIST_COMMAND_DEFINITION + "\n" +
        startCommandName + START_COMMAND_DEFINITION + "\n" +
        trackCommandName + TRACK_COMMAND_DEFINITION + "\n" +
        untrackCommandName + UNTRACK_COMMAND_DEFINITION;
    private final String helpCommandUnsuccessfulResponse = "Не получилось вывести все доступные команды";
    private final String listCommandSuccessfulResponse = "Список отслеживаемых ссылок:\n";
    private final String listCommandUnsuccessfulResponse = "Список отслеживаемых ссылок пуст, добавьте " +
        "отслеживаемые ссылки командой " + trackCommandName;
    private final String startCommandSuccessfulResponse = TICK + "Вы успешно зарегестрировались, для просмотра " +
        "всех доступных команд введите " + helpCommandName;
    private final Map<TelegramBotCommandType, TelegramBotCommandInfo> infoByType = Map.of(
        TelegramBotCommandType.HELP, new TelegramBotCommandInfo(
            helpCommandName,
            HELP_COMMAND_DEFINITION,
            helpCommandSuccessfulResponse,
            helpCommandSuccessfulResponse
        ),
        TelegramBotCommandType.LIST, new TelegramBotCommandInfo(
            listCommandName,
            LIST_COMMAND_DEFINITION,
            listCommandSuccessfulResponse,
            listCommandUnsuccessfulResponse
        ),
        TelegramBotCommandType.START, new TelegramBotCommandInfo(
            startCommandName,
            START_COMMAND_DEFINITION,
            startCommandSuccessfulResponse,
            startCommandUnsuccessfulResponse
        ),
        TelegramBotCommandType.TRACK, new TelegramBotCommandInfo(
            trackCommandName,
            TRACK_COMMAND_DEFINITION,
            trackCommandSuccessfulResponse,
            trackCommandUnsuccessfulResponse
        ),
        TelegramBotCommandType.UNTRACK, new TelegramBotCommandInfo(
            untrackCommandName,
            UNTRACK_COMMAND_DEFINITION,
            untrackCommandSuccessfulResponse,
            untrackCommandUnsuccessfulResponse
        )
    );
    private final String startCommandUnsuccessfulResponse = CROSS + "Не получилось вас зарегестрировать";
    private final String trackCommandSuccessfulResponse = "Введите ссылку которую хотите начать отслеживать";
    private final String trackCommandUnsuccessfulResponse = CROSS + "Не получилось добавить ссылку для отслеживания";
    private final String untrackCommandSuccessfulResponse =
        "Введите ссылку которую хотите прекратить отслеживать";
    private final String untrackCommandUnsuccessfulResponse = CROSS + "Не получилось прекратить отслеживание ссылки";
    private final String wrongInputMessage =
        CROSS + "Некорректная команда, для просморта доступных команд введите " + helpCommandName;
    private final String notAuthenticatedErrorMessage =
        CROSS + "Вы не зарегестрированы. Для регистрации введите " + startCommandName;
    private final String wrongLinkFormatMessage = CROSS + "Некорректный формат ссылки, попробуйте еще раз";
    private final String successfulTrackingMessage =
        TICK + "Введенная вами ссылка теперь отслеживается. Для просмотра всех отслеживаемых ссылок введите " +
            listCommandName;
    private final String successfulUntrackingMessage =
        TICK + "Введенная вами ссылка перестала быть отслеживаемой. Для просмотра всех отслеживаемых ссылок введите " +
            listCommandName;
    private final String linkIsAlreadyTrackedMessage =
        CROSS + "Введенная вами ссылка уже была добавлена ранее. Для просмотра всех отслеживаемых ссылок введите " +
            listCommandName;
    private final String linkIsNotTrackingMessage =
        CROSS + "Введенная вами ссылка не отслеживалась. Для просмотра всех отслеживаемых ссылок введите " +
            listCommandName;
    private final Map<String, TelegramBotCommandType> typeByName = Map.of(
        helpCommandName, TelegramBotCommandType.HELP,
        listCommandName, TelegramBotCommandType.LIST,
        startCommandName, TelegramBotCommandType.START,
        trackCommandName, TelegramBotCommandType.TRACK,
        untrackCommandName, TelegramBotCommandType.UNTRACK
    );
}
