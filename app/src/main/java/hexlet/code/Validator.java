package hexlet.code;

import hexlet.code.schemas.Schema;
import hexlet.code.schemas.StringSchema;

public class Validator {
    public Schema string() {
        return new StringSchema();
    }
}
