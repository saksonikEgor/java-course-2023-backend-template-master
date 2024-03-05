package edu.java.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.configuration.ClientConfiguration;
import edu.java.dto.response.StackOverflowResponse;
import java.time.OffsetDateTime;
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

//    @Test
//    void gettingResponse() {
//        final long USER_ID = 70452633L;
//        String json = """
//            {
//                "items": [
//                    {
//                        "tags": [
//                            "java",
//                            "junit",
//                            "junit5",
//                            "spring-test",
//                            "junit-jupiter"
//                        ],
//                        "owner": {
//                            "account_id": 965625,
//                            "reputation": 1698,
//                            "user_id": 1103606,
//                            "user_type": "registered",
//                            "accept_rate": 51,
//                            "profile_image": "https://www.gravatar.com/avatar/0d93cc650b93939ea91066fcb5aefae0?s=256&d=identicon&r=PG",
//                            "display_name": "Peter Penzov",
//                            "link": "https://stackoverflow.com/users/1103606/peter-penzov"
//                        },
//                        "post_state": "Published",
//                        "is_answered": true,
//                        "view_count": 127543,
//                        "accepted_answer_id": 70503467,
//                        "answer_count": 24,
//                        "score": 56,
//                        "last_activity_date": 1708025706,
//                        "creation_date": 1640192711,
//                        "last_edit_date": 1640210015,
//                        "question_id": 70452633,
//                        "content_license": "CC BY-SA 4.0",
//                        "link": "https://stackoverflow.com/questions/70452633/org-junit-platform-commons-junitexception-testengine-with-id-junit-jupiter-fa",
//                        "title": "org.junit.platform.commons.JUnitException: TestEngine with ID &#39;junit-jupiter&#39; failed to discover tests"
//                    }
//                ],
//                "has_more": false,
//                "quota_max": 300,
//                "quota_remaining": 265
//            }""";
//
//        stubFor(get(urlEqualTo("/questions/" + USER_ID + "?site=stackoverflow"))
//            .willReturn(aResponse()
//                .withHeader("Content-Type", "application/json")
//                .withBody(json)
//            ));
//
//        ClientConfiguration clientConfiguration = new ClientConfiguration();
//        clientConfiguration.setStackOverflowBaseURL("http://localhost:8080");
//
//        StackOverflowResponse response = clientConfiguration.stackOverflowClient().getInfo(USER_ID);
//
//        verify(getRequestedFor(urlEqualTo("/questions/" + USER_ID + "?site=stackoverflow")));
//        assertEquals(new StackOverflowResponse(
//            OffsetDateTime.parse("2021-12-22T17:05:11Z"),
//            OffsetDateTime.parse("2021-12-22T21:53:35Z"),
//            USER_ID,
//            "org.junit.platform.commons.JUnitException: TestEngine with ID &#39;junit-jupiter&#39; failed to discover tests"
//        ), response);
//    }
}
