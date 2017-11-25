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


	

}
