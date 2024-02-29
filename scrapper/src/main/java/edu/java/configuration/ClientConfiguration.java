package edu.java.configuration;

import edu.java.client.BotClient;
import edu.java.client.GitHubClient;
import edu.java.client.SiteAPIClient;
import edu.java.client.StackOverflowClient;
import edu.java.dto.model.BaseURL;
import java.util.Map;
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

    @Bean
    public Map<String, BaseURL> stringToBaseUrl() {
        return Map.of(
            "https://api.github.com", BaseURL.GITHUB,
            "https://api.stackexchange.com", BaseURL.STACKOVERFLOW
        );
    }

    @Bean
    public Map<BaseURL, SiteAPIClient> baseURLToClient() {
        return Map.of(
            BaseURL.GITHUB, gitHubClient(),
            BaseURL.STACKOVERFLOW, stackOverflowClient()
        );
    }

    private @NotNull WebClient buildClientByBaseURL(String baseURL) {
        return WebClient.builder()
            .baseUrl(baseURL)
            .build();
    }
}
