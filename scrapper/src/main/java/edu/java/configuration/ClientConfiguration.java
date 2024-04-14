package edu.java.configuration;

import edu.java.client.GitHubClient;
import edu.java.client.SiteAPIClient;
import edu.java.client.StackOverflowClient;
import edu.java.communication.httpClient.BotClient;
import edu.java.dto.model.BaseURL;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Setter
public class ClientConfiguration {
    private static final Pattern GITHUB_URL_PATTERN = Pattern.compile("https://github.com/(.+)/(.+)");
    private static final Pattern STACKOVERFLOW_URL_PATTERN = Pattern.compile(
        "https://stackoverflow.com/questions/(\\d+)/(.+)"
    );
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

    private @NotNull WebClient buildClientByBaseURL(String baseURL) {
        return WebClient.builder()
            .baseUrl(baseURL)
            .build();
    }

    @Bean
    public Map<String, BaseURL> domainToBaseURL() {
        return Map.of(
            "github.com", BaseURL.GITHUB,
            "stackoverflow.com", BaseURL.STACKOVERFLOW
        );
    }

    @Bean
    public Map<BaseURL, SiteAPIClient> baseURLToClient() {
        return Map.of(
            BaseURL.GITHUB, gitHubClient(),
            BaseURL.STACKOVERFLOW, stackOverflowClient()
        );
    }

    @Bean
    public Map<BaseURL, Function<String, Map<String, String>>> baseURLToExtractingInfoFunc() {
        return Map.of(
            BaseURL.GITHUB, url -> {
                Matcher matcher = GITHUB_URL_PATTERN.matcher(url);
                matcher.find();

                return Map.of(
                    "user", matcher.group(1),
                    "repo", matcher.group(2)
                );
            },
            BaseURL.STACKOVERFLOW, url -> {
                Matcher matcher = STACKOVERFLOW_URL_PATTERN.matcher(url);
                matcher.find();

                return Map.of("question_id", matcher.group(1));
            }
        );
    }

}
