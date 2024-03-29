import java.util.*;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 *  * Modified to make it a Star Wars text adventure, called "The Training of 
 *  * Chanda Fisk"
 * 
 * @author  Michael Kölling and David J. Barnes. Modified by Mark Gregory.
 * @version 2023-03-08
 */

public class Game 
{
    private Parser parser;
    private Player player1;
    private Deque<Room> previousRooms; // Used for 'back' command.
    private int countDown = 5; // Exercise 8.41 - Game has a time limit.

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms(); 
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        previousRooms = new ArrayDeque<>();
        Room landingPad, rockyOutcrop, viewingPlatform, caveEntrance, cave, pit;

        // create the rooms
        landingPad = new Room("You watch as the blue glow of the dropship’s engines disappear over the horizon to the west.\n" +
            "A shadow is cast over you from the mountain that looms to the north.\n " +
            "The wind keens as it passes through a rocky outcropping to the east. \n" +
            "Old stone steps lead down to the south. \nTo the west is a semi-overgrown viewing platform. \n" +
            "(There are exits <East>, <South>, and <West>) ");
        rockyOutcrop = new Room("You stand within the shadow of the mountain, there is a deathly chill as the wind rolls down the " +
            "mountainside and flows between several upright pillars near to the east, worn down by the ages. As you stand there you" +
            "realise that they resemble upturned fingers and thumb, and that you stand within the palm. A deep sense of foreboding " +
            "wells within you. \nTo the west lies the landing pad. \n(There are exits <West>) ");
        viewingPlatform = new Room("As with the landing pad, vines have done their best to reclaim the viewing platform for nature.\n" +
            "To the west is the remains of what appears to be a medium blaster cannon mount (the gun long gone).\n" +
            "It is attached to a four-foot wall that runs in a semi-circle from the north, to west, to south and offers a majestic view" +
            " of the valleys that stretch out far below.\n(There are exits <East>)  ");
        caveEntrance = new Room("You stand upon a narrow stone ledge, which leads to the mouth of a cave to the east, which burrows into" +
            " the sheer rockface.\nAt some time in the past the cave entrance has been carved to resemble what you decern to be some " +
            "\ngreat visage, but has almost been worn completely away over the years (possibly centuries). " +
            "\nTo the north are old stone steps leading up to the landing pad. \nA sheer drop is to the south and west. "+
            "\n(There are exits <North> and <East>) ");
        cave = new Room("You stand within the dark entranceway to the cave. The first few feet of the cave are lit by the light of the " +
            "setting sun from the west.\nIt is currently too dark to see any further.\n(There are exits <West>)   ");
        pit = new Room("The pit is cold but dry, and you have a deep sense of foreboding just standing within. \n" +
            "(There are exits <Up>) ");

        // initialise room exits
        landingPad.setExit("east", rockyOutcrop);
        landingPad.setExit("south", caveEntrance);
        landingPad.setExit("west", viewingPlatform);
        rockyOutcrop.setExit("west", landingPad);
        viewingPlatform.setExit("east", landingPad);
        caveEntrance.setExit("north", landingPad);
        caveEntrance.setExit("east", cave);
        cave.setExit("west", caveEntrance);
        cave.setExit("down", pit);
        //pit.setExit("up", cave); // Rem'd out to make this one way.
        // Exercise 8.42.

        // intialise room items
        viewingPlatform.addItem("glowrod", "A well worn <glowrod>, somewhat dirty but "
            + "still functional.", 2, true);
        viewingPlatform.addItem("mount", "A broken blaster cannon <mount>.", 250, true);
        viewingPlatform.addItem("mote", "A <mote> of the Force made manifest.", 0, true);
        // Create player and starting location.
        player1 = new Player("Chanda Fisk", "An aspiring Jedi of some potential", 50, landingPad);

    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }

        if(countDown < 1){
            System.out.println("\nSorry, you ran out of time!");
        }
        else{
            System.out.println("Thank you for playing.  Good bye.");
        }
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Star Wars: The Training of Chanda Fisk.");
        System.out.println("TBC");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(player1.getCurrentRoom().getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;
            case HELP:
                printHelp();
                break;
            case GO:
                goRoom(command);
                countDown--;
                break;
            case LOOK:
                look();
                countDown--;
                break;
            case ABSORB:
                player1.drawAponForce();
                countDown--;
                break;
            case BACK:
                back();
                countDown--;
                break;
            case TAKE:
                player1.pickUpItem(command);
                countDown--;
                break;
            case DROP:
                player1.dropItem(command);
                countDown--;
                break;
            case ITEMS:
                player1.displayInventory();
                countDown--;
                break;
            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        if(countDown < 1){
            wantToQuit = true;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player1.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no exit!");
        }
        else {
            // Add to deque
            previousRooms.addFirst(player1.getCurrentRoom());
            player1.setCurrentRoom(nextRoom);

            System.out.println(player1.getCurrentRoom().getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * Prints out the current room's description.
     */
    private void look()
    {
        System.out.println(player1.getCurrentRoom().getLongDescription());
    }

    /** Exercise 8.23 & 8.26
     * Player returns to previous room/location.
     */
    private void back()
    {
        // Check deque not empty
        if(previousRooms.peekFirst() != null){
            player1.setCurrentRoom(previousRooms.removeFirst());
            System.out.println(player1.getCurrentRoom().getLongDescription());
        }
        else{
            System.out.println("You are where you started.");
        }
    }
}
