package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.commands.TelegramBotCommandInfo;
import edu.java.commands.TelegramBotCommandType;
import edu.java.handler.ChatInputHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class LinkUpdateTrackerTelegramBot implements TelegramBotWrapper {
    private final String telegramBotToken;
    private final ChatInputHandler inputHandler;
    private final Map<TelegramBotCommandType, TelegramBotCommandInfo> typeToInfo;
    private TelegramBot telegramBot;

    public LinkUpdateTrackerTelegramBot(
            String telegramBotToken,
            ChatInputHandler inputHandler,
            Map<TelegramBotCommandType, TelegramBotCommandInfo> typeToInfo
    ) {
        this.telegramBotToken = telegramBotToken;
        this.inputHandler = inputHandler;
        this.typeToInfo = typeToInfo;
    }

    @Override
    public void start() {
        log.info("Starting Telegram bot with token: " + telegramBotToken);

        telegramBot = new TelegramBot(telegramBotToken);
        telegramBot.execute(getAllTelegramBotCommands());
        telegramBot.setUpdatesListener(this);
    }

    private SetMyCommands getAllTelegramBotCommands() {
        return new SetMyCommands(
                new BotCommand(
                        typeToInfo.get(TelegramBotCommandType.LIST).commandName(),
                        typeToInfo.get(TelegramBotCommandType.LIST).commandDefinition()
                ),
                new BotCommand(
                        typeToInfo.get(TelegramBotCommandType.HELP).commandName(),
                        typeToInfo.get(TelegramBotCommandType.HELP).commandDefinition()
                )
        );
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            log.info("Process update: {}", update);
            sendMessage(inputHandler.handle(update));
        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


    private void sendMessage(SendMessage message) {
        telegramBot.execute(message.replyMarkup(getKeyBoard()));
    }

    private InlineKeyboardMarkup getKeyBoard() {
        return new InlineKeyboardMarkup()
                .addRow(new InlineKeyboardButton("Начать отслеживать ссылку").switchInlineQueryCurrentChat("/track "))
                .addRow(new InlineKeyboardButton("Прекратить отслеживание ссылку").switchInlineQueryCurrentChat("/untrack "));
    }

    @Override
    public void close() throws Exception {
        log.info("Closing Telegram bot...");
        telegramBot.removeGetUpdatesListener();
    }
}
