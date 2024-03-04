package edu.java.commands;

import edu.java.client.ScrapperClient;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TelegramBotListCommand implements TelegramBotCommand {
    private final TelegramBotCommandInfo commandInfo;
    private final String notRegisteredErrorMessage;
    private final ScrapperClient scrapperClient;

    public TelegramBotListCommand(
            Map<TelegramBotCommandType, TelegramBotCommandInfo> typeToInfo,
            ScrapperClient scrapperClient,
            String notRegisteredErrorMessage
    ) {
        commandInfo = typeToInfo.get(TelegramBotCommandType.LIST);
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
    public String execute(String text, long chatId) {
        ListLinksResponse response = scrapperClient.getLinks(chatId);

        return response.links()
                .stream()
                .map(LinkResponse::url)
                .collect(Collectors.joining("\n----------------\n", "----------------\n", "\n----------------"));
    }
}
