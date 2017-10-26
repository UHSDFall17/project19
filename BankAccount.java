package com.mycompany.app.Accorns.src;

public class BankAccount {
	private int AccountNumber;
	private int RoutingNumber;
	private double balance;
	private String bankPin;
	
	public BankAccount(int acc, int routing, double bal, String ID) {
		AccountNumber = acc;
		RoutingNumber = routing;
		balance = bal;
		bankPin = ID;
		
	}

	public int getAccountNumber() {
		return this.AccountNumber;
	}
	public int getRoutingNumber() {
		return this.RoutingNumber;
		
	}
	public double getBalance() {
		return this.balance;
	}
	public String getPin() {
		return this.bankPin;
	}
	
	public void addFunds(double toAdd) {
		if(toAdd >= 0)
		balance+=toAdd;
		else
			System.out.println("Cannot add a negative amount");
	}
	
	public void withdrawFunds(double withdraw) {
		if(withdraw >=0 && withdraw <= balance)
			balance-=withdraw;
		else if(withdraw > balance)
			System.out.println("Insufficient funds");
		else if(withdraw < 0)
			System.out.println("Cannot withdraw a negative amount");
	}
	public String changePin(String newPin) {
		bankPin = newPin;
		
		return "Your pin has been changed";
	}
}

