package edu.java.configuration;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
    @Value("${spring.datasource.driver-class-name:org.postgresql.Driver}")
    private String driver;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username:postgres}")
    private String username;
    @Value("${spring.datasource.password:postgres}")
    private String password;

    @Bean
    public DataSource databaseSource() {
        DataSourceBuilder<?> builder = DataSourceBuilder.create();

        builder.driverClassName(driver);
        builder.url(url);
        builder.username(username);
        builder.password(password);

        return builder.build();
    }
}
