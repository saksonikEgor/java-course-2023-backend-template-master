package edu.java.service;

import edu.java.dto.model.Chat;
import org.apache.catalina.LifecycleState;
import java.util.List;

public interface ChatService {
    void register(long chatId);

    void unregister(long chatId);

    List<Chat> getTrackingChatsForLink(long linkId);
}
