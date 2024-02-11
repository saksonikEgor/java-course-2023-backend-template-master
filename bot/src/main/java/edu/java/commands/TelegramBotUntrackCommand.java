package edu.java.commands;

import com.pengrad.telegrambot.model.Message;
import edu.java.configuration.TelegramBotCommandConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class TelegramBotUntrackCommand implements TelegramBotCommand {
    private final TelegramBotCommandInfo commandInfo;

    @Autowired
    public TelegramBotUntrackCommand(TelegramBotCommandConfiguration commandConfiguration) {
        this.commandInfo = commandConfiguration.getInfoByType().get(TelegramBotCommandType.UNTRACK);
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
        return commandInfo.successfulResponse();
    }
}
