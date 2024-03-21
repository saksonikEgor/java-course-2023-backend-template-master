package edu.java.client;

import java.util.Map;

public interface SiteAPIClient {
    void call(Map<String, String> info) throws Exception;
}
