package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.DeleteMyCommands;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;
import edu.java.configuration.ApplicationConfig;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LinkUpdateTrackerTelegramBot implements TelegramBotWrapper {
    private TelegramBot telegramBot;
    private final ApplicationConfig applicationConfig;

    @Autowired
    public LinkUpdateTrackerTelegramBot(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
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

            String messageText = update.message().text();
            Long chatId = update.message().chat().id();

            if (messageText != null && messageText.equals("/start")) {
                sendMessage(chatId, "Было введено /start");
            } else {
                sendMessage(chatId, "Было введено: " + messageText);
            }
        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    void sendMessage(Long chatId, String text) {
        log.info("Sending message with text: '" + text + "' to charId: " + chatId);
        telegramBot.execute(new SendMessage(chatId, text));
    }

    @Override
    public void close() throws Exception {
        log.info("Closing Telegram bot...");
        telegramBot.removeGetUpdatesListener();
    }
}
