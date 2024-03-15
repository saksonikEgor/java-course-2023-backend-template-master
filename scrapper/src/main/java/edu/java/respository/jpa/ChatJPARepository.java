package edu.java.respository.jpa;

import edu.java.dto.model.Chat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatJPARepository extends JpaRepository<Chat, Long> {
    @Query("select c from Chat c join c.links l WHERE l.linkId = :linkId")
    List<Chat> findAllChatsByLinkId(@Param("linkId") long linkId);
}
