package space.eignatik.lab2;

import space.eignatik.exceptions.LabException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Main {
    /**
     *
     * @param args first arg is length of array
     */
    public static void main(String... args) {
        if (args.length == 0) throw new LabException("Where are arguments?");

        List<Product> products = add(args);

        System.out.println("\nHo-ho, these Classes implement interface Present. Let's see how polymorphism works.\n");
        products
                .forEach(product -> {
                    if (product instanceof Present) ((Present) product).itCanBePresented();
                });

        System.out.println("\nAll objects:\n");
        products.forEach(System.out::println);

        System.out.println("\nObjects which implement Present:\n");
        products
                .stream()
                .filter(product -> product instanceof Present)
                .forEach(System.out::println);

    }

    private static List<Product> add(String[] args) {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(args[0]); i++) {
            int randomNumber = new Random().nextInt(5);
            switch (randomNumber) {
                case 1: products.add(new Book());
                        break;
                case 2: products.add(new Shoe());
                        break;
                case 3: products.add(new Toy());
                        break;
                case 4: products.add(new Picture());
                        break;
            }
        }
        return products;
    }

}
