package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.commands.TelegramBotCommandInfo;
import edu.java.commands.TelegramBotCommandType;
import edu.java.configuration.ApplicationConfig;
import edu.java.configuration.TelegramBotCommandConfiguration;
import edu.java.handler.UserInputHandler;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LinkUpdateTrackerTelegramBot implements TelegramBotWrapper {
    private final ApplicationConfig applicationConfig;
    private final UserInputHandler inputHandler;
    private TelegramBot telegramBot;
    private final Map<TelegramBotCommandType, TelegramBotCommandInfo> infoByType;

    @Autowired
    public LinkUpdateTrackerTelegramBot(
        ApplicationConfig applicationConfig, UserInputHandler inputHandler,
        TelegramBotCommandConfiguration commandConfiguration
    ) {
        this.applicationConfig = applicationConfig;
        this.inputHandler = inputHandler;
        infoByType = commandConfiguration.getInfoByType();
    }

    @Override
    public void start() {
        log.info("Starting Telegram bot with token: " + applicationConfig.telegramToken());

        telegramBot = new TelegramBot(applicationConfig.telegramToken());
        telegramBot.execute(getAllTelegramBotCommands());
        telegramBot.setUpdatesListener(this);
    }

    private SetMyCommands getAllTelegramBotCommands() {
        return new SetMyCommands(
            infoByType.values()
                .stream()
                .map(info -> new BotCommand(info.commandName(), info.commandDefinition()))
                .toArray(BotCommand[]::new)
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
