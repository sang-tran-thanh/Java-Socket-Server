package server;



import java.awt.TextArea;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.FileWriter;
import java.io.File;

//<ESP8266_1/> 2000 <ESP8266_2/> 2000 <ESP32_1/> 1000 <ESP32_2/> 1000
//<61> 2000 <62> 2000 <21> 1000 <22> 1000
public class server {
	

//	public server(TextArea _txt_result_ttwt) throws IOException {
//	    //interval.DataPort = dataport; // Server port for Gateway
//	    //interval.GatewayPort = gatewayport;
//	    // Create a server socket to accept client connection requests
//		//while(interval.GatewayPort==0||interval.DataPort==0||interval.DataPort==interval.GatewayPort){}
//		ServerSocket servSock = new ServerSocket(interval.GatewayPort);
//		ServerSocket dataSock = new ServerSocket(interval.DataPort);
//		
//		System.out.println("server started with app port: "+Integer.toString(interval.DataPort)+", gateway port: "+Integer.toString(interval.GatewayPort));
//		
//	    Socket appSock = dataSock.accept();
//	    Thread thr = new Thread(new apphandle(appSock, _txt_result_ttwt));
//	    thr.start();
//	    System.out.println("App connected to data with "+thr.getName() );
//	    
//	    // Run forever, accepting and spawning a thread for each connection
//	    
//	    while (true) {	    	
//		    Socket clntSock = servSock.accept();// Block waiting for connection
//		    System.out.println(clntSock);
//		    // Spawn thread to handle new connection
//		    Thread thread = new Thread(new EchoProtocol(clntSock, appSock, _txt_result_ttwt));
//		    thread.start();
//		    System.out.println("Created connection with a new gateway with " + thread.getName());
//	    }
//	}
//	
	
	
	public static void main(String[] args) throws IOException {
	    //interval.DataPort = dataport; // Server port for Gateway
	    //interval.GatewayPort = gatewayport;
	    // Create a server socket to accept client connection requests
		frame fr = new frame();
		while(fr.getflag()==0){
			System.out.print(fr.getflag());
		}
		ServerSocket servSock = new ServerSocket(interval.GatewayPort);
		ServerSocket dataSock = new ServerSocket(interval.DataPort);
		
		System.out.println("server started with app port: "+Integer.toString(interval.DataPort)+", gateway port: "+Integer.toString(interval.GatewayPort));
		fr.getarea().append("server started with app port: "+Integer.toString(interval.DataPort)+", gateway port: "+Integer.toString(interval.GatewayPort)+"\n");
//	    Socket appSock = dataSock.accept();
		
	    File file = new File("data.csv");
	    File filetemp = new File("temp.txt");
		
	    Thread thr = new Thread(new appconnect(dataSock, fr.getarea(),fr.get_app_area(),filetemp));
	    thr.start();
//	    System.out.println("App connected to server");//. with "+thr.getName() );
//	    fr.getarea().append("App connected to server"+"\n");//. with "+thr.getName() );
	    // Run forever, accepting and spawning a thread for each connection
	    
	    
	    while (true) {	    	
		    Socket clntSock = servSock.accept();// Block waiting for connection
		    System.out.println(clntSock);
		    interval.gateway_num++;
		    fr.get_gat_area().setText(Integer.toString(interval.gateway_num));
		    // Spawn thread to handle new connection
		    Thread thread = new Thread(new gatewayhandle(clntSock, fr.getarea(),fr.get_gat_area(), file, filetemp));
		    thread.start();
		    System.out.println("Created connection with a new gateway");// with " + thread.getName());
		    fr.getarea().append("Created connection with a new gateway"+"\n");// with " + thread.getName()+"\n");
	    }
	}
	
}