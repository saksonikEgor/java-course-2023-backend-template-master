package edu.java.client;

import edu.java.dto.response.StackOverflowResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class StackOverflowClient implements SiteAPIClient {
    private final WebClient webClient;

    public StackOverflowResponse getInfo(long id) {
        return webClient.get()
            .uri(uri -> uri.path("/questions/{id}")
                .queryParam("site", "stackoverflow")
                .build(id))
            .retrieve()
            .bodyToMono(StackOverflowResponse.class)
            .block();
    }

    @Override
    public void call(Map<String, String> info) throws Exception {
        getInfo(Long.parseLong(info.get("question_id")));
    }
}
