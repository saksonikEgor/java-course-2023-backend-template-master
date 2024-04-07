package edu.java.kafka.consumer;

import edu.java.bot.TelegramBotWrapper;
import edu.java.dto.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScrapperQueueConsumer {
    private final TelegramBotWrapper telegramBotWrapper;

    @KafkaListener(topics = "${app.kafka.topic.name}",
                   groupId = "${spring.kafka.consumer.group-id}",
                   concurrency = "${spring.kafka.listener.concurrency:1}")
    public void listen(LinkUpdateRequest request) {
        log.info("Received link update: {}", request);
        telegramBotWrapper.sendMessages(request);
    }
}
