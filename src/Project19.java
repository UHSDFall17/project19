import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Project19 {

	public static void main(String[] args)throws IOException {
		boolean isThere = false;
		Users user = new Users("KASEYNIEMAN");
		//user.setUserName();
		user.createUserFileName("KASEYNIEMAN");
		user.setRealNameF();
		user.loadUserInfo("KASEYNIEMAN");
		user.outUserInfo();
	}
}
