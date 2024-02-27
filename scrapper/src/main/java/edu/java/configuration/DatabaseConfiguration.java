package edu.java.configuration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.validation.annotation.Validated;
import javax.sql.DataSource;

@Validated
@ConfigurationProperties(prefix = "db", ignoreUnknownFields = false)
public record DatabaseConfiguration(
    @NotNull
    Database database
) {
    public record Database(
        @NotBlank String driver,
        @NotBlank String url,
        @NotBlank String username,
        @NotBlank String password
    ) {
    }

    @Bean
    public DataSource databaseSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(database.driver());
        dataSource.setUrl(database.url());
        dataSource.setUsername(database.username());
        dataSource.setPassword(database.password());

        return dataSource;
    }

}
