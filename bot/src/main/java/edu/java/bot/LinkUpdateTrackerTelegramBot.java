package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.DeleteMyCommands;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.configuration.ApplicationConfig;
import edu.java.handler.UserInputHandler;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LinkUpdateTrackerTelegramBot implements TelegramBotWrapper {
    private TelegramBot telegramBot;
    private final ApplicationConfig applicationConfig;
    private final UserInputHandler inputHandler;

    @Autowired
    public LinkUpdateTrackerTelegramBot(ApplicationConfig applicationConfig, UserInputHandler inputHandler) {
        this.applicationConfig = applicationConfig;
        this.inputHandler = inputHandler;
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
        telegramBot.execute(message);
    }

    @Override
    public void close() throws Exception {
        log.info("Closing Telegram bot...");
        telegramBot.removeGetUpdatesListener();
    }
}
