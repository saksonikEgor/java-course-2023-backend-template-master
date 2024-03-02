package edu.java.configuration;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotNull
    Scheduler scheduler
) {
    @Bean
    public Duration schedulerInterval() {
        return scheduler.interval();
    }

    @Bean
    public Duration linkCheckInterval() {
        return scheduler.linkCheckInterval();
    }

    public record Scheduler(
        boolean enable,
        @NotNull Duration interval,
        @NotNull Duration linkCheckInterval,
        @NotNull Duration forceCheckDelay
    ) {
    }
}
