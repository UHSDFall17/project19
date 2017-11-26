import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class FileInOutTest {
    @Test
    public void testReplaceInFile() throws IOException{
        FileInOut file = new FileInOut();
        boolean status = file.replaceInFile("PRATIVA.txt", "prativa", "Prativa");
        assertEquals(true, status);
    }
    @Test
    public void testCheckForFile() throws IOException{
        FileInOut file = new FileInOut();
        boolean status = file.checkForFile("PRATIVA.txt");
        assertEquals(true, status);
    }
  //* @Test
   // public void testCheckForInvest() throws IOException{
     //  FileInOut file = new FileInOut();
      // String[] word = file.checkForInvest("PRATIVA.txt");
       //assertEquals("11-25-2017", word[0]);
   //}


	

}
