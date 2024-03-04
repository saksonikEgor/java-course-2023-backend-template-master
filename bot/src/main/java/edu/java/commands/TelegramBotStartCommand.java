package edu.java.commands;

import edu.java.client.ScrapperClient;
import java.util.Map;
import org.springframework.stereotype.Component;

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
        try {
            scrapperClient.registerChat(chatId);
        } catch (RuntimeException e) {
            String message = e.getMessage();
        }

        //TODO: вернуть response

        return commandInfo.successfulResponse();
    }
}

















