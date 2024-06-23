import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class UserInterfaceTest.
 *
 * @author  Mark Gregory
 * @version 2022-10-17
 */
public class UserInterfaceTest
{
    private UserInterface userInter1;
    private UserInterface userInter2;
    private UserInterface userInter3;
    
    /**
     * Default constructor for test class UserInterfaceTest
     */
    public UserInterfaceTest()
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
    public void testNegativeValue()
    {
        UserInterface userInte1 = new UserInterface();
        //Need to add code that tries to create a FuelCostCalculator with negative values.
    }

    @Test
    public void testCorrectResult()
    {
        UserInterface userInte1 = new UserInterface();
        //Need to add code that tries to create a FuelCostCalculator with correct values.
    }
    
    @Test
    public void testNonNumber()
    {
        UserInterface userInte1 = new UserInterface();
        //Need to add code that tries to create a FuelCostCalculator with values that aren't
        // a number (letters, symbols etc.)
    }
    
    @Test
    public void testZeroMpg()
    {
        UserInterface userInte1 = new UserInterface();
        //Need to add code that tries to create a FuelCostCalculator with a zero MPG.
    }
}


