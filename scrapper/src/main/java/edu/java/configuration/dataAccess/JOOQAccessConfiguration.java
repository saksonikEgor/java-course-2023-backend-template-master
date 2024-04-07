package edu.java.configuration.dataAccess;

import edu.java.repository.jooq.ChatJOOQRepository;
import edu.java.repository.jooq.LinkJOOQRepository;
import edu.java.service.ChatService;
import edu.java.service.LinkService;
import edu.java.service.jooq.ChatJOOQService;
import edu.java.service.jooq.LinkJOOQService;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
@RequiredArgsConstructor
public class JOOQAccessConfiguration implements AccessConfiguration {
    private final DSLContext dslContext;

    @Bean
    public ChatJOOQRepository chatJOOQRepository() {
        return new ChatJOOQRepository(dslContext);
    }

    @Bean
    public LinkJOOQRepository linkJOOQRepository() {
        return new LinkJOOQRepository(dslContext);
    }

    @Bean
    @Override
    public ChatService chatService() {
        return new ChatJOOQService(chatJOOQRepository());
    }

    @Bean
    @Override
    public LinkService linkService() {
        return new LinkJOOQService(linkJOOQRepository(), chatJOOQRepository());
    }
}
