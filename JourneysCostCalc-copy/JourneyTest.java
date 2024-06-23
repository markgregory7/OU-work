import java.util.Date;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class JourneyTest.
 *
 * @author  Mark Gregory
 * @version 2023-01-06 
 */
public class JourneyTest
{
    private Journey journey1;
    private FuelCostCalculator fcc1;
    private Date date1;
    
    /**
     * Default constructor for test class JourneyTest
     */
    public JourneyTest()
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
        fcc1 = new FuelCostCalculator(87.84, 189, 60);
        date1 = new Date();
        journey1 = new Journey(1, "Grandma's", date1, fcc1);      
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
}
