import java.io.IOException;
import java.text.DecimalFormat;

public class AccountManagement extends Users {
	
	FileInOut fileSystem;
	private String preinvestedAmount;
	private String investedAmount;
	private String profit;
	AccountManagement() throws IOException{
		super();
		fileSystem = new FileInOut();
	}
	
	public String retrevePreinvestedBalance() {
		return (preinvestedAmount = fileSystem.checkForInFile(getUserName() + ".txt", "PREBALANCE"));
	}
	
	public String retreveInvestedBalance() {
		return (investedAmount = fileSystem.checkForInFile(getUserName() + ".txt", "INVESTEDBAL"));
	}
	
	public String retreveProfit() {
		return ("0.00");
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
		System.out.println(preinvestedAmount);
		fileSystem.replaceInFile(getUserName() + ".txt", "PREBALANCE "+ df2.format(workingNum), "PREBALANCE " + df2.format(startingBal));
		preinvestedAmount = df2.format(workingNum);
		System.out.println(preinvestedAmount);
	}
	
	public void addMoneyInv(double addAmount, double startingBal)throws IOException {
		double workingNum = 0.00;
		DecimalFormat df2 = new DecimalFormat("#.##");
		workingNum = startingBal;
		workingNum = workingNum + addAmount;
		System.out.println("In money Inv " + startingBal);
		fileSystem.replaceInFile(getUserName() + ".txt", "INVESTEDBAL "+ df2.format(workingNum), "INVESTEDBAL " + df2.format(startingBal));
		investedAmount = df2.format(workingNum);
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
					addMoney(Double.parseDouble(df2.format(Double.parseDouble(temp))), Double.parseDouble(preinvestedAmount));
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
		workingNum = Double.parseDouble(preinvestedAmount);
		workingNum = workingNum - subAmount;
		fileSystem.replaceInFile(getUserName() + ".txt", "PREBALANCE " + df2.format(workingNum), "PREBALANCE " + preinvestedAmount);
		preinvestedAmount = df2.format(workingNum);
			
	}
	
	public void invest()throws IOException{
		loadAmounts();
		DecimalFormat df2 = new DecimalFormat("#.##");
		String temp;
		//System.out.println(investedAmount);
		
		System.out.println("Please Enter The Inital Dollar Amount You Would Like to invest from Your Account:");
		while(true) {
			temp = keys.nextLine();
			try {
				//Checks for integer
				if(0 <= Double.parseDouble(temp) && Double.parseDouble(temp) <= Double.parseDouble(preinvestedAmount)) {
					removeMoney(Double.parseDouble(df2.format(Double.parseDouble(temp))));
					System.out.println("In invest Method " + investedAmount);
					addMoneyInv(Double.parseDouble(df2.format(Double.parseDouble(temp))), Double.parseDouble(investedAmount));
					return;
				}
			}catch(NumberFormatException er)
			  {  }
			System.out.println("Make sure it is a positive amount, please try again!");
			
		}
	}
	public double returnPortfolioAverage() {
		return portfolio.portfolioAverage();
	}
	
	public void showPortfolioBreakdown() {
		String[] temp = portfolio.getEtf();
		for(int i = 0; i < temp.length; i ++) {
			System.out.println("Stock Symbol: " + temp[i] + "\n"+
							   "	Stock Name: " + Stocks.nameOf(temp[i]) +"\n"+ 
							   "		Stock Price: $" + Stocks.priceOf(temp[i]));
			
		}
	}
	
	public boolean logOut() {
		super.logOut();
		preinvestedAmount = "";
		return false;
	}
	
}
