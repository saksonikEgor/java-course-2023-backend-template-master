package edu.java.dto.request;

import edu.java.util.validation.ValidLink;

public record AddLinkRequest(
    @ValidLink
    String link
) {
}
