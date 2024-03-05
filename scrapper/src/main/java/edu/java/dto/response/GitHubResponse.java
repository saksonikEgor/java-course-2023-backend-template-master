package edu.java.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GitHubResponse(
    @JsonProperty("full_name") String fullName,
    @JsonProperty("created_at") OffsetDateTime createdAt,
    @JsonProperty("pushed_at") OffsetDateTime pushedAt
) implements SiteAPIResponse {
    @Override
    public OffsetDateTime getLastUpdate() {
        return pushedAt;
    }

    @Override
    public String getDescriptionOfUpdatesWhichAfter(OffsetDateTime time) {
        return "Новое изменение в репозитории '" + fullName + "'";
    }
}
