package hexlet.code.schemas;

import java.util.Objects;

public final class NumberSchema extends BaseSchema<Integer> {

    public NumberSchema required() {
        addCheck("required", Objects::nonNull);

        return this;
    }

    public NumberSchema positive() {
        addCheck("positive", num -> num >= 1);

        return this;
    }

    public NumberSchema range(int min, int max) {
        addCheck("range", num -> min <= num && num <= max);

        return this;
    }
}
