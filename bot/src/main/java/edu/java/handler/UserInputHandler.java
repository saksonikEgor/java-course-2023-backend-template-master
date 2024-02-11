package edu.java.handler;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.commands.TelegramBotCommand;
import edu.java.commands.TelegramBotCommandType;
import edu.java.configuration.TelegramBotCommandConfiguration;
import edu.java.configuration.UserInputHandlerConfiguration;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserInputHandler implements InputHandler {
    private final Map<String, TelegramBotCommandType> typeByName;
    private final Map<TelegramBotCommandType, TelegramBotCommand> commandByType;

    @Autowired
    public UserInputHandler(
        TelegramBotCommandConfiguration commandConfiguration,
        UserInputHandlerConfiguration handlerConfiguration
    ) {
        typeByName = commandConfiguration.getTypeByName();
        commandByType = handlerConfiguration.getCommandByType();
    }

    @Override
    public SendMessage handle(Update update) {
        Message message = update.message();

        try {
            TelegramBotCommandType type = typeByName.get(message.text());
            TelegramBotCommand command = commandByType.get(type);

            return command.execute(message);
        } catch (NullPointerException e) {
            //TODO: отправить сообщение о несуществующей команде
        }
    }
}
