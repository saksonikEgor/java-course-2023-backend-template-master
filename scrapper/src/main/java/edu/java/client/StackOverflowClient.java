package edu.java.client;

import edu.java.dto.response.StackOverflowResponse;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class StackOverflowClient implements SiteAPIClient {
    private static final String PATH_PATTERN = "/questions/\\d+?site=stackoverflow";
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
    public boolean matchPath(String path) {
        return Pattern.matches(PATH_PATTERN, path);
    }

    @Override
    public void call(String path) throws Exception {
        webClient.get()
            .uri(path)
            .retrieve()
            .bodyToMono(StackOverflowResponse.class)
            .block();
    }
}
