package edu.java.integration.jdbc;

import edu.java.configuration.DataAccess.JDBCAccessConfiguration;
import edu.java.dto.model.BaseURL;
import edu.java.dto.model.Link;
import edu.java.integration.IntegrationTest;
import edu.java.integration.configuration.DBTestAccessConfiguration;
import edu.java.repository.jdbc.LinkJDBCRepository;
import edu.java.service.jdbc.ChatJDBCService;
import edu.java.service.jdbc.LinkJDBCService;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
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
@ContextConfiguration(classes = DBTestAccessConfiguration.class)
public class LinkJDBCServiceTest extends IntegrationTest {
    @Autowired
    private LinkJDBCService linkService;
    @Autowired
    private LinkJDBCRepository linkRepository;
    @Autowired
    private ChatJDBCService chatService;
    private static final long CHAT_ID = 579324L;

    @Test
    @Transactional
    @Rollback
    void addLinkToChat() {
        Link link = new Link(
            "https://github.com/saksonikEgor/Checkers",
            OffsetDateTime.parse("2023-02-05T18:38:39Z"),
            OffsetDateTime.parse("2023-02-05T18:38:39Z"),
            BaseURL.GITHUB
        );

        chatService.register(CHAT_ID);
        linkService.addLinkToChat(CHAT_ID, link);

        List<Link> links = linkRepository.getAllLinksForChat(CHAT_ID);

        assertEquals(1, links.size());
        assertEquals(link.getUrl(), links.getFirst().getUrl());
    }

    @Test
    @Transactional
    @Rollback
    void removeLinkFromChat() {
        Link link = new Link(
            "https://github.com/saksonikEgor/Checkers",
            OffsetDateTime.parse("2023-02-05T18:38:39Z"),
            OffsetDateTime.parse("2023-02-05T18:38:39Z"),
            BaseURL.GITHUB
        );

        chatService.register(CHAT_ID);
        linkService.addLinkToChat(CHAT_ID, link);
        linkService.removeLinkFromChat(CHAT_ID, link.getUrl());

        assertTrue(linkRepository.findAll().isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    void listAllTrackingLinksForChat() {
        Link link = new Link(
            "https://github.com/saksonikEgor/Checkers",
            OffsetDateTime.parse("2023-02-05T18:38:39Z"),
            OffsetDateTime.parse("2023-02-05T18:38:39Z"),
            BaseURL.GITHUB
        );

        chatService.register(CHAT_ID);
        linkService.addLinkToChat(CHAT_ID, link);

        assertEquals(linkRepository.getAllLinksForChat(CHAT_ID), linkService.listAll(CHAT_ID));
    }

    @Test
    @Transactional
    @Rollback
    void listAll() {
        Link link = new Link(
            "https://github.com/saksonikEgor/Checkers",
            OffsetDateTime.parse("2023-02-05T18:38:39Z"),
            OffsetDateTime.parse("2023-02-05T18:38:39Z"),
            BaseURL.GITHUB
        );

        chatService.register(CHAT_ID);
        linkService.addLinkToChat(CHAT_ID, link);

        assertEquals(linkRepository.findAll(), linkService.listAll());
    }

    @Test
    @Transactional
    @Rollback
    void listAllOldCheckLinks() {
        Link link1 = new Link(
            "https://github.com/saksonikEgor/Checkers",
            OffsetDateTime.parse("2023-02-05T18:38:39Z"),
            OffsetDateTime.parse("2023-02-05T18:38:39Z"),
            BaseURL.GITHUB
        );

        Link link2 = new Link(
            "https://github.com/saksonikEgor/Checkers2",
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            BaseURL.GITHUB
        );

        chatService.register(CHAT_ID);
        linkService.addLinkToChat(CHAT_ID, link1);
        linkService.addLinkToChat(CHAT_ID, link2);

        assertEquals(
            Stream.of(link1)
                .map(Link::getUrl)
                .toList(),
            linkService.listAllOldCheckLinks(Duration.ofDays(1))
                .stream()
                .map(Link::getUrl)
                .toList()
        );
    }

    @Test
    @Transactional
    @Rollback
    void resetLastUpdate() {
        Link link1 = new Link(
            "https://github.com/saksonikEgor/Checkers",
            OffsetDateTime.parse("2024-02-05T18:38:39Z"),
            OffsetDateTime.parse("2024-02-05T18:38:39Z"),
            BaseURL.GITHUB
        );

        Link link2 = new Link(
            "https://github.com/saksonikEgor/Checkers2",
            OffsetDateTime.parse("2023-02-05T18:38:39Z"),
            OffsetDateTime.parse("2023-02-05T18:38:39Z"),
            BaseURL.GITHUB
        );

        chatService.register(CHAT_ID);
        linkService.addLinkToChat(CHAT_ID, link1);
        linkService.addLinkToChat(CHAT_ID, link2);

        linkService.resetLastUpdate(
            linkRepository.findAll()
                .stream()
                .filter(l -> l.getUrl().equals(link2.getUrl()))
                .toList()
        );

        List<Link> links = linkRepository.findAll();

        assertEquals(2, links.size());
        assertEquals(1, links.stream()
            .filter(l -> (
                Objects.equals(l.getUrl(), link1.getUrl()) && Objects.equals(l.getLastUpdate(), link1.getLastUpdate())
                    && Objects.equals(l.getLastCheck(), link1.getLastCheck())
                    && Objects.equals(l.getBaseURL(), link1.getBaseURL())))
            .count()
        );
        assertTrue(links.stream()
            .filter(l -> l.getUrl().equals(link2.getUrl()))
            .findFirst()
            .get()
            .getLastUpdate()
            .isAfter(link2.getLastUpdate())
        );
    }
}
