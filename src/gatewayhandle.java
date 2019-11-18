package server;

import java.awt.TextArea;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;
import java.io.FileWriter;
import java.io.File;

public class EchoProtocol implements Runnable {
	private static final int BUFSIZE = 128; // Size (in bytes) of I/O buffer
	private Socket clntSock;               // Socket connect to client
	private Socket appSock;  
	public static String gatewayID;
	public static String clientID;
	static StringTokenizer tokenizer;

	public static  String INIT ="<INIT/>";
	public static  String CLI_INT ="<CLI_INT/>";
	public static  String DATA ="<DATA/>";
	
	public TextArea _txt_result_ttwt;
	public File file;
	public File filetemp;
	
//	public EchoProtocol(Socket clntSock, Socket appSock,TextArea _txt_result_ttwt,File file) {
//		this.clntSock = clntSock;
//		this.appSock = appSock;
//		this._txt_result_ttwt =_txt_result_ttwt;
//		this.file=file;
//	}
//
//	public static void handleEchoClient(Socket clntSock, Socket appSock, TextArea _txt_result_ttwt,File file) {
//		try {
//			// Get the input and output I/O streams from socket
//			InputStream inapp = appSock.getInputStream();
//			OutputStream outapp = appSock.getOutputStream();
//    	
//			InputStream ingate = clntSock.getInputStream();
//			OutputStream outgate = clntSock.getOutputStream();
//	
//			int recvMsgSize; // Size of received message
//	      	//int totalBytesEchoed = 0; // Bytes received from client
//	      	byte[] echoBuffer = new byte[BUFSIZE]; // Receive Buffer
//      
//      // 	Receive until client closes connection, indicated by -1
//      
//	      	
//	      	String cmd;
//	      	while ((recvMsgSize = ingate.read(echoBuffer)) != -1) {
//    	  		String s = new String(echoBuffer);
//    	  		_txt_result_ttwt.append("RECEIVED        : " + s+"\n");
//    	  		System.out.println(s) ;
//    	  		tokenizer = new StringTokenizer(s," ");
//    	  		cmd = tokenizer.nextToken();
//    	  		if(cmd.equals(INIT)){
//    	  			String gate =tokenizer.nextToken();
//    	  			System.out.println(gate);
//    	  			String response = null;
//    	  			if (gate.equals(interval.gateway1_ID)){
//    	  				gatewayID = gate;
//    	  				 response = "<INVL/> "+ Integer.toString(interval.gateway1_interval);
//    	  				outgate.write(response.getBytes(), 0, response.length());
//    	  				_txt_result_ttwt.append("Accepted connection with gateway: "+gatewayID+", "+ clntSock.getRemoteSocketAddress()+"\n");
//    	  			}
//    	  			else if (gate.equals(interval.gateway2_ID)){
//    	  				gatewayID = gate;
//    	  				 response = "<INVL/> "+ Integer.toString(interval.gateway2_interval);
//    	  				outgate.write(response.getBytes(), 0, response.length());
//    	  				_txt_result_ttwt.append("Connection accepted with gateway: "+gatewayID+", "+ clntSock.getRemoteSocketAddress()+"\n");
//    	  			}
//    	  			else {
//    	  				 response = "<DENY/> 0000";
//    	  				outgate.write(response.getBytes(), 0, response.length());
//    	  				_txt_result_ttwt.append("Connection DENIED with gateway: "+gate+", "+ clntSock.getRemoteSocketAddress()+"\n");
//    	  			}
//    	  			_txt_result_ttwt.append("SENT TO GATEWAY : " + response+"\n");
//    	  		}   						 
//    	  		else if(cmd.equals(CLI_INT)){
//    	  			String clnt =tokenizer.nextToken();
//    	  			System.out.println(clnt);
//    	  			String response = null ;
//    	  			if (clnt.equals(interval.client1_ID )){
//    	  				clientID = clnt;
//    	  				 response = "<CLI_INT/> "+ Integer.toString(interval.client1_interval)+" "+clientID;
//    	  				outgate.write(response.getBytes(), 0, response.length());
//    	  			}
//    	  			else if (clnt.equals(interval.client2_ID)){
//    	  				clientID = clnt;
//    	  				 response = "<CLI_INT/> "+ Integer.toString(interval.client2_interval)+" "+clientID;
//    	  				outgate.write(response.getBytes(), 0, response.length());
//    	  			}
//    	  			if (response.length()==0);
//    	  			_txt_result_ttwt.append("SENT TO GATEWAY : " + response+"\n");
//    	  		}
//    	  		else if(cmd.equals(DATA)){
//    	  				String datwrite = tokenizer.nextToken()+" "+tokenizer.nextToken()+" "+tokenizer.nextToken();
//    	  				String response = "<DATA/> "+ datwrite;
//    	  				outapp.write(response.getBytes(), 0, response.length());
//    	  				_txt_result_ttwt.append("SENT TO APP     : " + response+"\n");
//    	  				StringTokenizer t = new  StringTokenizer(datwrite," ");
//    	  				FileWriter fileWriter = new FileWriter(file,true);
//    	  				fileWriter.append(t.nextToken()+","+t.nextToken()+","+t.nextToken()+"\n");
//    	  				fileWriter.close();
//    	  			if(interval.change == false){
//    	  				String res = "<OK/> 000000000000000000000000000000000000000000000000000000000000000000000";
//    	  				outgate.write(res.getBytes(), 0, response.length());
//    	  				_txt_result_ttwt.append("SENT TO GATEWAY : " + res+"\n");
//    	  			}
//    	  			else {
//    	  				String res = "<ITVC/> "+interval.gateway1_ID+" "+Integer.toString(interval.gateway1_interval)+" "+
//    	  						interval.gateway2_ID+" "+Integer.toString(interval.gateway2_interval)+" "+
//    	  						interval.client1_ID+" "+Integer.toString(interval.client1_interval)+" "+
//    	  						interval.client2_ID+" "+Integer.toString(interval.client2_interval);
//    	  				outgate.write(res.getBytes(), 0, response.length());
//    	  				_txt_result_ttwt.append("SENT TO GATEWAY : " + res+"\n");
//    	  				interval.change = false;
//    	  			}
//    	  		}   
//    	  		else { 
//    	  			String response = "<UNKNOWN/>";
//    	  			outgate.write(response.getBytes(), 0, response.length());
//    	  			_txt_result_ttwt.append("SENT TO GATEWAY : " + response+"\n");
//    	  		}
//    	  		
//    	  	}
//	      	
//
//	      	_txt_result_ttwt.append("gateway"+interval.gateway1_ID+" closed, " + appSock.getRemoteSocketAddress());
//
//		} catch (IOException ex) {
//			System.out.println("Exception in echo protocol"+ ex);
//		} finally {
//			try {
//				clntSock.close();
//			} catch (IOException e) {
//			}
//		}
//	}
//
//	public void run() {
//		handleEchoClient(clntSock, appSock, _txt_result_ttwt, file);
//	}
	
