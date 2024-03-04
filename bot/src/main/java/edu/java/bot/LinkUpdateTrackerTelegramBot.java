package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.commands.TelegramBotCommandInfo;
import edu.java.commands.TelegramBotCommandType;
import edu.java.handler.ChatInputHandler;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LinkUpdateTrackerTelegramBot implements TelegramBotWrapper {
    private final String telegramBotToken;
    private final ChatInputHandler inputHandler;
    private final Map<TelegramBotCommandType, TelegramBotCommandInfo> typeToInfo;
    private final Keyboard keyboard;
    private TelegramBot telegramBot;

    public LinkUpdateTrackerTelegramBot(
        String telegramBotToken,
        ChatInputHandler inputHandler,
        Map<TelegramBotCommandType, TelegramBotCommandInfo> typeToInfo
    ) {
        this.telegramBotToken = telegramBotToken;
        this.inputHandler = inputHandler;
        this.typeToInfo = typeToInfo;
        keyboard = createKeyboard();
    }

    private InlineKeyboardMarkup createKeyboard() {
        return new InlineKeyboardMarkup()
            .addRow(
                new InlineKeyboardButton(
                    typeToInfo.get(TelegramBotCommandType.TRACK).commandDefinition()
                )
                    .switchInlineQueryCurrentChat(
                        typeToInfo.get(TelegramBotCommandType.TRACK).commandName() + " "
                    )
            )
            .addRow(
                new InlineKeyboardButton(
                    typeToInfo.get(TelegramBotCommandType.UNTRACK).commandDefinition()
                )
                    .switchInlineQueryCurrentChat(
                        typeToInfo.get(TelegramBotCommandType.UNTRACK).commandName() + " "
                    )
            );
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

    public void sendMessage(SendMessage message) {
        telegramBot.execute(message.parseMode(ParseMode.Markdown).replyMarkup(keyboard));
    }

    @Override
    public void close() throws Exception {
        log.info("Closing Telegram bot...");
        telegramBot.removeGetUpdatesListener();
    }
}
