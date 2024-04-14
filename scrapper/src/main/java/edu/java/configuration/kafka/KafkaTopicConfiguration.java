package edu.java.configuration.kafka;

import edu.java.configuration.ApplicationConfiguration;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KafkaTopicConfiguration {
    private final ApplicationConfiguration.KafkaProperties.KafkaTopicProperties kafkaTopicProperties;

    @Bean
    public NewTopic topic() {
        return new NewTopic(
            kafkaTopicProperties.name(),
            kafkaTopicProperties.partitions(),
            kafkaTopicProperties.replicas()
        );
    }
}
