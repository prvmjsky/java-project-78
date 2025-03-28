package hexlet.code.schemas;

public interface Schema {
    void required();
    boolean isValid(String string);
}
