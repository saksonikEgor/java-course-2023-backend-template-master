package edu.java.dto.request;

import edu.java.util.validation.ValidLink;

public record RemoveLinkRequest(
    @ValidLink
    String link
) {
}
