package edu.java.service;

import edu.java.dto.model.Link;
import edu.java.exception.chat.ChatIsNotExistException;
import edu.java.exception.link.LinkIsAlreadyTrackedException;
import edu.java.exception.link.LinkIsNotTrackingException;
import java.time.Duration;
import java.util.List;

public interface LinkService {
    void addLinkToChat(long chatId, Link link) throws ChatIsNotExistException, LinkIsAlreadyTrackedException;

    void removeLinkFromChat(long chatId, String url) throws ChatIsNotExistException, LinkIsNotTrackingException;

    List<Link> listAll(long chatId) throws ChatIsNotExistException;

    List<Link> listAll();

    List<Link> listAllOldCheckLinks(Duration duration);

    void resetLastUpdate(List<Link> updatedLinks);
}
