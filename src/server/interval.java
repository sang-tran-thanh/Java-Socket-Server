package server;

public class interval {
	public static boolean change = false;  //if intervals are changed
	public static boolean datatosend = false;
	
	public static int app_num = 0;	//number of apps connected
	//public static int datatosend_num = 0;  // data send to app
	public static int gateway_num = 0; // number of gateways connected
	
	
	public static int gateway1_interval = 2000;
    public static int client1_interval= 1000;
	
    public static int gateway2_interval =2000;
    public static int client2_interval =1000;
    
    public static int DataPort ;
    public static int GatewayPort ;
    
	public static String gateway1_ID = "<ESP8266_1/>";
    public static String client1_ID = "<ESP32_1/>";
	
    public static String gateway2_ID = "<ESP8266_2/>";
    public static String client2_ID = "<ESP32_2/>";
}
