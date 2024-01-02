import java.util.*;
import java.io.*;
//import java.util.Date; // Needed?

/**
 * This class enables Journey objects to be stored as 'trips' and then
 * retrieved or modified at a later date storing the data as a csv file.
 *
 * @author Mark Gregory
 * @version 2023-12-27 - Adding search method...
 */
public class Trips
{
    private ArrayList<Journey> journeys;

    /**
     * Constructor for objects of class Trips
     */
    public Trips()
    {
        journeys = new ArrayList<>();
    }

    // The following will currently be the method that is called from the GUI to save
    // a journey. Needs to incorporate a default filename of 'journeys.csv'.

    /** 
     * Creates a CSV file of the current collection of journeys and saves it to the
     * local directory. Individual entities are stored as 6 Strings, plus "\n"
     * 
     * @param filename The name of the file that it will be saved under. 
     * Use *.csv nomenclature.
     */
    public void writeCSVFile(String filename)
    {
        try(FileWriter writer = new FileWriter(filename)){
            for(Journey jny : journeys){
                writer.write(Integer.toString(jny.getJourneyNumber())+",");
                writer.write(jny.getJourneyName()+",");
                writer.write(String.valueOf(jny.getDate())+",");
                writer.write(String.valueOf(jny.getFcc().getMilesTravelled())+",");
                writer.write(String.valueOf(jny.getFcc().getPencePerLitre())+",");
                writer.write(String.valueOf(jny.getFcc().getCurrentMpg())+",");
                writer.write("\n");
            }
        }
        catch(IOException e){
            System.err.println("There was a problem writing to " + filename);
        }
    }

    // The following will currently be the method that is called from the GUI to load
    // a journey. Needs to incorporate a default filename of 'journeys.csv'. 

    /** 
     * Reads a CSV file from the local directory and adds each Journey object to the 
     * current journey list. Individual entities are stored as 6 Strings, plus "\n".
     * 
     * @param filename The name of the file that will be read.
     * @return // return an array list of journeys?
     */
    //public void readCSVFile(String filename)
    public void readCSVFile()
    {
        String filename = "journeys.csv";
        try(Scanner scanner = new Scanner(new File(filename))){
            ArrayList<Journey> transferList = new ArrayList<Journey>();
            scanner.useDelimiter(",");

            while(scanner.hasNextLine()){
                String currentLineText = scanner.nextLine();
                String[] csvValueArray = currentLineText.split(",");
                // csv data is stored as 6 Strings plus \n, then needs to be converted back...
                int journeyNo = Integer.parseInt(csvValueArray[0]);
                String journeyName = csvValueArray[1];
                Date date = new Date(csvValueArray[2]);
                // Would the following be better as Double obs as converting Strings?           
                double miles = Double.parseDouble(csvValueArray[3]);
                double pencePerLitre = Double.parseDouble(csvValueArray[4]);
                double mpg = Double.parseDouble(csvValueArray[5]);

                FuelCostCalculator fcc = new FuelCostCalculator(miles, pencePerLitre, mpg);
                Journey jcc = new Journey(journeyNo, journeyName, date, fcc);

                transferList.add(jcc);
            }
            journeys.addAll(transferList);
        }
        catch(FileNotFoundException e){
            System.err.println("Unable to open " + filename);
        }
        catch(IOException e){
            System.err.println("There was a problem reading " + filename);
        }

    }

    /**
     * Searches the current Trips object for a particular journey by number, then returns
     * the particular Journey object if found.
     */
    public Journey searchTrips(int journeyNo)
    {
        //Search the passed in Trips object for a journey with a matching journeyNo & return it.
        boolean found = false;
        Iterator<Journey> jy = journeys.iterator();
        while(!found && jy.hasNext()){
            Journey j = jy.next();
            if(j.getJourneyNumber() == journeyNo){
                found = true;
                return j;
            }
        }
        if(found){
            System.out.println("The journeyNo found is " + journeyNo);
        }
        return null;
    }
       
    
    /** 
     * Prints the whole collection of journeys.
     *
     */
    public void printJourneys()
    {
        //for(Journey jny : journeys){
        //    System.out.println(jny);         
        //}
        //System.out.println("");
        System.out.println(journeys);
    }
    
    /**
     * Return a String representaion of a Trip object including...
     */
    @Override
    public String toString()
    {
        // Return a String of the ArrayList of journeys
        String outputString = "";
        for(Journey jny : journeys){
            outputString += jny + "\n";
        }
        return outputString;
    }

}
