package edu.java.commands;

import com.google.gson.Gson;
import com.pengrad.telegrambot.model.Message;
import edu.java.configuration.TelegramBotCommandConfiguration;
import edu.java.repository.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TelegramBotUntrackCommandTest {
    private final static long USER_ID = 123456L;
    private final TelegramBotCommandConfiguration commandConfiguration = new TelegramBotCommandConfiguration();
    private TelegramBotCommandInfo untrackCommandInfo;
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
        untrackCommandInfo = commandConfiguration.getTypeToInfo().get(TelegramBotCommandType.UNTRACK);
    }

    @Test
    void callTrackForAuthenticatedUser() {
        UserDAO userDAO = new UserDAO();
        userDAO.addUser(USER_ID);

        TelegramBotUntrackCommand untrackCommand = new TelegramBotUntrackCommand(commandConfiguration, userDAO);
        String response = untrackCommand.execute(message);

        assertEquals(untrackCommandInfo.successfulResponse(), response);
    }

    @Test
    void callTrackForUnauthenticatedUser() {
        TelegramBotUntrackCommand untrackCommand = new TelegramBotUntrackCommand(commandConfiguration, new UserDAO());
        String response = untrackCommand.execute(message);

        assertEquals(commandConfiguration.getNotAuthenticatedErrorMessage(), response);
    }
}
