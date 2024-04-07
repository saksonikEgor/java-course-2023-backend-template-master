package edu.java.configuration.dataAccess;

import edu.java.service.ChatService;
import edu.java.service.LinkService;

public interface AccessConfiguration {
    ChatService chatService();

    LinkService linkService();
}
