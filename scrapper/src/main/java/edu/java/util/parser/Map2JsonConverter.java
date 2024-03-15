package edu.java.util.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.SneakyThrows;

public class Map2JsonConverter {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private Map2JsonConverter() {
    }

    @SneakyThrows
    public static String map2Json(Map<String, String> map) {
        return OBJECT_MAPPER.writeValueAsString(map);
    }

    @SneakyThrows
    public static Map<String, String> json2Map(String json) {
        if (json == null) {
            return null;
        }

        return OBJECT_MAPPER.readValue(
            json,
            new TypeReference<HashMap<String, String>>() {
            }
        );
    }
}
