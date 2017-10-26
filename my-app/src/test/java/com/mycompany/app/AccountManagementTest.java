import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class AccountManagementTest {

	@Test
	public void testLogOut()throws IOException{
		AccountManagement user = new AccountManagement();
		assertEquals(false,user.logOut());
	}

	@Test
	public void testCalChange()throws IOException {
		AccountManagement user = new AccountManagement();
		double temp = user.calChange(100, 90);
		assertEquals(Double.toString(-0.1), Double.toString(temp));
		
	}


}
