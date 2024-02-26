package edu.java.commands;

import com.google.gson.Gson;
import com.pengrad.telegrambot.model.Message;
import edu.java.configuration.TelegramBotCommandConfiguration;
import edu.java.repository.UserDAO;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TelegramBotListCommandTest {
    private final static long USER_ID = 123456L;
    private final TelegramBotCommandConfiguration commandConfiguration = new TelegramBotCommandConfiguration();
    private TelegramBotCommandInfo listCommandInfo;
    private Message message;

    @BeforeEach
    void createMessage() {
        message = new Gson().fromJson(
            "{chat={id=" + USER_ID + "}}",
            Message.class
        );
    }

    @BeforeEach
    void setCommandInfo() {
        listCommandInfo = commandConfiguration.getTypeToInfo().get(TelegramBotCommandType.LIST);
    }

    @Test
    void callEmptyListForAuthenticatedUser() {
        UserDAO userDAOMock = Mockito.mock(UserDAO.class);
        Mockito.when(userDAOMock.getAllURIOfUser(USER_ID)).thenReturn(Collections.emptyList());

        TelegramBotListCommand listCommand = new TelegramBotListCommand(commandConfiguration, userDAOMock);
        String response = listCommand.execute(message);

        assertEquals(listCommandInfo.unSuccessfulResponse(), response);
    }

    @Test
    void callNotEmptyListForAuthenticatedUser() {
        List<URI> refs = List.of(
            URI.create("https://github.com/pengrad/java-telegram-bot-api/tree/master"),
            URI.create("https://github.com/saksonikEgor/JavaBackendCourse"),
            URI.create("https://github.com/saksonikEgor/java-course-2023-backend-template-master")
        );

        UserDAO userDAOMock = Mockito.mock(UserDAO.class);
        Mockito.when(userDAOMock.getAllURIOfUser(USER_ID)).thenReturn(refs);

        TelegramBotListCommand listCommand = new TelegramBotListCommand(commandConfiguration, userDAOMock);
        String response = listCommand.execute(message);

        assertEquals(
            listCommandInfo.successfulResponse() + refs.stream()
                .map(URI::toString)
                .collect(Collectors.joining("\n----------------\n", "----------------\n", "\n----------------")),
            response
        );
    }

    @Test
    void callListForUnauthenticatedUser() {
        TelegramBotListCommand listCommand = new TelegramBotListCommand(commandConfiguration, new UserDAO());
        String response = listCommand.execute(message);

        assertEquals(commandConfiguration.getNotAuthenticatedErrorMessage(), response);
    }
}
