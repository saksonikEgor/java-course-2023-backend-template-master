package edu.java.configuration;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfiguration(
    @NotEmpty
    String telegramBotToken,
    @NotEmpty
    String telegramBotName
) {
    @Bean
    public String telegramBotToken() {
        return telegramBotToken;
    }

    @Bean
    public String telegramBotName() {
        return telegramBotName;
    }
}
