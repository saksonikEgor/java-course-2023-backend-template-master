package edu.java.commands;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.configuration.TelegramBotCommandConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class TelegramBotHelpCommand implements TelegramBotCommand {
    private final TelegramBotCommandInfo commandInfo;

    @Autowired
    public TelegramBotHelpCommand(TelegramBotCommandConfiguration commandConfiguration) {
        this.commandInfo = commandConfiguration.getInfoByType().get(TelegramBotCommandType.HELP);
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
