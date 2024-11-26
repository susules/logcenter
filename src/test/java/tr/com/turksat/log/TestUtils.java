package tr.com.turksat.log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author scinkir
 * @since 25.11.2024
 */
public class TestUtils {

    public static String readJsonFile(String filename) {
        try {
            return new String(
                    TestUtils.class.getResourceAsStream("/json/" + filename)
                            .readAllBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException("Error reading test JSON file", e);
        }
    }

    public static void assertJsonEquals(String expected, String actual) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode expectedNode = mapper.readTree(expected);
            JsonNode actualNode = mapper.readTree(actual);
            assertEquals(expectedNode, actualNode);
        } catch (Exception e) {
            fail("JSON comparison failed: " + e.getMessage());
        }
    }
}

