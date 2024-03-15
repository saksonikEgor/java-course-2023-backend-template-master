package edu.java.configuration.DataAccess;

import edu.java.respository.jpa.ChatJPARepository;
import edu.java.respository.jpa.LinkJPARepository;
import edu.java.service.ChatService;
import edu.java.service.LinkService;
import edu.java.service.jpa.ChatJPAService;
import edu.java.service.jpa.LinkJPAService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
@RequiredArgsConstructor
public class JPAAccessConfiguration implements AccessConfiguration {
    private final ChatJPARepository chatRepository;
    private final LinkJPARepository linkRepository;

    @Bean
    @Override
    public ChatService chatService() {
        return new ChatJPAService(chatRepository);
    }

    @Bean
    @Override
    public LinkService linkService() {
        return new LinkJPAService(linkRepository, chatRepository);
    }
}
