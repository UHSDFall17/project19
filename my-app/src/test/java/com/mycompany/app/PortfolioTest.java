import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class PortfolioTest {


	@Test
	public void testLogOut() {
		Portfolio portfolio = new Portfolio();
		portfolio.logOut();
		String[] ETF = portfolio.getEtf();
		assertEquals("", ETF[0]);
	}



}
