# zendesk-intern-challenge
Implementation solving the problem laid out in the Zendesk engineering intern coding challenge

**Usage:**     

Download all files.
1. If make is downloaded on computer use make to quickly compile all files.
      If not, simply use the javac command to compile each file individually in the following order:
        
        1. Ticket.java   
        2. ZCCBackEnd.java   
        3. ZCCFrontEnd.java   
        4. ZCCApp.java   

2. Run the ZCCApp program with the following command:  

        java ZCCApp subdomain username token
        
   Where subdomain is the subdomain you wish to query,
   username is the username used to authenticate,
   token is the token used to authenticate
   
3. Follow the prompts on the screen to use the Ticket Viewer!


**Testing Notes:**     

The test file is created using junit 5 and as such must be run in an environment with junit 5 added on the class path.
In the test file, fields from a file named CONFIG.java are included. These are sensitive fields that must be used to access
the tickets but that cannot be uploaded to github. As such, included is a sample CONFIG.java file that must be filled out 
to use the test file.

