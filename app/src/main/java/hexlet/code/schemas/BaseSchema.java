package hexlet.code.schemas;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class BaseSchema<T> {
    private Map<String, Predicate<T>> checks = new HashMap<>();

    void addCheck(String name, Predicate<T> check) {
        this.checks.put(name, check);
    }

    public boolean isValid(T item) {
        if (item == null && !checks.containsKey("required")) {
            return true;
        }

        return checks.values().stream()
            .allMatch(check -> check.test(item));
    }
}
