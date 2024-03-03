package edu.java.client;

import edu.java.dto.response.GitHubResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class GitHubClient {
    private final WebClient webClient;

    public GitHubResponse getInfo(String user, String repo) {
        return webClient.get()
            .uri("repos/{user}/{repo}", user, repo)
            .retrieve()
            .bodyToMono(GitHubResponse.class)
            .block();
    }
}
