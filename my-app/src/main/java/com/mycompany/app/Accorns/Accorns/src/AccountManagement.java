import java.io.IOException;
import java.text.DecimalFormat;

public class AccountManagement extends Users {
	
	FileInOut fileSystem;
	private String preinvestedAmount;
	AccountManagement() throws IOException{
		super();
		fileSystem = new FileInOut();
			}
	public String retreveAccountBalance() {
		return (preinvestedAmount = fileSystem.checkForInFile(getUserName() + ".txt", "PREBALANCE"));
	}
	public void addMoney(double addAmount)throws IOException {
		double workingNum = 0.0;
		DecimalFormat df2 = new DecimalFormat(".##");
		workingNum = Double.parseDouble(preinvestedAmount);
		workingNum = workingNum + addAmount;
		fileSystem.replaceInFile(getUserName() + ".txt", "PREBALANCE " + df2.format(workingNum), "PREBALANCE " + preinvestedAmount);
		preinvestedAmount = df2.format(workingNum);
	}
	public void addMoney()throws IOException {
		DecimalFormat df2 = new DecimalFormat(".##");
		String temp;
		System.out.println("Please Enter The Inital Dollar Amount You Would Like to Add to Your Account:");
		while(true) {
			temp = keys.nextLine();
			try {
				//Checks for integer
				if(0 <= Double.parseDouble(temp)) {
					addMoney(Double.parseDouble(df2.format(Double.parseDouble(temp))));
					return;
				}
			}catch(NumberFormatException er)
			  {  }
			System.out.println("Make sure it is a positive amount, please try again!");
			
		}
	}
	
	public void removeMoney() {
		
	}
	public boolean logOut() {
		super.logOut();
		preinvestedAmount = "";
		return false;
	}
	
}
