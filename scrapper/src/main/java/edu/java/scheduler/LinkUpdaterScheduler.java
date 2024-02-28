package edu.java.scheduler;

import edu.java.client.BotClient;
import edu.java.client.GitHubClient;
import edu.java.client.StackOverflowClient;
import edu.java.dto.model.Chat;
import edu.java.dto.model.Link;
import edu.java.dto.request.LinkUpdateRequest;
import edu.java.dto.response.SiteAPIResponse;
import edu.java.service.ChatService;
import edu.java.service.LinkService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
@ConditionalOnProperty(value = "app.scheduler.enable", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private final ChatService chatService;
    private final LinkService linkService;
    private final BotClient botClient;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;

    @Scheduled(fixedDelayString = "#{@schedulerInterval.toMillis()}")
    public void update() {
        List<Update> updatedLinks = getUpdates();

        if (updatedLinks.isEmpty()) {
            log.info("No links have been updated");
        } else {
            log.info(updatedLinks.size() + " link(s) have been updated:");
            updatedLinks.stream()
                .map(Update::link)
                .forEach(link -> log.info(String.valueOf(link)));
        }

        for (Update update : updatedLinks) {
            Link link = update.link();
            List<Chat> trackingChats = chatService.getTrackingChatsForLink(link.getLinkId());

            botClient.updateLink(new LinkUpdateRequest(
                link.getLinkId(),
                link.getUrl(),
                update.response().getUpdateDescription(),
                trackingChats.stream().map(Chat::getChatId).toList()
            ));
        }
    }

    private List<Update> getUpdates() {
        return linkService.listAll()
            .stream()
            .map(link -> {
                try {
                    Map<String, String> info = link.getInfo();

                    SiteAPIResponse response = switch (link.getBaseURL()) {
                        case GITHUB -> gitHubClient.getInfo(info.get("user"), info.get("repo"));
                        case STACKOVERFLOW -> stackOverflowClient.getInfo(Long.parseLong(info.get("question_id")));
                    };

                    return new Update(link, response);
                } catch (NullPointerException | NumberFormatException e) {
                    log.error(e.getMessage());
                    return new Update(link, null);
                } catch (Exception e) {
                    log.error("Some fatal error while getting a response", e);
                    return new Update(link, null);
                }
            })
            .filter(update -> update.response().getLastUpdate().isAfter(update.link().getLastUpdate()))
            .toList();
    }

    private record Update(Link link, SiteAPIResponse response) {
    }
}
