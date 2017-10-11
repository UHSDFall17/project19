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
	Scanner keys = new Scanner(System.in);
	Logger(){
		fileSystem = new FileInOut();

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
			check = fileSystem.checkForName("Users.txt", tempInfo);
			if(tempInfo.equals("EXIT")) {
				return false;
			}
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
	        File file = new File(fileName);
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
		public String getUserName() {
			return userName;
		}
		public boolean checkLogIn() {
			return isLoggedIn;
		}
}