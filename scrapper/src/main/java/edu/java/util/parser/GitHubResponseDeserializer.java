package edu.java.util.parser;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import edu.java.dto.response.GitHubResponse;
import java.io.IOException;
import java.util.List;

public class GitHubResponseDeserializer extends StdDeserializer<GitHubResponse> {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public GitHubResponseDeserializer() {
        this(null);
    }

    protected GitHubResponseDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public GitHubResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        List<GitHubResponse.CommitInfo> commitInfos = MAPPER.readValue(
            node.toPrettyString(),
            new TypeReference<>() {
            }
        );

        return new GitHubResponse(commitInfos);
    }
}
