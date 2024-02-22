package edu.java.handler;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.commands.TelegramBotCommand;
import edu.java.commands.TelegramBotCommandType;
import edu.java.configuration.TelegramBotCommandConfiguration;
import edu.java.configuration.UserInputHandlerConfiguration;
import edu.java.exception.LinkIsAlreadyTrackedException;
import edu.java.exception.LinkIsNotTrackingException;
import edu.java.exception.WrongLinkFormatException;
import edu.java.model.UserState;
import edu.java.repository.UserDAO;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserInputHandler implements InputHandler {
    private final Map<String, TelegramBotCommandType> nameToType;
    private final Map<TelegramBotCommandType, TelegramBotCommand> typeToCommand;
    private final String wrongInputMessage;
    private final String wrongLinkFormatMassage;
    private final String successfulTrackingMessage;
    private final String successfulUntrackingMessage;
    private final String linkIsAlreadyTrackedMessage;
    private final String linkIsNotTrackingMessage;
    private final UserDAO userDAO;

    public UserInputHandler(
        TelegramBotCommandConfiguration commandConfiguration,
        UserInputHandlerConfiguration handlerConfiguration, UserDAO userDAO
    ) {
        nameToType = commandConfiguration.getNameToType();
        typeToCommand = handlerConfiguration.getTypeToCommand();
        wrongInputMessage = commandConfiguration.getWrongInputMessage();
        wrongLinkFormatMassage = commandConfiguration.getWrongLinkFormatMessage();
        successfulTrackingMessage = commandConfiguration.getSuccessfulTrackingMessage();
        successfulUntrackingMessage = commandConfiguration.getSuccessfulUntrackingMessage();
        linkIsAlreadyTrackedMessage = commandConfiguration.getLinkIsAlreadyTrackedMessage();
        linkIsNotTrackingMessage = commandConfiguration.getLinkIsNotTrackingMessage();
        this.userDAO = userDAO;
    }

    @Override
    public SendMessage handle(Update update) {
        Message message = update.message();
        long userId = message.chat().id();
        String textMessage;

        try {
            TelegramBotCommandType type = nameToType.get(message.text());
            TelegramBotCommand command = typeToCommand.get(type);

            log.info("Command type: " + type);

            textMessage = command.execute(message);
        } catch (NullPointerException nullPointerException) {
            if (userDAO.isUserWaiting(userId)) {
                log.info("Message is parsing like a link");
                textMessage = getResultOfLinkProcessing(userId, message.text());
            } else {
                log.info("Wrong command type");
                textMessage = wrongInputMessage;
            }
        }

        log.info("Sending message with text: '" + textMessage + "' and userId: " + message.chat().id());
        return new SendMessage(userId, textMessage);
    }

    private String getResultOfLinkProcessing(long userId, String text) {
        try {
            UserState state = userDAO.getStateOfUser(userId);
            userDAO.handleURIForUser(userId, text);

            return switch (state) {
                case WAITING_FOR_TRACK -> successfulTrackingMessage;
                case WAITING_FOR_UNTRACK -> successfulUntrackingMessage;
                default -> throw new IllegalStateException("Unexpected value: " + state);
            };
        } catch (WrongLinkFormatException wrongLinkFormatException) {
            log.error(wrongLinkFormatException.getMessage());
            return wrongLinkFormatMassage;
        } catch (LinkIsAlreadyTrackedException alreadyTrackedException) {
            log.error(alreadyTrackedException.getMessage());
            return linkIsAlreadyTrackedMessage;
        } catch (LinkIsNotTrackingException notTrackingException) {
            log.error(notTrackingException.getMessage());
            return linkIsNotTrackingMessage;
        }
    }
}
