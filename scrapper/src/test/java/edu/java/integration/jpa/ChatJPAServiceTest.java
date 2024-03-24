package edu.java.integration.jpa;

import edu.java.dto.model.Chat;
import edu.java.integration.IntegrationTest;
import edu.java.repository.jpa.ChatJPARepository;
import edu.java.service.jpa.ChatJPAService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = "app.database-access-type=jpa")
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
