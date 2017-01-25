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
	final String directoryPath = "D:\\IRAM\\ARX_FORECAST_ESTIM\\";
	
	/**
	 * Пустой конструктор по умолчанию
	 */
	public DataSaver(){
		
	}
	
	/**
	 * Конструктор с входным именем файла
	 * @param inFile
	 */
	public DataSaver(String inFileName){
		
		this.setFileName(inFileName);
	}
	
	/**
	 * Установка имени файла и создание fileWriter
	 * @param inFileName имя файла
	 */
	public void setFileName(String inFileName){
		
		String fullPath = directoryPath.concat(inFileName);
		myFile = new File(fullPath);
		//System.out.println(fullPath);
			if ( myFile.exists() ){
				//System.out.println("File exist");
				try {
					writer = new FileWriter(inFileName, false);
				} catch (IOException e) {
					System.out.println("Can't make FileWriter");
					e.printStackTrace();
				}
			}else{
				//System.out.println("try to make file");
				try {
					myFile.createNewFile();
					writer = new FileWriter(myFile, false);
					
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
