package edu.java.configuration;

import edu.java.commands.TelegramBotCommandInfo;
import edu.java.commands.TelegramBotCommandType;
import java.util.Map;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramBotCommandConfiguration {
    private final Map<TelegramBotCommandType, TelegramBotCommandInfo> infoByType = Map.of(
        TelegramBotCommandType.HELP, new TelegramBotCommandInfo(
            "/help",
            " -> показать все доступные команды"
        ),
        TelegramBotCommandType.LIST, new TelegramBotCommandInfo(
            "/list",
            " -> показать список отслеживаемые ссылок"
        ),
        TelegramBotCommandType.START, new TelegramBotCommandInfo(
            "/start",
            " -> зарегестрировать пользователя"
        ),
        TelegramBotCommandType.TRACK, new TelegramBotCommandInfo(
            "/track",
            " -> начать отслеживать ссылки"
        ),
        TelegramBotCommandType.UNTRACK, new TelegramBotCommandInfo(
            "/untrack",
            " -> прекратить отслеживать ссылки"
        )
    );

    public Map<TelegramBotCommandType, TelegramBotCommandInfo> getInfoByType() {
        return infoByType;
    }
}
