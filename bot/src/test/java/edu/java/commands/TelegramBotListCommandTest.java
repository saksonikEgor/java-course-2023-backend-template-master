package edu.java.commands;

import com.google.gson.Gson;
import com.pengrad.telegrambot.model.Message;
import edu.java.client.ScrapperClient;
import edu.java.configuration.TelegramBotCommandConfiguration;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;
import edu.java.exception.ScrapperAPIException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
public class TelegramBotListCommandTest {
    private final static long CHAT_ID = 123456L;
    @Autowired
    public Map<TelegramBotCommandType, TelegramBotCommandInfo> typeToInfo;
    @Autowired
    public String fatalExceptionMessage;
    @Autowired
    public String cross;
    private TelegramBotCommandInfo listCommandInfo;
    private Message message;

    @BeforeEach
    void createMessage() {
        message = new Gson().fromJson(
            "{chat={id=" + CHAT_ID + "}}",
            Message.class
        );
    }

    @BeforeEach
    void setCommandInfo() {
        listCommandInfo = typeToInfo.get(TelegramBotCommandType.LIST);
    }

    @Test
    void callEmptyListForAuthenticatedChat() {
        ScrapperClient scrapperClient = Mockito.mock(ScrapperClient.class);
        Mockito.when(scrapperClient.getLinks(CHAT_ID))
            .thenReturn(new ListLinksResponse(Collections.emptyList(), 0));

        TelegramBotListCommand listCommand =
            new TelegramBotListCommand(typeToInfo, scrapperClient, fatalExceptionMessage, cross);
        String response = listCommand.execute(message.text(), CHAT_ID);

        assertEquals(listCommandInfo.unSuccessfulResponse(), response);
    }

    @Test
    void callNotEmptyListForAuthenticatedChat() {
        List<LinkResponse> links = List.of(
            new LinkResponse(1, "https://github.com/saksonikEgor/java-course-2023-backend-template-master"),
            new LinkResponse(2, "https://github.com/saksonikEgor/Checkers")
        );

        ScrapperClient scrapperClient = Mockito.mock(ScrapperClient.class);
        Mockito.when(scrapperClient.getLinks(CHAT_ID))
            .thenReturn(new ListLinksResponse(links, 2));

        TelegramBotListCommand listCommand =
            new TelegramBotListCommand(typeToInfo, scrapperClient, fatalExceptionMessage, cross);
        String response = listCommand.execute(message.text(), CHAT_ID);

        assertEquals(
            listCommandInfo.successfulResponse() + links.stream()
                .map(LinkResponse::url)
                .collect(Collectors.joining("\n----------------\n", "----------------\n", "\n----------------")),
            response
        );
    }

    @Test
    void callListForUnauthenticatedChat() {
        String chatIsUnauthMessage = "Chat in unauthenticated";

        ScrapperClient scrapperClient = Mockito.mock(ScrapperClient.class);
        Mockito.when(scrapperClient.getLinks(CHAT_ID))
            .thenThrow(new ScrapperAPIException(chatIsUnauthMessage));

        TelegramBotListCommand listCommand =
            new TelegramBotListCommand(typeToInfo, scrapperClient, fatalExceptionMessage, cross);
        String response = listCommand.execute(message.text(), CHAT_ID);

        assertEquals(cross + chatIsUnauthMessage, response);
    }
}
