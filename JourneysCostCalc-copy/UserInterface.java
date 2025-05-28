import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import java.time.Instant;

/**
 * UserInterface is the main class of the Fuel Cost Calculator application. It builds and
 * displays the application GUI and initialises all other components.
 * 
 * To start the application, create an object of this class.
 * 
 * showAbout method & menu shortcuts based on ImageViewer class from Michael Kölling and
 * David J. Barnes in Objects First with Java (Sixth Ediion, 2017).
 * 
 * @author Mark Gregory.
 * @version 0.2, 2023-06-07 - Added litres to display.
 * @version 0.3, 2023-07-24 - Removed lables for load/save buttons.
 * @version 0.4, 2024-01-02 - Adding save/load functionality for 'trips'.
 *                          - Currently loads a journey if a valid number is in text field.
 * @version 0.5, 2024-08-25 - Adding save function, plus journey name details to UI.
 * @version 0.6, 2024-12-22 - Continuing to add save function, plus change from Date objects to java.time
 *                              because of BST loading/save issues.
 * @version 0.7, 2025-02-18 - Adding delete/remove journey function.
 *                          - Currently have to load a journey first before you can save.
 *                          - Output data now via window.
 * @version 0.8, 2025-03-04 - Save now works without loading, but errors twice before saving.
 * @version 0.9, 2025-05-28 - Adding search and delete functions.
 *                          - Need to add scroll bar to output window...
 *                          - Add Yes/No method in addition to Yes\No\Cancel...
 * 
 */
public class UserInterface
{
    private FuelCostCalculator journeyCost;
    private JFrame frame; // Main frame for FCC
    private JFrame frameTrips; // Frame for outputing Trips journey list info

    // Trip object for creating and saving trips
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

    //Add delete journey button etc here
    private JButton deleteJourneyButton;
    private JTextField deleteJourneyText;

    private JLabel litresUsedLabel;
    private JTextField litresUsedText;

    private int journeyNoInt;
    private Instant currentInstant;
    private Instant journeyInstant;

    private JLabel tripsJourneyDataLabel;
    private JTextArea tripsJourneyDataText;

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

        contentPane.setLayout(new GridLayout(11, 2));

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

        // Button to load a saved journey.
        loadJourneyButton = new JButton("Load a Journey by Number");
        changeFontAndAlign(loadJourneyButton);
        contentPane.add(loadJourneyButton);
        loadJourneyButton.addActionListener(e -> loadJourney());

        journeyNumberText = new JTextField();
        contentPane.add(journeyNumberText);
        changeFontAndAlign(journeyNumberText);

        // Button to save the current journey.
        saveJourneyButton = new JButton("Save Current Journey Name as");
        changeFontAndAlign(saveJourneyButton);
        contentPane.add(saveJourneyButton);
        saveJourneyButton.addActionListener(e -> saveJourney());

        journeyNameText = new JTextField();
        contentPane.add(journeyNameText);
        changeFontAndAlign(journeyNameText);

        deleteJourneyButton = new JButton("Delete a Journey by number");
        changeFontAndAlign(deleteJourneyButton);
        contentPane.add(deleteJourneyButton);
        deleteJourneyButton.addActionListener(e -> deleteJourneyFromTrips()); //To do!

        deleteJourneyText = new JTextField();
        contentPane.add(deleteJourneyText);
        changeFontAndAlign(deleteJourneyText);

