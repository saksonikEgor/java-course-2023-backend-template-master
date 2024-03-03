package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.commands.TelegramBotCommandInfo;
import edu.java.commands.TelegramBotCommandType;
import edu.java.configuration.ApplicationConfiguration;
import edu.java.handler.ChatInputHandler;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LinkUpdateTrackerTelegramBot implements TelegramBotWrapper {
    private final ApplicationConfiguration applicationConfiguration;
    private final ChatInputHandler inputHandler;
    private final Map<TelegramBotCommandType, TelegramBotCommandInfo> typeToInfo;
    private TelegramBot telegramBot;

    public LinkUpdateTrackerTelegramBot(
        ApplicationConfiguration applicationConfiguration,
        ChatInputHandler inputHandler,
        Map<TelegramBotCommandType, TelegramBotCommandInfo> typeToInfo
    ) {
        this.applicationConfiguration = applicationConfiguration;
        this.inputHandler = inputHandler;
        this.typeToInfo = typeToInfo;
    }

    @Override
    public void start() {
        log.info("Starting Telegram bot with token: " + applicationConfiguration.telegramToken());

        telegramBot = new TelegramBot(applicationConfiguration.telegramToken());
        telegramBot.execute(getAllTelegramBotCommands());
        telegramBot.setUpdatesListener(this);
    }

    private SetMyCommands getAllTelegramBotCommands() {
        return new SetMyCommands(
            new BotCommand(
                typeToInfo.get(TelegramBotCommandType.TRACK).commandName(),
                typeToInfo.get(TelegramBotCommandType.TRACK).commandDefinition()
            ),
            new BotCommand(
                typeToInfo.get(TelegramBotCommandType.UNTRACK).commandName(),
                typeToInfo.get(TelegramBotCommandType.UNTRACK).commandDefinition()
            ),
            new BotCommand(
                typeToInfo.get(TelegramBotCommandType.LIST).commandName(),
                typeToInfo.get(TelegramBotCommandType.LIST).commandDefinition()
            ),
            new BotCommand(
                typeToInfo.get(TelegramBotCommandType.HELP).commandName(),
                typeToInfo.get(TelegramBotCommandType.HELP).commandDefinition()
            )
        );
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            log.info("Process update: {}", update);
            sendMessage(inputHandler.handle(update));
        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void sendMessage(SendMessage message) {
        telegramBot.execute(message);
    }

    @Override
    public void close() throws Exception {
        log.info("Closing Telegram bot...");
        telegramBot.removeGetUpdatesListener();
    }
}
