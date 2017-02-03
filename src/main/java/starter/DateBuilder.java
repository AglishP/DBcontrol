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
	 * ���������� �� ���������
	 */
	public DateBuilder(){
		calendar = Calendar.getInstance();
	}
	
	/**
	 * ���������� � ����� ����������
	 * @param inCommand ��������� �������� ����
	 */
	public DateBuilder(String inCommand){
		
		command = inCommand;
		int newMonth=0;
		//������� ���������
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
		//������������� �����
		this.setMonth(newMonth);
	}
	
	/**
	 * ������������� �����
	 * @param ����� ������
	 */
	private void setMonth(int inMonth){
		calendar.set(Calendar.MONTH, inMonth);
	}
	
	/**
	 * ������������� ���
	 * @param inYear ����� - ���
	 */
	private void setYear(int inYear){
		calendar.set(Calendar.YEAR, inYear);
	}
	
	/**
	 * �������� ��������� ����
	 * @return ������ �� �������
	 */
	public String getStartDay(){
		
		calendar.set(Calendar.DAY_OF_MONTH, startDay);
		tempDate = calendar.getTime();
		startDate = dateFormater.format(tempDate);		
		return startDate;
	}
	
	/**
	 * �������� �������� ����
	 * @return ������ �� �������
	 */
	public String getEndDay(){
		calendar.set(Calendar.DAY_OF_MONTH, startDay);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(calendar.DAY_OF_MONTH));
		tempDate = calendar.getTime();
		endDate = dateFormater.format(tempDate);
		return endDate;
	}
	
	/**
	 * �������� ������� ����
	 * @return ������ �� �������
	 */
	public String getCurrDate(){
		
		String currDate = null;
		
		tempDate = calendar.getTime();
		currDate = dateFormater.format(tempDate);
		
		return currDate;
	}
	
	/**
	 * �������� ������� �����
	 * @return ������ �� �������
	 */
	public String getCurrTime(){
		
		String currTime = null;
		
		tempDate = calendar.getTime();
		DateFormat timeFormater = new SimpleDateFormat("HH-mm");
		currTime = timeFormater.format(tempDate);
		
		return currTime;
	}
	
}
