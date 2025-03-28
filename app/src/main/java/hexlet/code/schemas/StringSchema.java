package hexlet.code.schemas;

public class StringSchema implements Schema {
    private boolean isRequired;

    public StringSchema() {
        isRequired = false;
    }

    @Override
    public void required() {
        isRequired = !isRequired;
    }

    @Override
    public boolean isValid(String string) {

        if (isRequired) {
            if (string == null || string.isEmpty()) {
                return false;
            }
        }

        return true;
    }
}
