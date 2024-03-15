package edu.java.commands;

import com.google.gson.Gson;
import com.pengrad.telegrambot.model.Message;
import edu.java.configuration.TelegramBotCommandConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = TelegramBotCommandConfiguration.class)
public class TelegramBotHelpCommandTest {
    @Autowired
    public Map<TelegramBotCommandType, TelegramBotCommandInfo> typeToInfo;
    private final static long CHAT_ID = 123456L;
    private TelegramBotCommandInfo helpCommandInfo;
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
        helpCommandInfo = typeToInfo.get(TelegramBotCommandType.HELP);
    }

    @Test
    void callHelp() {
        TelegramBotHelpCommand helpCommand = new TelegramBotHelpCommand(typeToInfo);
        String response = helpCommand.execute(message.text(), CHAT_ID);

        assertEquals(helpCommandInfo.successfulResponse(), response);
    }
}
