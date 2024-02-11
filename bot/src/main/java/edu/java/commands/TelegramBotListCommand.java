package edu.java.commands;

import com.pengrad.telegrambot.model.Message;
import edu.java.configuration.TelegramBotCommandConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class TelegramBotListCommand implements TelegramBotCommand {
    private final TelegramBotCommandInfo commandInfo;

    @Autowired
    public TelegramBotListCommand(TelegramBotCommandConfiguration commandConfiguration) {
        this.commandInfo = commandConfiguration.getInfoByType().get(TelegramBotCommandType.LIST);
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
        return commandInfo.unSuccessfulResponse();
    }
}
