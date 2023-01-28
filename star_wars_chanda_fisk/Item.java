
/**
 * Contains the information for Item objects created in the game.
 *
 * @author Mark Gregory
 * @version 2023-01-26
 */
public class Item
{
    // Any of the below likely to change?
    private String description;
    private int weight;
    // What if the item is being carried? Inventory as a Room object?
    private Room room;

    /**
     * Constructor for objects of class Item
     */
    public Item(String aDescription, int aWeight, Room aRoom)
    {
        description = aDescription;
        weight = aWeight;
        room = aRoom;
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
        
    
}
