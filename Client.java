import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

	// member variables
  private String sender;
  private final File inbox;
	private final File outbox;
	private final File sent;
	private final File spam;
  private final File favorites;
	private File currentFolder;
	private FileWriter composer;
	private ArrayList<Email> emails;
  private Scanner scan;
  

  /*
  * 
  * pre-condition: 
  * post-condition: 
  */
	// constructor
  public Client(User user) {
    this.sender = user.getEmail();
    String userDir = "Users/" + sender;
		
		// intialize access path variables
  	emails = new ArrayList<Email>();
    inbox = new File(userDir + "/Inbox");
		outbox = new File(userDir + sender + "/Outbox");
		sent = new File(userDir + "/Sent");
		spam = new File(userDir + "/Spam");
    favorites = new File(userDir + "/Favorites");
    scan = new Scanner(System.in);
		

		// for each file in our folder add the file to our ArrayList.
		if (outbox.listFiles() != null) {
			for (File emailFile: outbox.listFiles()) {
				// new emailFile parses file and stores in appropriate variables
				emails.add(new Email(emailFile));
			} 
		}
		

  }

	// simply display options for User.
  public void displayOptions() {
    System.out.println("\nWelcome to your Mail Client\n"
                      + "What would you like to do?\n"
                      + "\t1 - Display Inbox\n"
                      + "\t2 - Display Outbox\n"
                      + "\t3 - Open email\n"
                      + "\t4 - Compose email\n"
                      + "\t5 - Delete email\n"
                      + "\t6 - Search for email\n"
                      + "\t7 - Log out of client\n");

  }

	/*
	1 recipient datetime
		subject
	2 recipient datetime
		subject
	*/

	// display only identifying info.
  public void displayOutbox() {
		int i = 1;
		for (Email email: emails) {
			System.out.print("-------------------------\n" +
                        i + " ");
			// System.out.println(email.getSender());
			System.out.print(email.getRecipient() + " ");
      System.out.println(email.getDateTime());
			System.out.println("  " + email.getSubject());
			// System.out.println(email.getMessage());
			// System.out.println(email.getSignature());
      ++i;
		}
  }

	// open user chosen email, picked by index
  public void openEmail(int index) {

			// use this email as the selected email
			Email chosenEmail = emails.get(index);

      // display info
      System.out.print("-------------------------\n");
			System.out.println("From: " + chosenEmail.getSender());
			System.out.println("To: " + chosenEmail.getRecipient());
      System.out.println("Date: " + chosenEmail.getDateTime());
			System.out.println("Subject: " + chosenEmail.getSubject());
			System.out.println("Message:\n" + chosenEmail.getMessage());
			System.out.println("Sincerely,\n" + chosenEmail.getSignature());
      System.out.print("-------------------------\n");
  }

	// create a new email and store it as a file
  public Email composeEmail() {
		// create the new email which will ask the user for information
    Email newEmail = new Email(sender);

		// add the email to arraylist of emails
		emails.add(newEmail);
		//System.out.println("ID: " + newEmail.getID());

		try {
			// create our file and name it as the id
			composer = new FileWriter("Outbox/" + newEmail.getID());
			System.out.println(newEmail.getSender() + "\n");
			
			// write all the required info to file.
			composer.write(newEmail.getSender() + "\n");
			composer.write(newEmail.getRecipient() + "\n");
			composer.write(newEmail.getDateTime() + "\n");
			composer.write(newEmail.getSubject() + "\n");
    	composer.write(newEmail.getMessage() + "\n");
    	composer.write(newEmail.getSignature() + "\n");

			// close file
			composer.close();
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
		

    return newEmail;  
  }

  public void deleteEmail(int index) {
		File deleteFile = new File ("Outbox/" + emails.get(index).getID());
    // Delete the email
		deleteFile.delete();
    // Remove from ArrayList
		emails.remove(index);
  }

  public void search() {
		System.out.print("Enter your search (for dates use (yyyy/MM/dd)): ");
		String search =  scan.nextLine();

    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    // 01-01-2020 

    // Search by date
    DateValidator validator = new DateValidatorUsingLocalDate(dateFormat);
    
    // If date input is valid
    if (validator.isValid(search)) {
      
			for (Email email: emails) {
				//System.out.println(email.getDateTime().substring(0, 10));
				if (email.getDateTime().substring(0, 10).equals(search)) {
					openEmail(emails.indexOf(email));
				}
			}
    }
    // Search keyword by subject, message, recipient
    else {
      for (Email email: emails) {
				if (email.getSubject().contains(search) || 
            email.getMessage().contains(search) || 
            email.getSender().contains(search) || 
            email.getRecipient().contains(search)) {
							openEmail(emails.indexOf(email));
						}
			}
    }       
  } 
}