import java.util.*; 
import java.io.*;
import java.lang.*;

public class Portfolio{
	private int portNum;
	private String etf;
	FileInOut fileSystem;
	Portfolio(){
		fileSystem = new FileInOut();
	}
	/*this method checks the userFile to see if the file has a portfolio dedicated to it and loads it
	If no portfolio is found, it asks the user for which portfolio they want
	accepts the userinput, and then ammends it to the userFile*/
	
	public boolean checkForPortfolio(String userName) throws IOException{
		portNum = Integer.parseInt(fileSystem.checkForInFile(userName+ ".txt", "PORTFOLIO"));
		if(portNum < 0)
			selectPortfolio(userName);
		
		return true;
	}
	//Allows the user on intial sign up to choose which portfolio they want.
	public void selectPortfolio(String userName) {
		Scanner keys = new Scanner(System.in);
		
		System.out.println("Please select one of the following\n"
				+ "There are three different portfolios you can pick based on risk\n"
				+ "1. Portfolio one will have the least risk, but the least return\n\n"
				+ "2. Portfolio two will have a moderate risk, and a moderate return\n\n"
				+ "3. Portfolio three will have a high risk, but will have a high return\n\n");
		while(true) {
			String temp;
			temp = keys.nextLine();
			try {
				//Checks for integer
				if(0 < Integer.parseInt(temp) && Integer.parseInt(temp) <4) {
					portNum = Integer.parseInt(temp);
					doWriteToFile(userName + ".txt","PORTFOLIO " + temp);
					return;
				}
			}catch(NumberFormatException er)
			  {  }
			System.out.println("That was not an option, please try again!");
			
		}
	}
	
	//loads the eft in based on portfolio, may add more etfs
	public void loadPortfolio() {
		if(portNum == 1)
			etf = "AAPL";
		if(portNum == 2)
			etf = "MUB";
		if(portNum == 3)
			etf = "PSI";
	}
	//sets portNum input string
	public void setPortNum(String input) {
		portNum = Integer.parseInt(input);
	}
	//sets portNum input int
	public void setPortNum(int input) {
		portNum = input;
	}
	public boolean logOut() {
		portNum = -1;
		etf = "";
		return false;
	}
	
	//returns the etf
	public String getEtf() {
		return etf;
	}
	public void doWriteToFile(String fileName, String portFol) {
		fileSystem.writeToFile(fileName, portFol);
	}
	
}
