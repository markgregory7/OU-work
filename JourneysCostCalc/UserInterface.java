import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

/**
 * UserInterface is the main class of the Fuel Cost Calculator application. It builds and
 * displays the application GUI and initialises all other components.
 * 
 * To start the application, create an object of this class.
 * 
 * showAbout method & menu shortcuts based on ImageViewer class from Michael Kölling and
 * David J. Barnes. 
 * 
 * @author Mark Gregory.
 * @version 0.2, 2023-06-07 - Added litres to display.
 * @version 0.3, 2023-07-24 - Removed lables for load/save buttons.
 * @version 0.4, 2024-01-02 - Adding save/load functionality for 'trips'.
 *                          - Currently loads a journey if a valid number is in text field.
 * @version 0.5, 2024-03-25- Adding save function, plus journey name details to UI.
 * 
 */
public class UserInterface //extends JFrame //implements ActionListener
{
    private FuelCostCalculator journeyCost;
    private JFrame frame;

    // Trip object for creating and saving trips?
    private Trips currentTrips;

    //private JLabel journeyNumberLabel;
    private JTextField journeyNumberText;

    private JLabel journeyNameLabel;
    private JTextField journeyNameText;

    private JLabel milesTraveledLabel;      
    private JTextField milesTraveledText;

    private JLabel currentMpgLabel;
    private JTextField currentMpgText;

    private JLabel pencePerLitreLabel;
    private JTextField pencePerLitreText;

    private JButton totalCostFuelButton;
    private JTextField totalCostFuelText;

    private JButton loadJourneyButton;
    private JTextField loadJourneyText;

    private JButton saveJourneyButton;
    private JTextField saveJourneyText;

    private JLabel litresUsedLabel;
    private JTextField litresUsedText;

    private int journeyNoInt;

    /**
     * Constructor for objects of class UserInterface
     */
    public UserInterface()
    {
        journeyCost = null;
        currentTrips = null;
        makeFrame();
    }

