import java.io.IOException;
import java.util.Scanner;

public class SignUp {
	private String userName;
	FileInOut fileSystem;
	Users user;
	Scanner keys = new Scanner(System.in);
	SignUp () throws IOException{
		
		fileSystem = new FileInOut();
		userName = "";
	}
	/*This function Requests a user name then writes to a file then asks 
	 * for a password which it appends under the userName
	 * Example:
	 * UserName1
	 * Password1
	 * UserName2
	 * Password2
	 */
	
	public boolean createUserName()throws IOException{
		String tempInfo= "";
		boolean isThere = true;
		fileSystem.checkFile("Users.txt");
		
		System.out.println("Type 'Exit' to return to the home menu");
		
		while(isThere) {
			System.out.println("Enter a userName:");
			tempInfo = keys.nextLine().toUpperCase();
			
			if(tempInfo.equals("EXIT"))
				return false;
			isThere = fileSystem.checkForName("Users.txt",tempInfo);
			if(isThere)
				System.out.println("That user name is already taken, please try another!");
			
		}
		
		userName = tempInfo;
		
		
		System.out.println("Please enter a password:");
		tempInfo = keys.nextLine();
		if(tempInfo.toUpperCase().equals("EXIT")) {
			userName = "";
			return false;
		}
		
		fileSystem.writeToFile("Users.txt", Integer.toString(userName.hashCode()));
		fileSystem.writeToFile("Users.txt", Integer.toString(tempInfo.hashCode())/*Password*/);
		
		
		fileSystem.checkFile(userName + ".txt");
		
		System.out.println("Thank You!");
		return true;
		
	}
	public String getUserName() {
		return userName;
	}
	
}
