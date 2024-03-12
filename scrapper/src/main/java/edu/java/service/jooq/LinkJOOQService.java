package edu.java.service.jooq;

import edu.java.dto.model.Link;
import edu.java.exception.chat.ChatIsNotExistException;
import edu.java.exception.link.LinkIsAlreadyTrackedException;
import edu.java.exception.link.LinkIsNotTrackingException;
import edu.java.respository.jooq.LinkJOOQRepository;
import edu.java.service.LinkService;
import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkJOOQService implements LinkService {
    private final LinkJOOQRepository linkRepository;

    @Override
    public void addLinkToChat(long chatId, Link link) throws ChatIsNotExistException, LinkIsAlreadyTrackedException {

    }

    @Override
    public void removeLinkFromChat(long chatId, String url) throws ChatIsNotExistException, LinkIsNotTrackingException {

    }

    @Override
    public List<Link> listAll(long chatId) throws ChatIsNotExistException {
        return null;
    }

    @Override
    public List<Link> listAll() {
        return null;
    }

    @Override
    public List<Link> listAllOldCheckLinks(Duration duration) {
        return null;
    }

    @Override
    public void resetLastUpdate(List<Link> updatedLinks) {

    }
}
