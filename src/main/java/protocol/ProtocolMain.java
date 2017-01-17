package protocol;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;

public class ProtocolMain {

	private Connection myConn;
	private String startDate;
	private String endDate;
	private ArrayList<String> allowedLocation = new ArrayList<String>(Arrays.asList( "Irkutsk"));
	private String location; 

	// конструктор
	public ProtocolMain(Connection inconnection,
					String inlocation,
					String instartDate,
					String inendDate){
		myConn = inconnection;
		this.setLocation(inlocation);
		startDate = instartDate;
		endDate = inendDate;
	}
		
	private boolean setLocation(String inLocation){
		if (allowedLocation.indexOf(inLocation) != -1){
			location = inLocation;
			return true;
		}else{
			location = null;
			return false;
		}
	}
	
	public void runProtocol(){
		
		switch (location){
		case "irkutsk":
			
			ProtocolIrkutsk pi = new ProtocolIrkutsk(myConn, startDate, endDate);
			pi.RunProtocol();
			break;
		default: 
			System.out.println("No such protocol: " + location);
			break;
		}
		
	}
		
}
