package hexlet.code.schemas;

public class BaseSchema<T> {
    void required() {

    }

    boolean isValid(T item) {
        return true;
    }
}
