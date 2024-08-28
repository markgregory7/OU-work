import java.util.*;
import java.util.Date;
import java.time.Instant;

/**
 * Creates a test example of a Date object
 *
 * @author Mark Gregory
 * @version 1, 2023-07-29 - Using Date objects.
 * @version 2, 2024-08-27 - Added using java.time.Instant....
 */
public class TestDateCreation
{
    private Date date1;
    private Date date2;
    private String dateString = "Sun Jan 15 18:00:00 GMT 2023";
    private Instant instant1;

    /**
     * Constructor for objects of class TestDateCreation
     */
    public TestDateCreation()
    {
        date1 = new Date();
        instant1 = instant1.now();
    }
    
    public void printDate()
    {
        System.out.println(date1);
        // The above prints date in following format: Sun Jan 15 17:45:00 GMT 2023 
        System.out.println(instant1);
        // The above prints date & time in following format: YYYY-MM-DDTHH:MM:SS.XXXXXXX00Z - last 3 digits time zone?
    }
    
    public void dateCreatedWithString()
    {
        date2 = new Date(dateString);
        System.out.println(date2);
        // The above works...
    }
}
