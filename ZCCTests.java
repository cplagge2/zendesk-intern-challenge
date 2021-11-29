import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test bench for ticket view application
 * Implemented using junit5
 */
public class ZCCTests {
    

    /**
     * Tests for the Ticket class
     * Ensures all methods and accessors work as intended
     */
    @Test
    public void testTicket(){
        //Test implicit constructor
        Ticket implicitTicket = new Ticket();
        if(!implicitTicket.getDate().equals("1/1/2021")){
            fail("Date acessor for implicit constructor fails");
        }
        if(!implicitTicket.toString().equals("ID: 0   Subject: No subject passed   Status: Invalid")){
            fail("toString method failed for implicit constructor");
        }
        //Test all accessors
        Ticket testTicket = new Ticket(3, "Practice " , "closed", "1/9/2002");
        if(!testTicket.getDate().equals("1/9/2002")){
                fail("Date acessor for basic ticket fails");
        }
        if(!testTicket.toString().equals("ID: 3   Subject: Practice    Status: closed")){
            System.out.println(testTicket);
            fail("toString method failed for basic ticket");
        }
    }

    /**
     * Tests for backEnd class
     * Ensures that all tickets are properly loaded
     * Ensures that erroneous input is handled properly
     */
    @Test
    public void testBackEnd(){
        //Invalid input
        try{
            LinkedList<Ticket> testTickets = ZCCBackEnd.pullTickets(null, null, null);
            if(testTickets.size() != 0) {
              fail("Information returned when querying api with invalid info");
            }
        } catch(Exception e){
            fail("Error encountered for querying api with invalid info");
        }
        
        //Tests that valid input gets correct values
        LinkedList<Ticket> testTickets = ZCCBackEnd.pullTickets
            (CONFIG.subdomain, CONFIG.username, CONFIG.token);
        if(testTickets.size() != 100) {
          fail("Wrong amount of tickets retrieved by back end");
        }
        //Tests first ticket
        if(!testTickets.get(0).toString().equals("ID: 1   Subject: Sample ticket: Meet the ticket   "
            + "Status: open")) {
          fail("Incorrect data in first ticket retrieved");
        }
        
        //Tests last ticket
        if(!testTickets.get(99).toString().equals("ID: 100   Subject: adipisicing duis quis consequat velit   "
            + "Status: open")) {
          fail("Incorrect data in last ticket retrieved");
        }
        
        //Arbitrary test ticket
        String testJson = "{\"url\":\"https://zcccodingchallenge2727.zendesk.com/api/v2/tickets/92.json\""
          + "\"id\":92,\"external_id\":null," + "\"created_at\":\"2021-11-20T17:03:53Z\","
              + "\"updated_at\":\"2021-11-20T17:03:53Z\", \"subject\":\"officia sunt aliquip duis nisi\","
              + "\"raw_subject\":\"officia sunt aliquip duis nisi\",\"status\":\"open\",\"recipient\":null";
        
        //Test findStatus with random ticket
        if(!ZCCBackEnd.findStatus(testJson).equals("open")) {
          fail("findStatus failed for arbitrary ticket");
        }
        
        //Test findID with random ticket
        if(ZCCBackEnd.findID(testJson) != 92) {
          fail("findID failed for arbitrary ticket");
        }
        
        //Test findSubject with random ticket
        if(!ZCCBackEnd.findSubject(testJson).equals("officia sunt aliquip duis nisi")) {
          fail("findSubject failed for arbitrary ticket");
        }
        
        //Test findDate with random ticket
        if(!ZCCBackEnd.findDate(testJson).equals("2021-11-20 at 17:03:53")) {
          fail("findDate failed for arbitrary ticket");
        }
        
    }
    
    /**
     * Tests for the frontEnd class
     * Ensures that the program executes without errors for a variety of inputs
     * Output was manually analyzed for accuracy
     */
    @Test
    public void testFrontEnd() {
      //Establishes Scanner with specific inputs
      Scanner testScan = new Scanner("");
      //Test launchFrontEnd for invalid input
      try{
        ZCCFrontEnd.launchFrontEnd(null, null, null, testScan);
      } catch(Exception e){
        fail("Error encountered for querying api with invalid info");
      }
      
      //Tests no error for immediate termination
      Scanner validScanner = new Scanner("3");
      try {
        ZCCFrontEnd.launchFrontEnd(CONFIG.subdomain, CONFIG.username, CONFIG.token, validScanner);
      } catch(Exception e) {
        e.printStackTrace();
        fail("Exception thrown for valid user input");
      }
      
      //Tests no error for entering display loop
      validScanner = new Scanner("1 exit 3");
      try {
        ZCCFrontEnd.launchFrontEnd(CONFIG.subdomain, CONFIG.username, CONFIG.token, validScanner);
      } catch(Exception e) {
        e.printStackTrace();
        fail("Exception thrown for valid user input");
      }
      
      //Tests no error for iteration of display loop
      validScanner = new Scanner("1 next\n exit\n 3");
      try {
        ZCCFrontEnd.launchFrontEnd(CONFIG.subdomain, CONFIG.username, CONFIG.token, validScanner);
      } catch(Exception e) {
        e.printStackTrace();
        fail("Exception thrown for valid user input");
      }
      
      //Tests no error for lookup of valid id number
      validScanner = new Scanner("2 30 3");
      try {
        ZCCFrontEnd.launchFrontEnd(CONFIG.subdomain, CONFIG.username, CONFIG.token, validScanner);
      } catch(Exception e) {
        e.printStackTrace();
        fail("Exception thrown for valid user input");
      }
      
      //Tests no error for lookup of invalid id number
      validScanner = new Scanner("2 Hello\n 3");
      try {
        ZCCFrontEnd.launchFrontEnd(CONFIG.subdomain, CONFIG.username, CONFIG.token, validScanner);
      } catch(Exception e) {
        e.printStackTrace();
        fail("Exception thrown for valid user input");
      }
      
      //Tests no error for initial invalid input
      validScanner = new Scanner("Hello\n 3");
      try {
        ZCCFrontEnd.launchFrontEnd(CONFIG.subdomain, CONFIG.username, CONFIG.token, validScanner);
      } catch(Exception e) {
        e.printStackTrace();
        fail("Exception thrown for valid user input");
      }
    }
}
