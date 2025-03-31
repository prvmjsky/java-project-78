package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map<?, ?>> {
    private boolean isRequired;
    private boolean sizeRequired;

    private Integer size;

    public MapSchema() {
        isRequired = false;
        sizeRequired = false;
    }

    public MapSchema sizeof(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("size cannot be less than 0");
        }

        this.size = number;
        sizeRequired = true;

        return this;
    }

    public MapSchema required() {
        isRequired = true;

        return this;
    }

    @Override
    public boolean isValid(Map<?, ?> map) {
        if (map == null) {
            return !isRequired;
        }

        if (sizeRequired && (map.size() != size)) {
            return false;
        }

        return true;
    }
}
