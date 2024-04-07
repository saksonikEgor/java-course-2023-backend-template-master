package edu.java.configuration;

import edu.java.communication.BotMessageSender;
import edu.java.communication.httpClient.BotClient;
import edu.java.communication.mq.kafka.ScrapperQueueProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BotMessageSenderConfiguration {
    @Bean
    public BotMessageSender botMessageSender(
        Boolean userQueue, ScrapperQueueProducer scrapperQueueProducer,
        BotClient botClient
    ) {
        return userQueue ? scrapperQueueProducer : botClient;
    }
}
