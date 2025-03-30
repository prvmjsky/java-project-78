package hexlet.code;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestStringSchema {
    private static String shortString;
    private static String longString;
    private static String emptyString;
    private static String nullString;

    private static StringSchema schema;

    @BeforeAll
    static void setUp() {
        shortString = "text";
        longString = "another text 123";
        emptyString = "";
        nullString = null;
    }

    @BeforeEach
    void setSchema() {
        var validator = new Validator();
        schema = validator.string();
    }

    @Test
    void testDefault() {
        assertTrue(schema.isValid(shortString));
        assertTrue(schema.isValid(emptyString));
        assertTrue(schema.isValid(nullString));
    }

    @Test
    void testRequired() {
        schema.required();
        assertTrue(schema.isValid(shortString));
        assertFalse(schema.isValid(emptyString));
        assertFalse(schema.isValid(nullString));

        schema.required();
        assertTrue(schema.isValid(shortString));
        assertTrue(schema.isValid(emptyString));
        assertTrue(schema.isValid(nullString));
    }

    @Test
    void testMinLength() {
        schema.minLength(0);
        assertTrue(schema.isValid(shortString));
        assertTrue(schema.isValid(longString));
        assertTrue(schema.isValid(emptyString));
        assertFalse(schema.isValid(nullString));

        schema.minLength(5);
        assertFalse(schema.isValid(shortString));
        assertTrue(schema.isValid(longString));
        assertFalse(schema.isValid(emptyString));
        assertFalse(schema.isValid(nullString));
    }

    @Test
    void testContains() {
        assertThrows(IllegalArgumentException.class, () -> schema.contains(null));

        schema.contains("");
        assertTrue(schema.isValid(shortString));
        assertTrue(schema.isValid(longString));
        assertTrue(schema.isValid(emptyString));
        assertFalse(schema.isValid(nullString));

        schema.contains("text");
        assertTrue(schema.isValid(shortString));
        assertTrue(schema.isValid(longString));
        assertFalse(schema.isValid(emptyString));
        assertFalse(schema.isValid(nullString));

        schema.contains(" ");
        assertFalse(schema.isValid(shortString));
        assertTrue(schema.isValid(longString));
        assertFalse(schema.isValid(emptyString));
        assertFalse(schema.isValid(nullString));
    }

    @Test
    void testContainsWithRequired() {
        schema.contains("");
        schema.required();
        assertFalse(schema.isValid(emptyString));

        schema.contains("");
        assertTrue(schema.isValid(emptyString));
    }
}
