package dataSaver;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Храним строки с шаблонами строк
 * @author Павел Аглиш
 *
 */
public class TemplateData {

	final ArrayList<String> headTemplate = new ArrayList<String>(Arrays.asList(
			"Дата начала расчета:?",
			"Дата окончания расчета:?",
			"Расчет выполнялся для прогнозов на:4ч",
			"Дата выполнения расчета:?",
			"Время выполнения расчета:?",
			"Расчет выполнен на стороне БД"			
			));
	
	
	/**
	 * Конструктор по умолчанию
	 */
	public TemplateData(){
		
	}

	/**
	 * Возвращаем массив данных с шаблоном основных данных
	 * @return ArrayList<String> 
	 */
	public ArrayList<String> getHeadTemplate(){
		
		return headTemplate;		
	}



}
