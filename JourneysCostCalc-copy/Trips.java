import java.util.*;
import java.io.*;
import java.time.Instant;

/**
 * This class enables Journey objects to be stored as 'trips' and then
 * retrieved or modified at a later date storing the data as a csv file.
 *
 * @author Mark Gregory
 * @version 2025-02-18 - Continuing to add save function, plus change from Date objects to java.time
 *                              because of BST loading/save issues and adding
 *                              add journey method.
 */
public class Trips
{
    private ArrayList<Journey> journeys;
    //private Instant currentInstant;

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
     * local directory. CURRENTLY IT WILL SAVE OVER A FILE WITH THE SAME NAME WITHOUT WARNING.
     * Individual entities are stored as 6 Strings, plus "\n"
     * 
     * @param filename The name of the file that it will be saved under. 
     * Use *.csv nomenclature.
     */
    //public void writeCSVFile(String filename)
    public void writeCSVFile()
    {
        String filename = "journeysTest2.csv";
        try(FileWriter writer = new FileWriter(filename)){
            for(Journey jny : journeys){
                writer.write(Integer.toString(jny.getJourneyNumber())+",");
                
                writer.write(jny.getJourneyName()+",");
                //writer.write(String.valueOf(jny.getDate())+",");
                //As of 27/10 following results in null...
                writer.write(String.valueOf(jny.getInstant())+",");
                writer.write(String.valueOf(jny.getFcc().getMilesTravelled())+",");
                writer.write(String.valueOf(jny.getFcc().getPencePerLitre())+",");
                writer.write(String.valueOf(jny.getFcc().getCurrentMpg())+",");
                writer.write("\r\n");
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
        String filename = "journeysTest2.csv";
        try(Scanner scanner = new Scanner(new File(filename))){
            ArrayList<Journey> transferList = new ArrayList<Journey>();
            scanner.useDelimiter(",");
            
            System.out.println("File reader has started.");
            
            while(scanner.hasNextLine()){
                String currentLineText = scanner.nextLine();
                String[] csvValueArray = currentLineText.split(",");
                // csv data is stored as 6 Strings plus \r\n, then needs to be converted back...
                int journeyNo = Integer.parseInt(csvValueArray[0]);
                String journeyName = csvValueArray[1];
                // Change the following to Instant....
                String csvValueArray2 = csvValueArray[2];
                //System.out.println("csvValueArray2 as String is (before parse): " + csvValueArray2);
                Instant currentInstant = Instant.parse(csvValueArray2);
                //System.out.println("curentInstant toString: " + currentInstant);
                //Date date = new Date(csvValueArray[2]);
                // Would the following be better as Double obs as converting Strings?           
                double miles = Double.parseDouble(csvValueArray[3]);
                double pencePerLitre = Double.parseDouble(csvValueArray[4]);
                double mpg = Double.parseDouble(csvValueArray[5]);

                FuelCostCalculator fcc = new FuelCostCalculator(miles, pencePerLitre, mpg);
                Journey jcc = new Journey(journeyNo, journeyName, currentInstant, fcc);

                transferList.add(jcc);
            }
            journeys.addAll(transferList);
            // Add a call to print out each journey instant to check if null.... 3/11/24
            //System.out.println("Journeys toString is : " + journeys);
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
    public Journey searchTripsForJourneyNo(int journeyNo)
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
     * Updates current Trips journey object using supplied journey's number or creates a new one.
     */
    public void updateJourneyInTrips(Journey journey)
    {
        boolean found = false;
        Iterator<Journey> jy = journeys.iterator();
        while(!found && jy.hasNext()){
            Journey j = jy.next();
            if(j.getJourneyNumber() == journey.getJourneyNumber()){
                found = true;
                j = journey;
                System.out.println("New journey data copied");
            }
        }
        if(found){
            //System.out.println("The journey updated is " + journey.getJourneyName() + " and number " +
            //journey.getJourneyNumber());
            writeCSVFile();
            System.out.println("Post CSV file method call");
            return;
        }
        // Add new journey object if nothing found....
        else{
            journeys.add(journey);
            System.out.println("New journey added.");
            writeCSVFile();
            System.out.println("Post CSV file method call");
        }
        return;
    }
    
    /**
     * Searches the current Trips object for highest journeyNo then returns
     * it.
     */
    public int searchTripsHighestJourneyNo()
    {
        int highestJourneyNo = 0;
        Iterator<Journey> jy = journeys.iterator();
        while(jy.hasNext()){
            Journey j = jy.next();
            int currentJourneyNo = j.getJourneyNumber();
            if(currentJourneyNo > highestJourneyNo){
                highestJourneyNo = currentJourneyNo;
            }
        }
        return highestJourneyNo;
    }
    
    /** 
     * Prints the whole collection of journeys.
     *
     */
    public void printJourneys()
    {
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
