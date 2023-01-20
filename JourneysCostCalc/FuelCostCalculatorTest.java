import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class FuelCostCalculatorTest.
 *
 * @author  Mark Gregory
 * @version 2022-10-16
 */
public class FuelCostCalculatorTest
{
    private FuelCostCalculator journey1;
    private FuelCostCalculator journey2;
    private FuelCostCalculator journey3;
    private FuelCostCalculator journey4;
    private FuelCostCalculator journey5;
    
    /**
     * Default constructor for test class FuelCostCalculatorTest
     */
    public FuelCostCalculatorTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        journey1 = new FuelCostCalculator(87.84, 189, 44);
        journey2 = new FuelCostCalculator(87.84, 189, 60);
        journey3 = new FuelCostCalculator(7.71, 189, 60);
        journey4 = new FuelCostCalculator(29.33, 179.1, 34);
        journey5 = new FuelCostCalculator(77.66, 179.1, 77);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void testCostCalc()
    {
        assertEquals("£17.15", journey1.calcCost());
        assertEquals("£1.10", journey3.calcCost());
        assertEquals("£12.58", journey2.calcCost());
        assertEquals("£7.02", journey4.calcCost());
        assertEquals("£8.21", journey5.calcCost());
    }
}

