package edu.java.commands;

import edu.java.client.ScrapperClient;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;
import edu.java.exception.ScrapperAPIException;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TelegramBotListCommand implements TelegramBotCommand {
    private final TelegramBotCommandInfo commandInfo;
    private final ScrapperClient scrapperClient;
    private final String fatalExceptionMessage;
    private final String cross;

    public TelegramBotListCommand(
        Map<TelegramBotCommandType, TelegramBotCommandInfo> typeToInfo,
        ScrapperClient scrapperClient,
        String fatalExceptionMessage,
        String cross
    ) {
        commandInfo = typeToInfo.get(TelegramBotCommandType.LIST);
        this.scrapperClient = scrapperClient;
        this.fatalExceptionMessage = fatalExceptionMessage;
        this.cross = cross;
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
            ListLinksResponse response = scrapperClient.getLinks(chatId);

            if (response.size() == 0) {
                return commandInfo.unSuccessfulResponse();
            }

            return commandInfo.successfulResponse() + response.links()
                .stream()
                .map(LinkResponse::url)
                .collect(Collectors.joining("\n----------------\n", "----------------\n", "\n----------------"));
        } catch (ScrapperAPIException e) {
            return cross + e.getMessage();
        } catch (Exception e) {
            return cross + fatalExceptionMessage;
        }
    }
}
