package edu.java.commands;

import com.google.gson.Gson;
import com.pengrad.telegrambot.model.Message;
import edu.java.client.ScrapperClient;
import edu.java.configuration.TelegramBotCommandConfiguration;
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
public class TelegramBotStartCommandTest {
    private final static long CHAT_ID = 123456L;
    @Autowired
    public Map<TelegramBotCommandType, TelegramBotCommandInfo> typeToInfo;
    @Autowired
    public String fatalExceptionMessage;
    @Autowired
    public String cross;
    private TelegramBotCommandInfo startCommandInfo;
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
        startCommandInfo = typeToInfo.get(TelegramBotCommandType.START);
    }

    @Test
    void callStartForAlreadyAuthenticatedChat() {
        ScrapperClient scrapperClient = Mockito.mock(ScrapperClient.class);

        TelegramBotStartCommand startCommand =
            new TelegramBotStartCommand(typeToInfo, scrapperClient, fatalExceptionMessage, cross);
        String response = startCommand.execute(message.text(), CHAT_ID);

        assertEquals(startCommandInfo.successfulResponse(), response);
    }

    @Test
    void callStartForUnauthenticatedChat() {
        String chatIsUnauthMessage = "Chat in unauthenticated";

        ScrapperClient scrapperClient = Mockito.mock(ScrapperClient.class);
        Mockito.doThrow(new ScrapperAPIException(chatIsUnauthMessage))
            .when(scrapperClient).registerChat(CHAT_ID);

        TelegramBotStartCommand startCommand =
            new TelegramBotStartCommand(typeToInfo, scrapperClient, fatalExceptionMessage, cross);
        String response = startCommand.execute(message.text(), CHAT_ID);

        assertEquals(cross + chatIsUnauthMessage, response);
    }
}
