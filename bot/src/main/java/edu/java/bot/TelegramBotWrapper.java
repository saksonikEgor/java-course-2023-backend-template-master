package edu.java.bot;

import com.pengrad.telegrambot.UpdatesListener;
import edu.java.dto.request.LinkUpdateRequest;
import java.util.List;

public interface TelegramBotWrapper extends UpdatesListener, AutoCloseable {
    void start();

    void sendMessages(LinkUpdateRequest request);
}
