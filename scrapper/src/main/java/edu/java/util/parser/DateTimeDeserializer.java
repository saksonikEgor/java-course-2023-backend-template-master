package edu.java.util.parser;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateTimeDeserializer extends StdDeserializer<OffsetDateTime> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        .withZone(ZoneOffset.UTC);

    public DateTimeDeserializer() {
        this(null);
    }

    protected DateTimeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public OffsetDateTime deserialize(
        JsonParser jsonParser,
        DeserializationContext deserializationContext
    ) throws IOException, JacksonException {
        String date = jsonParser.getText();
        return OffsetDateTime.parse(date, FORMATTER);
    }
}
