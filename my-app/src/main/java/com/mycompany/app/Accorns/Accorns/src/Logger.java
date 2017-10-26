import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Logger {
	Users user;
	private String userName;
	private boolean isLoggedIn;
	private FileInOut fileSystem;
	private String path;
	Scanner keys = new Scanner(System.in);
	Logger(){
		fileSystem = new FileInOut();
		path = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Accorns_Accounts" + File.separator;

	}
	public boolean signUp()throws IOException{
		String tempInfo= "";
		boolean isThere = true;
		fileSystem.checkForFile("Users.txt");
		
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
		
		
		fileSystem.checkForFile(userName + ".txt");
		
		System.out.println("Thank You!");
		return true;
		
	}
	/*Login method, asks for the user name, then looks for the user name in the 
	 * Users.txt file, then looks for the password in the same file,
	 * then loads all the users personal information from the users personal file
	 */
	public boolean logIn() {
		String tempInfo;
		boolean check = false;
		System.out.println("Type Exit to return to LogIn menu");
		System.out.println("Please enter your user name");
		
		do {
			tempInfo = keys.nextLine().toUpperCase();
			if(tempInfo.equals("EXIT")) {
				return false;
			}
			check = fileSystem.checkForName("Users.txt", tempInfo);
			if(!check)
				System.out.println("Sorry, that user name was not found, please try again");
		}while(!check);
		
		userName = tempInfo;
		check = false;
		System.out.println("Please enter your password");
		
		do {
			tempInfo = keys.nextLine();
			check = doLogIn("Users.txt", userName, tempInfo);
			if(tempInfo.toUpperCase().equals("EXIT")) {
				userName = "";
				return false;
			}
			if(!check)
				System.out.println("Sorry, that password was incorrect, please try again");
		}while(!check);
		System.out.println("LogIn was successful");
		isLoggedIn = true;
		return true;
		
	}
	//Checks to see if user name and password match
		/*file layout
		 * USERNAME1
		 * password1
		 * USERNAME2
		 * password2
		 */
		public boolean doLogIn(String fileName, String name, String passWord){
	        boolean isThere = false;
	        
	        name = name.toUpperCase();
	        String hashName = Integer.toString(name.hashCode());
	        String hashPassword = Integer.toString(passWord.hashCode());
	        File file = new File(path + fileName);
			try {
	            BufferedReader b = new BufferedReader(new FileReader(file));
	            String readLine = "";
	            //Looks for username
	            while ((readLine = b.readLine()) != null) {
	            	//after finding username
	                if(readLine.equals(hashName)) { 
	                	if((readLine = b.readLine()) != null){
	                		
	                		//checks to see if password is correct
	                		if(readLine.equals(hashPassword)) {
	                			
	                			b.close();
	                			return true;
	                		}
	                		else {
	                			
	                			b.close();
	                			return false;
	                		}
	            
	                	}
	                }	
	                //skips over passwords, just looks at user names		
	                readLine = b.readLine();
	           
	            }
	            b.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
			return isThere;
		}
		public boolean logOut() {
			userName = "";
			isLoggedIn = false;
			return false;
		}
		public String getUserName() {
			return userName;
		}
		public boolean checkLogIn() {
			return isLoggedIn;
		}
}
