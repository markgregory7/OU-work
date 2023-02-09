
import java.util.*;

/**
 * Represents a player in an adventure game. A player consists of a name,
 * description, collection of carried items, a maximum carrying capacity
 * and current room location.
 *
 * @author Mark Gregory
 * @version 2023-02-09
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
        inventory = new ArrayList<>();
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

    /** Exercise 8.29
     * Player tries to take an item from the current room/location.
     */
    public boolean pickUpItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take
            System.out.println("Take what?");
            return false;
        }

        // Item names so far can only be one word...i.e. "cannon mount" won't work...
        String itemName = command.getSecondWord();
        // Probably need an Iterator here as may be removing an object...
        // Pass itemName to Room method to deal with it's own items?
        Iterator<Item> it = currentRoom.getItems().iterator();
        System.out.println("itemName = " + itemName);
        while(it.hasNext()){
            Item i = it.next();
            String iName = i.getName();
            System.out.println("iName = " + iName);
            // If item name is found.
            if(iName.equals(itemName)){
                // Check current carried weight + new item is not > capacity.
                // Need to check boolean canBePickedUp 
                System.out.println("Item name found.");
                if(((i.getWeight() + getCurrentCapacity()) <= (carryingCapacity))
                && (i.getCanBePickedUp())){
                    // Add item to inventory and remove item from room.
                    // How to update room items collection?
                    System.out.println("Item can be picked up.");
                    if(currentRoom.removeItem(i)){
                        inventory.add(i);
                        System.out.println("You pick up the " + i.getName());
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                else{
                    System.out.println("You are overburdened. You may have to drop something.");
                    return false;
                }

            }

        }
        System.out.println("That item is not here.");
        return false; 
    }

    /** Exercise 8.29
     * Player tries to drop an item from their inventory into the current room/location.
     */
    public boolean dropItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take
            System.out.println("Take what?");
            return false;
        }

        // Item names so far can only be one word...i.e. "cannon mount" won't work...
        String itemName = command.getSecondWord();

        Iterator<Item> it = inventory.iterator();
        System.out.println("itemName = " + itemName);

        ArrayList<Item> revisedInventory = new ArrayList<>();

        while(it.hasNext()){
            Item i = it.next();
            String iName = i.getName();
            System.out.println("iName = " + iName);
            // If item name is found.
            if(iName.equals(itemName)){
                System.out.println("Item name found.");
                it.remove();
                System.out.println("You drop the " + i.getName());
                currentRoom.addItem(i.getName(), i.getDescription(), i.getWeight(),
                    i.getCanBePickedUp());
                // Need to copy from Iterator back to inventory here...?
                it.forEachRemaining(revisedInventory::add);
                return true;
            }
        }
        // Copy iterator list to inventory?
        System.out.println(itemName + " was not dropped.");
        return false;
    }

    /**
     * Calculates and returns the current total weight that the player is 
     * carrying.
     * 
     * @return int The total weight the player is currently carrying.
     */
    private int getCurrentCapacity()
    {
        int totalWeight = 0;
        if(!inventory.isEmpty()){
            for(Item item : inventory){
                totalWeight += item.getWeight();
            }
        }
        return totalWeight;
    }

    /**
     * Displays the player's current inventory of items, if any.
     */
    public void displayInventory()
    {
        System.out.println("You are currently holding: ");
        if(!inventory.isEmpty()){
            for(Item i : inventory){
                System.out.println(i.getDescription());
            }
        }
        else{
            System.out.println("Nothing.");
        }
    }
}