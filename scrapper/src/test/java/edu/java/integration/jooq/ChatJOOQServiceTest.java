package edu.java.integration.jooq;

import edu.java.configuration.DataAccess.JOOQAccessConfiguration;
import edu.java.dto.model.Chat;
import edu.java.integration.IntegrationTest;
import edu.java.integration.configuration.DBAccessConfiguration;
import edu.java.respository.jooq.ChatJOOQRepository;
import edu.java.service.jooq.ChatJOOQService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = JOOQAccessConfiguration.class, properties = {
    "app.database-access-type=jooq"
})
@ContextConfiguration(classes = DBAccessConfiguration.class)
public class ChatJOOQServiceTest extends IntegrationTest {
    @Autowired
    private ChatJOOQRepository chatRepository;
    @Autowired
    private ChatJOOQService chatService;
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
