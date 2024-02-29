package edu.java.client;

import edu.java.dto.response.GitHubResponse;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class GitHubClient implements SiteAPIClient {
    private static final String PATH_PATTERN = "/repos/\\.+/\\.+";
    private final WebClient webClient;

    public GitHubResponse getInfo(String user, String repo) {
        return webClient.get()
            .uri("repos/{user}/{repo}", user, repo)
            .retrieve()
            .bodyToMono(GitHubResponse.class)
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
            .bodyToMono(GitHubResponse.class)
            .block();
    }
}
