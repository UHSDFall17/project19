import java.util.*; 
import java.io.*;

public class Users {
	private String userName;
	private String password;
	private String userEmail;
	private String nameFirst;
	private String nameLast;
	
	Scanner keys = new Scanner(System.in);
	File userFile = new File("Users.txt");
	Users()throws IOException {
		checkFile();
	}
	//checkFile checks to see if the Users.txt file is there and creates one if needed
	public void checkFile() throws IOException {
		if(!userFile.exists()) {
			userFile.createNewFile();
		}
		
		
	}
	
	//checks the user folder to see if userName is there 
	public boolean checkForName(String name){
        boolean isThere = false;
		try {
            BufferedReader b = new BufferedReader(new FileReader(userFile));
            String readLine = "";
            while ((readLine = b.readLine()) != null) {
                if(readLine.equals(name)) {
                	isThere = true;
                } 
           
            }
            b.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return isThere;
	}
	
	//This function asks for the user name, then calls check for name
	public void setUserName()throws IOException{
		String tempUserName = "";
		boolean isThere = true;
		checkFile();
		while(isThere) {
			System.out.println("Enter a userName:");
			tempUserName = keys.nextLine();
			
			isThere = checkForName(tempUserName);
			if(isThere)
				System.out.println("That user name is already taken, please try another!");
			
		}
		userName = tempUserName;
		writeToFile("Users.txt", userName);
	}
	
	//appends to the end of a file, no matter which file
	public void writeToFile(String fileName, String content) {
		
		try(FileWriter fw = new FileWriter(fileName, true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
			    out.println(content);
			} catch (IOException e) {
			    
			}

	}

	


	
	public void setRealName() {
		System.out.println("Please Enter You First Name");
		
	}
	public String getUserName() {
		return userName;
	}
	
	
	
}
