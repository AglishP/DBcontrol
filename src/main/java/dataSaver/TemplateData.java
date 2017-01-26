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
			"Rh. Погрешность:<Rh>(%), При значених от<RhLvl>(%)"
			));
		
	final ArrayList<String> mainTemplate = new ArrayList<String>(Arrays.asList(
			"		Параметр <param>",
			"			VIS. Удачных прогнозов:<successforecast> ( <successforecastpercent>%)",
			"			VIS. Сумма прогнозов/исх.данных: <sumforecstdata>",
			"			VIS. Нет данных прогн:<noforecastdata>",
			"			VIS. СКО: <sko>",
			"			VIS. Среднее отклонение: <mean>",
			"			VIS. Среднее по молулю:  <absmean>"
			));
	
	final ArrayList<String> extendTemplate = new ArrayList<String>(Arrays.asList(
			"	Станция-<station>",
			"	Дата начала расчета:<indate>",
			"	Дата окончания расчета:<outdate>",
			"		Параметр:<param>",
			"		Пороговое значение для разделения по корзинам:<tsh_in>",
			"		Корзина А:<a_basket>",
			"		Корзина В:<b_basket>",
			"		Корзина С:<c_basket>",
			"		Корзина D:<d_basket>",
			"		Параметр PC:<pc>",
			"		Параметр PC plus:<pc_plus>",
			"		Параметр PC minus:<pc_minus>",
			"		Параметр P plus:<p_plus>",
			"		Параметр P minus:<p_minus>",
			"		Параметр F:<far>",
			"		Параметр H:<hit>",
			"		Параметр MISS:<miss>",
			"		Параметр ORSS:<orss>",
			"		Параметр EDI:<edi>",
			"		Параметр SEDI:<sedi>"
			));
	
	final ArrayList<String> statusTemplate = new ArrayList<String>(Arrays.asList(
			"	Дата начала расчета:<indate>",
			"	Дата окончания расчета:<outdate>",
			"		Пороговое значение для разделения по корзинам:<tsh_in>",
			"		Корзина А:<a_basket>",
			"		Корзина В:<b_basket>",
			"		Корзина С:<c_basket>",
			"		Корзина D:<d_basket>",
			"		Параметр PC:<pc>",
			"		Параметр PCplus:<pc_plus>",
			"		Параметр PCminus:<pc_minus>",
			"		Параметр Pplus:<p_plus>",
			"		Параметр Pminus:<p_minus>",
			"		Параметр FAR:<far>",
			"		Параметр HIT:<hit>",
			"		Параметр MISS:<miss>",
			"		Параметр ORSS:<orss>",
			"		Параметр EDI:<edi>",
			"		Параметр SEDI:<sedi>"
			));
	
	final ArrayList<String> mainHeadTemplate = new ArrayList<String>(Arrays.asList(
			"----------------------------------",
			"	Станция-<station>"
			));
	
	final ArrayList<String> extendHeadTemplate = new ArrayList<String>(Arrays.asList(
			"----------------------------------",
			"Вывод расширенной статистики по станциям за выбранный период"
			));
	
	final ArrayList<String> statusHeadTemplate = new ArrayList<String>(Arrays.asList(
			"----------------------------------",
			"Вывод расширенной статистики по оценке статуса прогноза за выбранный период",
			"Станция-<station>"
			));
	
	final ArrayList<String> basketAHeadTemplate = new ArrayList<String>(Arrays.asList(
			" ",
			"корзина a, только срок, за который прогноз оправдался",
			" "
			));
	
	final ArrayList<String> basketBHeadTemplate = new ArrayList<String>(Arrays.asList(
			" ",
			" ",
			" ",
			"********************************************************************",
			"********************************************************************",
			"********************************************************************",
			" ",
			" ",
			"корзина b",
			" "
			));
	
	final ArrayList<String> basketCHeadTemplate = new ArrayList<String>(Arrays.asList(
			" ",
			" ",
			" ",
			"********************************************************************",
			"********************************************************************",
			"********************************************************************",
			" ",
			" ",
			"корзина b",
			" "
			));
	
	final ArrayList<String> factFogHeadTemplate = new ArrayList<String>(Arrays.asList(
			" ",
			" ",
			" ",
			"********************************************************************",
			"********************************************************************",
			"********************************************************************",
			" ",
			" ",
			"срок, когда видимость была ниже 1000м",
			" "
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
	 * Возвращаем массив данных с шаблоном для расширенной статистики
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getExtendTemplate(){
		
		return extendTemplate;
	}
	
	/**
	 * Возвращаем массив данных с шаблоном для статистики по статусу
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getStatusTemplate(){
		
		return statusTemplate;
	}
	
	/**
	 * Возвращаем заголовок для общего прогноза
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getMainHeadTemplate(){
		
		return mainHeadTemplate;
	}
	
	/**
	 * Возвращаем заголовок для статуса прогноза
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getStatusHeadTemplate(){
		
		return statusHeadTemplate;
	}
	
	/**
	 * Возвращаем заголовок для расширенного прогноза
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getExtendHeadTemplate(){
		
		return extendHeadTemplate;
	}
	
	/**
	 * Получаем заголовок для корзины а
	 * @return
	 */
	public ArrayList<String> getBasketAHead(){
		
		return basketAHeadTemplate;
	}
	
	/**
	 * Получаем заголовок для корзины b
	 * @return
	 */
	public ArrayList<String> getBasketBHead(){
			
			return basketBHeadTemplate;
		}
	
	/**
	 * Получаем заголовок для корзины с
	 * @return
	 */
	public ArrayList<String> getBasketCHead(){
		
		return basketCHeadTemplate;
	}
	
	/**
	 * Получить заголовок для фактических данных видимости ниже 1000м
	 * @return
	 */
	public ArrayList<String> getFactFogHeadTemplate(){
		
		return factFogHeadTemplate;
	}
}
