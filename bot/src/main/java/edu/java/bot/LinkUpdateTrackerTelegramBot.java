package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.DeleteMyCommands;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.configuration.ApplicationConfig;
import edu.java.configuration.TelegramBotCommandConfiguration;
import edu.java.handler.UserInputHandler;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LinkUpdateTrackerTelegramBot implements TelegramBotWrapper {
    private final ApplicationConfig applicationConfig;
    private final UserInputHandler inputHandler;
    private TelegramBot telegramBot;
    private final String trackCommandName;
    private final String untrackCommandName;
    private final String listCommandName;
    private final String helpCommandName;

    @Autowired
    public LinkUpdateTrackerTelegramBot(
        ApplicationConfig applicationConfig, UserInputHandler inputHandler,
        TelegramBotCommandConfiguration commandConfiguration
    ) {
        this.applicationConfig = applicationConfig;
        this.inputHandler = inputHandler;
        trackCommandName = commandConfiguration.getTrackCommandName();
        untrackCommandName = commandConfiguration.getUntrackCommandName();
        listCommandName = commandConfiguration.getListCommandName();
        helpCommandName = commandConfiguration.getHelpCommandName();
    }

    @Override
    public void start() {
        log.info("Starting Telegram bot with token: " + applicationConfig.telegramToken());

        telegramBot = new TelegramBot(applicationConfig.telegramToken());
        telegramBot.execute(new DeleteMyCommands());
        telegramBot.setUpdatesListener(this);
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
        message.replyMarkup(new ReplyKeyboardMarkup(new String[][] {
            {trackCommandName, untrackCommandName},
            {listCommandName, helpCommandName}
        }).resizeKeyboard(true));
        telegramBot.execute(message);
    }

    @Override
    public void close() throws Exception {
        log.info("Closing Telegram bot...");
        telegramBot.removeGetUpdatesListener();
    }
}
