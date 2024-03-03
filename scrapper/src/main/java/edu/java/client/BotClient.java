package edu.java.client;

import edu.java.dto.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class BotClient {
    private final WebClient webClient;

    public void updateLink(LinkUpdateRequest request) {
        webClient.post()
            .uri("/updates")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }
}
