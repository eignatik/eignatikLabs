package space.eignatik.lab2;

public class Toy implements Present {
    @Override
    public String itCanBePresented() {
        whoAmI();
        System.out.println("\tI implement Present interface");
        return null;
    }

    @Override
    public void whoAmI() {
        System.out.println(Toy.class.getCanonicalName());
    }
}
