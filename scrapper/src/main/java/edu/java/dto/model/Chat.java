package edu.java.dto.model;

import jakarta.validation.Valid;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
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
public class Chat {
    private long chatId;
    @NotNull
    private OffsetDateTime createdAt = OffsetDateTime.now(ZoneOffset.UTC);
    @NotNull
    private ChatState state = ChatState.REGISTERED;
    @NotNull
    private List<Link> links = new ArrayList<>();

    public Chat(long chatId) {
        this.chatId = chatId;
    }
}
