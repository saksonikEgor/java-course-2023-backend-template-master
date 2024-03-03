package edu.java.handler;

import com.google.gson.Gson;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.commands.TelegramBotHelpCommand;
import edu.java.commands.TelegramBotListCommand;
import edu.java.commands.TelegramBotStartCommand;
import edu.java.commands.TelegramBotTrackCommand;
import edu.java.commands.TelegramBotUntrackCommand;
import edu.java.configuration.TelegramBotCommandConfiguration;
import edu.java.configuration.ChatInputHandlerConfiguration;
import edu.java.repository.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserInputHandlerTest {
    private static final long USER_ID = 123456L;
    private static final String VALID_URI = "https://github.com/saksonikEgor/java-course-2023-backend-template-master";
    private static final String INVALID_URI = "invalid uri";
    private static final String WRONG_INPUT = "wrong_input";
    private final TelegramBotCommandConfiguration commandConfiguration = new TelegramBotCommandConfiguration();
    private ChatInputHandler handler;
    private UserDAO userDAO;

    @BeforeEach
    void createHandler() {
        userDAO = new UserDAO();

        ChatInputHandlerConfiguration handlerConfiguration = new ChatInputHandlerConfiguration(
            new TelegramBotHelpCommand(commandConfiguration, userDAO),
            new TelegramBotListCommand(commandConfiguration, userDAO),
            new TelegramBotStartCommand(commandConfiguration, userDAO),
            new TelegramBotTrackCommand(commandConfiguration, userDAO),
            new TelegramBotUntrackCommand(commandConfiguration, userDAO),
            commandConfiguration
        );

        handler = new ChatInputHandler(commandConfiguration, handlerConfiguration, userDAO);
    }

    @Test
    void handleWrongInputForAuthenticatedUser() {
        userDAO.addUser(USER_ID);

        Update update = new Gson().fromJson(
            "{message={chat={id=" + USER_ID + "}, text='" + WRONG_INPUT + "'}}",
            Update.class
        );

        assertEquals(
            new SendMessage(USER_ID, commandConfiguration.getWrongInputMessage()).getParameters(),
            handler.handle(update).getParameters()
        );
    }

    @Test
    void handleWrongInputForUnauthenticatedUser() {
        Update update = new Gson().fromJson(
            "{message={chat={id=" + USER_ID + "}, text='" + WRONG_INPUT + "'}}",
            Update.class
        );

        assertEquals(
            new SendMessage(USER_ID, commandConfiguration.getWrongInputMessage()).getParameters(),
            handler.handle(update).getParameters()
        );
    }

    @Test
    void handleTrackingInvalidLink() {
        userDAO.addUser(USER_ID);
        userDAO.makeTheUserWaitForTrack(USER_ID);

        Update update = new Gson().fromJson(
            "{message={chat={id=" + USER_ID + "}, text='" + INVALID_URI + "'}}",
            Update.class
        );

        assertEquals(
            new SendMessage(USER_ID, commandConfiguration.getWrongLinkFormatMessage()).getParameters(),
            handler.handle(update).getParameters()
        );
    }

    @Test
    void handleUntrackingInvalidLink() {
        userDAO.addUser(USER_ID);
        userDAO.makeTheUserWaitForUntrack(USER_ID);

        Update update = new Gson().fromJson(
            "{message={chat={id=" + USER_ID + "}, text='" + INVALID_URI + "'}}",
            Update.class
        );

        assertEquals(
            new SendMessage(USER_ID, commandConfiguration.getWrongLinkFormatMessage()).getParameters(),
            handler.handle(update).getParameters()
        );
    }

    @Test
    void handleTrackingValidLink() {
        userDAO.addUser(USER_ID);
        userDAO.makeTheUserWaitForTrack(USER_ID);

        Update update = new Gson().fromJson(
            "{message={chat={id=" + USER_ID + "}, " +
                "text='" + VALID_URI + "'}}",
            Update.class
        );

        assertEquals(
            new SendMessage(USER_ID, commandConfiguration.getSuccessfulTrackingMessage()).getParameters(),
            handler.handle(update).getParameters()
        );
    }

    @Test
    void handleUntrackingValidLink() {
        userDAO.addUser(USER_ID);
        userDAO.makeTheUserWaitForTrack(USER_ID);
        userDAO.handleURIForUser(USER_ID, VALID_URI);
        userDAO.makeTheUserWaitForUntrack(USER_ID);

        Update update = new Gson().fromJson(
            "{message={chat={id=" + USER_ID + "}, " +
                "text='" + VALID_URI + "'}}",
            Update.class
        );

        assertEquals(
            new SendMessage(USER_ID, commandConfiguration.getSuccessfulUntrackingMessage()).getParameters(),
            handler.handle(update).getParameters()
        );
    }

    @Test
    void handleAddingDuplicateLink() {
        userDAO.addUser(USER_ID);
        userDAO.makeTheUserWaitForTrack(USER_ID);
        userDAO.handleURIForUser(USER_ID, VALID_URI);
        userDAO.makeTheUserWaitForTrack(USER_ID);

        Update update = new Gson().fromJson(
            "{message={chat={id=" + USER_ID + "}, " +
                "text='" + VALID_URI + "'}}",
            Update.class
        );

        assertEquals(
            new SendMessage(USER_ID, commandConfiguration.getLinkIsAlreadyTrackedMessage()).getParameters(),
            handler.handle(update).getParameters()
        );
    }

    @Test
    void handleRemovingNotExistingLink() {
        userDAO.addUser(USER_ID);
        userDAO.makeTheUserWaitForUntrack(USER_ID);

        Update update = new Gson().fromJson(
            "{message={chat={id=" + USER_ID + "}, " +
                "text='" + VALID_URI + "'}}",
            Update.class
        );

        assertEquals(
            new SendMessage(USER_ID, commandConfiguration.getLinkIsNotTrackingMessage()).getParameters(),
            handler.handle(update).getParameters()
        );
    }
}
