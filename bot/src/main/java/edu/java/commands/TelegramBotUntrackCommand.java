package edu.java.commands;

import edu.java.client.ScrapperClient;
import edu.java.dto.request.RemoveLinkRequest;
import edu.java.exception.ScrapperAPIException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class TelegramBotUntrackCommand implements TelegramBotCommand {
    private final TelegramBotCommandInfo commandInfo;
    private final ScrapperClient scrapperClient;
    private final String fatalExceptionMessage;
    private final String cross;

    public TelegramBotUntrackCommand(
            Map<TelegramBotCommandType, TelegramBotCommandInfo> typeToInfo,
            ScrapperClient scrapperClient,
            String fatalExceptionMessage,
            String cross
    ) {
        commandInfo = typeToInfo.get(TelegramBotCommandType.UNTRACK);
        this.scrapperClient = scrapperClient;
        this.cross = cross;
        this.fatalExceptionMessage = fatalExceptionMessage;
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
    public String execute(String url, long chatId) {
        try {
            scrapperClient.deleteLink(chatId, new RemoveLinkRequest(url));
            return commandInfo.successfulResponse();
        } catch (ScrapperAPIException e) {
            return cross + e.getMessage();
        } catch (Exception e) {
            return cross + fatalExceptionMessage;
        }
    }
}
