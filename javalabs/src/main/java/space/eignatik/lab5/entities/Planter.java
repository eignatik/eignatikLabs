package space.eignatik.lab5.entities;

import space.eignatik.lab5.HappyFarm;

public class Planter implements CouchPotato, Runnable {
    @Override
    public void run() {
        doWork();
        synchronized (this) {
            this.notify();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void doWork() {
        System.out.println(this.getClass().getCanonicalName() + " - " + HappyFarm.getCurrentPlantNumber());
    }
}
