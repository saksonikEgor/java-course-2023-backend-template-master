package edu.java.handler;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.commands.TelegramBotCommand;
import edu.java.commands.TelegramBotCommandType;
import edu.java.configuration.TelegramBotCommandConfiguration;
import edu.java.configuration.UserInputHandlerConfiguration;
import edu.java.exception.WrongLinkFormatException;
import edu.java.model.UserState;
import edu.java.repository.UserDAO;
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
    private final String wrongLinkFormatMassage;
    private final String successfulTrackingMessage;
    private final String successfulUntrackingMessage;
    private final UserDAO userDAO;

    @Autowired
    public UserInputHandler(
        TelegramBotCommandConfiguration commandConfiguration,
        UserInputHandlerConfiguration handlerConfiguration, UserDAO userDAO
    ) {
        typeByName = commandConfiguration.getTypeByName();
        commandByType = handlerConfiguration.getCommandByType();
        wrongInputMessage = commandConfiguration.getWrongInputMessage();
        wrongLinkFormatMassage = commandConfiguration.getWrongLinkFormatMessage();
        successfulTrackingMessage = commandConfiguration.getSuccessfulTrackingMessage();
        successfulUntrackingMessage = commandConfiguration.getSuccessfulUntrackingMessage();
        this.userDAO = userDAO;
    }

    @Override
    public SendMessage handle(Update update) {
        Message message = update.message();
        long userId = message.chat().id();
        String textMessage;

        try {
            TelegramBotCommandType type = typeByName.get(message.text());
            TelegramBotCommand command = commandByType.get(type);

            log.info("Command type: " + type);

            textMessage = command.execute(message);
        } catch (NullPointerException nullPointerException) {
            if (userDAO.isUserWaiting(userId)) {
                log.info("Message is parsing like a link");

                try {
                    UserState state = userDAO.getStateOfUser(userId);
                    userDAO.handleURIForUser(userId, message.text());

                    textMessage = switch (state) {
                        case WAITING_FOR_TRACK -> successfulTrackingMessage;
                        case WAITING_FOR_UNTRACK -> successfulUntrackingMessage;
                        default -> throw new IllegalStateException("Unexpected value: " + state);
                    };
                } catch (WrongLinkFormatException wrongLinkFormatException) {
                    log.error(wrongLinkFormatException.getMessage());
                    textMessage = wrongLinkFormatMassage;
                }
//                TelegramBotCommand command = commandByType.get(TelegramBotCommandType.LINK);
//                textMessage = command.execute(message);
            } else {
                log.info("Wrong command type");
                textMessage = wrongInputMessage;
            }

        }

        log.info("Sending message with text: '" + textMessage + "' and userId: " + message.chat().id());
        return new SendMessage(userId, textMessage);
    }
}
