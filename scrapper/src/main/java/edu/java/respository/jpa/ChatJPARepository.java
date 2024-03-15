package edu.java.respository.jpa;

import edu.java.dto.model.Chat;
import edu.java.dto.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatJPARepository extends JpaRepository<Chat, Long> {
    @Query("select c from Chat c join c.links l WHERE l.linkId = :linkId")
    List<Chat> findAllChatsByLinkId(@Param("linkId") long linkId);


//    List<Chat> findAllByLinksC(long linkId);
}
