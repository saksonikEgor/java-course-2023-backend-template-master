package edu.java.repository.jooq;

import edu.java.domain.jooq.tables.records.ChatsRecord;
import edu.java.dto.model.Chat;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import static edu.java.domain.jooq.tables.Chats.CHATS;
import static edu.java.domain.jooq.tables.LinksChats.LINKS_CHATS;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
@Validated
public class ChatJOOQRepository {
    private final DSLContext dslContext;

    @Transactional
    public void add(@Valid Chat chat) {
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

    public List<@Valid Chat> findAll() {
        return dslContext.selectFrom(CHATS)
            .fetchInto(Chat.class);
    }

    public Optional<@Valid Chat> getChatById(long chatId) {
        Optional<ChatsRecord> chatsOptional = dslContext.selectFrom(CHATS)
            .where(CHATS.CHAT_ID.eq(chatId))
            .fetchOptional();

        if (chatsOptional.isEmpty()) {
            return Optional.empty();
        } else {
            return chatsOptional.map(co -> co.into(Chat.class));
        }
    }

    public List<@Valid Chat> getAllChatsForLink(long linkId) {
        return dslContext.select()
            .from(CHATS)
            .join(LINKS_CHATS).on(LINKS_CHATS.CHAT_ID.eq(CHATS.CHAT_ID))
            .where(LINKS_CHATS.LINK_ID.eq(linkId))
            .fetchInto(Chat.class);
    }
}
