import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Accorns {
	private static AccountManagement user;
	private static boolean loggedIn;
	public static void main(String[] args)throws IOException {
			FileInOut fileSystem = new FileInOut();
			fileSystem.writeToFile("TEST.txt", "IT WORKED");
			user = new AccountManagement();
			signupLoginMenu();
	}
	//log in menu
	public static void signupLoginMenu() throws IOException{
		loggedIn = false;
		String select;
		Scanner keys = new Scanner(System.in);
		
		do{
			System.out.println("Enter the number of the command to excute:\n"
					+ "1.Log In\n2.Sign Up\n3.Exit");
			select = keys.nextLine();
			
			
			switch(select) {
			
				case "1":
					clearConsole();
					loggedIn = user.logIn();
					promptEnterKey();
					clearConsole();
					break;
			
				case "2":
					clearConsole();
					loggedIn = user.firstTimeSignup();
					clearConsole();
					break;
				
				case "3":
					clearConsole();
					System.exit(0);
					break;
				default:
					System.out.println("Sorry, that was not an option\n please try again\n");
					break;
			}
			
		
		}while(!loggedIn);
		
		signedInMenu();
	}
	
	public static void signedInMenu()throws IOException {
		
		String select = "";
		Scanner keys = new Scanner(System.in);
		while(loggedIn) {
			System.out.println("Welcome " + user.getFirstName()
								+ "\nPreinvested Balance: $" + user.retrevePreinvestedBalance()
								+ "\nInvested Amount: $" + user.retreveInvestedBalance()
								+ "\nCurrent Portfolio Worth Per Stock: $" + user.returnPortfolioAverage()
								+ "\nCurrent Entire Portfolio Worth: $" + user.retreveCalPortfolioWorth()
								+ "\nProfit: $" + user.retreveProfit()
								+ "\nProfit Increase Percentage: %" + user.retreveProfitPercent());
			
			System.out.println("1: Add To Money Account From Bank\n"
								+ "2: Add Purchases From Card\n"
								+ "3: Invest Money\n"
								+ "4: Sell Stocks\n"
								+ "5: Portfolio Menu\n"
								+ "6: Log Out");
			
			select = keys.nextLine();
			switch(select) {
				case "1":
					clearConsole();
					user.addMoneyPre();
					promptEnterKey();
					clearConsole();
					break;
		
				case "2":
					clearConsole();
					user.addPurchases();
					promptEnterKey();
					clearConsole();
					break;
					
				case "3":
					clearConsole();
					user.invest();
					clearConsole();
					break;
					
				case "4":
					clearConsole();
					user.cashOutPortfolio();
					clearConsole();
					break;
				
				case "5":
					clearConsole();
					portfolioMenu();
					clearConsole();
					break;
				
				case "6":
					clearConsole();
					loggedIn = user.logOut();
					System.out.println("Log out Successful!");
					promptEnterKey();
					clearConsole();
					break;
					
				default:
					System.out.println("Sorry, that was not an option\nPlease try again\n");
					break;
			}
		}
		signupLoginMenu();
		
		
	}
	public static void portfolioMenu() throws IOException{
		boolean inMenu = true;
		Scanner keys = new Scanner(System.in);
		String select;
		while(inMenu) {
			System.out.println("Portfolio Menu:\n\n"
								+ "Welcome " + user.getFirstName() 
								+ "\nUnInvested Balance: $" + user.retrevePreinvestedBalance()
								+ "\nInvested Amount: $" + user.retreveInvestedBalance()
								+ "\nCurrent Portfolio Worth Per Stock: $" + user.returnPortfolioAverage()
								+ "\nCurrent Entire Portfolio Worth: $" + user.retreveCalPortfolioWorth()
								+ "\nProfit: $" + user.retreveProfit()
								+ "\nProfit Increase Percentage: %" + user.retreveProfitPercent());
			
			System.out.println("1: Portfolio Breakdown\n"
								+ "2: Current Investment History\n"
								+ "3: Sold Investment History\n"
								+ "4: Back To Main Menu");
			
			select = keys.nextLine();
			switch(select) {
				case "1":
					clearConsole();
					user.showPortfolioBreakdown();
					promptEnterKey();
					clearConsole();
					break;
		
				case "2":
					clearConsole();
					user.currentInvestmentHistory();
					promptEnterKey();
					clearConsole();
					break;
					
				case "3":
					clearConsole();
					//user.cashOutPortfolio();
					clearConsole();
					break;
					
				case "4":
					clearConsole();
					inMenu = false;
					clearConsole();
					break;
				default:
					System.out.println("Sorry, that was not an option\nPlease try again\n");
					break;
			}
		}
	}
	public static void clearConsole() {
		for(int i = 0; i < 50; i++)
			System.out.println("\n");
	}


public static void promptEnterKey(){
	   System.out.println("Press \"ENTER\" to continue...");
	   Scanner scanner = new Scanner(System.in);
	   scanner.nextLine();
	}
}