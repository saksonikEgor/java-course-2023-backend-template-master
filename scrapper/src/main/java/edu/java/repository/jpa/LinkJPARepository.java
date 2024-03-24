package edu.java.repository.jpa;

import edu.java.dto.model.Link;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

@Repository
@Validated
public interface LinkJPARepository extends JpaRepository<Link, Long> {
    Optional<@Valid Link> findLinkByUrl(String url);

    @Query("select l from Link l join l.chats c WHERE c.chatId = :chatId and l.url = :url")
    Optional<@Valid Link> findLinkByChatIdAndUrl(@Param("chatId") long chatId, @Param("url") String url);

    List<@Valid Link> findAllByLastCheckBefore(OffsetDateTime time);
}
