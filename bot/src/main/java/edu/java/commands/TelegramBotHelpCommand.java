package edu.java.commands;

import edu.java.repository.UserDAO;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class TelegramBotHelpCommand implements TelegramBotCommand {
    private final TelegramBotCommandInfo commandInfo;

    public TelegramBotHelpCommand(Map<TelegramBotCommandType, TelegramBotCommandInfo> typeToInfo) {
        this.commandInfo = typeToInfo.get(TelegramBotCommandType.HELP);
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
        return commandInfo.successfulResponse();
    }
}
