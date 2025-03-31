package hexlet.code.schemas;

public class StringSchema extends BaseSchema<String> {
    private boolean isRequired;
    private boolean minLengthRequired;
    private boolean contentRequired;

    private Integer minLength;
    private String content;

    public StringSchema() {
        isRequired = false;
        minLengthRequired = false;
        contentRequired = false;
    }

    public StringSchema minLength(int number) {
        this.minLength = number;
        minLengthRequired = true;

        return this;
    }

    public StringSchema contains(String string) throws IllegalArgumentException {

        if (string == null) {
            throw new IllegalArgumentException("required content cannot be null");
        }

        this.content = string;
        contentRequired = true;

        return this;
    }

    public StringSchema required() {
        isRequired = true;

        return this;
    }

    @Override
    public boolean isValid(String string) {

        if (string == null) {
            return !isRequired;
        }

        if (isRequired && string.isEmpty()) {
            return false;
        }

        if (minLengthRequired && string.length() < minLength) {
            return false;
        }

        if (contentRequired && !string.contains(content)) {
            return false;
        }

        return true;
    }
}
