import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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
 * @version 1.0, 2022-11-01
 */
public class UserInterface //extends JFrame //implements ActionListener
{
    private FuelCostCalculator journeyCost;
    private JFrame frame;

    private JLabel milesTraveledLabel;      
    private JTextField milesTraveledText;

    private JLabel currentMpgLabel;
    private JTextField currentMpgText;

    private JLabel pencePerLitreLabel;
    private JTextField pencePerLitreText;

    private JButton totalCostFuelButton;
    private JTextField totalCostFuelText;

    /**
     * Constructor for objects of class UserInterface
     */
    public UserInterface()
    {
        journeyCost = null;
        makeFrame();
    }

    /**
     * Create the Swing frame and its content.
     */
    private void makeFrame()
    {
        frame = new JFrame("Fuel Cost Calculator");
        makeMenuBar(frame);

        Container contentPane = frame.getContentPane();

        contentPane.setLayout(new GridLayout(2, 8));

        milesTraveledLabel = new JLabel("Miles Traveled");
        milesTraveledLabel.setHorizontalAlignment(0);
        milesTraveledLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        contentPane.add(milesTraveledLabel);
        milesTraveledText = new JTextField();
        contentPane.add(milesTraveledText);
        milesTraveledText.setFont(new Font("SansSerif", Font.PLAIN, 20));
        milesTraveledText.setHorizontalAlignment(0);

        currentMpgLabel = new JLabel("Current MPG");
        currentMpgLabel.setHorizontalAlignment(0);
        currentMpgLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        contentPane.add(currentMpgLabel);
        currentMpgText = new JTextField();
        contentPane.add(currentMpgText);
        currentMpgText.setFont(new Font("SansSerif", Font.PLAIN, 20));
        currentMpgText.setHorizontalAlignment(0);

        pencePerLitreLabel = new JLabel("Pence Per Litre");
        pencePerLitreLabel.setHorizontalAlignment(0);
        pencePerLitreLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        contentPane.add(pencePerLitreLabel);
        pencePerLitreText = new JTextField();
        contentPane.add(pencePerLitreText);
        pencePerLitreText.setFont(new Font("SansSerif", Font.PLAIN, 20));
        pencePerLitreText.setHorizontalAlignment(0);

        // Button to instigate the calculation process.
        totalCostFuelButton = new JButton("Total Cost of Fuel (£)");
        totalCostFuelButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        contentPane.add(totalCostFuelButton);
        totalCostFuelButton.addActionListener(e -> calculate());

        // Displays the result.
        totalCostFuelText = new JTextField();
        contentPane.add(totalCostFuelText);
        totalCostFuelText.setFont(new Font("SansSerif", Font.PLAIN, 20));
        totalCostFuelText.setHorizontalAlignment(0);

        // Arrange the components and show.
        frame.pack();
        frame.setVisible(true);
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
                JOptionPane.showMessageDialog(null, "MPG cannot be nothing.",
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
            "FuelCostCalc by Mark Gregory\n" + "Version: 1.0, 2022-11-01",
            "About FuelCostCalc", 
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
        menu.setFont(new Font("SansSerif", Font.PLAIN, 20));

        item = new JMenuItem("Quit");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
        item.addActionListener(e -> quit());
        menu.add(item);
        item.setFont(new Font("SansSerif", Font.PLAIN, 20));

        // create the Help menu
        menu = new JMenu("Help");
        menubar.add(menu);
        menu.setFont(new Font("SansSerif", Font.PLAIN, 20));

        item = new JMenuItem("About FuelCostCalc");
        item.addActionListener(e -> showAbout());
        menu.add(item);
        item.setFont(new Font("SansSerif", Font.PLAIN, 20));
        
        item = new JMenuItem("Frequently Asked Questions");
        item.addActionListener(e -> showFAQ());
        menu.add(item);
        item.setFont(new Font("SansSerif", Font.PLAIN, 20));

    }

}
