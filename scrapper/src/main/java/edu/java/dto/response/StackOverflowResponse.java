package edu.java.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record StackOverflowResponse(
    @JsonProperty("items") List<Answer> answers
) implements SiteAPIResponse {
    @Override
    public OffsetDateTime getLastUpdate() {
        return answers.stream()
            .map(Answer::createAt)
            .max(OffsetDateTime::compareTo)
            .orElse(OffsetDateTime.MIN);
    }

    @Override
    public String getDescriptionOfUpdatesWhichAfter(OffsetDateTime time) {
        List<String> userNames = answers.stream()
            .filter(a -> a.createAt.isAfter(time))
            .map(a -> a.owner().name())
            .toList();

        return switch (userNames.size()) {
            case 0 -> "";
            case 1 -> userNames.stream()
                .collect(Collectors.joining(
                    ", ",
                    "",
                    " добавил(а) ответ на вопрос с id '" + answers.getLast().questionId + "'"
                ));
            default -> userNames.stream()
                .collect(Collectors.joining(
                    ", ",
                    "",
                    " добавили ответ на вопрос с id '" + answers.getLast().questionId + "'"
                ));
        };
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Answer(
        @JsonProperty("owner") Owner owner,
        @JsonProperty("creation_date") OffsetDateTime createAt,
        @JsonProperty("question_id") long questionId
    ) {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public record Owner(
            @JsonProperty("display_name") String name
        ) {
        }
    }
}
