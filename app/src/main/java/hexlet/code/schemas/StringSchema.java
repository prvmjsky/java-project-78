package hexlet.code.schemas;

public class StringSchema implements Schema {
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

    @Override
    public void required() {
        isRequired = !isRequired;

        if (isRequired && content.isEmpty()) {
            contentRequired = false;
        }
    }

    public void minLength(int number) {
        minLength = number;
        minLengthRequired = true;
    }

    public void contains(String string) throws IllegalArgumentException {

        if (string == null) {
            throw new IllegalArgumentException("required content cannot be null");
        }

        content = string;
        contentRequired = true;

        if (content.isEmpty() && isRequired) {
            isRequired = false;
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
