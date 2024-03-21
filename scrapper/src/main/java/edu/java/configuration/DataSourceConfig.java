package edu.java.configuration;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
    @Value("${datasource.driver:org.postgresql.Driver}")
    private String driver;
    @Value("${datasource.url:jdbc:postgresql://localhost:5432/scrapper}")
    private String url;
    @Value("${datasource.username:postgres}")
    private String username;
    @Value("${datasource.password:postgres}")
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
