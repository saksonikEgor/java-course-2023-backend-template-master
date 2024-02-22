package edu.java.commands;

import com.google.gson.Gson;
import com.pengrad.telegrambot.model.Message;
import edu.java.configuration.TelegramBotCommandConfiguration;
import edu.java.repository.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TelegramBotTrackCommandTest {
    private final static long USER_ID = 123456L;
    private final TelegramBotCommandConfiguration commandConfiguration = new TelegramBotCommandConfiguration();
    private TelegramBotCommandInfo trackCommandInfo;
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
        trackCommandInfo = commandConfiguration.getTypeToInfo().get(TelegramBotCommandType.TRACK);
    }

    @Test
    void callTrackForAuthenticatedUser() {
        UserDAO userDAO = new UserDAO();
        userDAO.addUser(USER_ID);

        TelegramBotTrackCommand trackCommand = new TelegramBotTrackCommand(commandConfiguration, userDAO);
        String response = trackCommand.execute(message);

        assertEquals(trackCommandInfo.successfulResponse(), response);
    }

    @Test
    void callTrackForUnauthenticatedUser() {
        TelegramBotTrackCommand trackCommand = new TelegramBotTrackCommand(commandConfiguration, new UserDAO());
        String response = trackCommand.execute(message);

        assertEquals(commandConfiguration.getNotAuthenticatedErrorMessage(), response);
    }
}
