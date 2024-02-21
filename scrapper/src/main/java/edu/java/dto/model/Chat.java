package edu.java.dto.model;

import java.time.OffsetDateTime;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
public class Chat {
    private Long chatId;
    @NotNull
    private OffsetDateTime createdAt;
    @NotNull
    private ChatState state;
    @NotNull
    private List<Link> links;
}
