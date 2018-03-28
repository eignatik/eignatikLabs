package space.eignatik.lab1;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CalculatorTest {

    @Test
    public void testGetAggregationResult() {
        assertEquals(Calculator.getAggregationResult(1.0, 2.0, 0.5), "argument,result\n1.0,-5.19\n1.5,-3.14\n2.0,-1.84\n");
    }

    @DataProvider(name = "valuesForAggregation")
    public Object[][] getValues() {
        return new Object[][] {
                {1.0, -5.19},
                {2.0, -1.84},
                {3.0, -3.29},
                {4.0, -9.8},
                {0.2, -2.58}
        };
    }

    @Test(dataProvider = "valuesForAggregation")
    public void testAggregate(double input, double expection) {
        assertEquals(Calculator.aggregate(input), expection);
    }

}