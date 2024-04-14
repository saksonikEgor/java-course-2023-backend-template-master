package edu.java.integration.jpa;

import edu.java.configuration.DataAccess.JPAAccessConfiguration;
import edu.java.dto.model.BaseURL;
import edu.java.dto.model.Link;
import edu.java.integration.IntegrationTest;
import edu.java.integration.configuration.DBTestAccessConfiguration;
import edu.java.integration.configuration.JPATestConfiguration;
import edu.java.repository.jpa.ChatJPARepository;
import edu.java.repository.jpa.LinkJPARepository;
import edu.java.service.jpa.ChatJPAService;
import edu.java.service.jpa.LinkJPAService;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = "app.database-access-type=jpa")
@ContextConfiguration(classes = {JPATestConfiguration.class, JPAAccessConfiguration.class})
public class LinkJPAServiceTest extends IntegrationTest {
    @Autowired
    private LinkJPAService linkService;
    @Autowired
    private ChatJPAService chatService;
    @Autowired
    private LinkJPARepository linkRepository;
    @Autowired
    private ChatJPARepository chatRepository;
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

        List<Link> links = chatRepository.findById(CHAT_ID).get().getLinks();

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

        assertEquals(
            chatRepository.findById(CHAT_ID).get().getLinks(),
            linkService.listAll(CHAT_ID)
        );
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
        OffsetDateTime dateBefore1 = OffsetDateTime.parse("2024-02-05T18:38:39Z");
        Link link1 = new Link(
            "https://github.com/saksonikEgor/Checkers",
            dateBefore1,
            dateBefore1,
            BaseURL.GITHUB
        );

        OffsetDateTime dateBefore2 = OffsetDateTime.parse("2023-02-05T18:38:39Z");
        Link link2 = new Link(
            "https://github.com/saksonikEgor/Checkers2",
            dateBefore2,
            dateBefore2,
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
        assertFalse(links.stream()
            .filter(l -> l.getUrl().equals(link1.getUrl()))
            .findFirst()
            .get()
            .getLastUpdate()
            .isAfter(dateBefore1)
        );
        assertTrue(links.stream()
            .filter(l -> l.getUrl().equals(link2.getUrl()))
            .findFirst()
            .get()
            .getLastUpdate()
            .isAfter(dateBefore2)
        );
    }
}
