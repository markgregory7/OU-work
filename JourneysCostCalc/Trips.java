import java.util.*;
import java.io.*;
//import java.util.Date; // Needed?

/**
 * This class enables Journey objects to be stored as 'trips' and then
 * retrieved or modified at a later date storing the data as a csv file.
 *
 * @author Mark Gregory
 * @version 2023-01-20
 */
public class Trips
{
    private List<Journey> journeys;

    /**
     * Constructor for objects of class Trips
     */
    public Trips()
    {
        journeys = new ArrayList<>();
    }

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
                // How to write fuel cost calc object?
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

    /** 
     * Reads a CSV file from the local directory and adds each Journey object to the 
     * current journey list. Individual entities are stored as 6 Strings, plus "\n".
     * 
     * @param filename The name of the file that will be read.
     */
    public void readCSVFile(String filename)
    {
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
                double mpg = Double.parseDouble(csvValueArray[4]);
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
     * Prints the whole collection of journeys.
     *
     */
    public void printJourneys()
    {
        for(Journey jny : journeys){
            System.out.println(jny);         
        }
        System.out.println("");
    }

}
