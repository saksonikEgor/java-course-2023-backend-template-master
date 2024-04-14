package edu.java.kafka.consumer;

import edu.java.bot.TelegramBotWrapper;
import edu.java.dto.request.LinkUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
@Slf4j
public class ScrapperQueueConsumer {
    private final TelegramBotWrapper telegramBotWrapper;
    private final String dltTopicName;
    private static final String DLT_TOPIC_SUFFIX = "_dlq";

    @RetryableTopic(attempts = "1",
                    kafkaTemplate = "kafkaTemplate",
                    dltTopicSuffix = DLT_TOPIC_SUFFIX,
                    dltStrategy = DltStrategy.FAIL_ON_ERROR)
    @KafkaListener(topics = "${app.kafka.topic.name}",
                   groupId = "${spring.kafka.consumer.group-id}",
                   concurrency = "${spring.kafka.listener.concurrency:1}")
    public void listen(@Valid LinkUpdateRequest request) {
        log.info("Received link update: {}", request);
        telegramBotWrapper.sendMessages(request);
    }

    @DltHandler
    public void handleDlt(LinkUpdateRequest request) {
        log.info("Sending link update request to topic: {}", dltTopicName);
    }
}
