import java.util.*;

/**
 * Represents a player in an adventure game. A player consists of a name,
 * description, collection of carried items, a maximum carrying capacity
 * and current room location.
 *
 * @author Mark Gregory
 * @version 2023-03-04
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
        //System.out.println("itemName = " + itemName);
        while(it.hasNext()){
            Item i = it.next();
            String iName = i.getName();
            //System.out.println("iName = " + iName);
            if(iName.equals(itemName)){
                //System.out.println("Item name found.");
                if(((i.getWeight() + getCurrentCapacity()) <= (carryingCapacity))
                && (i.getCanBePickedUp())){
                    //System.out.println("Item can be picked up.");
                    if(currentRoom.removeItem(i)){
                        inventory.add(i);
                        System.out.println("You pick up the " + i.getName());
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                else if(!i.getCanBePickedUp()){
                    System.out.println("It's not possible to pick up that item.");
                    return false;
                }
                else{
                    System.out.println("You are not strong enough to pick up that item. " + 
                    "You may have to drop something.");
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
        // Item name in text may have to be emboldened i.e. <mount> to suggest keyword.
        String itemName = command.getSecondWord();

        Iterator<Item> it = inventory.iterator();
        //System.out.println("itemName = " + itemName);

        ArrayList<Item> revisedInventory = new ArrayList<>();

        while(it.hasNext()){
            Item i = it.next();
            String iName = i.getName();
            //System.out.println("iName = " + iName);
            // If item name is found.
            if(iName.equals(itemName)){
                //System.out.println("Item name found.");
                it.remove();
                System.out.println("You drop the " + i.getName());
                currentRoom.addItem(i.getName(), i.getDescription(), i.getWeight(),
                    i.getCanBePickedUp());
                // Need to copy from Iterator back to inventory here...?
                it.forEachRemaining(revisedInventory::add);
                return true;
            }
        }
        System.out.println("You are not carrying the item " + itemName + ".");
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
        int weightCount = 0;
        if(!inventory.isEmpty()){
            for(Item i : inventory){
                System.out.println(i.getDescription());
                weightCount += i.getWeight();
            }
        }
        else{
            System.out.println("Nothing.");
        }
        System.out.println("You are currently carrying " + weightCount + " units in weight, " + 
            "with " + (carryingCapacity - weightCount) + " remaining." );
    }

    /** Exercise 8.33 TBC
     * Player draws apon the Force. Currently called by using the 'absorb' command word
     * and allows a player to multiply their carrying capacity by 5, by 'picking up' a 
     * mote of the force from a location and 'drawing apon it'/absorbing it.
     */
    public boolean drawAponForce()
    {
        // Search through player's inventory, absorb first 'mote' item (i.e. delete),
        // then multiply carrying capacity by 5.
        
        Iterator<Item> it = inventory.iterator();
        ArrayList<Item> revisedInventory = new ArrayList<>();

        while(it.hasNext()){
            Item i = it.next();
            String iName = i.getName();
            if(iName.equals("mote")){
                //System.out.println("mote found.");
                it.remove();
                System.out.println("You absorb the mote of the Force and feel stronger.");
                // Need to copy from Iterator back to inventory here...?
                it.forEachRemaining(revisedInventory::add);
                // Increase the player's carrying capacity.
                carryingCapacity = carryingCapacity * 5;
                return true;
            }
        }
        
        System.out.println("You fail to draw apon the Force.");
        return false;
    }
}