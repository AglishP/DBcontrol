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
	DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
	private String startDate;
	private String endDate;
	
	/**
	 * Конструтор по умолчанию
	 */
	public DateBuilder(){
		calendar = Calendar.getInstance();
	}
	
	/**
	 * Конструтор с типом вычислений
	 * @param inCommand строковое значение типа
	 */
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
				int newYear = calendar.get(Calendar.YEAR);
				newYear = newYear - 1;
				this.setYear(newYear);
				
			}else{
				newMonth = newMonth -1 ;
			}
			break;
		case "curr":
			break;
		default:
			newMonth = 0;
			break;
		}
		//устанавливаем месяц
		this.setMonth(newMonth);
	}
	
	/**
	 * Устанавливаем месяц
	 * @param номер месяца
	 */
	private void setMonth(int inMonth){
		calendar.set(Calendar.MONTH, inMonth);
	}
	
	/**
	 * Устанавливаем год
	 * @param inYear число - год
	 */
	private void setYear(int inYear){
		calendar.set(Calendar.YEAR, inYear);
	}
	
	/**
	 * Получить начальную дату
	 * @return Строка по формату
	 */
	public String getStartDay(){
		
		calendar.set(Calendar.DAY_OF_MONTH, startDay);
		tempDate = calendar.getTime();
		startDate = dateFormater.format(tempDate);		
		return startDate;
	}
	
	/**
	 * Получить конечную дату
	 * @return строка по формату
	 */
	public String getEndDay(){
		calendar.set(Calendar.DAY_OF_MONTH, startDay);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(calendar.DAY_OF_MONTH));
		tempDate = calendar.getTime();
		endDate = dateFormater.format(tempDate);
		return endDate;
	}
	
	/**
	 * Получить текущую дату
	 * @return строка по формату
	 */
	public String getCurrDate(){
		
		String currDate = null;
		
		tempDate = calendar.getTime();
		currDate = dateFormater.format(tempDate);
		
		return currDate;
	}
	
	/**
	 * Получить текущее время
	 * @return строка по формату
	 */
	public String getCurrTime(){
		
		String currTime = null;
		
		tempDate = calendar.getTime();
		DateFormat timeFormater = new SimpleDateFormat("HH-mm");
		currTime = timeFormater.format(tempDate);
		
		return currTime;
	}
	
}
