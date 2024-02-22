package edu.java.commands;

import com.pengrad.telegrambot.model.Message;
import edu.java.configuration.TelegramBotCommandConfiguration;
import edu.java.exception.UserIsUnauthenticatedException;
import edu.java.repository.UserDAO;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TelegramBotListCommand implements TelegramBotCommand {
    private final TelegramBotCommandInfo commandInfo;
    private final UserDAO userDAO;
    private final String notAuthenticatedErrorMessage;

    public TelegramBotListCommand(TelegramBotCommandConfiguration commandConfiguration, UserDAO userDAO) {
        commandInfo = commandConfiguration.getTypeToInfo().get(TelegramBotCommandType.LIST);
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
        userDAO.refuseWaitingIfAuthenticated(message.chat().id());
        List<URI> refs;

        try {
            refs = userDAO.getAllURIOfUser(message.chat().id());
        } catch (UserIsUnauthenticatedException e) {
            log.error(e.getMessage());
            return notAuthenticatedErrorMessage;
        }

        if (refs.isEmpty()) {
            return commandInfo.unSuccessfulResponse();
        }
        return commandInfo.successfulResponse() + refs.stream()
            .map(URI::toString)
            .collect(Collectors.joining("\n----------------\n", "----------------\n", "\n----------------"));
    }
}
