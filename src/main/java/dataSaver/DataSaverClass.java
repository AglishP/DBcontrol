package dataSaver;

import java.io.*;

public class DataSaverClass {
	
	FileWriter writer;
	
	//конструктор по имени файла
	public DataSaverClass(File inFile){
		try {
			if ( inFile.exists() ){
				writer = new FileWriter(inFile, false);
			}else{
				System.out.println("No such file " + inFile.getName());
			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	

	
	public void writeString (String s){
		
		try ( FileWriter writer = new FileWriter("D:\\java_writer.txt", false) )
		{
			writer.write(s);
			writer.append("\n");
			
			writer.flush();
		}
		catch(IOException ex){
			System.out.println(ex.getMessage());
			
		}
	}
	
}
