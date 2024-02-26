package edu.java.integration.jdbc;

import edu.java.dto.model.Link;
import edu.java.integration.IntegrationTest;
import edu.java.integration.configuration.JDBCConfiguration;
import edu.java.respository.LinkJDBCRepository;
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
public class LinkJDBCRepositoryTest extends IntegrationTest {
    @Autowired
    private LinkJDBCRepository linkRepository;

    @Test
    @Transactional
    @Rollback
    void addLink() {
        OffsetDateTime dateTime = OffsetDateTime.parse("2023-02-05T18:38:39Z");

        Link link = new Link();
        link.setUrl("https://github.com/saksonikEgor/Checkers");
        link.setLastUpdate(dateTime);
        link.setLastCheck(dateTime);

        linkRepository.add(link);
        List<Link> links = linkRepository.findAll();

        Link addedLink = links.getFirst();
        link.setLinkId(addedLink.getLinkId());

        assertEquals(1, links.size());
        assertEquals(link, addedLink);
    }

    @Test
    @Transactional
    @Rollback
    void removeLink() {
        OffsetDateTime dateTime = OffsetDateTime.parse("2023-02-05T18:38:39Z");

        Link link = new Link();
        link.setUrl("https://github.com/saksonikEgor/Checkers");
        link.setLastUpdate(dateTime);
        link.setLastCheck(dateTime);

        linkRepository.add(link);
        link.setLinkId(linkRepository.findAll().getFirst().getLinkId());
        linkRepository.remove(link);

        assertTrue(linkRepository.findAll().isEmpty());
    }
}
