package starter;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import dataSaver.DataSaver;
import dataSaver.ReportMaker;
import sqlQuery.FetchData;
import sqlQuery.SqlQuery;


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
			
			String startDate = null;
			String endDate = null;
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
				
				break;
				
			case "print":
				
				String s = inputCommand[1];
				String fileName = "java_writer.txt";
				
				DataSaver dsc = new DataSaver(fileName);
				dsc.writeString(s);
				
				
			case "calc":
				
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
			
				String calcType = null;
				SqlQuery sqlQuery = new SqlQuery(myConn, calcType,startDate, endDate);
				
				if (startDate != null || endDate != null){break;}
				
				switch(inputCommand[2]){
				case "main":
					
					//сначала считаем базовые параметры статистики
					calcType = "main";
					sqlQuery.setCalcType(calcType);
					sqlQuery.makeCalc();
					
					//меняем тип на расширенный
					calcType = "extend";
					sqlQuery.setCalcType(calcType);
					sqlQuery.makeCalc();
					
					break;
				case "status":
					
					calcType = "status";
					sqlQuery.makeCalc();
					break;
					
				case "alldata":
					break;
					
				case "protocol":
					//рассчет по протоколу иркутска
					
									
					break;
					
				default:
					System.out.println("No such command: " + inputCommand[2]);
					break;
				}
				
			case "test":
				
				db = new DateBuilder("prev");
				startDate = db.getStartDay();
				endDate = db.getEndDay();
				
				
				ReportMaker rm = new ReportMaker(myConn,"test", startDate, endDate);
				
				rm.writeCommonData();
				rm.writeTshData();
				rm.writeMainData();
				rm.writeExtendData();
				rm.writeStatusData();
				rm.close();
				//System.out.println(rm.nameFileCreator("DAY"));
				
		
				
			case "exit":
				runProgramm = false;
				System.out.println("exit programm");	
				inputStream.close();
				
			default:
				System.out.println("No such command: " + inputCommand[0]);
			}
		}while(runProgramm);
		
	}

}
