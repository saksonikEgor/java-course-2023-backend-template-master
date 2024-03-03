package edu.java.commands;

import edu.java.client.ScrapperClient;
import edu.java.exception.chat.ChatIsNotRegisteredException;
import edu.java.repository.UserDAO;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
    public String execute(String text, long chatId) {
        try {
            userDAO.makeTheUserWaitForTrack(chatId);
            return commandInfo.successfulResponse();
        } catch (ChatIsNotRegisteredException e) {
            log.error(e.getMessage());
            return notRegisteredErrorMessage;
        }
    }
}
