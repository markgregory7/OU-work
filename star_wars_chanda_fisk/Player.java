import java.util.*;

/**
 * Represents a player in an adventure game. A player consists of a name,
 * description, collection of carried items, a maximum carrying capacity
 * and current room location.
 *
 * @author Mark Gregory
 * @version 2023-02-02
 */
public class Player
{
    private String name;
    private String description;
    private ArrayList<Item> inventory;
    private int carryingCapacity;
    private Room currentRoom;

    /**
     * Constructor for objects of class Player
     */
    public Player(String aName, String aDescription, int carryingCapacity, Room currentRoom)
    {
        name = aName;
        description = aDescription;
        this.carryingCapacity = carryingCapacity;
        this.currentRoom = currentRoom;
    }

    /**
     * Sets the player's current room/location. Cannot be null i.e. the player must
     * be somewhere!
     *
     * @param aRoom The Room object that the player is currently in/associated with.
     * @return    the sum of x and y
     */
    public void setCurrentRoom(Room aRoom)
    {
        if(aRoom != null){
            currentRoom = aRoom;
        }
    }

    /**
     * Gets the player's current room/location.
     *
     * @param aRoom The Room object that the player is currently in/associated with.
     * @return Room The reference for the room object that the player is currently associated with.
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }


}
