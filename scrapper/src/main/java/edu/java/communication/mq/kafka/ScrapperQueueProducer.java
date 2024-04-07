package edu.java.communication.mq.kafka;

import edu.java.communication.BotMessageSender;
import edu.java.configuration.ApplicationConfig;
import edu.java.dto.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScrapperQueueProducer implements BotMessageSender {
    private final KafkaTemplate<Long, LinkUpdateRequest> kafkaTemplate;
    private final NewTopic topic;

    @Override
    public void sendUpdate(LinkUpdateRequest request) {
        CompletableFuture<SendResult<Long, LinkUpdateRequest>> future = kafkaTemplate.send(
            topic.name(),
            request.id(),
            request
        );

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Error while sending message to Kafka", ex);
            }
        });

//        try {
//            kafkaTemplate.send(
//                topic.name(),
//                request.id(),
//                request
//            );
//        } catch (Exception e) {
//            log.error("Error while sending message to Kafka", e);
//        }
    }
}