	public EchoProtocol(Socket clntSock, TextArea _txt_result_ttwt,File file, File filetemp) {
		this.clntSock = clntSock;
		this._txt_result_ttwt =_txt_result_ttwt;
		this.file=file;
		this.filetemp = filetemp;
	}

	public static void handleEchoClient(Socket clntSock, TextArea _txt_result_ttwt,File file, File filetemp) {
		try {
			// Get the input and output I/O streams from socket
    	
			InputStream ingate = clntSock.getInputStream();
			OutputStream outgate = clntSock.getOutputStream();
	
			int recvMsgSize; // Size of received message
	      	//int totalBytesEchoed = 0; // Bytes received from client
	      	byte[] echoBuffer = new byte[BUFSIZE]; // Receive Buffer
      
      
	      	
	      	String cmd;
	      	while ((recvMsgSize = ingate.read(echoBuffer)) != -1) {
    	  		String s = new String(echoBuffer);
    	  		_txt_result_ttwt.append("RECEIVED        : " + s+"\n");
    	  		System.out.println(s) ;
    	  		tokenizer = new StringTokenizer(s," ");
    	  		cmd = tokenizer.nextToken();
    	  		if(cmd.equals(INIT)){
    	  			String gate =tokenizer.nextToken();
    	  			System.out.println(gate);
    	  			String response = null;
    	  			if (gate.equals(interval.gateway1_ID)){
    	  				gatewayID = gate;
    	  				 response = "<INVL/> "+ Integer.toString(interval.gateway1_interval);
    	  				outgate.write(response.getBytes(), 0, response.length());
    	  				_txt_result_ttwt.append("Accepted connection with gateway: "+gatewayID+", "+ clntSock.getRemoteSocketAddress()+"\n");
    	  			}
    	  			else if (gate.equals(interval.gateway2_ID)){
    	  				gatewayID = gate;
    	  				 response = "<INVL/> "+ Integer.toString(interval.gateway2_interval);
    	  				outgate.write(response.getBytes(), 0, response.length());
    	  				_txt_result_ttwt.append("Connection accepted with gateway: "+gatewayID+", "+ clntSock.getRemoteSocketAddress()+"\n");
    	  			}
    	  			else {
    	  				 response = "<DENY/> 0000";
    	  				outgate.write(response.getBytes(), 0, response.length());
    	  				_txt_result_ttwt.append("Connection DENIED with gateway: "+gate+", "+ clntSock.getRemoteSocketAddress()+"\n");
    	  			}
    	  			_txt_result_ttwt.append("SENT TO GATEWAY : " + response+"\n");
    	  		}   						 
    	  		else if(cmd.equals(CLI_INT)){
    	  			String clnt =tokenizer.nextToken();
    	  			System.out.println(clnt);
    	  			String response = null ;
    	  			if (clnt.equals(interval.client1_ID )){
    	  				clientID = clnt;
    	  				 response = "<CLI_INT/> "+ Integer.toString(interval.client1_interval)+" "+clientID;
    	  				outgate.write(response.getBytes(), 0, response.length());
    	  			}
    	  			else if (clnt.equals(interval.client2_ID)){
    	  				clientID = clnt;
    	  				 response = "<CLI_INT/> "+ Integer.toString(interval.client2_interval)+" "+clientID;
    	  				outgate.write(response.getBytes(), 0, response.length());
    	  			}
    	  			if (response.length()==0);
    	  			_txt_result_ttwt.append("SENT TO GATEWAY : " + response+"\n");
    	  		}
    	  		else if(cmd.equals(DATA)){
    	  				String datwrite = tokenizer.nextToken()+" "+tokenizer.nextToken()+" "+tokenizer.nextToken();
    	  				String response = "<DATA/> "+ datwrite;
    	  				//outapp.write(response.getBytes(), 0, response.length());
    	  				
    	  				StringTokenizer t1 = new  StringTokenizer(datwrite," ");
    	  				FileWriter file1 = new FileWriter(filetemp);
    	  				file1.write(t1.nextToken()+","+t1.nextToken()+","+t1.nextToken()+"\n");
    	  				file1.flush();
    	  				file1.close();
    	  				
    	  				
    	  				StringTokenizer t = new  StringTokenizer(datwrite," ");
    	  				FileWriter fileWriter = new FileWriter(file,true);
    	  				fileWriter.append(t.nextToken()+","+t.nextToken()+","+t.nextToken()+"\n");
    	  				fileWriter.close();
    	  				interval.datatosend =true;
    	  			if(interval.change == false){
    	  				String res = "<OK/> 000000000000000000000000000000000000000000000000000000000000000000000";
    	  				outgate.write(res.getBytes(), 0, response.length());
    	  				_txt_result_ttwt.append("SENT TO GATEWAY : " + res+"\n");
    	  			}
    	  			else {
    	  				String res = "<ITVC/> "+interval.gateway1_ID+" "+Integer.toString(interval.gateway1_interval)+" "+
    	  						interval.gateway2_ID+" "+Integer.toString(interval.gateway2_interval)+" "+
    	  						interval.client1_ID+" "+Integer.toString(interval.client1_interval)+" "+
    	  						interval.client2_ID+" "+Integer.toString(interval.client2_interval);
    	  				outgate.write(res.getBytes(), 0, response.length());
    	  				_txt_result_ttwt.append("SENT TO GATEWAY : " + res+"\n");
    	  				interval.change = false;
    	  			}
    	  		}   
    	  		else { 
    	  			String response = "<UNKNOWN/>";
    	  			outgate.write(response.getBytes(), 0, response.length());
    	  			_txt_result_ttwt.append("SENT TO GATEWAY : " + response+"\n");
    	  		}
    	  		
    	  	}
	      	

	      	_txt_result_ttwt.append("gateway"+interval.gateway1_ID+" closed, " + clntSock.getRemoteSocketAddress()+"\n");

		} catch (IOException ex) {
			System.out.println("Exception in echo protocol"+ ex);
		} finally {
			try {
				clntSock.close();
			} catch (IOException e) {
			}
		}
	}

	public void run() {
		handleEchoClient(clntSock, _txt_result_ttwt, file, filetemp);
	}
}

