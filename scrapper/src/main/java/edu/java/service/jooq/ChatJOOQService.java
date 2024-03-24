package edu.java.service.jooq;

import edu.java.dto.model.Chat;
import edu.java.exception.chat.ChatIsAlreadyRegisteredException;
import edu.java.exception.chat.ChatIsNotExistException;
import edu.java.repository.jooq.ChatJOOQRepository;
import edu.java.service.ChatService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatJOOQService implements ChatService {
    private final ChatJOOQRepository chatRepository;

    @SuppressWarnings("MultipleStringLiterals")
    @Override
    public void register(long chatId) throws ChatIsAlreadyRegisteredException {
        if (chatRepository.getChatById(chatId).isPresent()) {
            throw new ChatIsAlreadyRegisteredException("Chat with id '" + chatId + "' is already registered");
        }

        chatRepository.add(new Chat(chatId));
    }

    @Override
    public void unregister(long chatId) throws ChatIsNotExistException {
        chatRepository.getChatById(chatId)
            .orElseThrow(() -> new ChatIsNotExistException("Chat with id '" + chatId + "' is not exist"));

        chatRepository.remove(chatId);
    }

    @Override
    public List<Chat> getTrackingChatsForLink(long linkId) {
        return chatRepository.getAllChatsForLink(linkId);
    }
}
