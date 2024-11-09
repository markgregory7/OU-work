import java.util.*;
import java.util.Date;
import java.time.Instant;

/**
 * A Journey object represents a journey taken and consists of a unique number, name, date
 * and FuelCostCalculator object.
 *
 * @author Mark Gregory
 * @version 2024-11-09 - Continuing to add save function, plus change from Date objects to java.time
 *                              because of BST loading/save issues.
 */
public class Journey
{
    private int journeyNumber;
    private String journeyName;
    //private Date date;
    private FuelCostCalculator journeyFcc;
    private Instant journeyInstant;
    //private Instant instant1;
    
    /**
     * Constructor for objects of class Journey
     */
    public Journey(int aNumber, String aName, /*Date aDate,*/ Instant instant,FuelCostCalculator aFcc)
    {
        journeyNumber = aNumber;
        journeyName = aName;
        //date = aDate;
        journeyInstant = instant;
        journeyFcc = aFcc;
    }
    
    public Journey()
    {
        defaultJourney();
    }
    
    public void defaultJourney()
    {
        Journey journey1;
        FuelCostCalculator fcc1;
        fcc1 = new FuelCostCalculator(87.84, 189, 60);
        journeyInstant = journeyInstant.now();
        System.out.println(journeyInstant);
        journey1 = new Journey(1, "Grandma's", journeyInstant, fcc1);
        System.out.println(journey1);
    }
    
    /**
     * Returns journeyNumber.
     */
    public int getJourneyNumber()
    {
        return journeyNumber;
    }
    
    /**
     * Updates journeyNumber.
     */
    public void setJourneyNumber(int journeyNumber)
    {
        this.journeyNumber = journeyNumber;
    }
    
    /**
     * Returns journeyName.
     */
    public String getJourneyName()
    {
        return journeyName;
    }
    
    /**
     * Updates journeyName.
     */
    public void setJourneyName(String journeyName)
    {
        this.journeyName = journeyName;
    }
    
    /**
     * Returns date.
     */
    public Instant getInstant()
    {
        if(journeyInstant == null){
            return journeyInstant.now();
        }
        return journeyInstant;
    }
    
    /**
     * Updates date.
     */
    public void setInstant(Instant journeyInstant)
    {
        this.journeyInstant = journeyInstant;  
    }
    
    /**
     * Returns the FuelCostCalculator object journeyFcc.
     */
    public FuelCostCalculator getFcc()
    {
        return journeyFcc;
    }
    
    /**
     * Updates journeyFcc.
     */
    public void setFcc(FuelCostCalculator journeyFcc)
    {
        this.journeyFcc = journeyFcc;
    }
    
    /**
     * Return a String representaion of a Journey object including
     * journehNumber, journeyName, date and a FuelCostCalculator object.
     */
    @Override
    public String toString()
    {
        // First section results in an null error for journeyInstant... 15/10/24
        // String outputString = String.format("journeyNumber is %s, journeyName is " + 
            // "%s, instant is %s \nand FuelCostCalculator consists of: %s", journeyNumber, journeyName, 
            // journeyInstant.toString(), journeyFcc.toString());
        String outputString = String.format("journeyNumber is %s, journeyName is " + 
            "%s,\nand FuelCostCalculator consists of: %s", journeyNumber, journeyName, 
            journeyFcc.toString());
        return outputString;
    }
    
}
