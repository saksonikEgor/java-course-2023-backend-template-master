package edu.java.configuration;

import edu.java.client.BotClient;
import edu.java.client.GitHubClient;
import edu.java.client.SiteAPIClient;
import edu.java.client.StackOverflowClient;
import edu.java.dto.model.BaseURL;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Setter
public class ClientConfiguration {
    @Value("${github.base-url:https://api.github.com}")
    private String gitHubBaseURL;
    @Value("${stackoverflow.base-url:https://api.stackexchange.com}")
    private String stackOverflowBaseURL;
    @Value("${bot.base-url:http://localhost:8090}")
    private String botBaseURL;
    @Getter
    private final Map<String, BaseURL> stringToBaseUrl = Map.of(
        gitHubBaseURL, BaseURL.GITHUB,
        stackOverflowBaseURL, BaseURL.STACKOVERFLOW
    );
    @Getter
    private final Map<BaseURL, SiteAPIClient> baseUrlToClient = Map.of(
        BaseURL.GITHUB, gitHubClient(),
        BaseURL.STACKOVERFLOW, stackOverflowClient()
    );

    @Bean
    public GitHubClient gitHubClient() {
        return new GitHubClient(buildClientByBaseURL(gitHubBaseURL));
    }

    @Bean
    public StackOverflowClient stackOverflowClient() {
        return new StackOverflowClient(buildClientByBaseURL(stackOverflowBaseURL));
    }

    @Bean
    public BotClient botClient() {
        return new BotClient(buildClientByBaseURL(botBaseURL));
    }

    private @NotNull WebClient buildClientByBaseURL(String baseURL) {
        return WebClient.builder()
            .baseUrl(baseURL)
            .build();
    }
}
