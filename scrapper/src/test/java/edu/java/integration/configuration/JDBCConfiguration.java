package edu.java.integration.configuration;

import edu.java.integration.IntegrationTest;
import edu.java.respository.jdbc.ChatJDBCRepository;
import edu.java.respository.jdbc.LinkJDBCRepository;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class JDBCConfiguration {
    @Bean
    public DataSource containerDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(IntegrationTest.POSTGRES.getJdbcUrl());
        dataSource.setUsername(IntegrationTest.POSTGRES.getUsername());
        dataSource.setPassword(IntegrationTest.POSTGRES.getPassword());

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplateForContainer() {
        return new JdbcTemplate(containerDataSource());
    }

    @Bean
    public LinkJDBCRepository linkJDBCRepository() {
        return new LinkJDBCRepository(jdbcTemplateForContainer());
    }

    @Bean
    public ChatJDBCRepository chatJDBCRepository() {
        return new ChatJDBCRepository(jdbcTemplateForContainer());
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(containerDataSource());
    }
}
