package space.eignatik.lab5;

import space.eignatik.lab5.entities.Digger;
import space.eignatik.lab5.entities.Planter;
import space.eignatik.lab5.entities.TieMan;

/**
 *
 */
public class HappyFarm {
    private static int DEFAULT_PLANTS_NUMBER = 10;
    private static volatile int currentPlantNumber;

    private HappyFarm() {}

    /**
     * Initiates the process of plants deployment with number of plants according to input value
     * @param numberOfPlants int:: number of plants to be planted
     * @throws InterruptedException because of Thread::wait calls
     */
    public static void deployPlants(int numberOfPlants) throws InterruptedException {
        deploy(numberOfPlants <= 0 ? DEFAULT_PLANTS_NUMBER : numberOfPlants);
    }

    /**
     * Initiates the process of plants deployment with default number of plants
     * @throws InterruptedException because of Thread::wait calls
     */
    public static void deployPlants() throws InterruptedException {
        deploy(DEFAULT_PLANTS_NUMBER);
    }

    private static void deploy(int numberOfPlants) throws InterruptedException {
        if (numberOfPlants > 0) {
            currentPlantNumber = 0;
        } else {
            throw new IllegalArgumentException("number of plants should be more than 0");
        }

        Thread worker1;
        Thread worker2;
        Thread worker3;

        for (int i = 0; i < DEFAULT_PLANTS_NUMBER; i++) {
            worker1 = new Thread(new Digger());
            worker1.start();
            synchronized (worker1) {
                worker1.wait();
            }

            worker2 = new Thread(new Planter());
            worker2.start();
            synchronized (worker2) {
                worker2.wait();
            }


            worker3 = new Thread(new TieMan());
            worker3.start();
            synchronized (worker3) {
                worker3.wait();
            }

            currentPlantNumber++;
        }
    }

    public static int getCurrentPlantNumber() {
        return currentPlantNumber;
    }
}
