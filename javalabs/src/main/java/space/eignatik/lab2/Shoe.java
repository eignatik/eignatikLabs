package space.eignatik.lab2;

public class Shoe implements Product {
    @Override
    public void whoAmI() {
        System.out.println(Shoe.class.getCanonicalName());
    }
}
