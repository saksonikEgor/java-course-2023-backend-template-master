package edu.java.bot;

import com.pengrad.telegrambot.UpdatesListener;

public interface TelegramBotWrapper extends UpdatesListener, AutoCloseable {
    void start();
}
