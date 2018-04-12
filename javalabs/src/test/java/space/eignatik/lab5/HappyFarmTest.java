package space.eignatik.lab5;

import org.testng.annotations.Test;

public class HappyFarmTest {

    @Test
    public void testDeployWithDefaultValue() throws InterruptedException {
        HappyFarm.deployPlants();
    }

    @Test
    public void testDeployWithCustomValue() throws InterruptedException {
        HappyFarm.deployPlants(20);
    }
}
