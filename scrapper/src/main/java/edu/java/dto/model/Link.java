package edu.java.dto.model;

import jakarta.validation.Valid;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
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

    public Link(String url) {
        this.url = url;
    }
}
