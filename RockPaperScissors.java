import java.util.Random;

/** Question 1a
 * RockPaperScissors emulates a game of Rock Paper Scissors 
 * using the terminal, and declares a winner once either
 * the user or computer has won 3 rounds. TMA02_Q1_SolMG
 *
 * @author Mark Gregory
 * @version 2022-01-28
 */
public class RockPaperScissors
{
    // Question 1b
    private InputReader reader;
    private int yourScore;
    private int computerScore;
    private Random ran;

    /** Question 1c
     * Create a RockPaperScissors object.
     */
    public RockPaperScissors()
    {
        reader = new InputReader();
        yourScore = 0;
        computerScore = 0;
        ran = new Random(1);
    }

    /** Question 1d
     * This prints out a prompt to the terminal to get the user
     * to enter their choice of either paper, rock or scissors.
     *
     */
    public void printPrompt()
    {
        System.out.println("");
        System.out.println("");
        System.out.print("Enter your choice, paper, rock or scissors > ");
    }

    /** Question 1e
     * Returns the user's input for a round of Rock Paper Scissors.
     *
     * @return    a string entered by the user, trimmed and changed to lower
     *            case. Note: No checks are performed on user input so string
     *            could be invalid.
     */
    public String userChoice()
    {
        String input = reader.getInput();
        input = input.trim().toLowerCase();
        return input;
    }

    /** Question 1f
     * Returns the computer's choice for a round of Rock Paper Scissors.
     *
     * @return    a string randomly generated from an integer between 0 and 2
     *            (inclusive) using a switch statement; "rock" (value 0), 
     *            "paper" (value 1) or "scissors" (value 2).
     */
    public String computerChoice()
    {
        int random = ran.nextInt(3);
        String choice = "";
        switch(random){
            case 0:
                choice = "rock";
                break;
            case 1:
                choice = "paper";
                break;
            case 2:
                choice = "scissors";
                break;
            default:
                break;
        }
        return choice;
    }

    /** Question 1g
     * Returns the result for a round of Rock Paper Scissors and adds to winner's total
     * (if applicable).
     *
     * @param yourChoice        The user's choice as a String. If not "rock", "paper" or 
     *                          "scissors" then computer wins the round by default.
     * @param computerChoice    The computer's choice of either "rock", "paper" or 
     *                          "scissors".
     * @return                  Returns a String of either "draw", "you" or "computer"
     *                          depending on who won the round.
     */
    public String findWinner(String yourChoice, String computerChoice)
    {
        if(!yourChoice.equals("rock") && !yourChoice.equals("paper") 
        && !yourChoice.equals("scissors")){
            computerScore++;
            return "computer";
        }
        else if(yourChoice.equals(computerChoice)){
            return "draw";
        }
        else if(yourChoice.equals("rock") && (computerChoice.equals("scissors"))){
            yourScore++;
            return "you";
        }
        else if(yourChoice.equals("paper") && (computerChoice.equals("rock"))){
            yourScore++;
            return "you";
        }
        else if(yourChoice.equals("scissors") && (computerChoice.equals("paper"))){
            yourScore++;
            return "you";
        }
        else{
            computerScore++;
            return "computer";
        }
    }
    /** Question 1h
     * Plays a round of Rock Paper Scissors. 
     */
    public void playRound()
    {
        printPrompt();
        String yourChoice = userChoice();
        String computerChoice = computerChoice();
        System.out.println("");
        System.out.println("You have chosen " + yourChoice + " and the computer has " 
            +"chosen " + computerChoice);
        String result = findWinner(yourChoice, computerChoice);
        switch(result){
            case "draw":
                System.out.println("This game is a draw");
                break;
            case "you":
                System.out.println("You are the winner");
                break;
            case "computer":
                System.out.println("The computer is the winner");
                break;
            default:
                System.out.println("Invalid result");
                break;
        }
        System.out.println("You have " + yourScore + " and the computer has " + computerScore);   
    }
    /** Question 1i
     * Simulates a game of Rock Paper Scissors until either the user or the computer
     * has won 3 rounds.
     */
    public void playGame()
    {
        while((yourScore < 3) && (computerScore < 3 )){
            playRound();
        }
        if(yourScore > computerScore){
            System.out.println("The overall winner is you.");
        }
        else{
            System.out.println("The overall winner is the computer.");
        }
    }
}
