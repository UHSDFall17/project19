import static org.junit.Assert.*;

import org.junit.Test;

public class StocksTest {



	@Test
	public void testPriceOfStringString() {
		
	}

	@Test
	public void testPriceOfString() {
		Stocks stock = new Stocks();
		double price; 
		price = stock.priceOf("AAPL");
		assertNotNull(price);
	}

	@Test
	public void testNameOfStringString() {
		Stocks stock = new Stocks();
		String name = stock.nameOf("AAPL");
		assertEquals("Apple Inc.", name);
	}
	@Test
    public void testReadHtml(){
        Stocks stock = new Stocks();
        String symbol = "AAPL";
       String html = stock.readHTML(symbol);
       assertNotSame(null, html);


    }


}
