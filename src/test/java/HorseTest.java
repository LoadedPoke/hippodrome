import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

class HorseTest {

    @Test
    void constructorNameNullException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 0, 0));
    }

    @Test
    void constructorNameNullMessage() {
        try {
            new Horse(null, 0, 0);
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void constructorBlankNameException(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, 0, 0));
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void constructorBlankNameMessage(String name) {
        try {
            new Horse(name, 0, 0);
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be blank.", e.getMessage());
        }
    }

    @Test
    void constructorNegativeSpeedException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("name", -1, 0));
    }

    @Test
    void constructorNegativeSpeedMessage() {
        try {
            new Horse("name", -1, 0);
        } catch (IllegalArgumentException e) {
            assertEquals("Speed cannot be negative.", e.getMessage());
        }
    }

    @Test
    void constructorNegativeDistanceException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("name", 0, -1));
    }

    @Test
    void constructorNegativeDistanceMessage() {
        try {
            new Horse("name", 0, -1);
        } catch (IllegalArgumentException e) {
            assertEquals("Distance cannot be negative.", e.getMessage());
        }
    }

    @Test
    void getName() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("horse name", 0, 0);
        Field name = Horse.class.getDeclaredField("name");
        name.setAccessible(true);
        String nameValue = (String) name.get(horse);
        assertEquals("horse name", nameValue);

        name.set(horse, "another horse name");
        assertEquals("another horse name", horse.getName());
    }

    @Test
    void getSpeed() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("horse name", 5.2, 0);
        Field speed = Horse.class.getDeclaredField("speed");
        speed.setAccessible(true);
        Double speedValue = (Double) speed.get(horse);
        assertEquals(5.2, speedValue);

        speed.set(horse, 10.5);
        assertEquals(10.5, horse.getSpeed());
    }

    @Test
    void getDistance() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("horse name", 0, 5.2);
        Field distance = Horse.class.getDeclaredField("distance");
        distance.setAccessible(true);
        Double distanceValue = (Double) distance.get(horse);
        assertEquals(5.2, distanceValue);

        distance.set(horse, 10.5);
        assertEquals(10.5, horse.getDistance());
    }

    @Test
    void getDistanceTwoParametersConstructor() {
        Horse twoParametersHorse = new Horse("horse name", 0);
        assertEquals(0, twoParametersHorse.getDistance());
    }

    @Test
    void moveCallGetRandomDouble() {
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            new Horse("name", 0, 0).move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.3, 0.5, 0.8})
    void moveCalculatedDistance(double doubleValue) {
        Horse horse = new Horse("name", 1, 1);
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(doubleValue);
            double expectedDistance = 1 + 1 * doubleValue;
            horse.move();
            assertEquals(expectedDistance, horse.getDistance());
        }
    }

}
