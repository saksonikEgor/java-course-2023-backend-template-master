package edu.java.commands;

import com.pengrad.telegrambot.model.Message;
import edu.java.client.ScrapperClient;
import edu.java.configuration.TelegramBotCommandConfiguration;
import edu.java.repository.UserDAO;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class TelegramBotStartCommand implements TelegramBotCommand {
    private final TelegramBotCommandInfo commandInfo;
    private final ScrapperClient scrapperClient;

    public TelegramBotStartCommand(
        Map<TelegramBotCommandType, TelegramBotCommandInfo> typeToInfo,
        ScrapperClient scrapperClient
    ) {
        commandInfo = typeToInfo.get(TelegramBotCommandType.START);
        this.scrapperClient = scrapperClient;
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
    public String execute(String text, long chatId) {
        userDAO.addUser(chatId);
        return commandInfo.successfulResponse();
    }
}

















