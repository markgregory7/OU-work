import java.util.*;
import java.text.DecimalFormat;

/**
 * FuelCostCalc calculates how much a trip cost in fuel in British
 * pounds, by using supplied miles travelled, miles-per-gallon (imperial) and
 * pence-per-litre variables.
 *
 *
 * @author  Mark Gregory
 * @version 2023-06-04 - Adding ability to display total of litres used.    
 */
public class FuelCostCalculator
{
    private double milesTravelled;
    private static final double LITRES_PER_GALLON = 4.54609;
    private double pencePerLitre;
    private double currentMpg;
    private double litresUsed;
    
    /**
     * Constructor for objects of class FuelCostCalculator
     */
    public FuelCostCalculator(double milesTravelled, double pencePerLitre, double mpg)
    {
        this.milesTravelled = milesTravelled;
        this.pencePerLitre = pencePerLitre;
        currentMpg = mpg;
        litresUsed = 0.0;
    }
    
    /**
     * Returns milesTravelled.
     */
    public double getMilesTravelled()
    {
        return milesTravelled;
    }
    
    /**
     * Returns pencePerLitre.
     */
    public double getPencePerLitre()
    {
        return pencePerLitre;
    }
        
    /**
     * Returns currentMpg.
     */
    public double getCurrentMpg()
    {
        return currentMpg;
    }
    
    /**
     * Returns litresUsed.
     */
    public double getLitresUsed()
    {
        return litresUsed;
    }
    
    /**
     * Calculates the cost of petrol for the miles travelled.
     *
     * @return    a double formatted to be in pounds and pence.
     */
    public String calcCost()
    {
        double gallonsUsed = milesTravelled / currentMpg;
        litresUsed = gallonsUsed * 4.54609;
        double costInGbp = (litresUsed * pencePerLitre) / 100;
        String str = new DecimalFormat("#0.00").format(costInGbp);
        //System.out.println("£"+str);
        return ("£"+str); 
    }
    
    /**
     * Return a String representaion of a FuelCostCalculator including
     * miles travelled, pence-per-litre and miles-per-gallon.
     */
    @Override
    public String toString()
    {
        String outputString = String.format("Miles travelled is %s, pence-per-litre is " + 
            "%s, litres used equals %s, and miles-per-gallon is %s.", milesTravelled, pencePerLitre, litresUsed, currentMpg);
        return outputString;
    }
}
