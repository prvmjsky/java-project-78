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
    private Map<String, BaseSchema<?>> schemas;

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

    @Test
    void testStringShape() {
        schemas.put("str1", validator.string().required().minLength(6).contains("ring"));
        schemas.put("str2", validator.string().required().minLength(1));
        schema.shape(schemas);

        Map<String, String> correct = Map.of("str1", "string", "str2", "spring");
        Map<String, String> incorrect = Map.of("str1", "string", "str2", " ");

        assertTrue(schema.isValid(correct));
        assertFalse(schema.isValid(incorrect));
    }

    @Test
    void testNumberShape() {
        schemas.put("num1", validator.number().positive());
        schemas.put("num2", validator.number().required().positive().range(-1, 1));
        schema.shape(schemas);

        Map<String, Integer> correct = new HashMap<>();
        correct.put("num1", null);
        correct.put("num2", 1);

        Map<String, Integer> incorrect = new HashMap<>();
        incorrect.put("num1", 1);
        incorrect.put("num2", 0);

        assertTrue(schema.isValid(correct));
        assertFalse(schema.isValid(incorrect));
    }

    @Test
    void testMapShape() {
        schemas.put("map1", validator.map().required().sizeof(1));
        schemas.put("map2", validator.map().sizeof(2));
        schema.shape(schemas);

        Map<String, Map<String, Number>> correct = new HashMap<>();
        correct.put("map1", Map.of("str1", 1));
        correct.put("map2", Map.of("str1", 1, "str2", 2));

        Map<String, Map<String, Number>> incorrect = new HashMap<>();
        incorrect.put("map1", Map.of("str1", 1));
        incorrect.put("map2", Map.of("str1", 1));

        assertTrue(schema.isValid(correct));
        assertFalse(schema.isValid(incorrect));
    }

    @Test
    void testComplexShape() {
        Map<String, BaseSchema<Integer>> numberSchema = Map.of("num", validator.number().positive());
        schemas.put("map1", validator.map().shape(numberSchema));
        schema.shape(schemas);

        Map<String, Map<String, Number>> correct = new HashMap<>();
        correct.put("map1", Map.of("num", 1));
        correct.put("map2", Map.of());

        Map<String, Map<String, Number>> incorrect = new HashMap<>();
        incorrect.put("map1", Map.of("num", -1));
        incorrect.put("map2", Map.of());

        assertTrue(schema.isValid(correct));
        assertFalse(schema.isValid(incorrect));
    }
}
