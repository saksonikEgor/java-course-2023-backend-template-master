package edu.java.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class URIUtils {
    private URIUtils() {
    }

    public static URI castStringToURI(String uri) throws URISyntaxException, MalformedURLException {
        return new URL(uri).toURI();
    }
}
