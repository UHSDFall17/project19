import java.util.*; 
import java.io.*;
import java.lang.*;
import java.text.DecimalFormat;

public class Portfolio{
	private int portNum;
	private double portWorth;
	private String[] etf;
	FileInOut fileSystem;
	Stocks stock;
	
	Portfolio(){
		fileSystem = new FileInOut();
		stock = new Stocks();
		etf = new String[3];
	}
	/*this method checks the userFile to see if the file has a portfolio dedicated to it and loads it
	If no portfolio is found, it asks the user for which portfolio they want
	accepts the userinput, and then ammends it to the userFile*/
	
	public boolean checkForPortfolio(String userName) throws IOException{
		portNum = Integer.parseInt(fileSystem.checkForInFile(userName + ".txt", "PORTFOLIO"));
		if(portNum < 0)
			selectPortfolio(userName);
		loadPortfolio();
		portWorth = portfolioAverage();
		return true;
	}
	public double portfolioWorth() {
		return portWorth;
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
		if(portNum == 1) {//Low risk
			etf[0] = "BUD";//Anheuser-Busch 
			etf[1] = "BAC";//Bank of america
			etf[2] = "JNJ";//Johnson and Johnson
		}
		
		if(portNum == 2) {//Moderate Risk
			etf[0] = "CVS";//CVS
			etf[1] = "WBA";//Walgreens
			etf[2] = "WMT";//Walmart
		}
		
		if(portNum == 3) {//High Risk
			etf[0] = "TEVA";//Teva Pharmacetuical 
			etf[1] = "SGMS";//Scientific Games Corp
			etf[2] = "SVU";//Supervalu grocery
		}
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
		for(int i =0; i < etf.length; i ++) {
			etf[i] = "";
		}
		return false;
	}
	
	//returns the etf
	public String[] getEtf() {
		return etf;
	}
	
	public void doWriteToFile(String fileName, String portFol) {
		fileSystem.writeToFile(fileName, portFol);
	}
	
	public double portfolioAverage() {
		double temp = 0.0;
		DecimalFormat df2 = new DecimalFormat("#.##");
		
		for(int i = 0; i < etf.length; i++) {
			temp += Stocks.priceOf(etf[i]);
		}
		return Double.parseDouble(df2.format((temp/etf.length)));
	}
	
	public double portfolioAverage(String[] portArray) {
		double temp = 0.0;
		DecimalFormat df2 = new DecimalFormat("#.##");
		
		for(int i = 0; i < portArray.length; i++) {
			temp += Stocks.priceOf(portArray[i]);
		}
		return Double.parseDouble(df2.format((temp/portArray.length)));
	}
	
}
