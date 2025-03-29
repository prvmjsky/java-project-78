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

    public void contains(String string) {
        content = string;
        contentRequired = true;

        if (content.isEmpty() && isRequired) {
            isRequired = false;
        }
    }

    private static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    @Override
    public boolean isValid(String string) {

        if (isRequired && isNullOrEmpty(string)) {
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
