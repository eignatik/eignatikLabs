package space.eignatik.lab5;

public class Main {
    public static void main(String[] args) {
        try {
            performTask(args);
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
    }

    private static void performTask(String...args) throws InterruptedException {
        if (args.length == 0) {
            System.out.println("No arguments passed. Default implementation will be used");
            HappyFarm.deployPlants();
        } else {
            HappyFarm.deployPlants(Integer.parseInt(args[0]));
        }
    }
}
