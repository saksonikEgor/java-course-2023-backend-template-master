package edu.java.dto.model;

import java.time.OffsetDateTime;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
public class Link {
    private Long linkId;
    @NotNull
    private String url;
    @NotNull
    private OffsetDateTime lastUpdate;
    @NotNull
    private OffsetDateTime lastCheck;
    @NotNull
    private List<Chat> chats;
}
