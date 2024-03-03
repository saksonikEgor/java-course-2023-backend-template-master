package edu.java.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

public record StackOverflowResponse(
    OffsetDateTime createAt,
    OffsetDateTime updateAt,
    long id,
    String title
) implements SiteAPIResponse {
    @JsonCreator
    public StackOverflowResponse(@JsonProperty("items") List<Question> items) {
        this(
            items.getFirst().createAt(),
            items.getFirst().updateAt(),
            items.getFirst().id(),
            items.getFirst().title()
        );
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Question(
        @JsonProperty("creation_date") OffsetDateTime createAt,
        @JsonProperty("last_edit_date") OffsetDateTime updateAt,
        @JsonProperty("question_id") long id,
        @JsonProperty("title") String title
    ) {
    }
}
