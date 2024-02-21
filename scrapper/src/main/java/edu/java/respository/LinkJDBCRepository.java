package edu.java.respository;

import edu.java.dto.model.Link;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class LinkJDBCRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void add(Link link) {
        jdbcTemplate.update(
            "INSERT INTO links(url, last_update, last_check) VALUES (?, ?, ?)",
            link.getUrl(),
            link.getLastUpdate(),
            link.getLastCheck()
        );
    }

    @Transactional
    public void remove(Link link) {
        jdbcTemplate.update("DELETE FROM links WHERE link_id = ?", link.getLinkId());
    }

    public List<Link> findAll() {
        return jdbcTemplate.query("SELECT * FROM links", new BeanPropertyRowMapper<>(Link.class));
    }
}
