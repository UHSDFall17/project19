import java.util.*; 
import java.io.*;
import java.lang.*
;public class Users {
	private String userName;
	private String userEmail;
	private String nameFirst;
	private String nameLast;
	private String userFileName;
	
	Scanner keys = new Scanner(System.in);
	Users()throws IOException {
		checkFile("Users.txt");
		userName = "";
		userEmail = "";
		nameFirst = "";
		nameLast = "";
	}
	Users(String user)throws IOException {
		checkFile("Users.txt");
		userName = "user";
		userEmail = "";
		nameFirst = "";
		nameLast = "";
		createUserFileName(user);
		loadUserInfo(user);
		outUserInfo();
	}
	//checkFile checks to see if the Users.txt file is there and creates one if needed
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
	public void outUserInfo() {
		System.out.println(userName);
		System.out.println(nameFirst);
		System.out.println(nameLast);
		System.out.println(userEmail);
	}
	public void createUserFileName() {
		
		StringBuilder tempUserFileName = new StringBuilder(userName);
		tempUserFileName.append(".txt");
		userFileName = tempUserFileName.toString();
	}
	public void createUserFileName(String name) {
		
		StringBuilder tempUserFileName = new StringBuilder(name);
		tempUserFileName.append(".txt");
		userFileName = tempUserFileName.toString();
	}
	
	//checks the user folder to see if userName is there 
	public boolean checkForName(String fileName, String name){
		boolean isThere = false;
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
	public boolean checkLogIn(String fileName, String name, String passWord){
        boolean isThere = false;
        
        name = name.toUpperCase();
        File file = new File(fileName);
		try {
            BufferedReader b = new BufferedReader(new FileReader(file));
            String readLine = "";
            while ((readLine = b.readLine()) != null) {
                if(readLine.equals(name)) { 
                	if((readLine = b.readLine()) != null){
                		if(readLine.equals(passWord)) {
                			System.out.println("Login was Successful!");
                			b.close();
                			return true;
                		}
                		else {
                			System.out.println("That is the wrong password\nPlease try again!");
                			b.close();
                			return false;
                		}
            
                	}
                }	
                		
                readLine = b.readLine();
           
            }
            b.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return isThere;
	}
	/*This function Requests a user name then writes to a file then asks 
	 * for a password which it appends under the userName
	 * Example:
	 * UserName1
	 * Password1
	 * UserName2
	 * Password2
	 */
	
	public void setUserName()throws IOException{
		String tempInfo= "";
		boolean isThere = true;
		checkFile("Users.txt");
		
		System.out.println("Type 'Exit' to return to the home menu");
		
		while(isThere) {
			System.out.println("Enter a userName:");
			tempInfo = keys.nextLine().toUpperCase();
			
			if(tempInfo.equals("EXIT"))
				return;
			isThere = checkForName("Users.txt",tempInfo);
			if(isThere)
				System.out.println("That user name is already taken, please try another!");
			
		}
		
		userName = tempInfo;
		writeToFile("Users.txt", userName);
		
		System.out.println("Please enter a password:");
		tempInfo = keys.nextLine();
		writeToFile("Users.txt", tempInfo);
		
		StringBuilder tempUserFileName = new StringBuilder(userName);
		tempUserFileName.append(".txt");
		tempInfo = tempUserFileName.toString();
		/*creates a user file that will fill
		 * with users information
		 */
		checkFile(tempInfo);
		System.out.println("Thank You!");
		
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
	public void setRealNameF()throws IOException {
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
	public String getUserName() {
		return userName;
	}
	
	
	
}
