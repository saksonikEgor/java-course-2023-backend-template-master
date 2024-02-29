package edu.java.dto.request;

import edu.java.util.validation.ValidLink;
import jakarta.validation.Valid;

public record RemoveLinkRequest(
    @ValidLink
    String link
) {
}
