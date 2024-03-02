package edu.java.client;

import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.request.RemoveLinkRequest;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class ScrapperClient {
    private static final String TG_CHAT_ID_HEADER_NAME = "Tg-Chat-Id";
    private final WebClient webClient;

    public void registerChat(long chatId) {
        webClient.post()
            .uri("/tg-chat{id}", chatId)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }

    public void removeChat(long chatId) {
        webClient.delete()
            .uri("/tg-chat/{id}", chatId)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }

    @SuppressWarnings("MultipleStringLiterals")
    public ListLinksResponse getLinks(long chatId) {
        return webClient.get()
            .uri("/links")
            .header(TG_CHAT_ID_HEADER_NAME, String.valueOf(chatId))
            .retrieve()
            .bodyToMono(ListLinksResponse.class)
            .block();
    }

    @SuppressWarnings("MultipleStringLiterals")
    public LinkResponse addLink(long chatId, AddLinkRequest request) {
        return webClient.post()
            .uri("/links")
            .header(TG_CHAT_ID_HEADER_NAME, String.valueOf(chatId))
            .bodyValue(request)
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .block();
    }

    @SuppressWarnings("MultipleStringLiterals")
    public LinkResponse deleteLink(long chatId, RemoveLinkRequest request) {
        return webClient.method(HttpMethod.DELETE)
            .uri("/links")
            .header(TG_CHAT_ID_HEADER_NAME, String.valueOf(chatId))
            .bodyValue(request)
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .block();
    }
}
