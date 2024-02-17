package edu.java.commands;

import com.google.gson.Gson;
import com.pengrad.telegrambot.model.Message;
import edu.java.configuration.TelegramBotCommandConfiguration;
import edu.java.repository.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TelegramBotHelpCommandTest {
    private final static long USER_ID = 123456L;
    private final TelegramBotCommandConfiguration commandConfiguration = new TelegramBotCommandConfiguration();
    private TelegramBotCommandInfo helpCommandInfo;
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
        helpCommandInfo = commandConfiguration.getInfoByType().get(TelegramBotCommandType.HELP);
    }

    @Test
    void callHelpForAuthenticatedUser() {
        UserDAO userDAO = new UserDAO();
        userDAO.addUser(USER_ID);

        TelegramBotHelpCommand helpCommand = new TelegramBotHelpCommand(commandConfiguration, userDAO);
        String response = helpCommand.execute(message);

        assertEquals(helpCommandInfo.successfulResponse(), response);
    }

    @Test
    void callHelpForUnauthenticatedUser() {
        TelegramBotHelpCommand helpCommand = new TelegramBotHelpCommand(commandConfiguration, new UserDAO());
        String response = helpCommand.execute(message);

        assertEquals(helpCommandInfo.successfulResponse(), response);
    }
}
