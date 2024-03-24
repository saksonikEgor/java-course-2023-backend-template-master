package edu.java.service.jpa;

import edu.java.dto.model.Chat;
import edu.java.exception.chat.ChatIsAlreadyRegisteredException;
import edu.java.exception.chat.ChatIsNotExistException;
import edu.java.repository.jpa.ChatJPARepository;
import edu.java.service.ChatService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatJPAService implements ChatService {
    private final ChatJPARepository chatRepository;

    @SuppressWarnings("MultipleStringLiterals")
    @Override
    @Transactional
    public void register(long chatId) throws ChatIsAlreadyRegisteredException {
        if (chatRepository.findById(chatId).isPresent()) {
            throw new ChatIsAlreadyRegisteredException("Chat with id '" + chatId + "' is already registered");
        }

        chatRepository.save(new Chat(chatId));
    }

    @Override
    @Transactional
    public void unregister(long chatId) throws ChatIsNotExistException {
        chatRepository.findById(chatId)
            .orElseThrow(() -> new ChatIsNotExistException("Chat with id '" + chatId + "' is not exist"));

        chatRepository.deleteById(chatId);
    }

    @Override
    public List<Chat> getTrackingChatsForLink(long linkId) {
        return chatRepository.findAllChatsByLinkId(linkId);
    }
}
