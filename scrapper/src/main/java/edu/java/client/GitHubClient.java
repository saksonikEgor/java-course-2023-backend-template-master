package edu.java.client;

import edu.java.dto.response.GitHubResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class GitHubClient implements SiteAPIClient {
    private final WebClient webClient;

    public GitHubResponse getInfo(String user, String repo) {
        return webClient.get()
            .uri("repos/{user}/{repo}/commits", user, repo)
            .retrieve()
            .bodyToMono(GitHubResponse.class)
            .block();
    }

    @Override
    public void call(Map<String, String> info) throws Exception {
        getInfo(info.get("user"), info.get("repo"));
    }
}
