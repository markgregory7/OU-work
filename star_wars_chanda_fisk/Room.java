import java.util.Set;
import java.util.HashMap;
import java.util.*;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes. Modified by Mark Gregory
 * @version 2023-02-02
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> exits; // Stores the exits of this room.
    private List<Item> items; // List of items currently in the room.

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbour The room in the given direction.
     */
    public void setExit(String direction, Room neighbour) 
    {
        exits.put(direction, neighbour);
    }

    /** Exercise 8.22
     * Adds an item that starts off in this room.
     * @param aDescription The description of the item as a String.
     * @param aWeight The weight of the item as an int.
     */
    public void addItem(String aName, String aDescription, int aWeight, boolean canBePickedUp)
    {
        Item item = new Item(aName, aDescription, aWeight, this, canBePickedUp);
        items.add(item);
    }

    //Exercise 8.6
    /**
     * Returns a Room object based on the user's chosen direction.
     */
    public Room getExit(String direction)
    {
        return exits.get(direction);
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /** Exercise 8.20
     * @return The long description of the room which includes exit
     * information and item details (if any).
     */
    public String getLongDescription()
    {
        String roomDetails = description + "\n" + getExitString();
        if(!items.isEmpty()){
            String roomItems = "\nIn the area are the following items:";
            for(Item item : items){
                roomItems += "\n" + item.getDescription();
            }
            roomDetails += roomItems;
        }
        return roomDetails;
    }

}
