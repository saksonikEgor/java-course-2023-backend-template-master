package edu.java.util;

import org.jetbrains.annotations.Nullable;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class URIUtils {
    private URIUtils() {
    }

    public static @Nullable URI castStringToURI(String uri) throws URISyntaxException, MalformedURLException{
            return new URL(uri).toURI();
    }
}
