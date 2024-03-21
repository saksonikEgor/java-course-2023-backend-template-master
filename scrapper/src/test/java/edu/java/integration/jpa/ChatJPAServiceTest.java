package edu.java.integration.jpa;

import edu.java.ScrapperApplication;
import edu.java.configuration.DataAccess.JPAAccessConfiguration;
import edu.java.dto.model.Chat;
import edu.java.integration.IntegrationTest;
import edu.java.integration.configuration.DBAccessConfiguration;
import edu.java.respository.jooq.ChatJOOQRepository;
import edu.java.respository.jpa.ChatJPARepository;
import edu.java.service.jooq.ChatJOOQService;
import edu.java.service.jpa.ChatJPAService;
import io.netty.resolver.dns.DnsServerResponseFeedbackAddressStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@SpringBootTest(classes = JPAAccessConfiguration.class, properties = {
//    "app.database-access-type=jpa"
//})
@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ContextConfiguration(classes = ScrapperApplication.class)
//@ContextConfiguration(classes = DBAccessConfiguration.class)
public class ChatJPAServiceTest extends IntegrationTest {
    @Autowired
    private ChatJPARepository chatRepository;
    @Autowired
    private ChatJPAService chatService;
    private static final long CHAT_ID = 579324L;

    @Test
    @Transactional
    @Rollback
    void register() {
        chatService.register(CHAT_ID);

        List<Chat> chats = chatRepository.findAll();

        assertEquals(1, chats.size());
        assertEquals(CHAT_ID, chats.getFirst().getChatId());
    }

    @Test
    @Transactional
    @Rollback
    void unregister() {
        chatService.register(CHAT_ID);
        chatService.unregister(CHAT_ID);

        assertTrue(chatRepository.findAll().isEmpty());
    }
}
