package edu.java.util;

import edu.java.dto.model.BaseURL;
import edu.java.dto.model.Link;
import java.net.URI;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LinkFactory {
    private final Map<String, BaseURL> domainToBaseURL;
    private final Map<BaseURL, Function<String, Map<String, String>>> baseURLToExtractingInfoFunc;

    @SneakyThrows
    public Link createLink(String url) {
        String domain = new URI(url).getHost();

        BaseURL baseURL = domainToBaseURL.get(domain);
        Map<String, String> info = baseURLToExtractingInfoFunc.get(baseURL)
            .apply(url);

        return new Link(url, baseURL, info);
    }
}
