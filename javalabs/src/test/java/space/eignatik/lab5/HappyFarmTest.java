package space.eignatik.lab5;

import org.testng.annotations.Test;

public class HappyFarmTest {

    @Test
    public void testWorkers() throws InterruptedException {
        HappyFarm.deployPlants();
    }
}
