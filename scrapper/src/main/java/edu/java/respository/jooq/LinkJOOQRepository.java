package edu.java.respository.jooq;

import edu.java.domain.jooq.enums.BaseUrlType;
import edu.java.domain.jooq.tables.records.LinksRecord;
import edu.java.dto.model.BaseURL;
import edu.java.dto.model.Link;
import edu.java.util.parser.Map2JsonConverter;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.JSON;
import org.jooq.Record;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static edu.java.domain.jooq.Tables.LINKS;
import static edu.java.domain.jooq.tables.LinksChats.LINKS_CHATS;
import static org.jooq.impl.DSL.row;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class LinkJOOQRepository {
    private final DSLContext dslContext;
    private final Function<Record, Link> recordMapper = lr -> new Link(
            lr.get(LINKS.LINK_ID),
            lr.get(LINKS.URL),
            lr.get(LINKS.LAST_UPDATE),
            lr.get(LINKS.LAST_CHECK),
            BaseURL.valueOf(lr.get(LINKS.BASE_URL).name()),
            Map2JsonConverter.json2Map(lr.get(LINKS.INFO).data())
    );

    @SuppressWarnings("MagicNumber")
    @Transactional
    public long add(Link link) {
        return dslContext.insertInto(LINKS, LINKS.URL, LINKS.LAST_UPDATE, LINKS.LAST_CHECK, LINKS.BASE_URL, LINKS.INFO)
                .values(
                        link.getUrl(),
                        link.getLastUpdate(),
                        link.getLastCheck(),
                        BaseUrlType.valueOf(link.getBaseURL().name()),
                        JSON.json(Map2JsonConverter.map2Json(link.getInfo()))
                )
                .returning(LINKS.LINK_ID)
                .fetchOne(LINKS.LINK_ID);
    }

    @Transactional
    public void remove(String url) {
        dslContext.deleteFrom(LINKS)
                .where(LINKS.URL.eq(url))
                .execute();
    }

    public List<Link> findAll() {
        return dslContext.selectFrom(LINKS)
                .stream()
                .map(recordMapper)
                .toList();
    }

    public Optional<Link> getLinkByURL(String url) {
        Optional<LinksRecord> linksOptional = dslContext.selectFrom(LINKS)
                .where(LINKS.URL.eq(url))
                .fetchOptional();

        if (linksOptional.isEmpty()) {
            return Optional.empty();
        } else {
            return linksOptional.map(co -> co.into(Link.class));
        }
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

    public List<Link> getAllLinksForChat(long chatId) {
        return dslContext.select()
                .from(LINKS)
                .join(LINKS_CHATS).on(LINKS_CHATS.LINK_ID.eq(LINKS.LINK_ID))
                .where(LINKS_CHATS.CHAT_ID.eq(chatId))
                .stream()
                .map(recordMapper)
                .toList();
    }

    public List<Link> getAllLinksWithLastCheckBeforeDuration(Duration duration) {
        return dslContext.selectFrom(LINKS)
                .where(LINKS.LAST_CHECK.lessThan(OffsetDateTime.now().minus(duration)))
                .stream()
                .map(recordMapper)
                .toList();
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
