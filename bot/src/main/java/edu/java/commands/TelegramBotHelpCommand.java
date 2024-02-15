package edu.java.commands;

import com.pengrad.telegrambot.model.Message;
import edu.java.configuration.TelegramBotCommandConfiguration;
import edu.java.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class TelegramBotHelpCommand implements TelegramBotCommand {
    private final TelegramBotCommandInfo commandInfo;
    private final UserDAO userDAO;

    @Autowired
    public TelegramBotHelpCommand(TelegramBotCommandConfiguration commandConfiguration, UserDAO userDAO) {
        this.commandInfo = commandConfiguration.getInfoByType().get(TelegramBotCommandType.HELP);
        this.userDAO = userDAO;
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
        return commandInfo.successfulResponse();
    }
}
