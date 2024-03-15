package edu.java.commands;

import com.google.gson.Gson;
import com.pengrad.telegrambot.model.Message;
import edu.java.client.ScrapperClient;
import edu.java.configuration.TelegramBotCommandConfiguration;
import edu.java.dto.request.AddLinkRequest;
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
public class TelegramBotTrackCommandTest {
    @Autowired
    public Map<TelegramBotCommandType, TelegramBotCommandInfo> typeToInfo;
    @Autowired
    public String fatalExceptionMessage;
    @Autowired
    public String cross;
    private final static long CHAT_ID = 123456L;
    private TelegramBotCommandInfo trackCommandInfo;
    private final String link = "https://github.com/saksonikEgor/java-course-2023-backend-template-master";

    @BeforeEach
    void setCommandInfo() {
        trackCommandInfo = typeToInfo.get(TelegramBotCommandType.TRACK);
    }

    @Test
    void callTrackForAuthenticatedChat() {
        ScrapperClient scrapperClient = Mockito.mock(ScrapperClient.class);

        TelegramBotTrackCommand trackCommand =
            new TelegramBotTrackCommand(typeToInfo, scrapperClient, fatalExceptionMessage, cross);
        String response = trackCommand.execute(link, CHAT_ID);

        assertEquals(trackCommandInfo.successfulResponse(), response);
    }

    @Test
    void callTrackForUnauthenticatedChat() {
        String chatIsUnauthMessage = "Chat in unauthenticated";

        ScrapperClient scrapperClient = Mockito.mock(ScrapperClient.class);
        Mockito.when(scrapperClient.addLink(CHAT_ID, new AddLinkRequest(link)))
            .thenThrow(new ScrapperAPIException(chatIsUnauthMessage));

        TelegramBotTrackCommand trackCommand =
            new TelegramBotTrackCommand(typeToInfo, scrapperClient, fatalExceptionMessage, cross);
        String response = trackCommand.execute(link, CHAT_ID);

        assertEquals(cross + chatIsUnauthMessage, response);
    }
}
