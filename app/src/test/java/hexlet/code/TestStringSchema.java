package hexlet.code;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestStringSchema {
    private static String emptyString;
    private static String shortString;
    private static String longString;

    private static StringSchema schema;

    @BeforeAll
    static void setFixtures() {
        emptyString = "";
        shortString = "text";
        longString = "another text 123";
    }

    @BeforeEach
    void setSchema() {
        var validator = new Validator();
        schema = validator.string();
    }

    @Test
    void testDefault() {
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(emptyString));
        assertTrue(schema.isValid(shortString));
    }

    @Test
    void testRequired() {
        schema.required();
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(emptyString));
        assertTrue(schema.isValid(shortString));
    }

    @Test
    void testMinLength() {
        schema.minLength(0);
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(emptyString));
        assertTrue(schema.isValid(shortString));
        assertTrue(schema.isValid(longString));

        schema.minLength(5);
        assertTrue(schema.isValid(null));
        assertFalse(schema.isValid(emptyString));
        assertFalse(schema.isValid(shortString));
        assertTrue(schema.isValid(longString));
    }

    @Test
    void testContains() {
        assertThrows(IllegalArgumentException.class, () -> schema.contains(null));

        schema.contains("");
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(emptyString));
        assertTrue(schema.isValid(shortString));
        assertTrue(schema.isValid(longString));

        schema.contains("text");
        assertTrue(schema.isValid(null));
        assertFalse(schema.isValid(emptyString));
        assertTrue(schema.isValid(shortString));
        assertTrue(schema.isValid(longString));

        schema.contains(" ");
        assertTrue(schema.isValid(null));
        assertFalse(schema.isValid(emptyString));
        assertFalse(schema.isValid(shortString));
        assertTrue(schema.isValid(longString));
    }

    @Test
    void testContainsWithMinLength() {
        schema.contains("text");
        schema.minLength(4);
        assertTrue(schema.isValid(shortString));
        assertTrue(schema.isValid(longString));

        schema.minLength(5);
        assertFalse(schema.isValid(shortString));
        assertTrue(schema.isValid(longString));
    }
}
