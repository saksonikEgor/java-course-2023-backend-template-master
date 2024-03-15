package edu.java.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.java.util.parser.DateTimeDeserializer;
import edu.java.util.parser.GitHubResponseDeserializer;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonDeserialize(using = GitHubResponseDeserializer.class)
public record GitHubResponse(
    List<CommitInfo> commitInfos

) implements SiteAPIResponse {
    @Override
    public OffsetDateTime getLastUpdate() {
        return commitInfos.stream()
            .map(commitInfo -> commitInfo.commit.author.date)
            .max(OffsetDateTime::compareTo)
            .orElse(OffsetDateTime.MIN);
    }

    @Override
    public String getDescriptionOfUpdatesWhichAfter(OffsetDateTime time) {
        List<CommitInfo> newCommits = commitInfos.stream()
            .filter(commitInfo -> commitInfo.commit.author.date.isAfter(time))
            .toList();

        return switch (newCommits.size()) {
            case 0 -> "";
            case 1 -> "1 новый коммит.\n________________\n" + newCommits.getFirst().toString();
            default -> newCommits.stream()
                .map(CommitInfo::toString)
                .collect(Collectors.joining(
                    "\n________________\n",
                    newCommits + " новых коммита.\n________________\n",
                    ""
                ));
        };
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CommitInfo(
        @JsonProperty("commit") Commit commit,
        @JsonProperty("html_url") String htmlUrl
    ) {

        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Commit(
            @JsonProperty("author") Author author,
            @JsonProperty("message") String message

        ) {
            @JsonIgnoreProperties(ignoreUnknown = true)
            public record Author(
                @JsonProperty("name")
                String name,
                @JsonDeserialize(using = DateTimeDeserializer.class)
                @JsonProperty("date")
                OffsetDateTime date

            ) {
            }
        }

        @Override
        public String toString() {
            return "Автор: " + commit.author.name + "\n"
                + "Сообщение коммита: " + commit.message + "\n"
                + "Ссылка на коммит: " + htmlUrl;
        }
    }
}
