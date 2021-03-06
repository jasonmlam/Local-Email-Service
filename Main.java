import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

// register
// login
class Main {
  public static void main(String[] args) {

    Scanner scan = new Scanner(System.in);
    String tempPW, line = "";
    User user = null;

    // Determine user Status

    // Either:
    // Log in Existing user(email and password match existing user)
    // Create new user (take in new details)

    System.out.print("Email: ");
    String loginEmail = scan.nextLine();

    try {
        // Check for existing user by scanning log
        File file = new File("Logins.txt");
        Scanner scanner = new Scanner(file);
        FileWriter writeNewEmail = new FileWriter("Logins.txt");

        // Validate email login
        do {
          // Get email attempt
					if (scanner.hasNextLine()) {
						line = scanner.nextLine();
					}
        }	while (scanner.hasNextLine() && !line.equalsIgnoreCase(loginEmail));
        
				//	this is when we don't have a user so create one
        if (!scanner.hasNextLine()) {
					do {
						System.out.print("User does not exist. Would you like to create one?  (yes/no) ");
          	String choice = scan.nextLine();
          	if (choice.equalsIgnoreCase("yes")) {
            	System.out.print("Enter a password: ");
              tempPW = scan.nextLine();
              System.out.print("Enter password again: ");
              while (!tempPW.equals(scan.nextLine())) {
                System.out.println("Not a match. Try again!");
              }
            	user = new User(loginEmail, tempPW);
              writeNewEmail.append(loginEmail + "\n");
              System.out.println("User account successfully created!");
							break;
         	 	} else if (choice.equalsIgnoreCase("no")) {
            	System.out.println("Ok, goodbye!");
            	return;
          	} else {
							System.out.println("Invalid response.");
						}
					}
          while (true);
        }
    } catch(Exception e) { 
        //handle this
				System.out.println("error " + e.getMessage());
    }  

		Client userClient = new Client(user);

		// display file on start
		userClient.displayOutbox();
		
		int option, emailId;

		// do until user exits program
    do {
			// display options
			userClient.displayOptions();
			System.out.print("Enter a choice: ");

			// get option from user input
    	option = scan.nextInt();
      
      
			// evaluate option
      switch (option)
      {
        case 1: // display inbox
          // userClient.displayInbox();
          System.out.println("Displaying Inbox");
          break;

        case 2: // display outbox
          userClient.displayOutbox();
          break;

        case 3: // open email
          // display iterator variable next to each inbox email 
          System.out.print("Select an email ID: ");
          emailId = scan.nextInt() - 1; // Adjust for zero-based index
          userClient.openEmail(emailId);
          break;

        case 4: // compose email
          userClient.composeEmail();
          break;

        case 5: // delete
          System.out.print("Select an email ID: ");
		      emailId = scan.nextInt() - 1;
          userClient.deleteEmail(emailId);
          //Show updated inbox
          System.out.println("Email Deleted. Now showing updated outbox:");
          userClient.displayOutbox();
          break;

        case 6: // search
          userClient.search();
          break;

        case 7: // exit client
          break; 
      }

			if (option != 7) {
				System.out.println("\nPress Enter key to continue...");
        try
        {
            System.in.read();
        }  
        catch(Exception e)
        {
					System.out.println(e.getMessage());
				}  
			}
    
		// 6 is exit option
    } while(option != 7);

		
    System.out.println("Logging out.");
    scan.close();
  }
}