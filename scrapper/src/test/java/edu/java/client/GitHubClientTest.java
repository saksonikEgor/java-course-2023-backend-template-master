package edu.java.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.configuration.ClientConfiguration;
import edu.java.dto.response.GitHubResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GitHubClientTest {
    private static WireMockServer wireMockServer;

    @BeforeAll
    static void setup() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
    }

    @AfterAll
    static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void gettingResponse() throws IOException {
        String user = "saksonikEgor";
        String repo = "Checkers";
        String json = new String(Files.readAllBytes(Paths.get("src/test/resources/github-response.json")));

        stubFor(get(urlEqualTo("/repos/" + user + "/" + repo + "/commits"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(json)));

        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setGitHubBaseURL("http://localhost:8080");

        GitHubResponse response = clientConfiguration.gitHubClient().getInfo(user, repo);

        verify(getRequestedFor(urlEqualTo("/repos/" + user + "/" + repo + "/commits")));

        assertEquals(
            new GitHubResponse(List.of(
                new GitHubResponse.CommitInfo(
                    new GitHubResponse.CommitInfo.Commit(
                        new GitHubResponse.CommitInfo.Commit.Author(user, OffsetDateTime.parse("2023-11-23T11:29:05Z")),
                        "message1"
                    ), "html_url1"
                ),
                new GitHubResponse.CommitInfo(
                    new GitHubResponse.CommitInfo.Commit(
                        new GitHubResponse.CommitInfo.Commit.Author(user, OffsetDateTime.parse("2023-02-14T18:09:44Z")),
                        "message2"
                    ), "html_url2"
                ),
                new GitHubResponse.CommitInfo(
                    new GitHubResponse.CommitInfo.Commit(
                        new GitHubResponse.CommitInfo.Commit.Author(user, OffsetDateTime.parse("2023-02-14T18:02:31Z")),
                        "message3"
                    ), "html_url3"
                )
            )),
            response
        );
    }
}
