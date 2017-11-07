import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileInOut {
		String path;
		FileInOut(){
			path = System.getProperty("user.home") + File.separator + "Documents";
		}
	
		//checks the user folder to see if userName is there 
		public boolean checkForName(String fileName, String name){
			boolean isThere = false;
			//all user names are uppercase
			name = name.toUpperCase();
			String hashName = Integer.toString(name.hashCode());
	        File file = new File(path + File.separator + "Accorns_Accounts" + File.separator + fileName);
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
			
			try(FileWriter fw = new FileWriter(path + File.separator + "Accorns_Accounts" + File.separator + fileName, true);
				    BufferedWriter bw = new BufferedWriter(fw);
				    PrintWriter out = new PrintWriter(bw))
				{
				    out.println(content);
				} catch (IOException e) {
				    
				}
			return true;
				
		}
		//changes info for userInfo file-Make sure to include the old string, including the identifier and amount
		public boolean replaceInFile(String fileName, String newString, String oldString) throws IOException{
			File file = new File(path + File.separator + "Accorns_Accounts" + File.separator + fileName);
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
		
		public void addToSubheading(String fileName, String content, String checkFor) throws IOException{
		    File file = new File(path + File.separator + "Accorns_Accounts" + File.separator + fileName); 
		    File filePath = new File(path + File.separator + "Accorns_Accounts");
		    File temp = File.createTempFile("temp-file-name", ".txt", filePath);
		    BufferedReader br = new BufferedReader(new FileReader( file ));
		    PrintWriter pw =  new PrintWriter(new FileWriter( temp ));
		    String line;
		    
		    while ((line = br.readLine()) != null) {
		        pw.println(line);
		        if(line.equals(checkFor)){
		        	
		            pw.println(content);
		        }
		      
		    }
		    br.close();
		    pw.close();
		    br.close();
		    pw.close();
		    System.out.println(file.exists());
		    System.out.println(file.getAbsolutePath());
		    System.out.println(file.delete());
		    System.out.println(temp.renameTo(file));
		}
		
		//checkFile checks to see if file is there and creates one if needed
		public boolean checkForFile(String fileName) throws IOException {
			 File files = new File(path + File.separator + "Accorns_Accounts");
			 	
		        if (!files.exists()) {
		            if (files.mkdirs()) {
		            } else {
		                System.out.println("Fatal Error-Failed to create multiple directories!");
		                System.exit(0);
		            }
		        }


			File file = new File(path + File.separator + "Accorns_Accounts" + File.separator + fileName);
			if(!file.exists()) {
				file.createNewFile();
			}
			return true;
		}
		
		//checks for variable in file and returns the value
		public String checkForInFile(String fileName, String dataType) {
			BufferedReader br;
			String line;
			try {
		        br = new BufferedReader(new FileReader(path + File.separator + "Accorns_Accounts" + File.separator + fileName));
		        try {
		            while((line = br.readLine()) != null)
		            {
		                String[] words = line.split(" ");

		                for (int i = 0; i < words.length; i++) {
		                	String word = words[i];
		                  if (word.equals(dataType)) {
		                	  word = words[i+1];
		           
		                    return word;
		                  }
		                }

		            }
		            br.close();
		        } catch (IOException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
			 } catch (FileNotFoundException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    }
		
			return "-1";
		}
		
		//Change for inclusion of pass investments
		public String[] checkForInvest(String fileName)throws IOException{
			BufferedReader br;
			String read = "";
			String hold = "";
			//used to return something
			String[] returnString = new String[1];
			boolean test = true;
			int countFinal = 0;
			
			try {
		        
				br = new BufferedReader(new FileReader(path + File.separator + "Accorns_Accounts" + File.separator + fileName));
		        
		        try {
		            
		            do{
		            		read = br.readLine();
		            		hold = hold + read + " ";
		            	
		                }while((read != null));
		            	
		            	String[] wholeFile = hold.split(" ");
		            	for(int i = 0; i < wholeFile.length && test; i ++) {
		            		if(wholeFile[i].equals("INVESTMENTS")) {
		            			countFinal = i;
		            			test = false;
		            		}
		            		
		            		
		            	}
		            	String[] finalData = new String[wholeFile.length - (countFinal + 2)];
		            	//countFinal incremented to move pass INVESTMENTS
		            	countFinal ++;
		            	for(int i = 0; countFinal < wholeFile.length - 1; i ++) {
		            		
		            		finalData[i] = wholeFile[countFinal];
		            		
		            		countFinal++;
		            		
		            	}
		              
		            	

		            
		            br.close();
		            return finalData;
		        } catch (IOException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
			 } catch (FileNotFoundException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    }
		
			return returnString;
		}
		public void deleteUnderSubheading(String fileName,String checkFor) throws IOException{
		    File file = new File(path + File.separator + "Accorns_Accounts" + File.separator + fileName); 
		    File filePath = new File(path + File.separator + "Accorns_Accounts");
		    File temp = File.createTempFile("temp-file-name", ".tmp", filePath);
		    BufferedReader br = new BufferedReader(new FileReader( file ));
		    PrintWriter pw =  new PrintWriter(new FileWriter( temp ));
		    String line;
		    
		    while ((line = br.readLine()) != null) {
		        pw.println(line);
		        if(line.equals(checkFor)){
		        	line = br.readLine();
		        }
		      
		    }
		    br.close();
		    pw.close();
		    file.delete();
		    temp.renameTo(file);
		}
}
