package edu.java.communication.httpClient;

import edu.java.communication.BotMessageSender;
import edu.java.dto.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class BotClient implements BotMessageSender {
    private final WebClient webClient;

    @Override
    public void sendUpdate(LinkUpdateRequest request) {
        webClient.post()
            .uri("/updates")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }
}
