package domain;

import java.util.Random;

public class RandomNumberGenerator implements NumberGenerator {

    private static final Random RANDOM = new Random();

    public int generate() {
        return RANDOM.nextInt(0, 10);
    }
}
