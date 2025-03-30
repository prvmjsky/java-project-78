package hexlet.code.schemas;

public class StringSchema extends BaseSchema<String> {
    private boolean isRequired;
    private boolean minLengthRequired;
    private boolean contentRequired;

    private int minLength;
    private String content;

    public StringSchema() {
        isRequired = false;
        minLengthRequired = false;
        contentRequired = false;

        minLength = 0;
        content = "";
    }

    public void minLength(int number) {
        this.minLength = number;
        minLengthRequired = true;
    }

    public void contains(String string) throws IllegalArgumentException {

        if (string == null) {
            throw new IllegalArgumentException("required content cannot be null");
        }

        this.content = string;
        contentRequired = true;

        if (this.content.isEmpty() && isRequired) {
            isRequired = false;
        }
    }

    @Override
    public void required() {
        isRequired = !isRequired;

        if (isRequired && content.isEmpty()) {
            contentRequired = false;
        }
    }

    @Override
    public boolean isValid(String string) {

        if (string == null
            && (isRequired || minLengthRequired || contentRequired)) {
            return false;
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