    /**
     * Create the Swing frame and its content.
     */
    private void makeFrame()
    {
        frame = new JFrame("Journeys Cost Calculator");
        makeMenuBar(frame);

        Container contentPane = frame.getContentPane();

        contentPane.setLayout(new GridLayout(10, 2));

        milesTraveledLabel = new JLabel("Miles Traveled");
        changeFontAndAlign(milesTraveledLabel);
        contentPane.add(milesTraveledLabel);

        milesTraveledText = new JTextField();
        contentPane.add(milesTraveledText);
        changeFontAndAlign(milesTraveledText);

        currentMpgLabel = new JLabel("Current MPG");
        changeFontAndAlign(currentMpgLabel);
        contentPane.add(currentMpgLabel);

        currentMpgText = new JTextField();
        contentPane.add(currentMpgText);
        changeFontAndAlign(currentMpgText);

        pencePerLitreLabel = new JLabel("Pence Per Litre");
        changeFontAndAlign(pencePerLitreLabel);
        contentPane.add(pencePerLitreLabel);

        pencePerLitreText = new JTextField();
        contentPane.add(pencePerLitreText);
        changeFontAndAlign(pencePerLitreText);

        // Button to instigate the calculation process.
        totalCostFuelButton = new JButton("Total Cost of Fuel (£)");
        changeFontAndAlign(totalCostFuelButton);
        contentPane.add(totalCostFuelButton);
        totalCostFuelButton.addActionListener(e -> calculate());

        // Displays the result.
        totalCostFuelText = new JTextField();
        contentPane.add(totalCostFuelText);
        changeFontAndAlign(totalCostFuelText);

        // Display the total number of litres of fuel used.
        litresUsedLabel = new JLabel("Litres Used");
        changeFontAndAlign(litresUsedLabel);
        contentPane.add(litresUsedLabel);

        litresUsedText = new JTextField();
        contentPane.add(litresUsedText);
        changeFontAndAlign(litresUsedText);

        // Button to load a saved journey. TBC
        loadJourneyButton = new JButton("Load a Journey");
        changeFontAndAlign(loadJourneyButton);
        contentPane.add(loadJourneyButton);
        // Display a window with available journeys to load and allow
        // user to select?
        // Call loadJourney method.
        loadJourneyButton.addActionListener(e -> loadJourney());
        //totalCostFuelButton.addActionListener(e -> calculate());

        journeyNumberText = new JTextField();
        contentPane.add(journeyNumberText);
        changeFontAndAlign(journeyNumberText);

        // Button to save the current journey. TBC (also will display currently loaded journey name).
        saveJourneyButton = new JButton("Save Current Journey as");
        changeFontAndAlign(saveJourneyButton);
        contentPane.add(saveJourneyButton);
        saveJourneyButton.addActionListener(e -> saveJourney());
        //Call saveJourney method.
        //totalCostFuelButton.addActionListener(e -> calculate());

        journeyNameText = new JTextField();
        contentPane.add(journeyNameText);
        changeFontAndAlign(journeyNameText);

        // Arrange the components and show.
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Load a Journey.
     */
    private void loadJourney()
    {
        // Clear total cost and litres used text fields in case method previously run.
        totalCostFuelText.setText("");
        litresUsedText.setText("");

        // Has csv file been already read?
        // Check currentTrips is null? If so try to load a file?
        if(currentTrips == null){
            //Trip objects handle their own file loading... 
            Trips tempTrips = new Trips();
            tempTrips.readCSVFile();
            // Read csv file and print out current list of saved journeys.
            System.out.println(tempTrips); // Replace with a pop up window?
            currentTrips = tempTrips;
            //At this point currentTrips has been replaced with journey objects loaded from file.
        }
        // User selects journey by number....
        // If journeyNumberText contains a number load that journey's details?
        String journeyNo = journeyNumberText.getText();
        if(!journeyNumberText.getText().equals("")){
            System.out.println("Journey Number in text box is: " + journeyNo);
            // Check below for non-number format....
            journeyNoInt = Integer.parseInt(journeyNo);
            // Search through currentTrips for Trip with matching journeyNo
            // Call the seachTrips method here, which either returns null or an journey
            // object which we then use to populate relevant textfields....?
            Journey foundJourney = currentTrips.searchTripsForJourneyNo(journeyNoInt);
            System.out.println(foundJourney);
            if(foundJourney == null ){
                System.out.println("Journey not found.");
            } else {
                // If current MPG text boxes etc have data check if ok to clear?
                // Get miles traveled, mpg, ppl (and journey name? where to display?)
                // and update relevant text fields....

                // Get fcc object within journey object then convert each double to String, then
                // update each text field?
                FuelCostCalculator fcc = foundJourney.getFcc();
                String milesTraveled = Double.toString(fcc.getMilesTravelled());
                String ppl = Double.toString(fcc.getPencePerLitre());
                String mpg = Double.toString(fcc.getCurrentMpg());
                // Load stored data into fields so calc can be run.
                milesTraveledText.setText(milesTraveled);
                pencePerLitreText.setText(ppl);
                currentMpgText.setText(mpg);
                journeyNameText.setText(foundJourney.getJourneyName());                             
                // Have a msg confirming journey loaded?
                // Automatically run calc?
            }

        } if (journeyNumberText.getText().equals("")) {
            System.out.println("Load Journey Box is empty.");
        }
        // If current MPG text boxes etc have data check if ok to clear?
    }

    /**
     * Save current Journey.
     */
    private void saveJourney()
    {
        // Check if a number is in the "Save Current Journey as" JTextField.
        if(!journeyNumberText.getText().equals("")){
            // If there is a number, check if already in use?
            String journeyNo = journeyNumberText.getText();
            int journeyNoInt = Integer.parseInt(journeyNo);
            Journey foundJourney = currentTrips.searchTripsForJourneyNo(journeyNoInt);
            System.out.println(foundJourney);
            // If so then is it ok to save over previous journey entry?
            if(foundJourney != null){
                // Show dialog window here which asks whether to overwrite yes/no.
                // Create a String of "Do you wish to save over Journey " + journeyNoInt + " ?"
                String saveOverJourneyQuestion = "Do you wish to save over Journey " + journeyNoInt + " ?";
                int choice = yesNoCancelResult(saveOverJourneyQuestion);
                // YES_OPTION = 0
                // NO_OPTION = 1
                // CANCEL_OPTION = 2
                System.out.println("Returned from yesNoCancel call...");
                System.out.println("choice = " + choice);
                if(choice == 0){
                    // Ask whether to update journey name.
                    String updateJourneyNameQuestion = "Do you wish to keep the Journey name as " + foundJourney.getJourneyName() + "?";
                    // Modify collection.
                    int decision = yesNoCancelResult(updateJourneyNameQuestion);
                    if(decision == 0){
                        // Update current journey within collection with updated MPG etc then update csv file?
                        // journeyNumber, journeyName remain the same - update date and journeyFcc with new data.
                        Date currentDate = new Date();
                        System.out.println("currentDate = " + currentDate);
                        foundJourney.setDate(currentDate); // Updates journey's date to current system date & time
                        // Now to get data from GUI text boxes and update FFC object.
                        // 1. get milesTravelled, pencePerLitre and currentMpg from GUI text fields
                        double miles = 0.0;
                        double mpg = 0.0;
                        double ppl = 0.0;
                        String milesTxt = milesTraveledText.getText();
                        miles = Double.parseDouble(milesTxt);
                        String mpgTxt = currentMpgText.getText();
                        mpg = Double.parseDouble(mpgTxt);
                        String pplTxt = pencePerLitreText.getText();
                        ppl = Double.parseDouble(pplTxt);
                        System.out.println("milesTxt = " + milesTxt);
                        System.out.println("mpgTxt = " + mpgTxt);
                        System.out.println("pplTxt = " + pplTxt);
                        // Create a new fcc then 'set' it. miles, ppl, mpg
                        FuelCostCalculator fccTransfer = new FuelCostCalculator(miles, ppl, mpg);
                        // 2. set new FCC object to foundJourney.
                        foundJourney.setFcc(fccTransfer);
                        
                    }
                } else if(choice == 1){
                    // If no then go to another window offering latest availble journey number
                    // and asking for a new journey name.
                    
                } else if(choice == 2){
                    // Add a save cancelled window?
                    return;
                } else{
                    
                }
                
                // Then confirm journey name to be used.                
                
                // YES_NO_CANCEL_OPTION (yes = saves over journey & prompts for new name?
                //                       no = uses new journey no and prompts for name
                //                       cancel = returns from save method)
                
            }
            // Call load journey then by default create a number on the last number ++?
            // though that would update text fields as the loadJourney method0
            // currently functions...
            
            // Iterate through currentTrips and find highest journeyNumber.
            int highestJourneyNo = currentTrips.searchTripsHighestJourneyNo();
            System.out.println("highestJourneyNo = " + highestJourneyNo);

        }else{
            // Find current highest journey number and add 1 to it.
            // Prompt for a journey name as a String.
            // Check journey with same name does not alread exist. If it does ask ok to overwrite?
            // Is current data in fields valid? Currently only checked on 'calculate' call.
            // Save to csv.
            int highestJourneyNo = currentTrips.searchTripsHighestJourneyNo();
            System.out.println("highestJourneyNo = " + highestJourneyNo);
        }

    }

    /**
     * Calculates the journey cost by creating a FuelCostCalculator object from the UI's JTextFields
     * values (converting them to Doubles) and then calling its calcCost method.
     * 
     * @throws NumberFormatException If any of the text fields do not contain a number that can be
     *          converted to a Double object.
     */
    private void calculate() throws NumberFormatException
    {
        //System.out.println("calculate method called");
        UIManager.put("OptionPane.messageFont", new Font("SansSerif", Font.PLAIN, 20));
        try {
            //System.out.println("try block reached");

            //What if mpg is 0?
            if(currentMpgText.getText().equals("0") ||
            currentMpgText.getText().equals("")){
                JOptionPane.showMessageDialog(null, "MPG cannot be zero.",
                    "Alert", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double miles = 0.0;
            double mpg = 0.0;
            double ppl = 0.0;

            String milesTxt = milesTraveledText.getText();
            miles = Double.parseDouble(milesTxt);
            String mpgTxt = currentMpgText.getText();
            mpg = Double.parseDouble(mpgTxt);
            String pplTxt = pencePerLitreText.getText();
            ppl = Double.parseDouble(pplTxt);

            //What if any number entered is a negative?
            if(miles <0 || mpg <0 || ppl <0){
                JOptionPane.showMessageDialog(null, "No values can be less than 0.",
                    "Alert", JOptionPane.ERROR_MESSAGE);
                return;
            }
            journeyCost = new FuelCostCalculator(miles, ppl, mpg);
            totalCostFuelText.setText(journeyCost.calcCost());
            // Need to get the litres used and then display.
            String strLitresUsed = new DecimalFormat("#0.00").format(journeyCost.getLitresUsed());
            litresUsedText.setText(strLitresUsed);
        }
        catch(NumberFormatException e) {
            //System.out.println("catch block reached");
            String msg = "Please enter whole, or decimal numbers, only.";
            String msgtyp = "Alert";
            JOptionPane.showMessageDialog(null, msg, msgtyp, JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    /**
     * Creates a confirmation window and returns an integer based on user selection.
     *
     *@String   Question that is to be displayed.
     * 
     * YES_OPTION = 0
     * NO_OPTION = 1
     * CANCEL_OPTION = 2
     */
    private int yesNoCancelResult(String question)
    {
        UIManager.put("OptionPane.messageFont", new Font("SansSerif", Font.PLAIN, 20));
        // int choice = JOptionPane.showConfirmDialog(frame,
                // "Do you wish to save over Journey " + journeyNoInt + " ?", "Save Journey Confirmation",
                // JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        int choice = JOptionPane.showConfirmDialog(frame,
                question, "Question Confirmation",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                
        System.out.println("choice = " + choice);
        return choice;
    }

    // Are the overloading methods below done better with a lamda? (if possible?)
    /**
     * Updates the font, size and horizontal alignment for a JLabel.
     * @param label The JLabel to be modified.
     */
    private void changeFontAndAlign(JLabel label)
    {
        label.setFont(new Font("SansSerif", Font.BOLD, 20));
        label.setHorizontalAlignment(0);
        String formatedText = label.getText();
    }

    /**
     * Updates the font, size and horizontal alignment for a JTextField.
     * @param textField The JTextField to be modified.
     */
    private void changeFontAndAlign(JTextField textField)
    {
        textField.setFont(new Font("SansSerif", Font.BOLD, 20));
        textField.setHorizontalAlignment(0);
        String formatedText = textField.getText();
    }

    /**
     * Updates the font and size for a JButton.
     * @param button The JButton to be modified.
     */
    private void changeFontAndAlign(JButton button)
    {
        button.setFont(new Font("SansSerif", Font.BOLD, 20));
        String formatedText = button.getText();
    }

    /**
     * Updates the font and size for a JMenu.
     * @param menu The JMenu to be modified.
     */
    private void changeFontAndAlign(JMenu menu)
    {
        menu.setFont(new Font("SansSerif", Font.BOLD, 20));
        String formatedText = menu.getText();
    }

    /**
     * Updates the font and size for a JMenuItem.
     * @param menuItem The JMenuItem to be modified.
     */
    private void changeFontAndAlign(JMenuItem menuItem)
    {
        menuItem.setFont(new Font("SansSerif", Font.BOLD, 20));
        String formatedText = menuItem.getText();
    }

    /**
     * Quit function: quit the application.
     */
    private void quit()
    {
        System.exit(0);
    }

    /**
     * Show the 'About FuelCostCalc' dialog.
     */
    private void showAbout()
    {
        UIManager.put("OptionPane.messageFont", new Font("SansSerif", Font.PLAIN, 20));
        JOptionPane.showMessageDialog(frame, 
            "JourneysCostCalc by Mark Gregory\n" + "Version: 0.5, 2024-02-13",
            "About JourneysCostCalc", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Show the 'Frequently Asked Questions' dialog. TBC
     */
    private void showFAQ()
    {
        UIManager.put("OptionPane.messageFont", new Font("SansSerif", Font.PLAIN, 20));
        JOptionPane.showMessageDialog(frame, 
            "Values need to be non negative, and zero values for MPG are not allowed.",
            "FAQs", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Create the main frame's menu bar. 
     * @param frame   The frame that the menu bar should be added to.
     */
    private void makeMenuBar(JFrame frame)
    {
        //Needed for keyboard shortcuts
        final int SHORTCUT_MASK =
            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);

        JMenu menu;
        JMenuItem item;

        // create the File menu
        menu = new JMenu("File");
        menubar.add(menu);
        changeFontAndAlign(menu);

        item = new JMenuItem("Quit");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
        item.addActionListener(e -> quit());
        menu.add(item);
        changeFontAndAlign(item);

        // create the Help menu
        menu = new JMenu("Help");
        menubar.add(menu);
        changeFontAndAlign(menu);

        item = new JMenuItem("About FuelCostCalc");
        item.addActionListener(e -> showAbout());
        menu.add(item);
        changeFontAndAlign(item);

        item = new JMenuItem("Frequently Asked Questions");
        item.addActionListener(e -> showFAQ());
        menu.add(item);
        changeFontAndAlign(item);

    }
}
