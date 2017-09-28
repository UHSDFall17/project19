import java.util.*; 
import java.io.*;
import java.lang.*;
public class Users {
	private String userName;
	private String userEmail;
	private String nameFirst;
	private String nameLast;
	private String userFileName;
	private boolean isLoggedIn;
	Scanner keys = new Scanner(System.in);
	Users()throws IOException {
		checkFile("Users.txt");
		userName = "";
		userEmail = "";
		nameFirst = "";
		nameLast = "";
		isLoggedIn = false;
	}
	Users(String user)throws IOException {
		checkFile("Users.txt");
		userName = user;
		userEmail = "";
		nameFirst = "";
		nameLast = "";
		createUserFileName(user);
		loadUserInfo(user);
		outUserInfo();
	}
	/*First time sign it, creates user name, password
	 * File to store information, then files that file
	 * with first name, last name, and email, then loads everything into 
	 * the class
	 */
	public void firstTimeSignup()throws IOException{
		if(createUserName())
			createUserFileName(userName);
		else {
			clearUserInfo();
			return;
		}
		setFirstName();
		setLastName();
		setEmail();
		loadUserInfo(userName);
		isLoggedIn = true;
	}
	
	/*Login method, asks for the user name, then looks for the user name in the 
	 * Users.txt file, then looks for the password in the same file,
	 * then loads all the users personal information from the users personal file
	 */
	public void logIn() {
		String tempInfo;
		boolean check = false;
		System.out.println("Type Exit to return to LogIn menu");
		System.out.println("Please enter your user name");
		
		do {
			tempInfo = keys.nextLine().toUpperCase();
			check = checkForName("Users.txt", tempInfo);
			if(tempInfo.equals("EXIT")) {
				clearUserInfo();
				return;
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
				clearUserInfo();
				return;
			}
			if(!check)
				System.out.println("Sorry, that password was incorrect, please try again");
		}while(!check);
		System.out.println("LogIn was successful");
		loadUserInfo(userName);
		isLoggedIn = true;
		
	}
	//checkFile checks to see if file is there and creates one if needed
	public void checkFile(String fileName) throws IOException {
		File file = new File(fileName);
		if(!file.exists()) {
			file.createNewFile();
		}
	}
	
