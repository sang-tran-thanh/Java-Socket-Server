package server;

import java.awt.TextArea;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;
import java.io.FileWriter;
import java.io.File;

public class apphandle implements Runnable {
	
	private static final int BUFSIZE = 128; // Size (in bytes) of I/O buffer            // Socket connect to client
	private Socket appSock;  
	TextArea _txt_result_ttwt;
	TextArea _txt_app_num;
	static StringTokenizer tokenizer;
	public File file;
	
	public apphandle( Socket appSock, TextArea _txt_result_ttwt, TextArea _txt_app_num,File file) {
		this.appSock = appSock; 
		this._txt_result_ttwt = _txt_result_ttwt;
		this.file = file; 
		this._txt_app_num = _txt_app_num;
	}

	public static void handleapp(Socket appSock, TextArea _txt_result_ttwt, TextArea _txt_app_num, File file) {
	    try {
	    	// Get the input and output I/O streams from socket
	    	InputStream inapp = appSock.getInputStream();
	    	
	
	    	int recvMsgSize; // Size of received message
	    	//int totalBytesEchoed = 0; // Bytes received from client
	    	byte[] echoBuffer = new byte[BUFSIZE]; // Receive Buffer
	    	String cmd;
	
	    	Thread thr = new Thread(new datatoapp(appSock, _txt_result_ttwt, file));
		    thr.start();
	    	
	    	while ((recvMsgSize = inapp.read(echoBuffer)) != -1) {
	    		String s = new String(echoBuffer);
	    		System.out.println(s.length()) ;
	    		_txt_result_ttwt.append("RECEIVED        : "+s+"\n");
    	  		tokenizer = new StringTokenizer(s," ");
    	  		
    	  		while(tokenizer.hasMoreTokens()){
    	  			cmd = tokenizer.nextToken();
    	  			System.out.println(cmd);
    	  			if(cmd.equals(interval.client1_ID)){
    	  				interval.client1_interval=Integer.parseInt(tokenizer.nextToken());
    	  				System.out.println("valua : "+interval.client1_interval);
    	  			}
    	  			else if(cmd.equals(interval.client2_ID)){
        	  			interval.client2_interval=Integer.parseInt(tokenizer.nextToken());
        	  			System.out.println("valua : "+interval.client2_interval);
    	  			}
    	  			else if(cmd.equals(interval.gateway1_ID)){
        	  			interval.gateway1_interval=Integer.parseInt(tokenizer.nextToken());
        	  			System.out.println("valua : "+interval.gateway1_interval);
    	  			}
    	  			else if(cmd.equals(interval.gateway2_ID)){
        	  			interval.gateway2_interval=Integer.parseInt(tokenizer.nextToken());
        	  			System.out.println("valua : "+interval.gateway2_interval);
    	  			}
    	  				
    	  		}
    	  		_txt_result_ttwt.append("GOT INTERVAL    : "+s+"\n");
    	  		interval.change = true; 
    	  		
	    	}
	    	interval.app_num--;
	    	//interval.datatosend_num --;
	    	_txt_app_num.setText(Integer.toString(interval.app_num));
	    	_txt_result_ttwt.append("app closed, " + appSock.getRemoteSocketAddress()+"\n");
	
	    } catch (IOException ex) {
	    	System.out.println("Exception in echo protocol"+ ex);
	    } finally {
	    	try {
	    		appSock.close();
	    	} catch (IOException e) {
	    	}
	    }
	}
	
	public void run() {
		handleapp(appSock,_txt_result_ttwt,_txt_app_num, file);
	}
}
