package edu.java.integration.configuration;

import edu.java.integration.IntegrationTest;
import javax.sql.DataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
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
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DBAccessConfiguration {
    @Bean
    public DataSource containerDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(IntegrationTest.POSTGRES.getJdbcUrl());
        dataSource.setUsername(IntegrationTest.POSTGRES.getUsername());
        dataSource.setPassword(IntegrationTest.POSTGRES.getPassword());

        return dataSource;
    }

//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean em
//            = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(containerDataSource());
//        em.setPackagesToScan("edu/java/dto/model");
//
//        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
//
//        return em;
//    }

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
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(containerDataSource());
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(containerDataSource());
    }

//    @Bean
//    public EntityManagerFactory entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
//
//        emf.setDataSource(containerDataSource());
////        emf.setPackagesToScan("edu.java.dto.model");
//        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        emf.afterPropertiesSet();
//
//        return emf.getObject();
//    }
//
//    @Bean
//    public EntityManager entityManager() {
//        return entityManagerFactory().createEntityManager();
//    }

//
//    @Bean
//    public LinkJDBCRepository linkJDBCRepository() {
//        return new LinkJDBCRepository(jdbcTemplateForContainer());
//    }
//
//    @Bean
//    public ChatJDBCRepository chatJDBCRepository() {
//        return new ChatJDBCRepository(jdbcTemplateForContainer());
//    }
//
//    @Bean
//    public ChatJOOQRepository chatJOOQRepository() {
//        return new ChatJOOQRepository(dslContext());
//    }
//
//    @Bean
//    public LinkJOOQRepository linkJOOQRepository() {
//        return new LinkJOOQRepository(dslContext());
//    }
//
//    @Bean
//    public LinkJDBCService linkJDBCService() {
//        return new LinkJDBCService(chatJDBCRepository(), linkJDBCRepository());
//    }
//
//    @Bean
//    public ChatJDBCService chatJDBCService() {
//        return new ChatJDBCService(chatJDBCRepository());
//    }
//
//    @Bean
//    public LinkJOOQService linkJOOQService() {
//        return new LinkJOOQService(linkJOOQRepository(), chatJOOQRepository());
//    }
//
//    @Bean
//    public ChatJOOQService chatJOOQService() {
//        return new ChatJOOQService(chatJOOQRepository());
//    }
//
//    @Bean
//    public LinkJPAService linkJPAService() {
//        return new LinkJPAService(linkJPARepository, chatJPARepository);
//    }
//
//    @Bean
//    public ChatJPAService chatJPAService() {
//        return new ChatJPAService(chatJPARepository);
//    }
}
