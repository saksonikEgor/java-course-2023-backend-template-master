package edu.java.handler;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.commands.TelegramBotCommandType;
import edu.java.handler.parser.MessageExecutor;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatInputHandler implements InputHandler {
    private final Map<String, TelegramBotCommandType> nameToType;
    private final MessageExecutor chainOfParsers;
    private final String wrongInputMessage;
    private final String wrongLinkFormatMessage;
    private final String successfulTrackingMessage;
    private final String successfulUntrackingMessage;
    private final String linkIsAlreadyTrackedMessage;
    private final String linkIsNotTrackingMessage;

    @Override
    public SendMessage handle(Update update) {
        Message message = update.message();
        long chatId = message.chat().id();
        String[] words = message.text().split(" ");

        String response = chainOfParsers.parse(words, chatId)
            .orElse(wrongInputMessage);

        return new SendMessage(chatId, response);
    }

//    private String getResultOfLinkProcessing(long userId, String text) {
//        try {
//            UserState state = userDAO.getStateOfUser(userId);
//            userDAO.handleURIForUser(userId, text);
//
//            return switch (state) {
//                case WAITING_FOR_TRACK -> successfulTrackingMessage;
//                case WAITING_FOR_UNTRACK -> successfulUntrackingMessage;
//                default -> throw new IllegalStateException("Unexpected value: " + state);
//            };
//        } catch (WrongLinkFormatException wrongLinkFormatException) {
//            log.error(wrongLinkFormatException.getMessage());
//            return wrongLinkFormatMessage;
//        } catch (LinkIsAlreadyTrackedException alreadyTrackedException) {
//            log.error(alreadyTrackedException.getMessage());
//            return linkIsAlreadyTrackedMessage;
//        } catch (LinkIsNotTrackingException notTrackingException) {
//            log.error(notTrackingException.getMessage());
//            return linkIsNotTrackingMessage;
//        }
//    }
}
