import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}
