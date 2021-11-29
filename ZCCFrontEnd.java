import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Runs the front end of the Coding Challenge Program
 * Uses back end for gathering all tickets
 * With all the tickets, it runs the fully functional ticket viewer
 */
public class ZCCFrontEnd {

    /**
     * Primary method for outputting ticket viewer
     * Uses ZCCBackEnd to create a linkedlist of tickets
     * A loop is used to continuously prompt user input and appropriately handle it
     * 
     * @param subdomain - Subdomain of web address used to access tickets
     * @param username - Username used to authenticate for tickets
     * @param token - Token used to authenticate for tickets
     */
    public static void launchFrontEnd(String subdomain, String username, String token, Scanner scnr){
      
        //Uses back end to pull all tickets
        LinkedList<Ticket> currentTickets = ZCCBackEnd.pullTickets(subdomain, username, token);
        //Handles potentially erroneous returns from back end
        if(currentTickets == null){
            System.out.println("Sorry! We encountered an error when connecting to the API.\nPlease check to confirm your credentials and subdomain are correct!");
            scnr.close();
            return;
        }
        else if(currentTickets.size() == 0){
            System.out.println("It appears no tickets are available! \nEither you don't have access to these tickets or you're up to date!");
            scnr.close();
            return;
        }

        //Welcoming output
        System.out.println("Welcome to the Zendesk Ticket Viewer! \n");
        System.out.println("Input a number to choose your desired function:");
        
        //Uses a while loop to continually prompt for user input
        int userInput = -1;
        boolean loopCheck = true;
        do {
            System.out.println("\t 1: Display all tickets \n \t 2: Display a single ticket \n \t 3: Exit the ticket viewer\n");
            //Try is used for potential scanner issues with eroneous input
            try{
                userInput = scnr.nextInt();
                //If the input is invalid, we return to main menu using the error catch coce
                if (userInput > 3 || userInput < 1){
                    throw new InputMismatchException();
                }

                //Displays all tickets recursively
                if(userInput == 1){
                    display25(currentTickets, 0, scnr);
                }

                //Allows user to search for ticket by id number
                else if(userInput == 2){
                    //Uses try catch to handle erroneous user input
                    System.out.println("Enter ticket id number:");
                    int ticketId = scnr.nextInt();
                    try{
                        //If input is a valid int, ticket with id is pulled and outputted with additional info
                        Ticket tempTicket = currentTickets.get(ticketId - 1);
                        System.out.println(tempTicket + "   Date Opened: " + tempTicket.getDate() + "\n");
                    } catch(IndexOutOfBoundsException exception){
                        //If ticket id was invalid, return to main menu
                        System.out.println("\nSorry! This is an invalid id \nReturning to main menu...\n");
                    }
                }
                //If the user inputs 3, stop the loop
                else{
                    loopCheck = false;
                }

            }
            //If erroneous input is encountered, return to main menu and restart with basic options
            catch(InputMismatchException excpt){
                System.out.println("Sorry! We don't quite understand \nReturning to main menu...\n");
                scnr.nextLine();
            }
        } while(loopCheck);
        
        System.out.println("Exiting...\nThank you for using the Zendesk Ticket Viewer!");
    }

    /**
     * Recursively displays tickets 25 at a time
     * If the start index is greater than the size, the program ends
     * Otherwise a start and stop index are calculated
     * These indices are used to display 25 tickets or the quantity remaining until the end if less than 25
     * 
     * @param currentTickets - All tickets to display
     * @param start - Index of first ticket to show
     * @param scnr - scanner used for user input
     */
    public static void display25(LinkedList<Ticket> currentTickets, int start, Scanner scnr){
        //Recursive base case
        if(start >= currentTickets.size()){
            System.out.println("\nYou have reached the end of the tickets\nReturning to main menu...\n");
            return;
        }

        //Calculating index of last ticket to show
        int end = start + 25;
        if(end > currentTickets.size()){
            end = currentTickets.size();
        }

        //Displaying 25 tickets
        for(int i = start; i < end; i++) {
            System.out.println(currentTickets.get(i) + "\n");
        }

        //Prompts and analyzes user input for more tickets
        System.out.println("\nEnter 'next' to see the next 25 tickets\n");
        String seeNext = scnr.next();
        if(seeNext.equals("next")){
            //If the user wants to see more tickets, 25 more display recursively
            display25(currentTickets, start + 25, scnr);       
        }
        else{
            //If the user input is not 'next', program terminates and returns to main menu
            System.out.println("Returning to main menu...\n");
        }

    }
}
