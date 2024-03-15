package edu.java.handler;

import com.google.gson.Gson;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.commands.TelegramBotCommand;
import edu.java.commands.TelegramBotCommandInfo;
import edu.java.commands.TelegramBotCommandType;
import edu.java.commands.TelegramBotHelpCommand;
import edu.java.commands.TelegramBotListCommand;
import edu.java.commands.TelegramBotStartCommand;
import edu.java.commands.TelegramBotTrackCommand;
import edu.java.commands.TelegramBotUntrackCommand;
import edu.java.configuration.TelegramBotCommandConfiguration;
import edu.java.handler.parser.MessageExecutor;
import edu.java.handler.parser.OneWordMessageExecutor;
import edu.java.handler.parser.TwoWordMessageExecutor;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TelegramBotCommandConfiguration.class)
public class UserInputHandlerTest {
    @Autowired
    public Map<TelegramBotCommandType, TelegramBotCommandInfo> typeToInfo;
    @Autowired
    public Map<String, TelegramBotCommandType> nameToType;
    @Autowired
    public String fatalExceptionMessage;
    @Autowired
    public String cross;
    @Autowired
    public String wrongInputMessage;
    public String telegramBotName = "LinkUpdateTrackerBot";
    private static final String VALID_URI = "https://github.com/saksonikEgor/java-course-2023-backend-template-master";
    private static final long CHAT_ID = 123456L;
    private static final String WRONG_INPUT = "wrong_input";
    private ChatInputHandler handler;

    @Mock TelegramBotHelpCommand helpCommand;
    @Mock TelegramBotListCommand listCommand;
    @Mock TelegramBotStartCommand startCommand;
    @Mock TelegramBotTrackCommand trackCommand;
    @Mock TelegramBotUntrackCommand untrackCommand;

    @BeforeEach
    void createHandler() {
        Map<TelegramBotCommandType, TelegramBotCommand> typeToOneWordCommand = Map.of(
            TelegramBotCommandType.HELP, helpCommand,
            TelegramBotCommandType.LIST, listCommand,
            TelegramBotCommandType.START, startCommand
        );
        Map<TelegramBotCommandType, TelegramBotCommand> typeToTwoWordCommand = Map.of(
            TelegramBotCommandType.TRACK, trackCommand,
            TelegramBotCommandType.UNTRACK, untrackCommand
        );

        MessageExecutor chainsOfParsers = new OneWordMessageExecutor(
            new TwoWordMessageExecutor(null, nameToType, typeToTwoWordCommand, telegramBotName),
            nameToType,
            typeToOneWordCommand
        );

        handler = new ChatInputHandler(chainsOfParsers, wrongInputMessage);
    }

    @Test
    void handleWrongInput() {
        Update update = new Gson().fromJson(
            "{message={chat={id=" + CHAT_ID + "}, text='" + WRONG_INPUT + "'}}",
            Update.class
        );

        assertEquals(
            new SendMessage(CHAT_ID, wrongInputMessage).getParameters(),
            handler.handle(update).getParameters()
        );
    }

    @Test
    void handleTrackingValidLink() {
        Update update = new Gson().fromJson(
            "{message={chat={id=" + CHAT_ID + "}, " +
                "text='@LinkUpdateTrackerBot /track " + VALID_URI + "'}}",
            Update.class
        );

        handler.handle(update);

        Mockito.verify(trackCommand, Mockito.times(1))
            .execute(VALID_URI, CHAT_ID);
    }

    @Test
    void handleUntrackingValidLink() {
        Update update = new Gson().fromJson(
            "{message={chat={id=" + CHAT_ID + "}, " +
                "text='@LinkUpdateTrackerBot /untrack " + VALID_URI + "'}}",
            Update.class
        );

        handler.handle(update);

        Mockito.verify(untrackCommand, Mockito.times(1))
            .execute(VALID_URI, CHAT_ID);
    }
}
