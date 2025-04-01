package hexlet.code.schemas;

import java.util.Map;

public class MapSchema<K, V> extends BaseSchema<Map<K, V>> {
    private boolean isRequired;
    private boolean sizeRequired;
    private boolean schemasProvided;

    private Integer size;
    private Map<K, BaseSchema<V>> schemas;

    public MapSchema() {
        isRequired = false;
        sizeRequired = false;
        schemasProvided = false;
    }

    public MapSchema<K, V> sizeof(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("size cannot be less than 0");
        }

        this.size = number;
        sizeRequired = true;

        return this;
    }

    public MapSchema<K, V> required() {
        isRequired = true;

        return this;
    }

    public MapSchema<K, V> shape(Map<K, BaseSchema<V>> map) {
        if (map == null) {
            throw new IllegalArgumentException("schemas cannot be null");
        }

        this.schemas = map;
        schemasProvided = true;

        return this;
    }

    @Override
    public boolean isValid(Map<K, V> map) {
        if (map == null) {
            return !isRequired;
        }

        if (sizeRequired && (map.size() != size)) {
            return false;
        }

        if (schemasProvided) {
            var isAllValid = schemas.keySet().stream()
                .filter(map::containsKey)
                .allMatch(key -> schemas.get(key).isValid(map.get(key)));

            if (!isAllValid) {
                return false;
            }
        }

        return true;
    }
}
