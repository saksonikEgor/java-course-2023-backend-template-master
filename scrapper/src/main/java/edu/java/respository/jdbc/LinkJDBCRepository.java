package edu.java.respository.jdbc;

import edu.java.dto.model.BaseURL;
import edu.java.dto.model.Link;
import edu.java.util.Map2JsonConverter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
@Validated
public class LinkJDBCRepository {
    private final JdbcTemplate jdbcTemplate;
    @SuppressWarnings("MagicNumber")
    private final RowMapper<Link> rowMapper = (rs, rn) -> new Link(
        rs.getLong(1),
        rs.getString(2),
        new Timestamp(rs.getTimestamp(3).getTime()).toInstant().atOffset(ZoneOffset.UTC),
        new Timestamp(rs.getTimestamp(4).getTime()).toInstant().atOffset(ZoneOffset.UTC),
        new ArrayList<>(),
        BaseURL.valueOf(rs.getString(5)),
        Map2JsonConverter.json2Map(rs.getString(6))
    );

    @SuppressWarnings("MagicNumber")
    @Transactional
    public long add(@Valid Link link) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(c -> {
            PreparedStatement pst = c.prepareStatement(
                "INSERT INTO links(url, last_update, last_check, base_url, info) "
                    + "VALUES (?, ?, ?, ?::base_url_type, ?::JSON)",
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
    public void remove(@NotBlank String url) {
        jdbcTemplate.update("DELETE FROM links WHERE url = ?", url);
    }

    public List<@Valid Link> findAll() {
        return jdbcTemplate.query("SELECT * FROM links", rowMapper);
    }

    public Optional<@Valid Link> getLinkByURL(@NotBlank String url) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT * FROM links WHERE url = ?",
                rowMapper,
                url
            ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
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

    public List<@Valid Link> getAllLinksForChat(long chatId) {
        return jdbcTemplate.query(
            "SELECT * FROM links l JOIN links_chats lc ON l.link_id = lc.link_id WHERE lc.chat_id = ?",
            rowMapper,
            chatId
        );
    }

    public List<@Valid Link> getAllLinksWithLastCheckBeforeDuration(Duration duration) {
        return jdbcTemplate.query(
            "SELECT * FROM links WHERE last_check < ?",
            rowMapper,
            OffsetDateTime.now().minus(duration)
        );
    }

    @Transactional
    public void updateCheckedLinks(@NotNull List<Long> checkedLinkIds) {
        if (checkedLinkIds.isEmpty()) {
            return;
        }

        jdbcTemplate.update(
            String.format(
                "UPDATE links SET last_check = now() WHERE link_id IN (%s)",
                String.join(",", Collections.nCopies(checkedLinkIds.size(), "?"))
            ),
            checkedLinkIds.toArray()
        );
    }
}
