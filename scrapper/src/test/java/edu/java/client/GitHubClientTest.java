package edu.java.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.dto.response.GitHubResponse;
import edu.java.configuration.ClientConfiguration;
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
    void gettingResponse() {
        String user = "saksonikEgor";
        String repo = "Checkers";
        String json = """
            {
               "id": 597823137,
               "node_id": "R_kgDOI6IOoQ",
               "name": "Checkers",
               "full_name": "saksonikEgor/Checkers",
               "private": false,
               "owner": {
                 "login": "saksonikEgor",
                 "id": 105850629,
                 "node_id": "U_kgDOBk8nBQ",
                 "avatar_url": "https://avatars.githubusercontent.com/u/105850629?v=4",
                 "gravatar_id": "",
                 "url": "https://api.github.com/users/saksonikEgor",
                 "html_url": "https://github.com/saksonikEgor",
                 "followers_url": "https://api.github.com/users/saksonikEgor/followers",
                 "following_url": "https://api.github.com/users/saksonikEgor/following{/other_user}",
                 "gists_url": "https://api.github.com/users/saksonikEgor/gists{/gist_id}",
                 "starred_url": "https://api.github.com/users/saksonikEgor/starred{/owner}{/repo}",
                 "subscriptions_url": "https://api.github.com/users/saksonikEgor/subscriptions",
                 "organizations_url": "https://api.github.com/users/saksonikEgor/orgs",
                 "repos_url": "https://api.github.com/users/saksonikEgor/repos",
                 "events_url": "https://api.github.com/users/saksonikEgor/events{/privacy}",
                 "received_events_url": "https://api.github.com/users/saksonikEgor/received_events",
                 "type": "User",
                 "site_admin": false
               },
               "html_url": "https://github.com/saksonikEgor/Checkers",
               "description": null,
               "fork": false,
               "url": "https://api.github.com/repos/saksonikEgor/Checkers",
               "forks_url": "https://api.github.com/repos/saksonikEgor/Checkers/forks",
               "keys_url": "https://api.github.com/repos/saksonikEgor/Checkers/keys{/key_id}",
               "collaborators_url": "https://api.github.com/repos/saksonikEgor/Checkers/collaborators{/collaborator}",
               "teams_url": "https://api.github.com/repos/saksonikEgor/Checkers/teams",
               "hooks_url": "https://api.github.com/repos/saksonikEgor/Checkers/hooks",
               "issue_events_url": "https://api.github.com/repos/saksonikEgor/Checkers/issues/events{/number}",
               "events_url": "https://api.github.com/repos/saksonikEgor/Checkers/events",
               "assignees_url": "https://api.github.com/repos/saksonikEgor/Checkers/assignees{/user}",
               "branches_url": "https://api.github.com/repos/saksonikEgor/Checkers/branches{/branch}",
               "tags_url": "https://api.github.com/repos/saksonikEgor/Checkers/tags",
               "blobs_url": "https://api.github.com/repos/saksonikEgor/Checkers/git/blobs{/sha}",
               "git_tags_url": "https://api.github.com/repos/saksonikEgor/Checkers/git/tags{/sha}",
               "git_refs_url": "https://api.github.com/repos/saksonikEgor/Checkers/git/refs{/sha}",
               "trees_url": "https://api.github.com/repos/saksonikEgor/Checkers/git/trees{/sha}",
               "statuses_url": "https://api.github.com/repos/saksonikEgor/Checkers/statuses/{sha}",
               "languages_url": "https://api.github.com/repos/saksonikEgor/Checkers/languages",
               "stargazers_url": "https://api.github.com/repos/saksonikEgor/Checkers/stargazers",
               "contributors_url": "https://api.github.com/repos/saksonikEgor/Checkers/contributors",
               "subscribers_url": "https://api.github.com/repos/saksonikEgor/Checkers/subscribers",
               "subscription_url": "https://api.github.com/repos/saksonikEgor/Checkers/subscription",
               "commits_url": "https://api.github.com/repos/saksonikEgor/Checkers/commits{/sha}",
               "git_commits_url": "https://api.github.com/repos/saksonikEgor/Checkers/git/commits{/sha}",
               "comments_url": "https://api.github.com/repos/saksonikEgor/Checkers/comments{/number}",
               "issue_comment_url": "https://api.github.com/repos/saksonikEgor/Checkers/issues/comments{/number}",
               "contents_url": "https://api.github.com/repos/saksonikEgor/Checkers/contents/{+path}",
               "compare_url": "https://api.github.com/repos/saksonikEgor/Checkers/compare/{base}...{head}",
               "merges_url": "https://api.github.com/repos/saksonikEgor/Checkers/merges",
               "archive_url": "https://api.github.com/repos/saksonikEgor/Checkers/{archive_format}{/ref}",
               "downloads_url": "https://api.github.com/repos/saksonikEgor/Checkers/downloads",
               "issues_url": "https://api.github.com/repos/saksonikEgor/Checkers/issues{/number}",
               "pulls_url": "https://api.github.com/repos/saksonikEgor/Checkers/pulls{/number}",
               "milestones_url": "https://api.github.com/repos/saksonikEgor/Checkers/milestones{/number}",
               "notifications_url": "https://api.github.com/repos/saksonikEgor/Checkers/notifications{?since,all,participating}",
               "labels_url": "https://api.github.com/repos/saksonikEgor/Checkers/labels{/name}",
               "releases_url": "https://api.github.com/repos/saksonikEgor/Checkers/releases{/id}",
               "deployments_url": "https://api.github.com/repos/saksonikEgor/Checkers/deployments",
               "created_at": "2023-02-05T18:38:39Z",
               "updated_at": "2023-02-05T18:39:09Z",
               "pushed_at": "2023-11-23T11:29:05Z",
               "git_url": "git://github.com/saksonikEgor/Checkers.git",
               "ssh_url": "git@github.com:saksonikEgor/Checkers.git",
               "clone_url": "https://github.com/saksonikEgor/Checkers.git",
               "svn_url": "https://github.com/saksonikEgor/Checkers",
               "homepage": null,
               "size": 45,
               "stargazers_count": 0,
               "watchers_count": 0,
               "language": "Java",
               "has_issues": true,
               "has_projects": true,
               "has_downloads": true,
               "has_wiki": true,
               "has_pages": false,
               "has_discussions": false,
               "forks_count": 0,
               "mirror_url": null,
               "archived": false,
               "disabled": false,
               "open_issues_count": 0,
               "license": {
                 "key": "mit",
                 "name": "MIT License",
                 "spdx_id": "MIT",
                 "url": "https://api.github.com/licenses/mit",
                 "node_id": "MDc6TGljZW5zZTEz"
               },
               "allow_forking": true,
               "is_template": false,
               "web_commit_signoff_required": false,
               "topics": [

               ],
               "visibility": "public",
               "forks": 0,
               "open_issues": 0,
               "watchers": 0,
               "default_branch": "master",
               "temp_clone_token": null,
               "network_count": 0,
               "subscribers_count": 1
             }""";

        stubFor(get(urlEqualTo("/repos/" + user + "/" + repo))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(json)));

        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setGitHubBaseURL("http://localhost:8080");

        GitHubResponse response = clientConfiguration.gitHubClient().getInfo(user, repo);

        verify(getRequestedFor(urlEqualTo("/repos/" + user + "/" + repo)));
        assertEquals(new GitHubResponse(
            user + "/" + repo,
            OffsetDateTime.parse("2023-02-05T18:38:39Z"),
            OffsetDateTime.parse("2023-02-05T18:39:09Z")
        ), response);
    }
}
