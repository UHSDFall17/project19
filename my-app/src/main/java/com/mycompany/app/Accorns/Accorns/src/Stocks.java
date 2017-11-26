
public class Stocks {

	Stocks(){
	
	}
	
    // Given symbol, get HTML
    public static String readHTML(String symbol) {
        In page = new In("https://finance.google.com/finance?q=NASDAQ:" + symbol);
        String html = page.readAll();
        if (html.contains("<title></title>")) return null;
        else return html;
    }

   

    // Given symbol, get current stock price.
    public static double priceOf(String symbol, String html) {
    	int p     = html.indexOf("<meta itemprop=\"price\"", 0);      // "price.0" index
        int from  = html.indexOf("content=\"", p);            // ">" index
        int to    = html.indexOf("\" />", from);   // "</span>" index
        String price = html.substring(from + 9, to);

        // remove any comma separators
        return Double.parseDouble(price.replaceAll(",", ""));
    }

 // Given symbol, get current stock price.
    public static double priceOf(String symbol) {
    	String html = readHTML(symbol);
    	int p     = html.indexOf("<meta itemprop=\"price\"", 0);      // "price.0" index
        int from  = html.indexOf("content=\"", p);            // ">" index
        int to    = html.indexOf("\" />", from);   // "</span>" index
        String price = html.substring(from + 9, to);

        // remove any comma separators
        return Double.parseDouble(price.replaceAll(",", ""));
    }
    // Given symbol, get current stock name.
    public static String nameOf(String symbol, String html) {
        int p   = html.indexOf("<meta itemprop=\"name\"", 0);
        int from = html.indexOf("content=\"", p);
        int to   = html.indexOf("\" />", from);
        String name = html.substring(from + 9, to);
        return name;
    }
    // Given symbol, get current stock name.
    public static String nameOf(String symbol) {
    	String html = readHTML(symbol);
        int p   = html.indexOf("<meta itemprop=\"name\"", 0);
        int from = html.indexOf("content=\"", p);
        int to   = html.indexOf("\" />", from);
        String name = html.substring(from + 9, to);
        return name;
    }
}
   
