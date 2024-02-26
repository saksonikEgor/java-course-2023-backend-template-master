package edu.java.dto.model;

import jakarta.validation.Valid;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class Link {
    private Long linkId;
    @NotNull
    private String url;
    @NotNull
    private OffsetDateTime lastUpdate;
    @NotNull
    private OffsetDateTime lastCheck;
    @NotNull
    private List<Chat> chats = new ArrayList<>();
}
