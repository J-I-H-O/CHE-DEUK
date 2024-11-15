package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CarTest {

    @DisplayName("자동차는 랜덤 값이 4 이상인 경우에만 앞으로 전진한다.")
    @ParameterizedTest
    @CsvSource(value = {"3, 0", "4, 1"})
    void move(int number, int expectedLocation) {
        ManualNumberGenerator numberGenerator = new ManualNumberGenerator();
        numberGenerator.setNumber(number);

        int initialLocation = 0;
        Car car = new Car(initialLocation);
        car.move(numberGenerator);

        assertThat(car.getLocation()).isEqualTo(expectedLocation);
    }
}
