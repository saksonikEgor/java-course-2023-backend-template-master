package edu.java.configuration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfiguration(
    @NotNull
    Scheduler scheduler,
    @NotNull
    AccessType databaseAccessType,
    @NotNull
    Boolean useQueue,
    KafkaProperties kafka
) {
    @Bean
    public Duration schedulerInterval() {
        return scheduler.interval();
    }

    @Bean
    public Duration linkCheckInterval() {
        return scheduler.linkCheckInterval();
    }

    @Bean
    public KafkaProperties.KafkaTopicProperties kafkaTopicProperties() {
        return kafka.topic;
    }

    @Bean
    public Boolean useQueue() {
        return useQueue;
    }

    public record Scheduler(
        boolean enable,
        @NotNull Duration interval,
        @NotNull Duration linkCheckInterval,
        @NotNull Duration forceCheckDelay
    ) {
    }

    public enum AccessType {
        JDBC, JPA, JOOQ
    }

    public record KafkaProperties(
        @NotNull
        ApplicationConfiguration.KafkaProperties.KafkaTopicProperties topic
    ) {
        public record KafkaTopicProperties(
            @NotBlank
            String name,
            @NotNull
            Integer partitions,
            @NotNull
            Short replicas
        ) {
        }
    }
}
