package utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSONReaderTest {
    private JSONReader jsonReader = new JSONReader("src/res/testParameters.json");

    @Test
    void testGetWidth() {
        assertEquals(20, jsonReader.getWidth());
    }

    @Test
    void testGetHeight() {
        assertEquals(10, jsonReader.getHeight());
    }

    @Test
    void testGetJungleRatio() {
        assertEquals(0.4, jsonReader.getJungleRatio());
    }

    @Test
    void testGetStartEnergy() {
        assertEquals(30, jsonReader.getStartEnergy());
    }

    @Test
    void testGetMoveEnergy() {
        assertEquals(1, jsonReader.getMoveEnergy());
    }

    @Test
    void testGetPlantEnergy() {
        assertEquals(10, jsonReader.getPlantEnergy());
    }

    @Test
    void testGetInitialNumberOfAnimals() {
        assertEquals(3, jsonReader.getInitialNumberOfAnimals());
    }
}
