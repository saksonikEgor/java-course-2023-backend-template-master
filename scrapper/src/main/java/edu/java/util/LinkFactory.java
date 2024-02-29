package edu.java.util;

import edu.java.configuration.ClientConfiguration;
import edu.java.dto.model.BaseURL;
import edu.java.dto.model.Link;
import java.net.URI;
import java.util.Map;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class LinkFactory {
    private final Map<String, BaseURL> stringToBaseURL;

    public LinkFactory(ClientConfiguration clientConfiguration) {
        this.stringToBaseURL = clientConfiguration.getStringToBaseUrl();
    }

    @SneakyThrows
    public Link createLink(String url) {
        String domain = new URI(url).getHost();
        return new Link(url, stringToBaseURL.get(domain));
    }
}
