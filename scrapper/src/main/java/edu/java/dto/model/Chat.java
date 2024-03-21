package edu.java.dto.model;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    private long chatId;
    @NotNull(message = "createdAt should not be null")
    private OffsetDateTime createdAt = OffsetDateTime.now(ZoneOffset.UTC);
    @NotNull(message = "state should not be null")
    private ChatState state = ChatState.REGISTERED;
    @NotNull(message = "links should not be null")
    private List<Link> links = new ArrayList<>();

    public Chat(long chatId) {
        this.chatId = chatId;
    }
}
