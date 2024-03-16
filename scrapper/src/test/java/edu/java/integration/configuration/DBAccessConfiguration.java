package edu.java.integration.configuration;

import edu.java.configuration.DataAccess.JDBCAccessConfiguration;
import edu.java.configuration.DataAccess.JOOQAccessConfiguration;
import edu.java.configuration.DataAccess.JPAAccessConfiguration;
import edu.java.integration.IntegrationTest;
import edu.java.respository.jdbc.ChatJDBCRepository;
import edu.java.respository.jdbc.LinkJDBCRepository;
import edu.java.respository.jooq.ChatJOOQRepository;
import edu.java.respository.jooq.LinkJOOQRepository;
import edu.java.respository.jpa.ChatJPARepository;
import edu.java.respository.jpa.LinkJPARepository;
import edu.java.service.jdbc.ChatJDBCService;
import edu.java.service.jdbc.LinkJDBCService;
import edu.java.service.jooq.ChatJOOQService;
import edu.java.service.jooq.LinkJOOQService;
import edu.java.service.jpa.ChatJPAService;
import edu.java.service.jpa.LinkJPAService;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.Settings;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jooq.JooqExceptionTranslator;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
@SpringBootTest
//@ContextConfiguration(classes = JooqAutoConfiguration.DslContextConfiguration.class)
@ContextConfiguration(classes = {JDBCAccessConfiguration.class, JOOQAccessConfiguration.class,
    JPAAccessConfiguration.class})
public class DBAccessConfiguration {
    //    @Autowired
//    public DSLContext dslContext;
    @Autowired
    public ChatJPARepository chatJPARepository;
    @Autowired
    public LinkJPARepository linkJPARepository;

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
    public DataSourceConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(containerDataSource()));
    }

    @Bean
    public DSLContext dslContext() {
        return new DefaultDSLContext(configuration());
    }

    @Bean
    public DefaultConfiguration configuration() {
        DefaultConfiguration config = new DefaultConfiguration();

        config.set(connectionProvider());
        config.set(SQLDialect.POSTGRES);
        config.set(new Settings().withRenderNameCase(RenderNameCase.LOWER));
        config.set(new DefaultExecuteListenerProvider(new JooqExceptionTranslator()));

        return config;
    }

    @Bean
    public JdbcTemplate jdbcTemplateForContainer() {
        return new JdbcTemplate(containerDataSource());
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(containerDataSource());
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
    public ChatJOOQRepository chatJOOQRepository() {
        return new ChatJOOQRepository(dslContext());
    }

    @Bean
    public LinkJOOQRepository linkJOOQRepository() {
        return new LinkJOOQRepository(dslContext());
    }

    @Bean
    public LinkJDBCService linkJDBCService() {
        return new LinkJDBCService(chatJDBCRepository(), linkJDBCRepository());
    }

    @Bean
    public ChatJDBCService chatJDBCService() {
        return new ChatJDBCService(chatJDBCRepository());
    }

    @Bean
    public LinkJOOQService linkJOOQService() {
        return new LinkJOOQService(linkJOOQRepository(), chatJOOQRepository());
    }

    @Bean
    public ChatJOOQService chatJOOQService() {
        return new ChatJOOQService(chatJOOQRepository());
    }

    @Bean
    public LinkJPAService linkJPAService() {
        return new LinkJPAService(linkJPARepository, chatJPARepository);
    }

    @Bean
    public ChatJPAService chatJPAService() {
        return new ChatJPAService(chatJPARepository);
    }
}
