package edu.java.util;

import edu.java.dto.model.BaseURL;
import edu.java.dto.model.Link;
import java.net.URI;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LinkFactory {
    private final Map<String, BaseURL> stringToBaseURL;

    @SneakyThrows
    public Link createLink(String url) {
        String domain = new URI(url).getHost();
        return new Link(url, stringToBaseURL.get(domain));
    }
}
