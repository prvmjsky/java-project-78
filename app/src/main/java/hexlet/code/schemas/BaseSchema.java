package hexlet.code.schemas;

public class BaseSchema<T> {
    boolean isValid(T item) {
        return true;
    }
}
