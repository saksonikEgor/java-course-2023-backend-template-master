package edu.java.respository.jooq;

import edu.java.domain.jooq.tables.pojos.Links;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.domain.jooq.tables.Links.LINKS;
import static edu.java.domain.jooq.tables.LinksChats.LINKS_CHATS;
import static org.jooq.impl.DSL.row;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class LinkJOOQRepository {
    private final DSLContext dslContext;

    @SuppressWarnings("MagicNumber")
    @Transactional
    public long add(Links link) {
        return dslContext.insertInto(LINKS, LINKS.URL, LINKS.LAST_UPDATE, LINKS.LAST_CHECK, LINKS.BASE_URL, LINKS.INFO)
            .values(link.getUrl(), link.getLastUpdate(), link.getLastCheck(), link.getBaseUrl(), link.getInfo())
            .returning(LINKS.LINK_ID)
            .fetchInto(Long.class)
            .getFirst();
    }

    @Transactional
    public void remove(String url) {
        dslContext.deleteFrom(LINKS)
            .where(LINKS.URL.eq(url))
            .execute();
    }

    public List<Links> findAll() {
        return dslContext.selectFrom(LINKS)
            .fetchInto(Links.class);
    }

    public Optional<Links> getLinkByURL(String url) {
        return Optional.ofNullable(dslContext.selectFrom(LINKS)
            .where(LINKS.URL.eq(url))
            .fetchInto(Links.class)
            .getFirst());
    }

    @Transactional
    public void addLinkToChat(long linkId, long chatId) {
        dslContext.insertInto(LINKS_CHATS, LINKS_CHATS.CHAT_ID, LINKS_CHATS.LINK_ID)
            .values(chatId, linkId)
            .execute();
    }

    @Transactional
    public void removeLinkFromChat(long linkId, long chatId) {
        dslContext.deleteFrom(LINKS_CHATS)
            .where(LINKS_CHATS.CHAT_ID.eq(chatId))
            .and(LINKS_CHATS.LINK_ID.eq(linkId))
            .execute();
    }

    public List<Links> getAllLinksForChat(long chatId) {
        return dslContext.select()
            .from(LINKS)
            .join(LINKS_CHATS).on(LINKS_CHATS.LINK_ID.eq(LINKS.LINK_ID))
            .where(LINKS_CHATS.CHAT_ID.eq(chatId))
            .fetchInto(Links.class);
    }

    public List<Links> getAllLinksWithLastCheckBeforeDuration(Duration duration) {
        return dslContext.selectFrom(LINKS)
            .where(LINKS.LAST_CHECK.lessThan(OffsetDateTime.now().minus(duration)))
            .fetchInto(Links.class);
    }

    @Transactional
    public void updateCheckedLinks(List<Long> checkedLinkIds) {
        dslContext.update(LINKS)
            .set(row(LINKS.LAST_CHECK), row(OffsetDateTime.now()))
            .where(LINKS.LINK_ID.in(checkedLinkIds))
            .execute();
    }

    @Transactional
    public void resetLastUpdate(List<Long> updatedLinkIds) {
        dslContext.update(LINKS)
            .set(row(LINKS.LAST_UPDATE), row(OffsetDateTime.now()))
            .where(LINKS.LINK_ID.in(updatedLinkIds))
            .execute();
    }
}
