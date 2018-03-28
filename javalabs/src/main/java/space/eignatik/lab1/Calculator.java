package space.eignatik.lab1;

import space.eignatik.exceptions.LabException;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Calculator {
    private static final String DELIMITER = ",";

    private Calculator() {}

    /**
     * Get result of expression execution to string according to input range [start; end] and step between limits
     *
     * Function:
     * ****************************************
     * *********   f(x) = tg(2x) - 3   ********
     * ****************************************
     *
     * @param start left range (including this value)
     * @param end right change (including this value)
     * @param step step for function
     * @return String formatted for CSV
     */
    public static String getAggregationResult(double start, double end, double step) {
        StringBuilder table = getTableHeader();

        boolean isOutOfRange = false;
        while (!isOutOfRange) {
            table
                    .append(BigDecimal
                            .valueOf(start)
                            .setScale(2, RoundingMode.HALF_UP)
                            .doubleValue())
                    .append(DELIMITER)
                    .append(aggregate(start))
                    .append("\n");
            start+=step;
            if (start > end) isOutOfRange = true;
        }

        return table.toString();
    }

    /**
     * Print some CSV formatted data to CSV file
     * @param data data in CSV formatted view
     */
    public static void printInCSVFile(String data) {
        try{
            Files.write(Paths.get("aggregationResults.csv"), data.getBytes());
        } catch (IOException e) {
            throw new LabException(e);
        }
    }

    static double aggregate(double input) {
        BigDecimal number = BigDecimal.valueOf(Math.tan(input * 2.0) - 3.0);
        return number.setScale(2, RoundingMode.HALF_UP).doubleValue();

    }

    private static StringBuilder getTableHeader() {
        return new StringBuilder()
                .append("argument")
                .append(DELIMITER)
                .append("result\n");
    }
}
