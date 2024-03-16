package edu.java.integration.configuration;

import edu.java.integration.IntegrationTest;
import org.jooq.impl.DataSourceConnectionProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;

@Configuration
public class JDBCTemplateConfiguration {
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
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(containerDataSource());
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(containerDataSource());
    }
}
