package edu.java.respository;

import edu.java.dto.model.Chat;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class ChatJDBCRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void add(Chat chat) {
        jdbcTemplate.update(
            "INSERT INTO chats(chat_id, created_at, state) VALUES (?, ?, ?::chat_state)",
            chat.getChatId(),
            chat.getCreatedAt(),
            chat.getState().name()
        );
    }

    @Transactional
    public void remove(Chat chat) {
        jdbcTemplate.update("DELETE FROM chats WHERE chat_id = ?", chat.getChatId());
    }

    public List<Chat> findAll() {
        return jdbcTemplate.query("SELECT * FROM chats", new BeanPropertyRowMapper<>(Chat.class));
    }
}
