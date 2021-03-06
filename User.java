import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class User {
	// email
	private String email;
	// password
	private String password;
	private FileWriter writer;
	private Scanner fileScanner;
	// user is a folder
	// Folders - Inbox, outbox, spam, favorites

  // Existing user 
	public User(File userFile) {
		fileScanner = new Scanner(userFile.getPath() + "/details.txt");
		this.password = fileScanner.nextLine();
		this.email = fileScanner.nextLine();	
	}

  // Creating a new user
  public User(String Email, String Password) {
		this.email = Email;
    this.password = Password;

		//  EX. Users/Samuel.Menaged@knights.edu
		String userDir = "Users/" + Email;
		try {

			new File(userDir).mkdir();
    	new File(userDir + "/Inbox").mkdir();
			new File(userDir + "/Outbox").mkdir();
			new File(userDir + "/Spam").mkdir();
			new File(userDir + "/Favorites").mkdir();
			writer = new FileWriter(userDir + "/details.txt");
			writer.write(Password + "\n");
			writer.write(Email + "\n");

			writer.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    
  }

  public String getEmail() {
    return email;
  }

}