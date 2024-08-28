import java.util.*;
import java.util.Date;

/**
 * A Journey object represents a journey taken and consists of a unique number, name, date
 * and FuelCostCalculator object.
 *
 * @author Mark Gregory
 * @version 2024-08-27 - Continuing to add save function, plus change from Date objects to java.time
 *                              because of BST loading/save issues.
 */
public class Journey
{
    private int journeyNumber;
    private String journeyName;
    private Date date;
    private FuelCostCalculator journeyFcc;

    /**
     * Constructor for objects of class Journey
     */
    public Journey(int aNumber, String aName, Date aDate, FuelCostCalculator aFcc)
    {
        journeyNumber = aNumber;
        journeyName = aName;
        date = aDate;
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
        Date date1;
        fcc1 = new FuelCostCalculator(87.84, 189, 60);
        date1 = new Date();
        journey1 = new Journey(1, "Grandma's", date1, fcc1);
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
    public Date getDate()
    {
        return date;
    }
    
    /**
     * Updates date.
     */
    public void setDate(Date date)
    {
        this.date = date;  
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
        String outputString = String.format("journeyNumber is %s, journeyName is " + 
            "%s, date is %s \nand FuelCostCalculator consists of: %s", journeyNumber, journeyName, 
            date.toString(), journeyFcc.toString());
        return outputString;
    }
    
}
