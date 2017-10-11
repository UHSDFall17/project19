import java.util.*; 
import java.io.*;
import java.lang.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
public class Users {
	private String userName;
	private String userEmail;
	private String nameFirst;
	private String nameLast;
	private boolean isLoggedIn;
	private Logger log;
	private SignUp signer;
	public FileInOut fileSystem;
	private Portfolio portfolio;
	Scanner keys = new Scanner(System.in);
	Users()throws IOException {
		fileSystem = new FileInOut();
		checkFile("Users.txt");
		log = new Logger();
		signer = new SignUp();
		userName = "";
		userEmail = "";
		nameFirst = "";
		nameLast = "";
		isLoggedIn = false;
		log = new Logger();
		portfolio = new Portfolio();
	}
	Users(String user)throws IOException {
		checkFile("Users.txt");
		log = new Logger();
		userName = user;
		userEmail = "";
		nameFirst = "";
		nameLast = "";
		loadUserInfo(user);
		outUserInfo();
	}
	/*First time sign it, creates user name, password
	 * File to store information, then files that file
	 * with first name, last name, and email, then loads everything into 
	 * the class
	 */
	public boolean firstTimeSignup()throws IOException{
		if(signer.createUserName())
			userName = signer.getUserName();
		else {
			clearUserInfo();
			return false;
		}
		
		setFirstName();
		setLastName();
		setEmail();
		loadUserInfo(userName);
		initBank();
		portfolio.checkForPortfolio(userName);
		isLoggedIn = true;
		return isLoggedIn;
	}
	public boolean logIn() {
		log.logIn();
		userName = log.getUserName();
		isLoggedIn = log.checkLogIn();
		loadUserInfo(userName);
		return log.checkLogIn();
		
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
		File file = new File(userName +".txt");
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
		isLoggedIn = false;
	}
	//used to output users info to command line
	public void outUserInfo() {
		System.out.println(userName);
		System.out.println(nameFirst);
		System.out.println(nameLast);
		System.out.println(userEmail);
	}
	
	
	
	
	//returns if the user is logged in based on everything loaded in
	public boolean checkLogIn() {
		isLoggedIn = log.checkLogIn();
		return isLoggedIn;
	}
	public boolean checkLogIn(String decider) {
		return isLoggedIn;
	}

	
	
	//Set the first name on the account
	public void setFirstName()throws IOException {
		String tempInfo;//Used to hold user input
		
		if(nameFirst.length() == 0) {
			System.out.println("Please Enter Your First Name");
			tempInfo = keys.nextLine();
			fileSystem.writeToFile(userName + ".txt",tempInfo);
		}
		else {
			System.out.println("Please Enter Your Replacement First Name:");
			tempInfo = keys.nextLine();
			fileSystem.replaceInFile(userName + ".txt", tempInfo, nameFirst);
		}
		
	}
	//changes the users last name 
	public void setLastName()throws IOException{
		String tempInfo;//Used to hold user input
		
		if(nameLast.length() == 0) {
			System.out.println("Please Enter Your Last Name");
			tempInfo = keys.nextLine();
			fileSystem.writeToFile(userName + ".txt",tempInfo);
		}
		else {
			System.out.println("Please Enter Your Replacement Last Name:");
			tempInfo = keys.nextLine();
			fileSystem.replaceInFile(userName + ".txt", tempInfo, nameLast);
		}
		
		
	}
	//changes email
	public void setEmail()throws IOException{
		String tempInfo;//Used to hold user input
		
		if(nameFirst.length() == 0) {
			System.out.println("Please Enter Your Email");
			tempInfo = keys.nextLine();
			fileSystem.writeToFile(userName + ".txt",tempInfo);
		}
		else {
			System.out.println("Please Enter Your Replacement Email:");
			tempInfo = keys.nextLine();
			fileSystem.replaceInFile(userName + ".txt", tempInfo, userEmail);
		}
		
	}
	public void initBank() {
		DecimalFormat df2 = new DecimalFormat(".##");
		String temp;
		System.out.println("Please Enter The Inital Dollar Amount You Would Like to Add to Your Account:");
		while(true) {
			temp = keys.nextLine();
			try {
				//Checks for integer
				if(0 <= Double.parseDouble(temp)) {
					fileSystem.writeToFile(getUserName() + ".txt","PREBALANCE " + df2.format(Double.parseDouble(temp)));
					return;
				}
			}catch(NumberFormatException er)
			  {  }
			System.out.println("Make sure it is a positive amount, please try again!");
			
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
	public boolean logOut() {
		clearUserInfo();
		log.logOut();
		portfolio.logOut();
		return false;
	}
	
	
}
