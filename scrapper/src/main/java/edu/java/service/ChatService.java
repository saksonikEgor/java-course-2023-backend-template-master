package edu.java.service;

import edu.java.dto.model.Chat;
import edu.java.exception.chat.ChatIsAlreadyRegisteredException;
import edu.java.exception.chat.ChatIsNotExistException;
import java.util.List;

public interface ChatService {
    void register(long chatId) throws ChatIsAlreadyRegisteredException;

    void unregister(long chatId) throws ChatIsNotExistException;

    List<Chat> getTrackingChatsForLink(long linkId);
}
