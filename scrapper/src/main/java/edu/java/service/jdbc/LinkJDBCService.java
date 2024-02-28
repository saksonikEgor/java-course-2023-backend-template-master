package edu.java.service.jdbc;

import edu.java.dto.model.Chat;
import edu.java.dto.model.Link;
import edu.java.exception.chat.ChatIsNotExistException;
import edu.java.exception.link.LinkIsNotTrackingException;
import edu.java.respository.jdbc.ChatJDBCRepository;
import edu.java.respository.jdbc.LinkJDBCRepository;
import edu.java.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkJDBCService implements LinkService {
    private final ChatJDBCRepository chatRepository;
    private final LinkJDBCRepository linkRepository;

    @Override
    public void addLinkToChat(long chatId, Link link) throws ChatIsNotExistException {
        if (chatRepository.getChatById(chatId).isEmpty()) {
            throw new ChatIsNotExistException("Chat with id:" + chatId + " is not exist");
        }

        long linkId = linkRepository.getLinkByURI(link.getUrl())
                .map(Link::getLinkId)
                .orElse(linkRepository.add(link));

        linkRepository.addLinkToChat(linkId, chatId);
    }

    @Override
    public void removeLinkFromChat(long chatId, URI url) throws ChatIsNotExistException {
        String stringURL = url.toString();

        if (chatRepository.getChatById(chatId).isEmpty()) {
            throw new ChatIsNotExistException("Chat with id:" + chatId + " is not exist");
        }

        long linkId = linkRepository.getLinkByURI(stringURL)
                .map(Link::getLinkId)
                .orElseThrow(() -> new LinkIsNotTrackingException("Link with url:" + url + " is not tracking"));

        linkRepository.removeLinkFromChat(linkId, chatId);

        if (getChatsForLink(stringURL).isEmpty()) {
            removeLink(stringURL);
        }
    }

    private List<Chat> getChatsForLink(String stringURL) {
        return chatRepository.getAllChatsForLink(stringURL);
    }

    private void removeLink(String stringURL) {
        linkRepository.remove(stringURL);
    }

    @Override
    public List<Link> listAll(long chatId) {
        return linkRepository.getAllLinksForChat(chatId);
    }

    @Override
    public List<Link> listAll() {
        return linkRepository.findAll();
    }
}
