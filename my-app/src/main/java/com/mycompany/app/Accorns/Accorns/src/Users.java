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
	protected Portfolio portfolio;
	Scanner keys = new Scanner(System.in);
	Users()throws IOException {
		fileSystem = new FileInOut();
		checkForFile("Users.txt");
		log = new Logger();
		
		userName = "";
		userEmail = "";
		nameFirst = "";
		nameLast = "";
		isLoggedIn = false;
		log = new Logger();
		portfolio = new Portfolio();
	}
	Users(String user)throws IOException {
		checkForFile("Users.txt");
		log = new Logger();
		userName = user;
		userEmail = "";
		nameFirst = "";
		nameLast = "";
		loadUserInfo(user);
		
	}
	/*First time sign it, creates user name, password
	 * File to store information, then files that file
	 * with first name, last name, and email, then loads everything into 
	 * the class
	 */
	public boolean firstTimeSignup()throws IOException{
		if(log.signUp())
			userName = log.getUserName();
		else {
			clearUserInfo();
			return false;
		}
		
		setFirstName();//Sets first name
		setLastName();//Sets last name
		setEmail();//sets email
		
		//redundecny to ensure that everything is loaded
		loadUserInfo(userName);
		
		//initizes the banking in file
		initBank();
		
		//creates the portfolio and loads the portfolio
		portfolio.checkForPortfolio(userName);
		
		//Header to declare in file when investments start
		fileSystem.writeToFile(userName + ".txt", "INVESTMENTS");
		isLoggedIn = true;
		return isLoggedIn;
	}
	
	//Calls the logger login method, and then gets the userName and and logged in status
	//
	public boolean logIn() throws IOException {
		if(log.logIn()) {
			//loads all of the users information, including updating logged in status,
			//userName, user info, and portfolio info
			userName = log.getUserName();
			isLoggedIn = log.checkLogIn();
			loadUserInfo(userName);
			System.out.println("One Moment Please...");
			portfolio.checkForPortfolio(userName);
			return log.checkLogIn();
		}
		return false;
		
	}
	
	//checkForFile checks to see if file is there and creates one if needed
	public void checkForFile(String fileName) throws IOException {
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
		File file = new File(System.getProperty("user.home") + File.separator + "Documents" 
							 + File.separator + "Accorns_Accounts" + File.separator +  userName + ".txt");
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

	
	
	
	//returns if the user is logged in based on everything loaded in
	public boolean checkLogIn() {
		isLoggedIn = log.checkLogIn();
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
	
	//initilzes the bank 
	public void initBank() {
		DecimalFormat df2 = new DecimalFormat("#.##");
		String temp;
		System.out.println("Please Enter The Inital Dollar Amount You Would Like to Add to Your Account:");
		while(true) {
			temp = keys.nextLine();
			try {
				//Checks for integer
				if(0 <= Double.parseDouble(temp)) {
					fileSystem.writeToFile(getUserName() + ".txt","PREBALANCE " + df2.format(Double.parseDouble(temp)));
					fileSystem.writeToFile(getUserName() + ".txt", "INVESTEDBAL " + df2.format(0));
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
	
	public static void promptEnterKey(){
		   System.out.println("Press \"ENTER\" to continue...");
		   Scanner scanner = new Scanner(System.in);
		   scanner.nextLine();
		}
	
	
}
