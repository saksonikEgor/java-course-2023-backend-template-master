package edu.java.configuration;

import edu.java.client.ScrapperClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    @Value("${scrapper.base-url:http://localhost:8080}")
    private String scrapperBaseURL;

    @Bean
    public ScrapperClient scrapperClient() {
        return new ScrapperClient(buildClientByBaseURL(scrapperBaseURL));
    }

    private @NotNull WebClient buildClientByBaseURL(String baseURL) {
        return WebClient.builder()
            .baseUrl(baseURL)
            .build();
    }
}
