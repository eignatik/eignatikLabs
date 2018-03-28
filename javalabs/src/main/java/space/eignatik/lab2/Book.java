package space.eignatik.lab2;

public class Book implements Product {
    @Override
    public void whoAmI() {
        System.out.println(Book.class.getCanonicalName());
    }
}
