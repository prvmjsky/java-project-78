package hexlet.code;

import hexlet.code.schemas.MapSchema;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMapSchema {
    private static Map<Object, Object> emptyMap;
    private static Map<String, Number> smallMap;
    private static Map<String, Number> bigMap;

    private MapSchema schema;

    @BeforeAll
    static void setUp() {
        emptyMap = Map.of();
        smallMap = Map.of("one", 1, "two", 2);
        bigMap = Map.of("one", 1, "two", 2, "three", 3, "four", 4);
    }

    @BeforeEach
    void setSchema() {
        var validator = new Validator();
        schema = validator.map();
    }

    @Test
    void testDefault() {
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(emptyMap));
        assertTrue(schema.isValid(smallMap));
        assertTrue(schema.isValid(bigMap));
    }

    @Test
    void testRequired() {
        schema.required();
        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(emptyMap));
        assertTrue(schema.isValid(smallMap));
    }

    @Test
    void testSizeof() {
        assertThrows(IllegalArgumentException.class, () -> schema.sizeof(-1));

        schema.sizeof(0);
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(emptyMap));
        assertFalse(schema.isValid(smallMap));

        schema.sizeof(2);
        assertTrue(schema.isValid(null));
        assertFalse(schema.isValid(emptyMap));
        assertTrue(schema.isValid(smallMap));

        schema.sizeof(4);
        assertFalse(schema.isValid(smallMap));
        assertTrue(schema.isValid(bigMap));
    }
}
