package edu.java.respository.jdbc;

import edu.java.dto.model.BaseURL;
import edu.java.dto.model.Link;
import edu.java.util.Map2JsonConverter;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.postgresql.util.PGobject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class LinkJDBCRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Link> rowMapper = (rs, rn) -> new Link(
        rs.getLong(1),
        rs.getString(2),
        new Timestamp(rs.getTimestamp(3).getTime()).toInstant().atOffset(ZoneOffset.UTC),
        new Timestamp(rs.getTimestamp(4).getTime()).toInstant().atOffset(ZoneOffset.UTC),
        new ArrayList<>(),
        BaseURL.valueOf(rs.getString(5)),
//        rs.getObject(5, BaseURL.class),
        Map2JsonConverter.json2Map((PGobject) rs.getObject(6))
    );

    @Transactional
    public long add(Link link) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(c -> {
            PreparedStatement pst = c.prepareStatement(
                "INSERT INTO links(url, last_update, last_check, base_url, info) VALUES (?, ?, ?, ?::base_url_type, ?)",
                Statement.RETURN_GENERATED_KEYS
            );
            pst.setString(1, link.getUrl());
            pst.setObject(2, link.getLastUpdate());
            pst.setObject(3, link.getLastCheck());
            pst.setObject(4, link.getBaseURL().name());
            pst.setObject(5, Map2JsonConverter.map2Json(link.getInfo()));
            return pst;
        }, keyHolder);

        return (long) Objects.requireNonNull(keyHolder.getKeys()).get("link_id");
    }

    @Transactional
    public void remove(String url) {
        jdbcTemplate.update("DELETE FROM links WHERE url = ?", url);
    }

    public List<Link> findAll() {
        return jdbcTemplate.query("SELECT * FROM links", rowMapper);
//        return jdbcTemplate.query("SELECT * FROM links", new BeanPropertyRowMapper<>(Link.class));
    }

    public Optional<Link> getLinkByURI(String url) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
            "SELECT * FROM links WHERE url = ?",
            rowMapper,
            url
        ));
//        return Optional.ofNullable(jdbcTemplate.queryForObject(
//            "SELECT * FROM links WHERE url = ?",
//            new BeanPropertyRowMapper<>(Link.class),
//            url
//        ));
    }

    @Transactional
    public void addLinkToChat(long linkId, long chatId) {
        jdbcTemplate.update(
            "INSERT INTO links_chats(chat_id, link_id) VALUES (?, ?)",
            chatId,
            linkId
        );
    }

    @Transactional
    public void removeLinkFromChat(long linkId, long chatId) {
        jdbcTemplate.update(
            "DELETE FROM links_chats WHERE chat_id = ? and link_id = ?",
            chatId,
            linkId
        );
    }

    public List<Link> getAllLinksForChat(long chatId) {
        return jdbcTemplate.query(
            "SELECT * FROM links l JOIN links_chats lc ON l.link_id = lc.link_id WHERE lc.chat_id = ?",
            rowMapper,
            chatId
        );
//        return jdbcTemplate.query(
//            "SELECT * FROM links l JOIN links_chats lc ON l.link_id = lc.link_id WHERE lc.chat_id = ?",
//            new BeanPropertyRowMapper<>(Link.class),
//            chatId
//        );
    }
}
