package dataSaver;

import java.io.*;

/**
 * Класс записи данных в файл
 * @author MASTER
 *
 */
public class DataSaver {
	
	File myFile = null;
	FileWriter writer;
	final String directoryPath = "D:\\IRAM\\ARX_FORECAST_ESTIM";
	
	/**
	 * Пустой конструктор по умолчанию
	 */
	public DataSaver(){
		
	}
	
	/**
	 * Конструктор с входным именем файла
	 * @param inFile
	 */
	public DataSaver(String inFile){
		
		String fullPath = directoryPath.concat(inFile);
		File myFile = new File(fullPath);
		
			if ( myFile.exists() ){
				try {
					writer = new FileWriter(inFile, false);
				} catch (IOException e) {
					System.out.println("Can't make FileWriter");
					e.printStackTrace();
				}
			}else{
				try {
					myFile.createNewFile();
				} catch (IOException e) {
					System.out.println("Can't create file");
					e.printStackTrace();
				}
			}
		
	}
	
	/**
	 * Запись строки в файл
	 * @param s строка, которую пишем
	 */
	public void writeString (String s){
		
		try {
			writer.write(s);
			writer.append("\n");
			writer.flush();
		}
		catch(IOException ex){
			System.out.println("Error in write to file");
			System.out.println(ex.getMessage());	
		}
	}
	
	/**
	 * Закрываем writer 
	 */
	public void closeAndFlush(){
		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
