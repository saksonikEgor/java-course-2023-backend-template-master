package edu.java.configuration;

import edu.java.bot.LinkUpdateTrackerTelegramBot;
import edu.java.bot.TelegramBotWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class TelegramBotWrapperConfiguration {
    private final TelegramBotWrapper telegramBotWrapper;

    @Autowired
    public TelegramBotWrapperConfiguration(LinkUpdateTrackerTelegramBot linkUpdateTrackerTelegramBot) {
        telegramBotWrapper = linkUpdateTrackerTelegramBot;
    }

    @Bean
    public TelegramBotWrapper telegramBotWrapper() {
        telegramBotWrapper.start();
        return telegramBotWrapper;
    }
}
