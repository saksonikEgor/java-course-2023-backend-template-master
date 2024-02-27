package edu.java.integration.jdbc;

import edu.java.dto.model.Chat;
import edu.java.dto.model.ChatState;
import edu.java.integration.IntegrationTest;
import edu.java.integration.configuration.JDBCConfiguration;
import edu.java.respository.jdbc.ChatJDBCRepository;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration(classes = JDBCConfiguration.class)
public class ChatJDBCRepositoryTest extends IntegrationTest {
    @Autowired
    private ChatJDBCRepository chatRepository;

    @Test
    @Transactional
    @Rollback
    void addChat() {
        Chat chat = new Chat();
        chat.setChatId(579324L);
        chat.setState(ChatState.REGISTERED);
        chat.setCreatedAt(OffsetDateTime.parse("2023-02-05T18:38:39Z"));

        chatRepository.add(chat);
        List<Chat> chats = chatRepository.findAll();

        assertEquals(1, chats.size());
        assertEquals(chat, chats.getFirst());
    }

    @Test
    @Transactional
    @Rollback
    void removeChat() {
        Chat chat = new Chat();
        chat.setChatId(579324L);
        chat.setState(ChatState.REGISTERED);
        chat.setCreatedAt(OffsetDateTime.parse("2023-02-05T18:38:39Z"));

        chatRepository.add(chat);
        chatRepository.remove(chat.getChatId());

        assertTrue(chatRepository.findAll().isEmpty());
    }
}
