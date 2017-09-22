import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Accorns {
	
	public static void main(String[] args)throws IOException {
			Users user = new Users();
			signupLoginMenu(user);
			
		}
	//log in menu
	public static void signupLoginMenu(Users user) throws IOException{
		boolean loggedIn = false;
		String select;
		Scanner keys = new Scanner(System.in);
		while(!loggedIn) {
			System.out.println("Enter the number of the command to excute:\n"
					+ "1.Log In\n2.SignUp");
			select = keys.nextLine();
			switch(select) {
			case "1":
				clearConsole();
				user.logIn();
				clearConsole();
				break;
			
			case "2":
				clearConsole();
				user.firstTimeSignup();
				clearConsole();
				break;
			default:
				System.out.println("Sorry, that was not an option\n please try again\n");
			}
			loggedIn = user.checkLogIn();
		}
		
	}
	public static void clearConsole() {
		for(int i = 0; i < 50; i++)
			System.out.println("\n");
	}
}