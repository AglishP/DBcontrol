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
			"Дата начала расчета:<StartDate>",
			"Дата окончания расчета:<EndDate>",
			"Расчет выполнялся для прогнозов на:4ч",
			"Дата выполнения расчета:<CurrDate>",
			"Время выполнения расчета:<CurrTime>",
			"Расчет выполнен на стороне БД"			
			));
	
	final ArrayList<String> tshTemplate = new ArrayList<String>(Arrays.asList(
			"Параметры, при которых проводилась оценка:",
			"Vis. Погрешность:<Vis1Tsh>(м) при видимости  до:<Vis1Lvl>(м)",
			"Vis. Погрешность:<Vis2Tsh>(м) при видимости  до:<Vis2Lvl>(м)",
			"Cldh. Погрешность:<Cldh1Tsh>(м) на высоте  до:<Cldh1Lvl>(м)",
			"Cldh. Погрешность:<Cldh2Tsh>(%) на высоте  до:<Cldh2Lvl>(м)",
			"Tair. Погрешность:<Ta>(град.С)",
			"Tdew. Погрешность:<Tdew>(град.С)",
			"Ws. Погрешность:<Ws>(град.С) при скорости ветра свыше:<Ws_LowLvl>(м/с)",
			"Wd. Погрешность:<Wd>(град.С) при скорости ветра свыше:<Wd_LowLvl>(м/с)",
			"Rh. Погрешность:<Rh>(%), При значених от<RhLvl>(%)",
			"----------------------------------"
			));
		
	final ArrayList<String> mainTemplate = new ArrayList<String>(Arrays.asList(
			"		Параметр <param>",
			"			VIS. Удачных прогнозов:<successforecast> ( <successforecastpercent>%)",
			"			VIS. Сумма прогнозов/исх.данных: <sumforecstdata>",
			"			VIS. Нет данных прогн:<noforecastdata>",
			"			VIS. СКО: <sko>",
			"			VIS. Среднее отклонение: <mean>",
			"			VIS. Среднее по молулю:  <absmean>",
			"----------------------------------"
			));
	
	final ArrayList<String> mainHeadTemplate = new ArrayList<String>(Arrays.asList(
			"	Станция-<station>"
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

	/**
	 * Возвращаем массив данных с шаблоном для пороговых значений
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getTshTemplate(){
		
		return tshTemplate;
	}

	/**
	 * Возвращаем массив данных с шаблоном для основной статистики
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getMainTemplate(){
		
		return mainTemplate;
	}
	
	/**
	 * Возвращаем заголовок для общего прогноза
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getMainHeadTemplate(){
		
		return mainHeadTemplate;
	}
}
