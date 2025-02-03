import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class HorseTest {

    @Test
    void constructorNameNullException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 0, 0));
    }
}
