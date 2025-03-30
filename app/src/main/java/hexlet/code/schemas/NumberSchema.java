package hexlet.code.schemas;

public class NumberSchema extends BaseSchema<Integer> {
    private boolean isRequired;
    private boolean positivityRequired;
    private boolean rangeRequired;

    private Integer minRange;
    private Integer maxRange;

    public NumberSchema() {
        isRequired = false;
        positivityRequired = false;
        rangeRequired = false;
    }

    public void positive() {
        positivityRequired = !positivityRequired;
    }

    public void range(int min, int max) {
        this.minRange = min;
        this.maxRange = max;
        rangeRequired = true;
    }

    @Override
    public void required() {
        isRequired = !isRequired;
    }

    @Override
    public boolean isValid(Integer number) {
        if (number == null
            && (isRequired || positivityRequired || rangeRequired)) {
            return false;
        }

        if (positivityRequired && number < 1) {
            return false;
        }

        if (rangeRequired && (number < minRange || number > maxRange)) {
            return false;
        }

        return true;
    }
}
