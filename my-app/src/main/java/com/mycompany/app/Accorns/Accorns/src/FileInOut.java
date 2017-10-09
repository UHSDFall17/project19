import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileInOut {
	
	//checks the user folder to see if userName is there 
		public boolean checkForName(String fileName, String name){
			boolean isThere = false;
			//all user names are uppercase
			name = name.toUpperCase();
			String hashName = Integer.toString(name.hashCode());
	        File file = new File(fileName);
			try {
	            BufferedReader b = new BufferedReader(new FileReader(file));
	            String readLine = "";
	            while ((readLine = b.readLine()) != null) {
	                if(readLine.equals(hashName)) 
	                	isThere = true;
	                readLine = b.readLine();
	           
	            }
	            b.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
			return isThere;
		}
		//appends to the end of a file, requires file name, not file type
		public boolean writeToFile(String fileName, String content) {
			
			try(FileWriter fw = new FileWriter(fileName, true);
				    BufferedWriter bw = new BufferedWriter(fw);
				    PrintWriter out = new PrintWriter(bw))
				{
				    out.println(content);
				} catch (IOException e) {
				    
				}
			return true;
				
		}
		//changes info for userInfo file
		public boolean replaceInFile(String fileName,String newString, String oldString) throws IOException{
			File file = new File(fileName);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String oldCon = "";
			String line = reader.readLine();
			
			while (line != null) 
			{
				oldCon = oldCon + line + System.lineSeparator();
				line = reader.readLine();
			}		
			String newCon = oldCon.replaceAll(oldString, newString);
			FileWriter writer = new FileWriter(file);
			writer.write(newCon);
			writer.close();
			reader.close();
			return true;
			
		}
		//checkFile checks to see if file is there and creates one if needed
		public boolean checkFile(String fileName) throws IOException {
			File file = new File(fileName);
			if(!file.exists()) {
				file.createNewFile();
			}
			return true;
		}
}
