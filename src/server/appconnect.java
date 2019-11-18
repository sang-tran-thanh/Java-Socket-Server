package server;

import java.awt.TextArea;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.StringTokenizer;
import java.net.ServerSocket;

public class appconnect implements Runnable {
	private ServerSocket appSock;
	TextArea _txt_result_ttwt;
	TextArea _txt_app_num;
	static StringTokenizer tokenizer;
	public File file;
	
	public appconnect( ServerSocket appSock, TextArea _txt_result_ttwt,TextArea _txt_app_num, File file) {
		this.appSock = appSock; 
		this._txt_result_ttwt = _txt_result_ttwt;
		this.file = file; 
		this._txt_app_num = _txt_app_num;
	}

	public static void handleappconnect(ServerSocket appSock, TextArea _txt_result_ttwt, TextArea _txt_app_num,File file) throws IOException {
		while (true) {	    	
		    Socket app = appSock.accept();// Block waiting for connection
		    //interval.appconnect =true;
		    interval.app_num ++;
		    //interval.datatosend_num ++;
		    _txt_app_num.setText(Integer.toString(interval.app_num));
		    System.out.println(app);
		    Thread thr = new Thread(new apphandle(app, _txt_result_ttwt, _txt_app_num, file));
		    thr.start();
		    System.out.println("App connected to server");//. with "+thr.getName() );
		    _txt_result_ttwt.append("App connected to server"+"\n");//. with "+thr.getName() );
	    }
	}
	
	public void run() {
		try {
			handleappconnect(appSock,_txt_result_ttwt, _txt_app_num , file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
