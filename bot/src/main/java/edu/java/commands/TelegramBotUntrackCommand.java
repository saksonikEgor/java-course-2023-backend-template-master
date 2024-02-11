package edu.java.commands;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
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
    public SendMessage execute(Message message) {
        return new SendMessage(message.chat().id(), commandInfo.successfulResponse());
    }
}
