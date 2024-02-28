package edu.java.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.SneakyThrows;
import org.postgresql.util.PGobject;

public class Map2JsonConverter {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private Map2JsonConverter() {
    }

    @SneakyThrows
    public static PGobject map2Json(Map<String, String> map) {
        PGobject json = new PGobject();

        json.setType("json");
        json.setValue(OBJECT_MAPPER.writeValueAsString(map));

        return json;
    }

    @SneakyThrows
    public static Map<String, String> json2Map(PGobject json) {
        if (json == null || json.getValue() == null) {
            return null;
        }

        return OBJECT_MAPPER.readValue(
            json.getValue(),
            new TypeReference<HashMap<String, String>>() {
            }
        );
    }
}
