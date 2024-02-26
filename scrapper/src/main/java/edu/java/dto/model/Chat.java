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
public class Chat {
    private Long chatId;
    @NotNull
    private OffsetDateTime createdAt;
    @NotNull
    private ChatState state;
    @NotNull
    private List<Link> links = new ArrayList<>();
}
