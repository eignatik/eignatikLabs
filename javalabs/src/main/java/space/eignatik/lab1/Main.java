package space.eignatik.lab1;

import space.eignatik.exceptions.LabException;

import static java.lang.Double.*;
import static space.eignatik.lab1.Calculator.*;

public class Main {
    public static void main(String... args) {
        if (args.length == 0) throw new LabException("There are no available arguments passed");

        printInCSVFile(
                getAggregationResult(parseDouble(args[0]), parseDouble(args[1]), parseDouble(args[2]))
        );
    }
}
