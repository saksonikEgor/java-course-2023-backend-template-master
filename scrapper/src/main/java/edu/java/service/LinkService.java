package edu.java.service;

import edu.java.dto.model.Link;
import java.net.URI;
import java.time.Duration;
import java.util.List;

public interface LinkService {
    void addLinkToChat(long chatId, Link link);

    void removeLinkFromChat(long chatId, URI url);

    List<Link> listAll(long chatId);

    List<Link> listAll();

    List<Link> listAllOldCheckLinks(Duration duration);
}
