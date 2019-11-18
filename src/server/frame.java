package server;

import java.awt.*;
import java.awt.event.*;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import java.util.Scanner;
import javax.swing.*;

public class frame 
	extends JFrame
	implements ActionListener, ItemListener	{
	
	private static final long serialVersionUID = 1L;
	
	JScrollPane _scrollpane;
	Font _font_job;
	
	int i =0;// ports start flag
//	int j =0;// server stop flag
	
	JLabel _lbl_import;
	JTextField _txt_import;
	
	JLabel _lbl_gatport;
	JTextField _txt_gatport;
		
	JButton _btn_save;
//	JButton _btn_stop;
	
	JLabel _lbl_app_num;
	TextArea _txt_app_num;
	JLabel _lbl_gat_num;
	TextArea _txt_gat_num;
	
	JLabel _lbl_TT;
	TextArea _txt_result_ttwt;
	
	public frame()	{
		initUI();
	}
	public TextArea getarea(){
		return _txt_result_ttwt;
	}
	public TextArea get_app_area(){
		return _txt_app_num;
	}
	public TextArea get_gat_area(){
		return _txt_gat_num;
	}
	public int getflag(){
		return i;
	}
//	public int getserverflag(){
//		return i;
//	}
	void initUI()	{
		
		_font_job = new Font("Times New Roman", Font.PLAIN, 16);
								
				
		_lbl_import = new JLabel("app port : ");
		_lbl_import.setBounds(20, 20, 100, 20);
		_txt_import = new JTextField();
		_txt_import.setBounds(120, 20, 100, 25);
		
		_lbl_gatport = new JLabel("gateway port : ");
		_lbl_gatport.setBounds(20, 50, 100, 20);
		_txt_gatport = new JTextField();
		_txt_gatport.setBounds(120, 50, 100, 25);
		
		_btn_save = new JButton("Start");
		_btn_save.setBounds(240, 35, 100, 25);
		_btn_save.addActionListener(this);

//		_btn_save = new JButton("Stop");
//		_btn_save.setBounds(360, 35, 100, 25);
//		_btn_save.addActionListener(this);
		
		_lbl_app_num = new JLabel("number of current connected app(s) :");
		_lbl_app_num.setBounds(550, 20, 250, 25);
		_txt_app_num = new TextArea("0", 540, 120, TextArea.SCROLLBARS_NONE);
		_txt_app_num.setBounds(810, 20, 50, 25);
		_txt_app_num.setEditable(false);
		
		_lbl_gat_num = new JLabel("number of current connected gateway(s) :");
		_lbl_gat_num.setBounds(550, 50, 250, 25);
		_txt_gat_num = new TextArea("0", 540, 120, TextArea.SCROLLBARS_NONE);
		_txt_gat_num.setBounds(810, 50, 50, 25);
		_txt_gat_num.setEditable(false);
		
		
		_lbl_TT = new JLabel("Server Log:...");
		_lbl_TT.setBounds(20, 85, 200, 25);
		_txt_result_ttwt = new TextArea("", 540, 120, TextArea.SCROLLBARS_VERTICAL_ONLY);
		_txt_result_ttwt.setBounds(20, 120, 840, 320);
		_txt_result_ttwt.setEditable(false);		
		
		this.setLayout(null);
		
				
		this.add(_lbl_import);
		this.add(_txt_import);
		
		this.add(_lbl_gatport);
		this.add(_txt_gatport);
		
		this.add(_btn_save);
//		this.add(_btn_stop);
		
		this.add(_lbl_app_num);
		this.add(_txt_app_num);
		
		this.add(_lbl_gat_num);
		this.add(_txt_gat_num);
		
		this.add(_lbl_TT);
		this.add(_txt_result_ttwt);
		
		this.setSize(900, 500);
		this.setTitle("The Computer Network IoT Server ");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	void refreshList()	{
	}
	
	
	
	public void actionPerformed(ActionEvent e)	{
		if(e.getSource() == _btn_save)	{
			String	s= _txt_import.getText();
			interval.DataPort = Integer.parseInt(s);
			s =_txt_gatport.getText();
			interval.GatewayPort = Integer.parseInt(s);
			if(interval.DataPort<0|| interval.GatewayPort<0||interval.DataPort>65535|| interval.GatewayPort>65535 || interval.DataPort== interval.GatewayPort){
				JOptionPane.showMessageDialog(null,"The two ports must be different and in the range from 0 to 65535", 
						"ERROR", JOptionPane.ERROR_MESSAGE);
			}
			else {
				
				JOptionPane.showMessageDialog(null,"server started with app port: "+Integer.toString(interval.DataPort)+", gateway port: "+Integer.toString(interval.GatewayPort), 
						"OK", JOptionPane.INFORMATION_MESSAGE);
				i=1;
			}
			
			//System.out.println("app port: "+Integer.toString(interval.DataPort)+", gateway port: "+Integer.toString(interval.GatewayPort));
		}
//		if(e.getSource() == _btn_save)	{
//			
//		}
	}
	
	public void itemStateChanged(ItemEvent e)	{
	}

	public static void main(String[] args) {
		System.out.println("start");
		new frame();
	}

}