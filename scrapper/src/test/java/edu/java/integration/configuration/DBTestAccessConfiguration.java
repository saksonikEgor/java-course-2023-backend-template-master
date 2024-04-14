package edu.java.integration.configuration;

import edu.java.integration.IntegrationTest;
import javax.sql.DataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.Settings;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.boot.autoconfigure.jooq.JooqExceptionTranslator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DBTestAccessConfiguration {
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
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(containerDataSource());
    }

    @Bean
    public DataSourceConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(containerDataSource()));
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
    public DSLContext dslContext() {
        return new DefaultDSLContext(configuration());
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(containerDataSource());
    }
}
