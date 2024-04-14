package edu.java.communication;

import edu.java.dto.request.LinkUpdateRequest;

public interface BotMessageSender {
    void sendUpdate(LinkUpdateRequest request);
}
