package edu.java.commands;

import edu.java.client.ScrapperClient;
import edu.java.configuration.TelegramBotCommandConfiguration;
import edu.java.dto.request.RemoveLinkRequest;
import edu.java.exception.ScrapperAPIException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TelegramBotCommandConfiguration.class)
public class TelegramBotUntrackCommandTest {
    private final static long CHAT_ID = 123456L;
    private final String link = "https://github.com/saksonikEgor/java-course-2023-backend-template-master";
    @Autowired
    public Map<TelegramBotCommandType, TelegramBotCommandInfo> typeToInfo;
    @Autowired
    public String fatalExceptionMessage;
    @Autowired
    public String cross;
    private TelegramBotCommandInfo untrackCommandInfo;

    @BeforeEach
    void setCommandInfo() {
        untrackCommandInfo = typeToInfo.get(TelegramBotCommandType.UNTRACK);
    }

    @Test
    void callTrackForAuthenticatedChat() {
        ScrapperClient scrapperClient = Mockito.mock(ScrapperClient.class);

        TelegramBotUntrackCommand untrackCommand =
            new TelegramBotUntrackCommand(typeToInfo, scrapperClient, fatalExceptionMessage, cross);
        String response = untrackCommand.execute(link, CHAT_ID);

        assertEquals(untrackCommandInfo.successfulResponse(), response);
    }

    @Test
    void callTrackForUnauthenticatedUser() {
        String chatIsUnauthMessage = "Chat in unauthenticated";

        ScrapperClient scrapperClient = Mockito.mock(ScrapperClient.class);
        Mockito.when(scrapperClient.deleteLink(CHAT_ID, new RemoveLinkRequest(link)))
            .thenThrow(new ScrapperAPIException(chatIsUnauthMessage));

        TelegramBotUntrackCommand untrackCommand =
            new TelegramBotUntrackCommand(typeToInfo, scrapperClient, fatalExceptionMessage, cross);
        String response = untrackCommand.execute(link, CHAT_ID);

        assertEquals(cross + chatIsUnauthMessage, response);
    }
}
