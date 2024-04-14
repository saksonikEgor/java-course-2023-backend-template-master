package edu.java.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record LinkUpdateRequest(
    @NotNull
    Long id,

    @NotBlank
    String url,

    @NotBlank
    String description,

    @NotNull
    @NotEmpty
    List<@NotNull Long> tgChatIds
) {
}
