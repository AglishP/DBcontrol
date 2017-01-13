package starter;

import java.io.File;
import java.sql.*;
import java.util.Scanner;
import dataSaver.DataSaverClass;


public class StarterClass {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ConnectionClass conn = new ConnectionClass();
		Connection myConn = conn.letsConnect();
		
		Boolean runProgramm = true;
		
		if (myConn == null){
			System.out.println("STARTED CLASS NO CONNECTED");
			runProgramm = false;
		}
		
		
		do{
		
			System.out.print("Your command: ");
			Scanner inputStream = new Scanner(System.in);
			
			String[] inputCommand = inputStream.nextLine().split(" ");
			
			String startDate;
			String endDate;
			DateBuilder db;
			
			switch(inputCommand[0]){
			case "load":
				
				switch(inputCommand[1]){
				case "this":								
					db = new DateBuilder("this");
					startDate = db.getStartDay();
					System.out.println(startDate);
					endDate = db.getEndDay();
					System.out.println(endDate);
					break;
				case "prev":
					db = new DateBuilder("prev");
					startDate = db.getStartDay();
					System.out.println(startDate);
					endDate = db.getEndDay();
					System.out.println(endDate);
					break;
				default:
					System.out.println("No such command: " + inputCommand[1]);	
					break;
				}
				
				switch(inputCommand[2]){
				case "main":
					break;
				case "status":
					break;
				case "alldata":
									
				
				default:
					System.out.println("No such command: " + inputCommand[2]);
					break;
				}
				
				break;
				
			case "print":
				
				String s = inputCommand[1];
				String fileName = "D:\\java_writer.txt";
				File file = new File(fileName);
				
				DataSaverClass dsc = new DataSaverClass(file);
				dsc.writeString(s);
				
			case "exit":
				runProgramm = false;
				System.out.println("exit programm");	
				inputStream.close();
				break;
			default:
				System.out.println("No such command: " + inputCommand[0]);
			}
		}while(runProgramm);
		
	}

}
