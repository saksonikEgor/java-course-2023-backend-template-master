package edu.java.dto.request;

import edu.java.util.validation.ValidLink;
import jakarta.validation.Valid;

public record AddLinkRequest(
    @ValidLink
    String link
) {
}