        // Arrange the components and show.
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Creates a window to display trips.
     */
    private void displayTrips()
    {
        //Check if frameTrips already exists and therefore needs to be updated rather a second window 
        // created.
        if(frameTrips == null){
            frameTrips = new JFrame("Trips Journey Data Output");
            frameTrips.setLocationRelativeTo(frame);

            Container contentPane2 = frameTrips.getContentPane();

            contentPane2.setLayout(new GridLayout(0, 1));

            tripsJourneyDataLabel = new JLabel("Trips Journey Data");
            changeFontAndAlign(tripsJourneyDataLabel);
            contentPane2.add(tripsJourneyDataLabel);

            tripsJourneyDataText = new JTextArea();
            contentPane2.add(tripsJourneyDataText);
            changeFontAndAlign(tripsJourneyDataText);

            String output = currentTrips.toString();
            tripsJourneyDataText.setText(output);
        }else{
            String output = currentTrips.toString();
            tripsJourneyDataText.setText(output);
        }

        // Arrange the components and show.
        frameTrips.pack();
        frameTrips.setVisible(true);
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
            currentTrips = tempTrips;
            displayTrips();
            //At this point currentTrips has been replaced with journey objects loaded from file.
        }
        // User selects journey by number.
        // If journeyNumberText contains a number load that journey's details.
        String journeyNo = journeyNumberText.getText();
        if(!journeyNumberText.getText().equals("")){
            // Check below for non-number format....
            journeyNoInt = Integer.parseInt(journeyNo);
            // Search through currentTrips for Trip with matching journeyNo
            // Call the seachTrips method here, which either returns null or an journey
            // object which we then use to populate relevant textfields....?
            Journey foundJourney = currentTrips.searchTripsForJourneyNo(journeyNoInt);
            if(foundJourney == null ){
                displayErrorMessageWindow("Journey not found.");
                return;
            } else {
                // Get fcc object within journey object then convert each double to String, then
                // update each text field.
                FuelCostCalculator fcc = foundJourney.getFcc();
                String milesTraveled = Double.toString(fcc.getMilesTravelled());
                String ppl = Double.toString(fcc.getPencePerLitre());
                String mpg = Double.toString(fcc.getCurrentMpg());
                // Load stored data into fields so calc can be run.
                milesTraveledText.setText(milesTraveled);
                pencePerLitreText.setText(ppl);
                currentMpgText.setText(mpg);
                journeyNameText.setText(foundJourney.getJourneyName());                             
            }
        } if (journeyNumberText.getText().equals("")) {
            displayErrorMessageWindow("Load Journey Box is empty.");
        }
    }

    /**
     * Save current Journey.
     */
    private void saveJourney()
    {
        // Place within Try/Catch for null journeys?
        // Currently doesn't check for empty text fields first so run calculate?
        calculate(); // User will need to cancel after this to avoid incorrect data being stored.   

        // Checking if load journey yet to be called as currently errors on
        // certain saves.
        if(currentTrips == null){
            loadJourney(); 
        }

        if(!journeyNumberText.getText().equals("")){
            // If there is a number, check if already in use.
            String journeyNo = journeyNumberText.getText();
            int journeyNoInt = Integer.parseInt(journeyNo);
            Journey foundJourney = currentTrips.searchTripsForJourneyNo(journeyNoInt);
            // If so then is it ok to save over previous journey entry?
            if(foundJourney != null){
                // Show dialog window here which asks whether to overwrite yes/no.
                String saveOverJourneyQuestion = "Do you wish to save over Journey " + journeyNoInt + " ?";
                int choice = yesNoCancelResult(saveOverJourneyQuestion);
                // YES_OPTION = 0
                // NO_OPTION = 1
                // CANCEL_OPTION = 2
                if(choice == 0){ //Save over choice chosen
                    String updateJourneyNameQuestion = "Do you wish to keep the Journey name as " + foundJourney.getJourneyName() + "?";
                    int decision = yesNoCancelResult(updateJourneyNameQuestion);
                    if(decision == 0){ // Save over chosen & Journey Name kept
                        // Update current journey within collection with updated MPG etc then update csv file?
                        // journeyNumber, journeyName remain the same - update date and journeyFcc with new data.
                        currentInstant.now();
                        foundJourney.setInstant(currentInstant);
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
                        // Create a new fcc then 'set' it. miles, ppl, mpg
                        FuelCostCalculator fccTransfer = new FuelCostCalculator(miles, ppl, mpg);
                        // 2. set new FCC object to foundJourney.
                        foundJourney.setFcc(fccTransfer);
                        // 3. save foundJourney over previous by using JourneyNo...
                        // Update currentTrips here then save to CSV?
                        // Create an update method in Trips?
                        currentTrips.updateJourneyInTrips(foundJourney);
                    } // End of Save over chosen & Journey Name kept
                    else if(decision == 1){ // Save over chosen & Journey Name to change..
                        String newJourneyName = userInputWindow("Please enter the new Journey name.");
                        // Update journey name here
                        // What if null/cancel chosen?
                        if(newJourneyName == null){
                            showOKWindow("Save over cancelled. Name cannot be null.");
                            return;
                        }

                        foundJourney.setJourneyName(newJourneyName);
                        double miles = 0.0;
                        double mpg = 0.0;
                        double ppl = 0.0;
                        String milesTxt = milesTraveledText.getText();
                        miles = Double.parseDouble(milesTxt);
                        String mpgTxt = currentMpgText.getText();
                        mpg = Double.parseDouble(mpgTxt);
                        String pplTxt = pencePerLitreText.getText();
                        ppl = Double.parseDouble(pplTxt);

                        FuelCostCalculator fccTransfer = new FuelCostCalculator(miles, ppl, mpg);
                        foundJourney.setFcc(fccTransfer);

                        currentTrips.updateJourneyInTrips(foundJourney);
                    }
                    else if(decision == 2){ // Save over cancelled.
                        showOKWindow("Save over cancelled.");
                    }
                } else if(choice == 1){
                    // If no then go to another window offering latest availble journey number
                    // and asking for a new journey name.
                    // Iterate through currentTrips and find highest journeyNumber.
                    int highestJourneyNo = currentTrips.searchTripsHighestJourneyNo();
                    //System.out.println("highestJourneyNo = " + highestJourneyNo);
                    int newJourneyNo = highestJourneyNo + 1;
                    // Menu displays new journey number and accepts user entered journey name
                    // Will need to validate entry....
                    String userInput = userInputWindow("New Journey number will be " +
                            newJourneyNo + ". Now please enter a new journey name.");
                    //System.out.println("User input is: " + userInput);
                    // Create the new journey here and update current trips
                    // Need to add an "add journey method" to trips?

                    if(userInput == null){
                        showOKWindow("Save over cancelled. Name cannot be null.");
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

                    FuelCostCalculator fccTransfer = new FuelCostCalculator(miles, ppl, mpg);

                    // create new journey object....
                    journeyInstant.now();
                    Journey newJourney = new Journey(newJourneyNo, userInput,journeyInstant, fccTransfer);

                    currentTrips.updateJourneyInTrips(newJourney);
                } else if(choice == 2){
                    showOKWindow("Save cancelled.");
                    return;
                }

                // Then confirm journey name to be used.                

                // YES_NO_CANCEL_OPTION (yes = saves over journey & prompts for new name?
                //                       no = uses new journey no and prompts for name
                //                       cancel = returns from save method)

            } else { //foundJourney == null and journey number not null)

                // Call load journey then by default create a number on the last number ++?
                // though that would update text fields as the loadJourney method0
                // currently functions...

                // 27-12-24 
                int highestJourneyNo = currentTrips.searchTripsHighestJourneyNo();
                int newJourneyNo = highestJourneyNo + 1;
                // Menu displays new journey number and accepts user entered journey name
                // Will need to validate entry....
                String userInput = userInputWindow("New Journey number will be " +
                        newJourneyNo + ". Now please enter a new journey name.");
                double miles = 0.0;
                double mpg = 0.0;
                double ppl = 0.0;
                String milesTxt = milesTraveledText.getText();
                miles = Double.parseDouble(milesTxt);
                String mpgTxt = currentMpgText.getText();
                mpg = Double.parseDouble(mpgTxt);
                String pplTxt = pencePerLitreText.getText();
                ppl = Double.parseDouble(pplTxt);

                FuelCostCalculator fccTransfer = new FuelCostCalculator(miles, ppl, mpg);

                // create new journey object....
                journeyInstant.now();
                Journey newJourney = new Journey(newJourneyNo, userInput,journeyInstant, fccTransfer);

                currentTrips.updateJourneyInTrips(newJourney);

            }//End of checking if found journey is null

        }else{
            displayErrorMessageWindow("Journey Box is empty.");
            // 22-12-24 the following not activating?

            // As above ask for new journey name and use latest journey number +1?
            int highestJourneyNo = currentTrips.searchTripsHighestJourneyNo();
            int newJourneyNo = highestJourneyNo + 1;
            // Menu displays new journey number and accepts user entered journey name
            // Will need to validate entry....
            String userInput = userInputWindow("New Journey number will be " +
                    newJourneyNo + ". Now please enter a new journey name.");
            double miles = 0.0;
            double mpg = 0.0;
            double ppl = 0.0;
            String milesTxt = milesTraveledText.getText();
            miles = Double.parseDouble(milesTxt);
            String mpgTxt = currentMpgText.getText();
            mpg = Double.parseDouble(mpgTxt);
            String pplTxt = pencePerLitreText.getText();
            ppl = Double.parseDouble(pplTxt);

            FuelCostCalculator fccTransfer = new FuelCostCalculator(miles, ppl, mpg);

            // create new journey object....
            journeyInstant.now();
            Journey newJourney = new Journey(newJourneyNo, userInput,journeyInstant, fccTransfer);

            currentTrips.updateJourneyInTrips(newJourney);
        }
        displayTrips();
    }

    /*
     * Delete current Journey
     */
    private void deleteJourneyFromTrips()
    {
        //To do!
        if(!deleteJourneyText.getText().equals("")){

            //Call load method first?
            if(currentTrips == null){
                loadJourney();
                // 
            }
            //String journeyNo = journeyNumberText.getText();
            String journeyNo = deleteJourneyText.getText();
            int journeyNoInt = Integer.parseInt(journeyNo);
            Journey foundJourney = currentTrips.searchTripsForJourneyNo(journeyNoInt);

            if(foundJourney == null){
                displayErrorMessageWindow("Journey " + journeyNoInt + " not found.");
            } else {
                String saveOverJourneyQuestion = "Do you wish to delete Journey " + journeyNoInt + " ?";
                int choice = yesNoResult(saveOverJourneyQuestion);
                if(choice == 0){
                    currentTrips.deleteJourney(foundJourney);
                    displayTrips();
                    currentTrips.writeCSVFile();
                    deleteJourneyText.setText("");
                }
            }

        } else {
            displayErrorMessageWindow("Delete Journey Box is empty.");
            return;
        }

    }

    /**
     * Creates a user input window which displays a supplied string and returns the user input as a string.
     * 
     * @param   displayString   Message to be displayed.
     * 
     * @return  userInputString String value of what user entered.
     */
    private String userInputWindow(String displayString)
    {
        UIManager.put("OptionPane.messageFont", new Font("SansSerif", Font.PLAIN, 20));
        String userInput = "";
        userInput = JOptionPane.showInputDialog(displayString);
        return userInput;
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
            String msg = "Please enter whole, or decimal numbers only. Do not use commas.";
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
        int choice = JOptionPane.showConfirmDialog(frame,
                question, "Question Confirmation",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        return choice;
    }

    /**
     * Creates a confirmation window and returns an integer based on user selection.
     *
     *@String   Question that is to be displayed.
     * 
     * YES_OPTION = 0
     * NO_OPTION = 1
     */
    private int yesNoResult(String question)
    {
        UIManager.put("OptionPane.messageFont", new Font("SansSerif", Font.PLAIN, 20));
        int choice = JOptionPane.showConfirmDialog(frame,
                question, "Question Confirmation",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        return choice;
    }

    /**
     * Display an error window with supplied String.
     */
    private void displayErrorMessageWindow(String message)
    {
        JOptionPane.showMessageDialog(null, message,
            "Alert", JOptionPane.ERROR_MESSAGE);
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
     * Updates the font, size and horizontal alignment for a JTextArea.
     * @param textArea The JTextArea to be modified.
     */
    private void changeFontAndAlign(JTextArea textArea)
    {
        textArea.setFont(new Font("SansSerif", Font.BOLD, 20));
        //textArea.setHorizontalAlignment(0);
        String formatedText = textArea.getText();
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
            "JourneysCostCalc by Mark Gregory\n" + "Version: 0.8, 2025-02-22",
            "About JourneysCostCalc", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Show an OK dialog window with supplied String.
     */
    private void showOKWindow(String displayString)
    {
        UIManager.put("OptionPane.messageFont", new Font("SansSerif", Font.PLAIN, 20));
        JOptionPane.showMessageDialog(frame,displayString,"Confirmation Window",JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Show the 'Frequently Asked Questions' dialog.
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
