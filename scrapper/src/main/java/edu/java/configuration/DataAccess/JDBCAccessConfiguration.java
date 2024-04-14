package edu.java.configuration.DataAccess;

import edu.java.repository.jdbc.ChatJDBCRepository;
import edu.java.repository.jdbc.LinkJDBCRepository;
import edu.java.service.ChatService;
import edu.java.service.LinkService;
import edu.java.service.jdbc.ChatJDBCService;
import edu.java.service.jdbc.LinkJDBCService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
@RequiredArgsConstructor
public class JDBCAccessConfiguration implements AccessConfiguration {
    private final JdbcTemplate jdbcTemplate;

    @Bean
    public ChatJDBCRepository chatJDBCRepository() {
        return new ChatJDBCRepository(jdbcTemplate);
    }

    @Bean
    public LinkJDBCRepository linkJDBCRepository() {
        return new LinkJDBCRepository(jdbcTemplate);
    }

    @Bean
    @Override
    public ChatService chatService() {
        return new ChatJDBCService(chatJDBCRepository());
    }

    @Bean
    @Override
    public LinkService linkService() {
        return new LinkJDBCService(chatJDBCRepository(), linkJDBCRepository());
    }
}
