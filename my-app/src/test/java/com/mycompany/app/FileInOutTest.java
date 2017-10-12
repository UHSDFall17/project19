import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class FileInOutTest {

	@Test
	public void testWriteToFile() {
		FileInOut fileSystem = new FileInOut();
		boolean test = fileSystem.writeToFile("testDoc.txt", "Test");
		assertEquals(true, test);
	}

	@Test
	public void testReplaceInFile() throws IOException{
		FileInOut fileSystem = new FileInOut();
		boolean test = fileSystem.replaceInFile("testDoc.txt", "Test", "test2");
		assertEquals(true, test);
	}

	@Test
	public void testCheckFile() throws IOException {
		FileInOut fileSystem = new FileInOut();
		boolean test = fileSystem.checkFile("testDoc.txt");
		assertEquals(true, test);
	}

}
