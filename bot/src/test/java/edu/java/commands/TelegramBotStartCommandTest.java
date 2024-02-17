package edu.java.commands;

import com.google.gson.Gson;
import com.pengrad.telegrambot.model.Message;
import edu.java.configuration.TelegramBotCommandConfiguration;
import edu.java.repository.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TelegramBotStartCommandTest {
    private final static long USER_ID = 123456L;
    private final TelegramBotCommandConfiguration commandConfiguration = new TelegramBotCommandConfiguration();
    private TelegramBotCommandInfo startCommandInfo;
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
        startCommandInfo = commandConfiguration.getInfoByType().get(TelegramBotCommandType.START);
    }

    @Test
    void callStartForAlreadyAuthenticatedUser() {
        UserDAO userDAO = new UserDAO();
        userDAO.addUser(USER_ID);

        TelegramBotStartCommand startCommand = new TelegramBotStartCommand(commandConfiguration, userDAO);
        String response = startCommand.execute(message);

        assertEquals(startCommandInfo.successfulResponse(), response);
    }

    @Test
    void callStartForUnauthenticatedUser() {
        TelegramBotStartCommand startCommand = new TelegramBotStartCommand(commandConfiguration, new UserDAO());
        String response = startCommand.execute(message);

        assertEquals(startCommandInfo.successfulResponse(), response);
    }
}
