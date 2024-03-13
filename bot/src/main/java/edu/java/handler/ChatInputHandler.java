package edu.java.handler;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.handler.parser.MessageExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatInputHandler implements InputHandler {
    private final MessageExecutor chainOfParsers;
    private final String wrongInputMessage;

    @Override
    public SendMessage handle(Update update) {
        Message message = update.message();
        long chatId = message.chat().id();
        String[] words = message.text().split(" ");

        String response = chainOfParsers.parse(words, chatId)
            .orElse(wrongInputMessage);

        return new SendMessage(chatId, response);
    }
}
