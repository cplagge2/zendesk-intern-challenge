import java.util.Scanner;

/**
 * Class that runs the entire coding challenge app
 * Args parameter is used to collect user data and then inputted into front end
 */
public class ZCCApp {
    /**
     * Launcher for the ZCC Ticket Viewer
     * Uses the args array to gather necessary information from application user
     * 
     * @param args - Used to collect information necessary for interfacing with the api
     *          comes in the form [subdomain containing the tickets, username for ticket access, token for ticket access]
     */
    public static void main(String[] args){
        //Takes important information from args array
        String subdomain = "";
        String username = "";
        String token = "";
        try{
            subdomain = args[0];
            username = args[1];
            token = args[2];
        } catch(IndexOutOfBoundsException e){
            System.out.println("Sorry! It looks like you are missing a required paramter.\nMake sure you are using form: \n"
                + "java ZCCApp subdomain username token");
            return;
        }
        Scanner scnr = new Scanner(System.in);
        //Launch front end
        ZCCFrontEnd.launchFrontEnd(subdomain, username, token, scnr);
    }
}
