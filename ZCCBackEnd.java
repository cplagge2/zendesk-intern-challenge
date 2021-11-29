import java.util.LinkedList;
import java.util.Scanner;

/**
 * Back end class for the Coding Challenge program
 * This class is used for pulling and formatting tickets
 */
public class ZCCBackEnd{
    
    /**
     * The java runtime object is used to generate a process that uses curl
     * We use curl to query the api for tickets
     * Once all tickets are recieved, scanner is used to process the input stream
     * 
     * The result is split by the url to create an array of strings containing the json information
     * For each element in the array, the string is parsed and turned into a ticket
     * Each ticket is added to our list of tickets which is returned
     * 
     * If any exception is encountered while connecting to the api, a null list is returned to be handled by front end
     * 
     * @param subdomain - Subdomain to pull tickets from
     * @param username - Username to use for ticket access
     * @param token - Token to use for ticket access
     * @return LinkedList<Ticket> containing all tikets pulled by using curl
     */
    public static LinkedList<Ticket> pullTickets(String subdomain, String username, String token){
        try{
            //Gets tickets using runTime
            Runtime runTime = Runtime.getRuntime();
            Process process = runTime.exec("curl https://" + subdomain + ".zendesk.com/api/v2/tickets.json -u " + username + "/token:" + token);
            //Parses raw json string
            Scanner scnr = new Scanner(process.getInputStream());
            String result = scnr.nextLine();
            scnr.close();
            //Splits the string into individual tickets
            String[] tickets = result.split("url"); 
            //Each string is formatted, turned to a ticket, and added to the list
            LinkedList<Ticket> ticketList = new LinkedList<Ticket>();
            for(int i = 1; i < tickets.length; i++){
                String jsonTicket = tickets[i];
                Ticket newTicket = new Ticket(findID(jsonTicket), findSubject(jsonTicket), findStatus(jsonTicket), findDate(jsonTicket));
                ticketList.add(newTicket);
            }
            return ticketList;
        }
        catch(Exception e){
            //If an issue occurs connecting to the api, it is handled by front end
            return null;
        }
    }

    /**
     * Accesses the id of json string
     * Uses indexOf to find the initial index of the id
     * Uses the Character class methods to parse the entire id
     *  as a single int value
     * 
     * @param jsonString - String containing all the ticket information with json formatting
     * @return int with the ticket's id value
     */
    public static int findID(String jsonString){
        int location = jsonString.indexOf("id") + 4;
        int id = 0;
        char idDigit = jsonString.charAt(location);
        do{
            id = id * 10 + Character.getNumericValue(idDigit);
            location = location + 1;
            idDigit = jsonString.charAt(location);
        } while(Character.isDigit(idDigit));
        return id;
    }

    /**
     * Accesses the status of the json string
     * Uses indexOf to find initial and final index of status
     * Uses substring to get the entire status of the ticket
     * 
     * @param jsonStringString - String containing all the ticket information with json formatting
     * @return String with the ticket's current status
     */
    public static String findStatus(String jsonString){
        int startSubstring = jsonString.indexOf("status") + 9;
        int endSubstring = jsonString.indexOf("recipient") - 3;
        return jsonString.substring(startSubstring, endSubstring);
    }

    /**
     * Accesses the subject of the json string
     * Uses indexOf to find initial and final index of subject
     * Uses substring to get the entire subject for the ticket
     * 
     * @param jsonString - String containing all the ticket information with json formatting
     * @return String with the ticket's subject
     */
    public static String findSubject(String jsonString){
        int startSubstring = jsonString.indexOf("subject") + 10;
        int endSubstring = jsonString.indexOf("raw_subject") - 3;
        return jsonString.substring(startSubstring, endSubstring);
    }

    /**
     * Accesses the date of the json string
     * Uses indexOf to find initial and final index of the date
     * Uses substring to get the entire date for the ticket
     * 
     * @param jsonString - String containing all the ticket information with json formatting
     * @return String with the ticket's date
     */
    public static String findDate(String jsonString){
        int startSubstring = jsonString.indexOf("created_at") + 13;
        int endSubstring = jsonString.indexOf("updated_at") - 3;
        String rawDate = jsonString.substring(startSubstring, endSubstring);
        String formattedDate = rawDate.substring(0, rawDate.indexOf("T")) + " at " + rawDate.substring(rawDate.indexOf("T") + 1, rawDate.length()-1);
        return formattedDate;
    }
}