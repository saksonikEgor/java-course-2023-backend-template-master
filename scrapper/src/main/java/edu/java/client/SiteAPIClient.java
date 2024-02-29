package edu.java.client;

public interface SiteAPIClient {
    boolean matchPath(String path);

    void call(String path) throws Exception;
}
