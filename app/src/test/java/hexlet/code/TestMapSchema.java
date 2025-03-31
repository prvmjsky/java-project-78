package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMapSchema {
    private static Map<Object, Object> emptyMap;
    private static Map<String, Number> smallMap;
    private static Map<String, Number> bigMap;

    private Validator validator;
    private MapSchema schema;
    Map<String, BaseSchema<?>> schemas;

    @BeforeAll
    static void setFixtures() {
        emptyMap = Map.of();
        smallMap = Map.of("one", 1, "two", 2);
        bigMap = Map.of("one", 1, "two", 2, "three", 3, "four", 4);
    }

    @BeforeEach
    void setValidationTools() {
        validator = new Validator();
        schema = validator.map();
        schemas = new HashMap<>();
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

//    @Test
//    void testShape() {
//        schemas.put("string", validator.string().required().minLength(2));
//        schemas.put("number");
//        schemas.put("map");
//        schema.shape(schemas);
//    }
}
