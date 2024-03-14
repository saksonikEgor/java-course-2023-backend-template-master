package edu.java.scrapper;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LiquibaseMigrationTest extends IntegrationTest {
    @Test
    @SneakyThrows
    public void migrateDatabase() {
        String query = """
            SELECT table_name
                FROM information_schema.tables
                WHERE table_schema='public' AND table_type='BASE TABLE'""";

        ResultSet resultSet = POSTGRES.createConnection("")
            .createStatement()
            .executeQuery(query);

        Set<String> actualTables = new HashSet<>();
        while (resultSet.next()) {
            actualTables.add(resultSet.getString("TABLE_NAME"));
        }

        assertEquals(
            Set.of("databasechangelog", "databasechangeloglock", "chats", "links_chats", "links"),
            actualTables
        );
    }
}
