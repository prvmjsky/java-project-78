package hexlet.code;

import hexlet.code.schemas.Schema;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestValidator {
    private static String shortString;
    private static String longString;
    private static String emptyString;
    private static String nullString;

    private Schema schema;

    @BeforeAll
    public static void setUp() {
        shortString = "text";
        longString = "another text 123";
        emptyString = "";
        nullString = null;
    }

    @BeforeEach
    public void setValidator() {
        var validator = new Validator();
        schema = validator.string();
    }

    @Test
    public void testDefault() {
        assertTrue(schema.isValid(shortString));
        assertTrue(schema.isValid(emptyString));
        assertTrue(schema.isValid(nullString));
    }

    @Test
    public void testRequired() {
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
    public void testMinLength() {

    }

    @Test
    public void testContains() {

    }
}
