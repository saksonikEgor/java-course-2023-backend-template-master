package edu.java.service.jdbc;

import edu.java.dto.model.Chat;
import edu.java.dto.model.Link;
import edu.java.exception.chat.ChatIsNotExistException;
import edu.java.exception.link.LinkIsAlreadyTrackedException;
import edu.java.exception.link.LinkIsNotTrackingException;
import edu.java.respository.jdbc.ChatJDBCRepository;
import edu.java.respository.jdbc.LinkJDBCRepository;
import edu.java.service.LinkService;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkJDBCService implements LinkService {
    private final ChatJDBCRepository chatRepository;
    private final LinkJDBCRepository linkRepository;

    @Override
    public void addLinkToChat(long chatId, Link link) throws ChatIsNotExistException, LinkIsAlreadyTrackedException {
        if (
            listAll(chatId).stream()
                .anyMatch(l -> Objects.equals(l.getUrl(), link.getUrl()))
        ) {
            throw new LinkIsAlreadyTrackedException("Link with url '" + link.getUrl() + "' is already tracked");
        }

        long linkId = linkRepository.getLinkByURL(link.getUrl())
            .map(Link::getLinkId)
            .orElseGet(() -> linkRepository.add(link));

        linkRepository.addLinkToChat(linkId, chatId);
    }

    @Override
    public void removeLinkFromChat(long chatId, String url) throws ChatIsNotExistException, LinkIsNotTrackingException {
        long linkId = listAll(chatId).stream()
            .filter(l -> Objects.equals(l.getUrl(), url))
            .findAny()
            .orElseThrow(() -> new LinkIsNotTrackingException("Link with url:" + url + " is not tracking"))
            .getLinkId();

        linkRepository.removeLinkFromChat(linkId, chatId);

        if (getChatsForLink(linkId).isEmpty()) {
            linkRepository.remove(url);
        }
    }

    private List<Chat> getChatsForLink(long linkId) {
        return chatRepository.getAllChatsForLink(linkId);
    }

    @Override
    public List<Link> listAll(long chatId) throws ChatIsNotExistException {
        chatRepository.getChatById(chatId)
            .orElseThrow(() -> new ChatIsNotExistException("Chat with id '" + chatId + "' is not exist"));

        return linkRepository.getAllLinksForChat(chatId);
    }

    @Override
    public List<Link> listAll() {
        return linkRepository.findAll();
    }

    @Override
    public List<Link> listAllOldCheckLinks(Duration duration) {
        List<Link> checkedLinks = linkRepository.getAllLinksWithLastCheckBeforeDuration(duration);

        linkRepository.updateCheckedLinks(
            checkedLinks.stream()
                .map(Link::getLinkId)
                .toList()
        );

        return checkedLinks;
    }
}
