package hexlet.code;

import hexlet.code.schemas.NumberSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestNumberSchema {
    private NumberSchema schema;

    @BeforeEach
    void setSchema() {
        var validator = new Validator();
        schema = validator.number();
    }

    @Test
    void testDefault() {
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(0));
        assertTrue(schema.isValid(-1));
        assertTrue(schema.isValid(1));
    }

    @Test
    void testRequired() {
        schema.required();
        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(0));
        assertTrue(schema.isValid(-1));
        assertTrue(schema.isValid(1));
    }

    @Test
    void testPositive() {
        schema.positive();
        assertTrue(schema.isValid(null));
        assertFalse(schema.isValid(0));
        assertFalse(schema.isValid(-1));
        assertTrue(schema.isValid(1));
    }

    @Test
    void testRange() {
        schema.range(-1, 3);
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(0));
        assertTrue(schema.isValid(-1));
        assertFalse(schema.isValid(4));
    }

    @Test
    void testMixed() {
        schema.required().positive().range(-1, 3);
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(0));
        assertFalse(schema.isValid(-1));
        assertTrue(schema.isValid(1));
        assertFalse(schema.isValid(4));
    }
}
