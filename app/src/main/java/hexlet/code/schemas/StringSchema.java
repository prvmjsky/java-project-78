package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {

    public StringSchema required() {
        addCheck("required", str -> str != null && !str.isEmpty());

        return this;
    }

    public StringSchema minLength(int number) {
        addCheck("minLength", str -> str.length() >= number);

        return this;
    }

    public StringSchema contains(String content) throws IllegalArgumentException {

        if (content == null) {
            throw new IllegalArgumentException("required content cannot be null");
        }

        addCheck("contains", str -> str.contains(content));

        return this;
    }
}
