package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public class MapSchema extends BaseSchema<Map<?, ?>> {

    public MapSchema required() {
        addCheck("required", Objects::nonNull);

        return this;
    }

    public MapSchema sizeof(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("size cannot be less than 0");
        }

        addCheck("sizeof", map -> map.size() == size);

        return this;
    }

    public <T> MapSchema shape(Map<?, BaseSchema<T>> schemas) {
        if (schemas == null) {
            throw new IllegalArgumentException("schemas cannot be null");
        }

        Predicate<Map<?, ?>> shapeCheck = (map) -> schemas.keySet().stream()
            .filter(map::containsKey)
            .allMatch(key -> schemas.get(key).isValid((T) map.get(key)));

        addCheck("shape", shapeCheck);

        return this;
    }
}
