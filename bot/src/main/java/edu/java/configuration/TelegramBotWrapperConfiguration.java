package edu.java.configuration;

import edu.java.bot.TelegramBotWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class TelegramBotWrapperConfiguration {
    private final TelegramBotWrapper telegramBotWrapper;

    @Bean
    public TelegramBotWrapper telegramBotWrapper() {
        telegramBotWrapper.start();
        return telegramBotWrapper;
    }
}
