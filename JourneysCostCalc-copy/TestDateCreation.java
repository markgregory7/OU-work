import java.util.*;
import java.util.Date;

/**
 * Creates a test example of a Date object
 *
 * @author Mark Gregory
 * @version 2023-07-29
 */
public class TestDateCreation
{
    private Date date1;
    private Date date2;
    private String dateString = "Sun Jan 15 18:00:00 GMT 2023";

    /**
     * Constructor for objects of class TestDateCreation
     */
    public TestDateCreation()
    {
        date1 = new Date();      
    }
    
    public void printDate()
    {
        System.out.println(date1);
        // The above prints date in following format: Sun Jan 15 17:45:00 GMT 2023 
    }
    
    public void dateCreatedWithString()
    {
        date2 = new Date(dateString);
        System.out.println(date2);
        // The above works...
    }
}
