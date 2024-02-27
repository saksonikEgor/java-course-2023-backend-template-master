package edu.java.service;

import edu.java.dto.model.Link;
import java.net.URI;
import java.util.Collection;

public interface LinkService {
    void addLinkToChat(long chatId, URI url);

    void removeLinkFromChat(long chatId, URI url);

    Collection<Link> listAll(long chatId);

    void update(Link link);
}
