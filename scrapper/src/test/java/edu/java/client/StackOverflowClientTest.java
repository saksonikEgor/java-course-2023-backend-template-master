package edu.java.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.configuration.ClientConfiguration;
import edu.java.dto.response.StackOverflowResponse;
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

public class StackOverflowClientTest {
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
        final long QUESTION_ID = 70452633L;
        String json = new String(Files.readAllBytes(Paths.get("src/test/resources/stackoverflow-response.json")));

        stubFor(get(urlEqualTo("/questions/" + QUESTION_ID + "/answers?site=stackoverflow"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(json)
            ));

        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setStackOverflowBaseURL("http://localhost:8080");

        StackOverflowResponse response = clientConfiguration.stackOverflowClient().getInfo(QUESTION_ID);

        verify(getRequestedFor(urlEqualTo("/questions/" + QUESTION_ID + "/answers?site=stackoverflow")));

        assertEquals(new StackOverflowResponse(List.of(
            new StackOverflowResponse.Answer(
                new StackOverflowResponse.Answer.Owner("Marek Podyma"),
                OffsetDateTime.parse("2024-02-29T18:08:07Z"),
                QUESTION_ID
            ),
            new StackOverflowResponse.Answer(
                new StackOverflowResponse.Answer.Owner("Joe Caruso"),
                OffsetDateTime.parse("2024-02-15T19:35:06Z"),
                QUESTION_ID
            ),
            new StackOverflowResponse.Answer(
                new StackOverflowResponse.Answer.Owner("Yaohui Wu"),
                OffsetDateTime.parse("2024-02-01T03:35:16Z"),
                QUESTION_ID
            )
        )), response);
    }
}
