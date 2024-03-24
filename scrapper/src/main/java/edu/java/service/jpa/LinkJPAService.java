package edu.java.service.jpa;

import edu.java.dto.model.Chat;
import edu.java.dto.model.Link;
import edu.java.exception.chat.ChatIsNotExistException;
import edu.java.exception.link.LinkIsAlreadyTrackedException;
import edu.java.exception.link.LinkIsNotTrackingException;
import edu.java.repository.jpa.ChatJPARepository;
import edu.java.repository.jpa.LinkJPARepository;
import edu.java.service.LinkService;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LinkJPAService implements LinkService {
    private final LinkJPARepository linkRepository;
    private final ChatJPARepository chatRepository;

    @Override
    @Transactional
    public void addLinkToChat(long chatId, Link link) throws ChatIsNotExistException, LinkIsAlreadyTrackedException {
        Chat chat = chatRepository.findById(chatId)
            .orElseThrow(() -> new ChatIsNotExistException("Chat with id '" + chatId + "' is not exist"));

        if (chat.getLinks().stream()
            .anyMatch(l -> Objects.equals(l.getUrl(), link.getUrl()))
        ) {
            throw new LinkIsAlreadyTrackedException("Link with url '" + link.getUrl() + "' is already tracked");
        }

        Link fetchedLink = linkRepository.findLinkByUrl(link.getUrl())
            .orElseGet(() -> linkRepository.save(link));

        fetchedLink.getChats().add(chat);
        chat.getLinks().add(fetchedLink);

        linkRepository.save(fetchedLink);
        chatRepository.save(chat);
    }

    @Override
    @Transactional
    public void removeLinkFromChat(long chatId, String url) throws ChatIsNotExistException, LinkIsNotTrackingException {
        Chat chat = chatRepository.findById(chatId)
            .orElseThrow(() -> new ChatIsNotExistException("Chat with id '" + chatId + "' is not exist"));

        Link link = linkRepository.findLinkByChatIdAndUrl(chatId, url)
            .orElseThrow(() -> new LinkIsNotTrackingException("Link with url:" + url + " is not tracking"));

        chat.getLinks().remove(link);
        link.getChats().remove(chat);

        linkRepository.save(link);
        chatRepository.save(chat);

        if (link.getChats().isEmpty()) {
            linkRepository.delete(link);
        }
    }

    @Override
    public List<Link> listAll(long chatId) throws ChatIsNotExistException {
        Chat chat = chatRepository.findById(chatId)
            .orElseThrow(() -> new ChatIsNotExistException("Chat with id '" + chatId + "' is not exist"));

        return chat.getLinks();
    }

    @Override
    public List<Link> listAll() {
        return linkRepository.findAll();
    }

    @Override
    public List<Link> listAllOldCheckLinks(Duration duration) {
        List<Link> checkedLinks = linkRepository.findAllByLastCheckBefore(OffsetDateTime.now().minus(duration));

        checkedLinks.forEach(l -> l.setLastCheck(OffsetDateTime.now()));
        linkRepository.saveAll(checkedLinks);

        return checkedLinks;
    }

    @Override
    @Transactional
    public void resetLastUpdate(List<Link> updatedLinks) {
        updatedLinks.forEach(l -> l.setLastUpdate(OffsetDateTime.now()));
        linkRepository.saveAll(updatedLinks);
    }
}
