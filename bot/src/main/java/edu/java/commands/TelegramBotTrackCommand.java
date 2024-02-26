package edu.java.commands;

import com.pengrad.telegrambot.model.Message;
import edu.java.configuration.TelegramBotCommandConfiguration;
import edu.java.exception.UserIsUnauthenticatedException;
import edu.java.repository.UserDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TelegramBotTrackCommand implements TelegramBotCommand {
    private final TelegramBotCommandInfo commandInfo;
    private final UserDAO userDAO;
    private final String notAuthenticatedErrorMessage;

    public TelegramBotTrackCommand(TelegramBotCommandConfiguration commandConfiguration, UserDAO userDAO) {
        commandInfo = commandConfiguration.getTypeToInfo().get(TelegramBotCommandType.TRACK);
        this.userDAO = userDAO;
        notAuthenticatedErrorMessage = commandConfiguration.getNotAuthenticatedErrorMessage();
    }

    @Override
    public String getName() {
        return commandInfo.commandName();
    }

    @Override
    public String getDescription() {
        return commandInfo.commandDefinition();
    }

    @Override
    public String execute(Message message) {
        try {
            userDAO.makeTheUserWaitForTrack(message.chat().id());
            return commandInfo.successfulResponse();
        } catch (UserIsUnauthenticatedException e) {
            log.error(e.getMessage());
            return notAuthenticatedErrorMessage;
        }
    }
}
