package edu.java.client.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record StackOverflowResponse(
    @JsonProperty("creation_date") OffsetDateTime createAt,
    @JsonProperty("last_edit_date") OffsetDateTime updateAt,
    @JsonProperty("question_id") long id,
    @JsonProperty("title") String title
) implements APIResponse {
}
