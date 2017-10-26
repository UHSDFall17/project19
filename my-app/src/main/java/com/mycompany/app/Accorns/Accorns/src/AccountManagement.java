import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountManagement extends Users {
	
	FileInOut fileSystem;
	private double preinvestedAmount;
	private double investedAmount;
	private double profit;
	private double portfolioWorth;
	private double profitPer;
	AccountManagement() throws IOException{
		super();
		fileSystem = new FileInOut();
	}
	
	public double retrevePreinvestedBalance() {
		return (preinvestedAmount = Double.parseDouble(fileSystem.checkForInFile(getUserName() + ".txt", "PREBALANCE")));
	}
	
	public double retreveInvestedBalance() {
		return (investedAmount = Double.parseDouble(fileSystem.checkForInFile(getUserName() + ".txt", "INVESTEDBAL")));
	}
	
	public double retreveCalPortfolioWorth()throws IOException {
		return (calPortfolioWorth());
	}
	public double retreveProfitPercent()throws IOException{
		String[] investments = fileSystem.checkForInvest(getUserName() +".txt");
		DecimalFormat df2 = new DecimalFormat("#.######");
		
		
		return Double.parseDouble(df2.format(calChange(investedAmount, calPortfolioWorth())));
	}
	
	public double retreveProfit()throws IOException{
		String[] investments = fileSystem.checkForInvest(getUserName() +".txt");
		DecimalFormat df2 = new DecimalFormat("#.##");
		profit = 0;
		
		for(int i = 1; i < investments.length; i++) {
			
			profit +=  ((Double.parseDouble(investments[i+1]) * calChange(Double.parseDouble(investments[i]), returnPortfolioAverage())));
			i += 2;
		}
		return Double.parseDouble(df2.format(profit));
	}
	
	
	public void loadAmounts() {
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
		return((newAmount - orginalAmount)/ orginalAmount);
	}
	public void investmentHistory() throws IOException{
		String[] temp = fileSystem.checkForInvest(getUserName() + ".txt");
		System.out.println("Investment History");
		for(int i = 0; i < temp.length; i++) {
			System.out.println("Investment Date: " + temp[i]
								+ "\nWorth of Portfolio Per Stock: $" + temp[i+1]
								+ "\nInvestment Amount: $" + temp[i + 2] + "\n");
			i += 2;
		}
	}
	
	public boolean logOut() {
		super.logOut();
		preinvestedAmount = 0.0;
		return false;
	}
	
}
