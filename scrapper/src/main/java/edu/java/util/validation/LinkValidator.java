package edu.java.util.validation;

import edu.java.client.SiteAPIClient;
import edu.java.dto.model.BaseURL;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.net.URI;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LinkValidator implements ConstraintValidator<ValidLink, String> {
    private final Map<String, BaseURL> domainToBaseURL;
    private final Map<BaseURL, Function<String, Map<String, String>>> baseURLToExtractingInfoFunc;
    private final Map<BaseURL, SiteAPIClient> baseURLToClient;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        try {
            URI url = new URI(s);
            String domain = url.getHost();

            BaseURL baseURL = domainToBaseURL.get(domain);
            Map<String, String> info = baseURLToExtractingInfoFunc.get(baseURL)
                .apply(url.toString());

            SiteAPIClient client = baseURLToClient.get(baseURL);
            client.call(info);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
