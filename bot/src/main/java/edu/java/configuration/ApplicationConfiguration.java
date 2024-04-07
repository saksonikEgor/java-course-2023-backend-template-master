package edu.java.configuration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfiguration(
    @NotEmpty
    String telegramBotToken,
    @NotEmpty
    String telegramBotName,
    KafkaProperties kafka
) {
    @Bean
    public String telegramBotToken() {
        return telegramBotToken;
    }

    @Bean
    public String telegramBotName() {
        return telegramBotName;
    }

    @Bean
    public String topicName() {
        return kafka.topic.name;
    }

    public record KafkaProperties(
        @NotNull
        ApplicationConfiguration.KafkaProperties.KafkaTopicProperties topic
    ) {
        public record KafkaTopicProperties(
            @NotBlank
            String name
        ) {
        }
    }
}