	/*Example of user text file
	 * First name
	 * Last name
	 * Email
	 */
	public void loadUserInfo(String userName) {
		createUserFileName(userName);
		File file = new File(userFileName);
		try {
            BufferedReader b = new BufferedReader(new FileReader(file));
            nameFirst = b.readLine();
            nameLast = b.readLine();
            userEmail = b.readLine();
            
            b.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	//clears out loaded user info when logging out
	public void clearUserInfo() {
		userName = "";
		userEmail = "";
		nameFirst = "";
		nameLast = "";
		userFileName = "";
		isLoggedIn = false;
	}
	//used to output users info to command line
	public void outUserInfo() {
		System.out.println(userName);
		System.out.println(nameFirst);
		System.out.println(nameLast);
		System.out.println(userEmail);
	}
	
	//creates the users file name, just adds the ".txt" to the end of the user name
	public void createUserFileName() {
		
		StringBuilder tempUserFileName = new StringBuilder(userName);
		tempUserFileName.append(".txt");
		userFileName = tempUserFileName.toString();
	}
	public String appened(String orig, String add) {
		StringBuilder tempUserFileName = new StringBuilder(orig);
		tempUserFileName.append(add);
		return (tempUserFileName.toString());
	}
	
	//accepts input from the user for ".txt" addition
	public void createUserFileName(String name) {
		
		StringBuilder tempUserFileName = new StringBuilder(name);
		tempUserFileName.append(".txt");
		userFileName = tempUserFileName.toString();
	}
	
	//checks the user folder to see if userName is there 
	public boolean checkForName(String fileName, String name){
		boolean isThere = false;
		//all user names are uppercase
		name = name.toUpperCase();
        File file = new File(fileName);
		try {
            BufferedReader b = new BufferedReader(new FileReader(file));
            String readLine = "";
            while ((readLine = b.readLine()) != null) {
                if(readLine.equals(name)) 
                	isThere = true;
                readLine = b.readLine();
           
            }
            b.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return isThere;
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
        File file = new File(fileName);
		try {
            BufferedReader b = new BufferedReader(new FileReader(file));
            String readLine = "";
            //Looks for username
            while ((readLine = b.readLine()) != null) {
            	//after finding username
                if(readLine.equals(name)) { 
                	if((readLine = b.readLine()) != null){
                		//checks to see if password is correct
                		if(readLine.equals(passWord)) {
                			
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
		isLoggedIn = true;
		return isThere;
	}
	
	//returns if the user is logged in based on everything loaded in
	public boolean checkLogIn() {
		return isLoggedIn;
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
		checkFile("Users.txt");
		
		System.out.println("Type 'Exit' to return to the home menu");
		
		while(isThere) {
			System.out.println("Enter a userName:");
			tempInfo = keys.nextLine().toUpperCase();
			
			if(tempInfo.equals("EXIT"))
				return false;
			isThere = checkForName("Users.txt",tempInfo);
			if(isThere)
				System.out.println("That user name is already taken, please try another!");
			
		}
		
		userName = tempInfo;
		
		
		System.out.println("Please enter a password:");
		tempInfo = keys.nextLine();
		if(tempInfo.toUpperCase().equals("EXIT")) {
			clearUserInfo();
			return false;
		}
		
		writeToFile("Users.txt", userName);
		writeToFile("Users.txt", tempInfo/*Password*/);
		
		createUserFileName(userName);
		checkFile(userFileName);
		
		System.out.println("Thank You!");
		return true;
		
	}
	
	//appends to the end of a file, requires file name, not file type
	public void writeToFile(String fileName, String content) {
		
		try(FileWriter fw = new FileWriter(fileName, true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
			    out.println(content);
			} catch (IOException e) {
			    
			}
			
	}

	
	//changes info for userInfo file
	public void writeChangeUserInfo(String fileName,String newString, String oldString) throws IOException{
		File file = new File(fileName);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String oldCon = "";
		String line = reader.readLine();
		
		while (line != null) 
		{
			oldCon = oldCon + line + System.lineSeparator();
			line = reader.readLine();
		}		
		String newCon = oldCon.replaceAll(oldString, newString);
		FileWriter writer = new FileWriter(file);
		writer.write(newCon);
		writer.close();
		reader.close();
		
		
	}
	
	//Set the first name on the account
	public void setFirstName()throws IOException {
		String tempInfo;//Used to hold user input
		
		if(nameFirst.length() == 0) {
			System.out.println("Please Enter Your First Name");
			tempInfo = keys.nextLine();
			writeToFile(userFileName,tempInfo);
		}
		else {
			System.out.println("Please Enter Your Replacement First Name:");
			tempInfo = keys.nextLine();
			writeChangeUserInfo(userFileName, tempInfo, nameFirst);
		}
		
	}
	//changes the users last name 
	public void setLastName()throws IOException{
		String tempInfo;//Used to hold user input
		
		if(nameLast.length() == 0) {
			System.out.println("Please Enter Your Last Name");
			tempInfo = keys.nextLine();
			writeToFile(userFileName,tempInfo);
		}
		else {
			System.out.println("Please Enter Your Replacement Last Name:");
			tempInfo = keys.nextLine();
			writeChangeUserInfo(userFileName, tempInfo, nameLast);
		}
		
		
	}
	//changes email
	public void setEmail()throws IOException{
		String tempInfo;//Used to hold user input
		
		if(nameFirst.length() == 0) {
			System.out.println("Please Enter Your Email");
			tempInfo = keys.nextLine();
			writeToFile(userFileName,tempInfo);
		}
		else {
			System.out.println("Please Enter Your Replacement Email:");
			tempInfo = keys.nextLine();
			writeChangeUserInfo(userFileName, tempInfo, userEmail);
		}
		
	}
	//returns first name
	public String getFirstName() {
		return nameFirst;
	}
	//returns last name 
	public String getLastName() {
		return nameLast;
	}
	//returns last name
	public String getEmail() {
		return userEmail;
	}
	//returns username 
	public String getUserName() {
		return userName;
	}
	public String getUserFileName() {
		return userFileName;
	}
	
}
