package edu.java.respository.jooq;

import java.util.List;
import java.util.Optional;
import edu.java.domain.jooq.tables.pojos.Chats;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.domain.jooq.tables.Chats.CHATS;
import static edu.java.domain.jooq.tables.LinksChats.LINKS_CHATS;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class ChatJOOQRepository {
    private final DSLContext dslContext;

    @Transactional
    public void add(Chats chat) {
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

    public List<Chats> findAll() {
        return dslContext.selectFrom(CHATS)
            .fetchInto(Chats.class);
    }

    public Optional<Chats> getChatById(long chatId) {
        return Optional.ofNullable(dslContext.selectFrom(CHATS)
            .where(CHATS.CHAT_ID.eq(chatId))
            .fetchInto(Chats.class)
            .getFirst());
    }

    public List<Chats> getAllChatsForLink(long linkId) {
        return dslContext.select()
            .from(CHATS)
            .join(LINKS_CHATS).on(LINKS_CHATS.CHAT_ID.eq(CHATS.CHAT_ID))
            .where(LINKS_CHATS.LINK_ID.eq(linkId))
            .fetchInto(Chats.class);
    }
}
