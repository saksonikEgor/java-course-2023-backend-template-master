package edu.java.service.jdbc;

import edu.java.dto.model.Chat;
import edu.java.dto.model.Link;
import edu.java.exception.chat.ChatIsNotExistException;
import edu.java.respository.jdbc.ChatJDBCRepository;
import edu.java.respository.jdbc.LinkJDBCRepository;
import edu.java.service.LinkService;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkJDBCService implements LinkService {
    private final ChatJDBCRepository chatRepository;
    private final LinkJDBCRepository linkRepository;

    @Override
    public void addLinkToChat(long chatId, URI url) throws ChatIsNotExistException {
        if (chatRepository.getChatById(chatId).isEmpty()) {
            throw new ChatIsNotExistException("Chat with id:" + chatId + " is not exist");
        }

        long linkId = linkRepository.getLinkByURI(url.toString())
            .map(Link::getLinkId)
            .orElse(linkRepository.add(new Link(url.toString())));

        linkRepository.addLinkToChat(linkId, chatId);
    }

    @Override
    public void removeLinkFromChat(long chatId, URI url) {
        String stringURL = url.toString();

        if (chatRepository.getChatById(chatId).isEmpty()) {
            throw new ChatIsNotExistException("Chat with id:" + chatId + " is not exist");
        }

        long linkId = linkRepository.getLinkByURI(stringURL)
            .map(Link::getLinkId)
            .orElse(linkRepository.add(new Link(stringURL)));

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
    public Collection<Link> listAll(long chatId) {
        return linkRepository.findAll();
    }

    @Override
    public void updateAllLinks() {
        //TODO
//        linkRepository.getLinkByURI(link.getUrl())
//            .orElse(new Link(url.toString()));

    }
}
