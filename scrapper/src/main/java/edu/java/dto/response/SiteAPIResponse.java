package edu.java.dto.response;

import java.time.OffsetDateTime;

public interface SiteAPIResponse {
    OffsetDateTime getLastUpdate();

    String getDescriptionOfUpdatesWhichAfter(OffsetDateTime time);
}
