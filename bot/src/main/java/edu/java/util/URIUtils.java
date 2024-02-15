package edu.java.util;

import java.net.URI;
import java.net.URISyntaxException;
import org.jetbrains.annotations.Nullable;

public class URIUtils {
    private URIUtils() {
    }

    public static @Nullable URI castStringToURI(String uri) {
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            return null;
        }
    }
}
