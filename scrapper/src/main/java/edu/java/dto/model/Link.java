package edu.java.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class Link {
    private long linkId;
    @NotBlank
    private String url;
    @NotNull
    private OffsetDateTime lastUpdate = OffsetDateTime.now();
    @NotNull
    private OffsetDateTime lastCheck = OffsetDateTime.now();
    @NotNull
    private List<Chat> chats = new ArrayList<>();
    @NotNull
    private BaseURL baseURL;
    @NotNull
    private Map<String, String> info = new HashMap<>();

    public Link(
            String url,
            @NotNull OffsetDateTime lastUpdate,
            @NotNull OffsetDateTime lastCheck,
            @NotNull BaseURL baseURL
    ) {
        this.url = url;
        this.lastUpdate = lastUpdate;
        this.lastCheck = lastCheck;
        this.baseURL = baseURL;
    }

    public Link(
            String url,
            @NotNull BaseURL baseURL,
            @NotNull Map<String, String> info
    ) {
        this.url = url;
        this.baseURL = baseURL;
        this.info = info;
    }
}
