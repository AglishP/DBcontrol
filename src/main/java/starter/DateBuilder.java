package starter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class DateBuilder {
	
	String command;

	private Calendar calendar = null;
	private Date tempDate = null;
	private int startDay = 1;
	DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");;
	private String startDate;
	private String endDate;
	
	
	public DateBuilder(String inCommand){
		
		command = inCommand;
		int newMonth=0;
		//создаем календарь
		calendar = Calendar.getInstance();	
		switch(command){
		case "this":
			newMonth = calendar.get(Calendar.MONTH);
			System.out.println("input month this: " + newMonth);
			break;
		case "prev":
			newMonth = calendar.get(Calendar.MONTH);
			if (newMonth == 0){
				newMonth = 11;
			}else{
				newMonth = newMonth -1 ;
			}
			System.out.println("input month prev: " + newMonth);
			//System.out.println("max value of month is " + calendar.getActualMaximum(calendar.MONTH));
			break;
		default:
			newMonth = 0;
			break;
		}
		//устанавливаем месяц
		this.setMonth(newMonth);
		
	}
	
	private void setMonth(int inMonth){
		calendar.set(Calendar.MONTH, inMonth);
	}
	
	public String getStartDay(){
		
		calendar.set(Calendar.DAY_OF_MONTH, startDay);
		tempDate = calendar.getTime();
		startDate = dateFormater.format(tempDate);		
		return startDate;
	}
	
	public String getEndDay(){
		calendar.set(Calendar.DAY_OF_MONTH, startDay);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(calendar.DAY_OF_MONTH));
		tempDate = calendar.getTime();
		endDate = dateFormater.format(tempDate);
		return endDate;
	}
	
	
	
	
}
