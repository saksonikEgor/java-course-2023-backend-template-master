package edu.java.commands;

import edu.java.client.ScrapperClient;
import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.response.LinkResponse;
import edu.java.exception.ScrapperAPIException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class TelegramBotTrackCommand implements TelegramBotCommand {
    private final TelegramBotCommandInfo commandInfo;
    private final ScrapperClient scrapperClient;
    private final String notRegisteredErrorMessage;

    public TelegramBotTrackCommand(
            Map<TelegramBotCommandType, TelegramBotCommandInfo> typeToInfo,
            ScrapperClient scrapperClient,
            String notRegisteredErrorMessage
    ) {
        commandInfo = typeToInfo.get(TelegramBotCommandType.TRACK);
        this.scrapperClient = scrapperClient;
        this.notRegisteredErrorMessage = notRegisteredErrorMessage;
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
        //TODO: использовать response

        try {
            LinkResponse response = scrapperClient.addLink(chatId, new AddLinkRequest(url));
            return commandInfo.successfulResponse();
        } catch (ScrapperAPIException e) {
            return e.getMessage();
        }
    }
}
