package edu.java.commands;

import edu.java.client.ScrapperClient;
import edu.java.exception.chat.ChatIsNotRegisteredException;
import edu.java.repository.UserDAO;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
        userDAO.refuseWaitingIfAuthenticated(chatId);
        List<URI> refs;

        try {
            refs = userDAO.getAllURIOfUser(chatId);
        } catch (ChatIsNotRegisteredException e) {
            log.error(e.getMessage());
            return notRegisteredErrorMessage;
        }

        if (refs.isEmpty()) {
            return commandInfo.unSuccessfulResponse();
        }
        return commandInfo.successfulResponse() + refs.stream()
            .map(URI::toString)
            .collect(Collectors.joining("\n----------------\n", "----------------\n", "\n----------------"));
    }
}
