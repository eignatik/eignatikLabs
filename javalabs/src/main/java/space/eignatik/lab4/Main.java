package space.eignatik.lab4;

import java.util.Scanner;

public class Main {
    private static PropertiesCache cache;

    public static void main(String[] args) {
        if (args.length != 1 || args[0] == null || args[0].isEmpty()) throw new IllegalArgumentException("You had to specify filepath");
        startDialog(args[0]);
    }

    private static void startDialog(String filePath) {
        cache = new PropertiesCache(filePath);

        System.out.println(
                "\n\n************************************************************" +
                "\n***** Welcome to hide and seek game with properties! *******\n" +
                "************************************************************\n" +
                        "> You are now looking for properties in \n\t" + filePath + "\n\nWrite property key to display its value. Press \"\\q\" to exit.");


        boolean isTerminated = false;
        Scanner scanner = new Scanner(System.in);
        while(!isTerminated) {
            String next = scanner.next();
            if ("\\q".equals(next)) {
                isTerminated = true;
                continue;
            }
            System.out.println(next + "=" + cache.getProperties().get(next) + "\n");
        }
    }
}
