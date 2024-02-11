package edu.java.handler;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.commands.TelegramBotCommand;
import edu.java.commands.TelegramBotCommandType;
import edu.java.configuration.TelegramBotCommandConfiguration;
import edu.java.configuration.UserInputHandlerConfiguration;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserInputHandler implements InputHandler {
    private final Map<String, TelegramBotCommandType> typeByName;
    private final Map<TelegramBotCommandType, TelegramBotCommand> commandByType;
    private final String wrongInputMessage;

    @Autowired
    public UserInputHandler(
        TelegramBotCommandConfiguration commandConfiguration,
        UserInputHandlerConfiguration handlerConfiguration
    ) {
        typeByName = commandConfiguration.getTypeByName();
        commandByType = handlerConfiguration.getCommandByType();
        wrongInputMessage = commandConfiguration.getWrongInputMessage();
    }

    @Override
    public SendMessage handle(Update update) {
        Message message = update.message();

        try {
            TelegramBotCommandType type = typeByName.get(message.text());
            TelegramBotCommand command = commandByType.get(type);

            log.info("CommandType: " + type);
            log.info("Command: " + command);

            return command.execute(message);
        } catch (NullPointerException e) {
            return getWrongSendMessage(message);
        }
    }

    private SendMessage getWrongSendMessage(Message message) {
        return new SendMessage(message.chat().id(), wrongInputMessage);
    }
}
