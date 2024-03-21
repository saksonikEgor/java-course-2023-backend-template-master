package edu.java.configuration.DataAccess;

import edu.java.respository.jpa.ChatJPARepository;
import edu.java.respository.jpa.LinkJPARepository;
import edu.java.service.ChatService;
import edu.java.service.LinkService;
import edu.java.service.jpa.ChatJPAService;
import edu.java.service.jpa.LinkJPAService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
@RequiredArgsConstructor
public class JPAAccessConfiguration implements AccessConfiguration {
    private final ChatJPARepository chatRepository;
    private final LinkJPARepository linkRepository;
//    private final DataSource databaseSource;

//    @Bean
//    public EntityManagerFactory entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
//
//        emf.setDataSource(databaseSource);
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
