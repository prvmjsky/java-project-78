package hexlet.code.schemas;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class BaseSchema<T> {

    private Map<String, Predicate<T>> checks = new HashMap<>();

    final void addCheck(String name, Predicate<T> check) {
        this.checks.put(name, check);
    }

    public final boolean isValid(T item) {
        if (item == null) {
            return !checks.containsKey("required");
        }

        return checks.values().stream()
            .allMatch(check -> check.test(item));
    }
}
