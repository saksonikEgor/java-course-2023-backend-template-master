package edu.java.integration.jdbc;

import edu.java.dto.model.BaseURL;
import edu.java.dto.model.Link;
import edu.java.integration.IntegrationTest;
import edu.java.integration.configuration.JDBCConfiguration;
import edu.java.respository.jdbc.LinkJDBCRepository;
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
        Link link = new Link("https://github.com/saksonikEgor/Checkers", dateTime, dateTime, BaseURL.GITHUB);

        link.setLinkId(linkRepository.add(link));
        List<Link> links = linkRepository.findAll();

        assertEquals(1, links.size());
        assertEquals(link, links.getFirst());
    }

    @Test
    @Transactional
    @Rollback
    void removeLink() {
        OffsetDateTime dateTime = OffsetDateTime.parse("2023-02-05T18:38:39Z");
        Link link = new Link("https://github.com/saksonikEgor/Checkers", dateTime, dateTime, BaseURL.GITHUB);

        link.setLinkId(linkRepository.add(link));
        linkRepository.remove(link.getUrl());

        assertTrue(linkRepository.findAll().isEmpty());
    }
}
