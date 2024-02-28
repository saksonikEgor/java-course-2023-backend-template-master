package edu.java.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Slf4j
@Component
@ConditionalOnProperty(value = "app.scheduler.enable", havingValue = "true", matchIfMissing = true)
public class LinkUpdaterScheduler {
    @Scheduled(fixedDelayString = "#{@schedulerInterval.toMillis()}")
    public void update() {
        log.info("Checking for updates...");
    }
}
