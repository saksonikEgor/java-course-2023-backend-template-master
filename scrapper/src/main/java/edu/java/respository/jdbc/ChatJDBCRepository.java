package edu.java.respository.jdbc;

import edu.java.dto.model.Chat;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
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
            "INSERT INTO chats(chat_id, created_at) VALUES (?, ?)",
            chat.getChatId(),
            chat.getCreatedAt()
        );
    }

    @Transactional
    public void remove(long chatId) {
        jdbcTemplate.update("DELETE FROM chats WHERE chat_id = ?", chatId);
    }

    public List<Chat> findAll() {
        return jdbcTemplate.query("SELECT * FROM chats", new BeanPropertyRowMapper<>(Chat.class));
    }

    public Optional<Chat> getChatById(long chatId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT * FROM chats WHERE chat_id = ?",
                new BeanPropertyRowMapper<>(Chat.class),
                chatId
            ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Chat> getAllChatsForLink(long linkId) {
        return jdbcTemplate.query(
            "SELECT * FROM chats c JOIN links_chats lc ON c.chat_id = lc.chat_id WHERE lc.link_id = ?",
            new BeanPropertyRowMapper<>(Chat.class),
            linkId
        );
    }
}
