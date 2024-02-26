package edu.java.commands;

import com.pengrad.telegrambot.model.Message;
import edu.java.configuration.TelegramBotCommandConfiguration;
import edu.java.repository.UserDAO;
import org.springframework.stereotype.Component;

@Component
public class TelegramBotStartCommand implements TelegramBotCommand {
    private final TelegramBotCommandInfo commandInfo;
    private final UserDAO userDAO;

    public TelegramBotStartCommand(TelegramBotCommandConfiguration commandConfiguration, UserDAO userDAO) {
        commandInfo = commandConfiguration.getTypeToInfo().get(TelegramBotCommandType.START);
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
        userDAO.addUser(message.chat().id());
        return commandInfo.successfulResponse();
    }
}

















