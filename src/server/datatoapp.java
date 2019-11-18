package server;

import java.awt.TextArea;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.OutputStream;


public class datatoapp implements Runnable {
	//private static boolean send = false;
	private Socket appSock;
	TextArea _txt_result_ttwt;
	static StringTokenizer tokenizer;
	public File file;
	public datatoapp( Socket appSock, TextArea _txt_result_ttwt, File file) {
		this.appSock = appSock; 
		this._txt_result_ttwt = _txt_result_ttwt;
		this.file = file; 
	}

	public static void handledatatoapp(Socket appSock, TextArea _txt_result_ttwt,File file) throws IOException {
		OutputStream outapp = appSock.getOutputStream();
		while (true) {
			if(interval.datatosend==true ){//&& send==false){
				Scanner sc = new Scanner(file);
				String line = sc.nextLine();
	        	String elements[] = line.split(",");
	        	String response = elements[0]+ " " +elements[1]+ "\n";
	        	outapp.write(response.getBytes(), 0, response.length());
  				_txt_result_ttwt.append("SENT TO APP     : " + response);
  				sc.close();
  				try {
  					Thread.sleep(50);
  				} catch (InterruptedException e) {
  					// TODO Auto-generated catch block
  					e.printStackTrace();
  				}
  				interval.datatosend=false;
  				//send = true;
  				//interval.datatosend_num --;
//  				if(interval.datatosend_num==0){
//  					interval.datatosend = false;
//  					interval.datatosend_num=interval.app_num;
//  					send = false;
//  				}
			}
//			else if (interval.datatosend==true && send==true && interval.datatosend_num<=0){
//				interval.datatosend = false;
//				interval.datatosend_num=interval.app_num;
//				send = false;
//
//			}
			else System.out.print("f");

	    }
	}
	
	public void run() {
		try {
			handledatatoapp(appSock,_txt_result_ttwt, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
	}
}
