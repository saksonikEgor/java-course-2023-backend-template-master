package edu.java.client;

import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.request.RemoveLinkRequest;
import edu.java.dto.response.APIErrorResponse;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;
import edu.java.exception.ScrapperAPIException;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class ScrapperClient {
    private static final String TG_CHAT_ID_HEADER_NAME = "Tg-Chat-Id";
    private static final Duration GETTING_RESPONSE_TIMEOUT = Duration.ofSeconds(5);
    private final WebClient webClient;

    public void registerChat(long chatId) {
        webClient.post()
            .uri("/tg-chat/{id}", chatId)
            .retrieve()
            .onStatus(
                HttpStatusCode::isError,
                clientResponse -> clientResponse.bodyToMono(APIErrorResponse.class)
                    .map(error -> new ScrapperAPIException(error.description()))
            )
            .bodyToMono(Void.class)
            .block(GETTING_RESPONSE_TIMEOUT);
    }

    public void removeChat(long chatId) {
        webClient.delete()
            .uri("/tg-chat/{id}", chatId)
            .retrieve()
            .onStatus(
                HttpStatusCode::isError,
                clientResponse -> clientResponse.bodyToMono(APIErrorResponse.class)
                    .map(error -> new ScrapperAPIException(error.description()))
            )
            .bodyToMono(Void.class)
            .block(GETTING_RESPONSE_TIMEOUT);
    }

    @SuppressWarnings("MultipleStringLiterals")
    public ListLinksResponse getLinks(long chatId) {
        return webClient.get()
            .uri("/links")
            .header(TG_CHAT_ID_HEADER_NAME, String.valueOf(chatId))
            .retrieve()
            .onStatus(
                HttpStatusCode::isError,
                clientResponse -> clientResponse.bodyToMono(APIErrorResponse.class)
                    .map(error -> new ScrapperAPIException(error.description()))
            )
            .bodyToMono(ListLinksResponse.class)
            .block(GETTING_RESPONSE_TIMEOUT);
    }

    @SuppressWarnings("MultipleStringLiterals")
    public LinkResponse addLink(long chatId, AddLinkRequest request) {
        return webClient.post()
            .uri("/links")
            .header(TG_CHAT_ID_HEADER_NAME, String.valueOf(chatId))
            .bodyValue(request)
            .retrieve()
            .onStatus(
                HttpStatusCode::isError,
                clientResponse -> clientResponse.bodyToMono(APIErrorResponse.class)
                    .map(error -> new ScrapperAPIException(error.description()))
            )
            .bodyToMono(LinkResponse.class)
            .block(GETTING_RESPONSE_TIMEOUT);
    }

    @SuppressWarnings("MultipleStringLiterals")
    public LinkResponse deleteLink(long chatId, RemoveLinkRequest request) {
        return webClient.method(HttpMethod.DELETE)
            .uri("/links")
            .header(TG_CHAT_ID_HEADER_NAME, String.valueOf(chatId))
            .bodyValue(request)
            .retrieve()
            .onStatus(
                HttpStatusCode::isError,
                clientResponse -> clientResponse.bodyToMono(APIErrorResponse.class)
                    .map(error -> new ScrapperAPIException(error.description()))
            )
            .bodyToMono(LinkResponse.class)
            .block(GETTING_RESPONSE_TIMEOUT);
    }
}
