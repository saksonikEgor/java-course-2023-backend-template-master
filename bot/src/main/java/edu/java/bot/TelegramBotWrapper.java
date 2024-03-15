package edu.java.bot;

import com.pengrad.telegrambot.UpdatesListener;
import edu.java.dto.request.LinkUpdateRequest;

public interface TelegramBotWrapper extends UpdatesListener, AutoCloseable {
    void start();

    void sendMessages(LinkUpdateRequest request);
}
