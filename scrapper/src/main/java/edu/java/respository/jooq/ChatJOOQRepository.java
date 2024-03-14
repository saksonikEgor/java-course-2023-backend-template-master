package edu.java.respository.jooq;

import edu.java.dto.model.Chat;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static edu.java.domain.jooq.tables.Chats.CHATS;
import static edu.java.domain.jooq.tables.LinksChats.LINKS_CHATS;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class ChatJOOQRepository {
    private final DSLContext dslContext;

    @Transactional
    public void add(Chat chat) {
        dslContext.insertInto(CHATS, CHATS.CHAT_ID, CHATS.CREATED_AT)
                .values(chat.getChatId(), chat.getCreatedAt())
                .execute();
    }

    @Transactional
    public void remove(long chatId) {
        dslContext.deleteFrom(CHATS)
                .where(CHATS.CHAT_ID.eq(chatId))
                .execute();
    }

    public List<Chat> findAll() {
        return dslContext.selectFrom(CHATS)
                .fetchInto(Chat.class);
    }

    public Optional<Chat> getChatById(long chatId) {
        return Optional.ofNullable(dslContext.selectFrom(CHATS)
                .where(CHATS.CHAT_ID.eq(chatId))
                .fetchInto(Chat.class)
                .getFirst());
    }

    public List<Chat> getAllChatsForLink(long linkId) {
        return dslContext.select()
                .from(CHATS)
                .join(LINKS_CHATS).on(LINKS_CHATS.CHAT_ID.eq(CHATS.CHAT_ID))
                .where(LINKS_CHATS.LINK_ID.eq(linkId))
                .fetchInto(Chat.class);
    }
}
