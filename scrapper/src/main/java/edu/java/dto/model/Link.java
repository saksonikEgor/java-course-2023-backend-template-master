package edu.java.dto.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Link {
    private long linkId;
    @NotBlank(message = "url should be not empty")
    private String url;
    @NotNull(message = "lastUpdate should not be null")
    private OffsetDateTime lastUpdate = OffsetDateTime.now();
    @NotNull(message = "lastCheck should not be null")
    private OffsetDateTime lastCheck = OffsetDateTime.now();
    @NotNull(message = "chats should not be null")
    private List<Chat> chats = new ArrayList<>();
    @NotNull(message = "baseURL should not be null")
    private BaseURL baseURL;
    @NotNull(message = "info should not be null")
    private Map<String, String> info = new HashMap<>();

    public Link(
        String url,
        OffsetDateTime lastUpdate,
        OffsetDateTime lastCheck,
        BaseURL baseURL
    ) {
        this.url = url;
        this.lastUpdate = lastUpdate;
        this.lastCheck = lastCheck;
        this.baseURL = baseURL;
    }

    public Link(
        String url,
        BaseURL baseURL,
        Map<String, String> info
    ) {
        this.url = url;
        this.baseURL = baseURL;
        this.info = info;
    }

    public Link(
        long linkId,
        String url,
        OffsetDateTime lastUpdate,
        OffsetDateTime lastCheck,
        BaseURL baseURL,
        Map<String, String> info
    ) {
        this.linkId = linkId;
        this.url = url;
        this.lastUpdate = lastUpdate;
        this.lastCheck = lastCheck;
        this.baseURL = baseURL;
        this.info = info;
    }
}
