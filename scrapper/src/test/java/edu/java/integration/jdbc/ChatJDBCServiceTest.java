package edu.java.integration.jdbc;

import edu.java.configuration.DataAccess.JDBCAccessConfiguration;
import edu.java.dto.model.Chat;
import edu.java.integration.IntegrationTest;
import edu.java.integration.configuration.DBAccessConfiguration;
import edu.java.repository.jdbc.ChatJDBCRepository;
import edu.java.service.jdbc.ChatJDBCService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = JDBCAccessConfiguration.class, properties = {
    "app.database-access-type=jdbc"
})
@ContextConfiguration(classes = DBAccessConfiguration.class)
public class ChatJDBCServiceTest extends IntegrationTest {
    @Autowired
    private ChatJDBCService chatService;
    @Autowired
    private ChatJDBCRepository chatRepository;
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
