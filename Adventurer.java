import java.util.Objects;

/** Question 3b.1
 * Class Adenturer simulates an adventurer that is a member of a Guild.
 *
 * TMA03_Q3_SolMG
 * 
 * Part of the solution for an Open University tutor marked assessment.
 * 
 * @author M250 Module Team originally, updated by Mark Gregory.
 * @version 2022-06-18
 */
public class Adventurer implements Comparable<Adventurer>
{
    private String name;
    private final String RACE;
    private String characterClass;
    private String status;

    /**
     * Constructor for objects of class Adventurer
     * @param aName The name of the adventurer i.e. "Darren"
     * @param aRace The race of the adventurer i.e. "elf". Note this cannot change after
     *              creation.
     * @param aCharClass The character class, such as "Fighter"
     * @param aStatus The adventurers current status. Must be either "Alive", "Dead", 
     *              "Undead", or "Not Sure".
     * sets the String fields name, race, characterClass and status to those of the provided 
     * parameters.
     */
    public Adventurer(String aName, String aRace, String aCharClass, String aStatus)
    {
        name = aName;
        RACE = aRace;
        characterClass = aCharClass;
        status = aStatus;        
    }

    /** Question 3b.2
     * Returns a String representation of a Adventurer including
     * their name, race, class, and finally their current status.
     * @return the string representation of the adventurer. 
     */
    @Override
    public String toString()
    {
        String outputString = ("Adventurer " + name + " who is a " + RACE + ", is currently "
                + "classed as a " + characterClass + " and their status is " + status);
        return outputString;
    }

    /** 
     *  Checks for equality between two Adventurer objects.
     *  @param Reference of object to be check for equality.
     *  @return True if adventurer name, race and class are the same as this
     *  object's, and false otherwise (also returns false for subclasses).
     */
    @Override
    public boolean equals(Object obj)
    {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Adventurer)) {
            return false;
        }
        if(this.getClass() != obj.getClass()) {
            return false;
        }
        Adventurer other = (Adventurer) obj;
        return this.name.equals(other.name) &&
        this.RACE.equals(other.RACE) && this.characterClass.equals(other.characterClass);
    }

    /** 
     *  Returns the sum of a Adventurer's name length and car capacity. 
     *  @return An int hashcode of the Adventurer's name, race and character class lengths.
     */
    @Override
    public int hashCode()
    {
        int result;
        return result = Objects.hash((this.getName()),(this.getRace()),(this.getCharClass()));
    }

    /** 
     *  Compares the name of adventurer objects so that they can be sorted.
     *  @param anAdventurer Reference of adventurer object.
     *  @return int of 0 if name is equal, < 0 if it is less than, or > 0 if it is
     *  greater than.  
     */
    @Override
    public int compareTo(Adventurer anAdventurer)
    {
        String thisName = this.name;
        String otherName = anAdventurer.name;
        return thisName.compareTo(otherName);
    }

    /**
     * Setter method for name.
     * @param aName A name for the adventurer.
     */    
    public void setName(String aName)
    {
        name = aName;
    }

    /**
     * Getter method for name.
     * @return The name of the adventurer.
     */    
    public String getName()
    {
        return name;
    }

    /**
     * Setter method for characterClass.
     * @param aName A class for the adventurer.
     */    
    public void setCharClass(String aCharClass)
    {
        characterClass = aCharClass;
    }

    /**
     * Getter method for characterClass.
     * @return The character class of the adventurer.
     */    
    public String getCharClass()
    {
        return characterClass;
    }

    /**
     * Setter method for status.
     * @param aStatus A status for the adventurer.
     */    
    public void setStatus(String aStatus)
    {
        status = aStatus;
    }

    /**
     * Getter method for status.
     * @return The status of the adventurer.
     */    
    public String getStatus()
    {
        return status;
    }

    /**
     * Getter method for race.
     * @return The race of the adventurer.
     */    
    public String getRace()
    {
        return RACE;
    }
}
