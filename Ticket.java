/**
 * Class containing ticket objects
 * Tickets contain a variety of information used for outputting data
 */
public class Ticket {
    //Fields for each ticket
    private int id; //Ticket id number
    private String subject; //Subject line of ticket
    private String status; //Current ticket status
    private String date; //Date ticket was originated

    /**
     * Implicit Constructor for the ticket item
     * Sets fields to default values signaling creation of implicit ticket
     */
    public Ticket(){
        this.id = 0;
        this.subject = "No subject passed";
        this.status = "Invalid";
        this.date = "1/1/2021";
    }

    /**
     * Constructor for ticket item
     * Fields are intitated by each parameter
     * 
     * @param id - ID number of the ticket
     * @param subject - Ticket subject line
     * @param status - Ticket status
     * @param date - Date of ticket origin
     */
    public Ticket(int id, String subject, String status, String date){
        this.id = id;
        this.subject = subject;
        this.status = status;
        this.date = date;
    }

    /**
     * Overrides toString() method from object class
     * This allows for convinient and fast outputting on the front end
     * 
     * @return String containing formatted output with id, subject, and status of the ticket
     */
    @Override
    public String toString(){
        return("ID: " + id + "   Subject: " + subject + "   Status: " + status);
    }

    /**
     * Accessor for the date field of the ticket object
     * 
     * @return String containing the formatted date the ticket was created
     */
    public String getDate(){
        return date;
    }
}
