package edu.java.util.validation;

import edu.java.client.SiteAPIClient;
import edu.java.configuration.ClientConfiguration;
import edu.java.dto.model.BaseURL;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.net.URI;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class LinkValidator implements ConstraintValidator<ValidLink, String> {
    private final Map<String, BaseURL> stringToBaseURL;
    private final Map<BaseURL, SiteAPIClient> baseURLToClient;

    public LinkValidator(ClientConfiguration clientConfiguration) {
        this.stringToBaseURL = clientConfiguration.getStringToBaseUrl();
        this.baseURLToClient = clientConfiguration.getBaseUrlToClient();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        try {
            URI url = new URI(s);

            String domain = url.getHost();
            String path = url.getPath();

            BaseURL baseURL = stringToBaseURL.get(domain);
            SiteAPIClient client = baseURLToClient.get(baseURL);

            if (!client.matchPath(path)) {
                return false;
            }
            client.call(path);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
