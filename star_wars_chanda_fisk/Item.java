
/**
 * Contains the information for Item objects created in the game.
 *
 * @author Mark Gregory
 * @version 2023-02-03
 */
public class Item
{
    // Any of the below likely to change?
    private String name;
    private String description;
    private int weight;
    private boolean canBePickedUp;
    private Room room;

    /**
     * Constructor for objects of class Item
     */
    public Item(String aName, String aDescription, int aWeight, Room aRoom, boolean canBePickedUp)
    {
        name = aName;
        description = aDescription;
        weight = aWeight;
        room = aRoom;
        this.canBePickedUp = canBePickedUp;
    }

    public String getName()
    {
        return name;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public int getWeight()
    {
        return weight;
    }
    
    public Room getRoom()
    {
        return room;
    }
    
    public void setRoom(Room aRoom)
    {
        room = aRoom;
    }
        
    public boolean getCanBePickedUp()
    {
        return canBePickedUp;
    }
}
