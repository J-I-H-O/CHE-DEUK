package domain;

public class Car {

    private int location;

    public Car(int location) {
        this.location = location;
    }

    public void move(NumberGenerator numberGenerator) {
        if (numberGenerator.generate() >= 4) {
            location++;
        }
    }

    public int getLocation() {
        return location;
    }
}
