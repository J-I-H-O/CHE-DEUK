package domain;

public class ManualNumberGenerator implements NumberGenerator {

    private int number;

    @Override
    public int generate() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
