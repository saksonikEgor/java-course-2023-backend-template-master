package edu.java.service.jdbc;

import edu.java.dto.model.Chat;
import edu.java.dto.model.ChatState;
import edu.java.respository.jdbc.ChatJDBCRepository;
import edu.java.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatJDBCService implements ChatService {
    private final ChatJDBCRepository chatRepository;

    @Override
    public void register(long chatId) {
        chatRepository.getChatById(chatId)
                .ifPresentOrElse(
                        chat -> chatRepository.changeState(chatId, ChatState.REGISTERED),
                        () -> chatRepository.add(new Chat(chatId))
                );
    }

    @Override
    public void unregister(long chatId) {
        chatRepository.remove(chatId);
    }

    @Override
    public List<Chat> getTrackingChatsForLink(long linkId) {
        return chatRepository.getAllChatsForLink(linkId);
    }
}
