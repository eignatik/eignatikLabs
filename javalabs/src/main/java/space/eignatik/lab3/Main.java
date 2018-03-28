package space.eignatik.lab3;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) throw new IllegalArgumentException("Wrong arguemnts!");
        System.out.println(args[1] + "=" + PropertyHelper.getProperty(args[0], args[1]).orElse(null));
    }
}
