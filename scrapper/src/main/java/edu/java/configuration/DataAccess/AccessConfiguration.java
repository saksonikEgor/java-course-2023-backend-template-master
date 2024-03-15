package edu.java.configuration.DataAccess;

import edu.java.service.ChatService;
import edu.java.service.LinkService;

public interface AccessConfiguration {
    ChatService chatService();

    LinkService linkService();
}
