import java.util.*;
import java.io.*;

/** Question 3c.1
 * Class Guild models information used by an animated skeleton called Colin to
 * store the details of adventurers who are members of a guild.
 * 
 * Part of the solution for an Open University tutor marked assessment.
 *
 * @author Mark Gregory.
 * @version 2022-06-18
 */
public class Guild
{
    private String name;
    private TreeSet<Adventurer> roster;

    /**
     * Constructor for objects of class Guild.
     * 
     * Name defaults to The Fellowship of Dungeoneers and creates an empty roster.
     */
    public Guild()
    {
        name = "The Fellowship of Dungeoneers";
        roster = new TreeSet<Adventurer>();
    }

    /** Question 3c.2
     * Populates a TreeSet roster with five different adventurers.
     */
    public void populate()
    {
        Adventurer adventurer1 = new Adventurer("Darren the Dark", "Human", "Necromancer", "Alive");
        Adventurer adventurer2 = new Adventurer("Alice of Heartlypool", "Human", "Fighter", "Alive");
        Adventurer adventurer3 = new Adventurer("Poorly Derrick", "Human", "Fighter", "Undead");
        Adventurer adventurer4 = new Adventurer("Ryllae Thefaren", "Elf", "Cleric", "Alive");
        Adventurer adventurer5 = new Adventurer("Stein Tenpints", "Dwarf", "Berserker", "Alive");

        roster.add(adventurer1);
        roster.add(adventurer2);
        roster.add(adventurer3);
        roster.add(adventurer4);
        roster.add(adventurer5);
    }

    /** Question 3c.3
     * Creates a CSV file of the current roster of adventurers and saves it to the
     * local directory.
     * 
     * @param filename The name of the file that it will be saved under. Use *.csv nomenclature.
     */
    public void writeCSVFile(String filename)
    {
        try(FileWriter writer = new FileWriter(filename)){
            for(Adventurer adv : roster){
                writer.write(adv.getName()+",");
                writer.write(adv.getRace()+",");
                writer.write(adv.getCharClass()+",");
                writer.write(adv.getStatus()+",");
                writer.write("\n");
            }
        }
        catch(IOException e){
            System.err.println("There was a problem writing to " + filename);
        }
    }

    /** Question 3c.4
     * Reads a CSV file from the local directory and adds each Adventurer to the current roster.
     * 
     * @param filename The name of the file that will be read.
     */
    public void readCSVFile(String filename)
    {
        try(Scanner scanner = new Scanner(new File(filename))){
            TreeSet<Adventurer> transferRoster = new TreeSet<Adventurer>();
            scanner.useDelimiter(",");

             while(scanner.hasNextLine()){
                String currentLineText = scanner.nextLine();
                String[] csvValueArray = currentLineText.split(",");
                Adventurer ad = new Adventurer(csvValueArray[0], csvValueArray[1], csvValueArray[2], csvValueArray[3]);
                transferRoster.add(ad);
            }
            roster.addAll(transferRoster);
        }
        catch(FileNotFoundException e){
            System.err.println("Unable to open " + filename);
        }
        catch(IOException e){
            System.err.println("There was a problem reading " + filename);
        }
    }

    /** Question 3d
     * Adds an adventurer to the roster if not already present.
     *
     * @param anAdventurer References an adventurer to be added to the roster.
     */
    public void addMember(Adventurer anAdventurer)
    {
        boolean found = false;
        Iterator<Adventurer> ad = roster.iterator();
        while(!found && ad.hasNext()){
            Adventurer a = ad.next();
            if(a.getName().equals(anAdventurer.getName())){
                found = true;
            }
        }
        if(found){
            System.out.println("The adventurer " + anAdventurer.getName() + " is already on the roster");
        }
        else{
            roster.add(anAdventurer);
        }
    }

    /** Question 3d
     * Removes a member from the roster.
     *
     * @param anAdventurer References an adventurer to be removed from the roster.
     * @returns True if an adventurer is removed, false otherwise.
     */
    public boolean removeMember(Adventurer anAdventurer)
    {
        if(anAdventurer == null){
            return false;
        }
        else{
            Iterator<Adventurer> ad = roster.iterator();
            while(ad.hasNext()){
                Adventurer a = ad.next();
                String name = a.getName();
                if(name.equals(anAdventurer.getName())){
                    ad.remove();
                    return true;
                }
            }
        }
        return false;
    }

    /** Question 3d
     * Updates the status of an adventurer.
     *
     *@param aName The name of the adventurer to be updated.
     *@param aStatus The new status value to be used. Must be either "Alive", "Dead", 
     *              "Undead", or "Not Sure". 
     */
    public void updateMember(String aName, String aStatus)
    {
        Iterator<Adventurer> ad = roster.iterator();
        while(ad.hasNext()){
            Adventurer a = ad.next();
            String name = a.getName();
            if(name.equals(aName)){
                a.setStatus(aStatus);
            }
        }
    }

    /** Question 3d
     * Returns a collection of members by character class (and who are not dead)
     * using a supplied argument as a filter.
     *
     * @param filter The filter string to be used for the search.
     */
    public TreeSet selectMembers(String filter)
    {
        TreeSet<Adventurer> filteredRoster = new TreeSet<Adventurer>();
        Iterator<Adventurer> ad = roster.iterator();
        while(ad.hasNext()){
            Adventurer a = ad.next();
            String charClass = a.getCharClass();
            if(charClass.contains(filter) && a.getStatus() != "Dead"){
                filteredRoster.add(a);
            }
        }
        System.out.println(filteredRoster);
        return filteredRoster;
    }

    /** Question 3d
     * Prints the whole collection of members, one member per line plus finally a blank line.
     *
     */
    public void printMembers()
    {
        for(Adventurer adv : roster){
            System.out.println(adv.getName() +" " + adv.getRace() + " " + adv.getCharClass() +
                " " + adv.getStatus());         
        }
        System.out.println("");
    }

    /**
     * Deletes all the adventurers from a roster.
     *
     */
    public void clear()
    {
        roster.clear();
    }
}
