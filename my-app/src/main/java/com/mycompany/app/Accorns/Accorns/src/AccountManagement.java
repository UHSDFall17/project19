import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class AccountManagement extends Users {
	
	FileInOut fileSystem;
	private double preinvestedAmount;
	private double investedAmount;
	private double profit;
	private double portfolioWorth;
	private double profitPer;
	DecimalFormat df2;
	AccountManagement() throws IOException{
		super();
		df2 = new DecimalFormat("#.##");
		profit = 0;
		investedAmount = 0;
		portfolioWorth = 0;
		profitPer = 0;
		fileSystem = new FileInOut();
	}
	
	public double retrevePreinvestedBalance()throws IOException {

		
		return (preinvestedAmount = Double.parseDouble(fileSystem.checkForInFile(getUserName() + ".txt", "PREBALANCE")));
	}
	
	public double retreveInvestedBalance()throws IOException {
		String[] investments = fileSystem.checkForInvest(getUserName() +".txt");
		DecimalFormat df2 = new DecimalFormat("#.##");
		investedAmount = 0.0;
		
		for(int i = 1; i < investments.length; i++) {
			
			investedAmount +=  Double.parseDouble(investments[i+1]);
			i += 2;
		}
		return Double.parseDouble(df2.format(investedAmount));
	}
	
	public double retreveCalPortfolioWorth()throws IOException {
		return (calPortfolioWorth());
	}
	public double retreveProfitPercent()throws IOException{
		DecimalFormat df2 = new DecimalFormat("#.##");
		
		return Double.parseDouble(df2.format(100*calChange(investedAmount, calPortfolioWorth())));
	}
	
	public double retreveProfit()throws IOException{
		if(fileSystem.checkForInvest(getUserName() +".txt") == null)
			return 0.0;
		String[] investments = fileSystem.checkForInvest(getUserName() +".txt");
		DecimalFormat df2 = new DecimalFormat("#.##");
		profit = 0;
		
		for(int i = 1; i < investments.length; i++) {
			
			profit +=  ((Double.parseDouble(investments[i+1]) * calChange(Double.parseDouble(investments[i]), returnPortfolioAverage())));
			i += 2;
		}
		return Double.parseDouble(df2.format(profit));
	}
	
	
	public void loadAmounts() throws IOException {
		retrevePreinvestedBalance();
		retreveInvestedBalance();
	}
	
	public void addMoney(double addAmount, double startingBal)throws IOException {
		double workingNum = 0.00;
		DecimalFormat df2 = new DecimalFormat("#.##");
		workingNum = startingBal;
		workingNum = workingNum + addAmount;
		
		fileSystem.replaceInFile(getUserName() + ".txt", "PREBALANCE "+ df2.format(workingNum), "PREBALANCE " + df2.format(startingBal));
		preinvestedAmount = Double.parseDouble(df2.format(workingNum));
		
	}
	
	public void addMoneyInv(double addAmount, double startingBal)throws IOException {
		double workingNum = 0.00;
		DecimalFormat df2 = new DecimalFormat("#.##");
		workingNum = startingBal;
		workingNum = workingNum + addAmount;
		
		fileSystem.replaceInFile(getUserName() + ".txt", "INVESTEDBAL "+ df2.format(workingNum), "INVESTEDBAL " + df2.format(startingBal));
		investedAmount = Double.parseDouble(df2.format(workingNum));
	}
	
	public void addMoneyPre()throws IOException {
		
		DecimalFormat df2 = new DecimalFormat("#.##");
		loadAmounts();
		String temp;
		
		System.out.println("Please Enter The Inital Dollar Amount You Would Like to Add to Your Account:");
		
		while(true) {
			temp = keys.nextLine();
			try {
				//Checks for integer
				if(0 <= Double.parseDouble(temp)) {
					addMoney(Double.parseDouble(df2.format(Double.parseDouble(temp))), preinvestedAmount);
					return;
				}
			}catch(NumberFormatException er)
			  {  }
			System.out.println("Make sure it is a positive amount, please try again!");
			
		}
	}
	
	
	public void removeMoney(double subAmount)throws IOException {
		
		double workingNum = 0.00;
		DecimalFormat df2 = new DecimalFormat("#.##");
		workingNum = preinvestedAmount;
		workingNum = workingNum - subAmount;
		fileSystem.replaceInFile(getUserName() + ".txt", "PREBALANCE " + df2.format(workingNum), "PREBALANCE " + df2.format(preinvestedAmount));
		preinvestedAmount = Double.parseDouble(df2.format(workingNum));
			
	}
	
	
	public void invest()throws IOException{
		loadAmounts();
		DecimalFormat df2 = new DecimalFormat("#.##");
		String temp;
		System.out.println("Preinvested Balance: $" + preinvestedAmount);
		System.out.println("Please Enter The Inital Dollar Amount You Would Like to invest from Your Account:");
		while(true) {
			temp = keys.nextLine();
			try {
				//Checks for Double
				if(0 <= Double.parseDouble(temp) && Double.parseDouble(temp) <= preinvestedAmount) {
					//Removes the money from the preinvested balance
					removeMoney(Double.parseDouble(df2.format(Double.parseDouble(temp))));
					
					//Adds the money to the invested Balance
					addMoneyInv(Double.parseDouble(df2.format(Double.parseDouble(temp))), investedAmount);

					//Adds the Date and the invested amount(One time not total) to the bottom of the text file
					fileSystem.writeToFile(getUserName() + ".txt", new SimpleDateFormat("MM-dd-yyyy").format(new Date()) 
											+ " " + returnPortfolioAverage() + " " + df2.format(Double.parseDouble(temp)));
					return;
				}
			}catch(NumberFormatException er)
			  {  }
			System.out.println("Make sure it is a positive amount, please try again!");
			
		}
	}
	public double returnPortfolioAverage() {
		return portfolio.portfolioWorth();
	}
	
	public void showPortfolioBreakdown() {
		String[] temp = portfolio.getEtf();
		for(int i = 0; i < temp.length; i ++) {
			System.out.println("Stock Symbol: " + temp[i] + "\n"+
							   "	Stock Name: " + Stocks.nameOf(temp[i]) +"\n"+ 
							   "		Stock Price: $" + Stocks.priceOf(temp[i]));
			
		}
	}
	
	public double calPortfolioWorth() throws IOException {
		if(fileSystem.checkForInvest(getUserName() +".txt") == null)
			return 0.0;
		String[] investments = fileSystem.checkForInvest(getUserName() +".txt");
		DecimalFormat df2 = new DecimalFormat("#.##");
		portfolioWorth = 0;
		
		for(int i = 1; i < investments.length; i++) {
			
			portfolioWorth +=  (Double.parseDouble(investments[i+1]) + (Double.parseDouble(investments[i+1]) * calChange(Double.parseDouble(investments[i]), returnPortfolioAverage())));
			i += 2;
		}
		
		return Double.parseDouble(df2.format(portfolioWorth));
	}
	
	public double calChange(double orginalAmount, double newAmount) {
		if(orginalAmount == 0)
			return 0;
		
		return((newAmount - orginalAmount)/ orginalAmount);
	}
	
	
	public void currentInvestmentHistory() throws IOException{
		String[] temp = fileSystem.checkForInvest(getUserName() + ".txt");
		System.out.println("Investment History");
		for(int i = 0; i < temp.length; i++) {
			System.out.println("Investment Date: " + temp[i]
								+ "\nWorth of Portfolio Per Stock: $" + temp[i+1]
								+ "\nInvestment Amount: $" + temp[i + 2] + "\n");
			i += 2;
		}
	}
	
	public void cashOutPortfolio()throws IOException {
		Scanner keys = new Scanner(System.in);
		double cashOutAmount = 0.0;
		DecimalFormat df2 = new DecimalFormat("#.##");
		boolean inputAccepted = false;
		String content = "";
		String temp = "";
		String[] investments = fileSystem.checkForInvest(getUserName() + ".txt");
		
		System.out.println("Enter the dollar amount you would like to pull from your portfolio\n"
							+ "Please make sure it is less than your portfolios value\n"
							+ "Portfolio Value: $" + calPortfolioWorth());
		
		
		while(!inputAccepted) {
		    temp = keys.nextLine();
			try {
				//Checks for double and correct amount
				if(0 <= Double.parseDouble(temp) && Double.parseDouble(temp) <= calPortfolioWorth() ) {
					
					inputAccepted = true;
					
				}
			}catch(NumberFormatException er){}
			
			if(!inputAccepted)
				System.out.println("Make sure it is a positive amount that is less than your full portfolio value, please try again!");
		}
			cashOutAmount = Double.parseDouble(df2.format(Double.parseDouble(temp)));
			
			double calCashOut = cashOutAmount;
			
			//Date invested----Date cashed Out-----Worth Per stock when bought-------Worth per stock now-------Amount invested-----Amount Worth Now
			for(int i = 0; i < investments.length && calCashOut > 0; i ++) {
				String date = investments[i];
				double ogPricePer = Double.parseDouble(investments[i+1]);
				double ogAmount = Double.parseDouble(investments[i+2]);
				double worth = Double.parseDouble(df2.format(ogAmount + (ogAmount * calChange(ogPricePer, portfolio.portfolioWorth()))));
				
				calCashOut -= worth;
				
				
				fileSystem.deleteUnderSubheading(getUserName() + ".txt", "INVESTMENTS");
				
				if(calCashOut < 0) {
					//This is used to hold converted og value
					double newWorth = ((-calCashOut)+ (-calCashOut * calChange(portfolio.portfolioWorth(), ogPricePer)));
					
					System.out.println(newWorth);
					System.out.println(newWorth + (newWorth * calChange(ogPricePer, portfolio.portfolioWorth())));
					content = date + " " + ogPricePer + " " + df2.format(newWorth);
					
					//Used to hold converted og value
					newWorth = ((cashOutAmount) + ((cashOutAmount) * calChange(portfolio.portfolioWorth(), ogPricePer)));
					System.out.println(newWorth);
					fileSystem.addToSubheading(getUserName() + ".txt", content , "INVESTMENTS");
					content = date + " " + new SimpleDateFormat("MM-dd-yyyy").format(new Date()) + " " + ogPricePer
							+ " " + portfolio.portfolioWorth() + " " +  df2.format(newWorth) + " "
							+ df2.format(calCashOut + worth);
					
				}
				else {
					
					content = date + " " + new SimpleDateFormat("MM-dd-yyyy").format(new Date()) + " " + ogPricePer
							+ " " + portfolio.portfolioWorth() + " " +  df2.format(ogAmount) + " " + df2.format(worth);
				}
					fileSystem.addToSubheading(getUserName() + ".txt", content, "PAST_INVESTMENTS");
				
					i +=2;
			}
			addMoney(cashOutAmount, preinvestedAmount);
			addMoneyInv(-cashOutAmount, investedAmount);
		
	}
	
	public void addPurchases() throws IOException {
		Scanner keys = new Scanner(System.in);
		String desc = "";
		String temp = "";
		double convert = 0.0;
		double convert2 = 0.0;
		boolean test = true;
		boolean test2= true;
		
		while(test) {
			System.out.println("Please add the description of the purchase\nType \"Exit\" to return to the home menu.");
			
			temp = keys.nextLine();
			if(temp.toUpperCase().equals("EXIT"))
				return;
			
			desc = temp;
			System.out.println("Please Enter the Price of the purchase:");
			while(test2) {
				temp = keys.nextLine();
				try {
					//Checks for Double
					if(0 <= Double.parseDouble(temp)) {
					
						addPurchases(temp, desc);
					
						test2 = false;
					
					}
				}catch(NumberFormatException er)
				{  }
				
				if(temp.toUpperCase().equals("EXIT"))
					return;
				
				if(test2)
					System.out.println("Make sure it is a positive amount, please try again!");
			
			}
			
			
			
			boolean whileCheck = true;
			
			while(whileCheck) {
				System.out.println("Would you like to add another purchase?\ny/n");
				temp = keys.nextLine();
				
				if(temp.toUpperCase().equals("Y")) {
					test2 = true;
					whileCheck = false;
				}
				else if(temp.toUpperCase().equals("N")) {
					test = false;
					whileCheck = false;
				}
				else
					System.out.println("That was not an option, please try again");
			}	
			
		}
	}
	
	public void addPurchases(String temp, String desc)throws IOException {
		
		double convert = Math.ceil(Double.parseDouble(temp));
		double convert2 = 0.0;
		
		if(convert == Double.parseDouble(temp))
			convert2 = 0;
		else
			convert2 = convert - Double.parseDouble(temp);
		
		addMoney(Double.parseDouble(df2.format(convert2)), preinvestedAmount);
		fileSystem.addToSubheading(getUserName() + ".txt", (new SimpleDateFormat("MM-dd-yyyy").format(new Date()) 
								+ " " + df2.format(Double.parseDouble(temp)) + " "+ df2.format(convert2)) , "PURCHASES");
		fileSystem.addToSubheading(getUserName() + ".txt", desc, "PURCHASES");
	
	}
	
	public boolean logOut() {
		super.logOut();
		preinvestedAmount = 0.0;
		return false;
	}
	
}
