package Geo_dispersed_bigData;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream.GetField;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javax.crypto.CipherOutputStream;
import javax.swing.*;

import Database.JDBC;
import Enums.IpAddress;
import Enums.ports;

public class Cloudlet extends Thread {

	static DefaultListModel model2=new DefaultListModel();
	static JTextArea t2=new JTextArea();
	public 	static JList t1;
	static ServerSocket server;
	static Socket client;
	static DataInputStream in;
	static DataOutputStream out;
	static int count=0;
	static JFrame f1;
	static Cloudlet a=new Cloudlet();

	public void initi() {


		f1=new JFrame("Cloudlet::Processing Geo-Dispersed Big Data");
		f1.setVisible(true);
		f1.setLayout(null);
		f1.setSize(500, 500);

		JLabel l2=new JLabel("Processing Geo-Dispersed Big Data in",JLabel.CENTER);
		l2.setFont(new Font("Brush Script M", Font.BOLD, 23));
		add_compo(f1, l2, 0, 10, 500, 25);

		JLabel l3=new JLabel("Advanced MapReduce Framework",JLabel.CENTER);
		l3.setFont(new Font("Brush Script M", Font.BOLD, 22));
		add_compo(f1, l3, 0, 35, 500, 25);

		JLabel l4=new JLabel("Cloudlet",JLabel.CENTER);
		l4.setFont(new Font("Brush Script M", Font.BOLD, 18));
		add_compo(f1, l4, 0, 60, 500, 40);

		JLabel l5=new JLabel("Users");
		l5.setFont(new Font("Brush Script M",Font.BOLD, 14));
		add_compo(f1, l5, 30, 100, 100, 20);

		JLabel l6=new JLabel("Status history");
		l6.setFont(new Font("Brush Script M",Font.BOLD, 14));
		add_compo(f1, l6, 250, 100, 150, 20);

		t1=new JList();
		t1.setFont(new Font("Brush Script M",Font.BOLD,15));
		JScrollPane s1=new JScrollPane(t1);
		add_compo(f1,s1,20,130,200,250);
		t1.setBorder(BorderFactory.createEtchedBorder(new Color(153,204,255),new Color(153,204,255)));


		//t2.setFont(new Font("Brush Script M",Font.BOLD,15));
		t2.setLineWrap(true);
		JScrollPane s2=new JScrollPane(t2);
		t2.setEditable(false);
		t2.setBorder(BorderFactory.createEtchedBorder(new Color(153,204,255),new Color(153,204,255)));
		add_compo(f1,s2,250,130,200,250);

		//		JButton b1=new JButton("User detail");
		//		b1.setToolTipText("select a user in user list to view details");
		//		add_compo(f1, b1, 40, 400, 150, 30);

		JButton b2=new JButton("exit");
		add_compo(f1, b2, 300, 400, 100, 30);

		JLabel l1=new JLabel(new ImageIcon("images/a.jpg"));
		l1.setBounds(0, 0, 500, 500);
		f1.add(l1);

		DefaultListModel users = JDBC.getAllUserList();
		t1.setModel(users);

		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}});


	}

	public static void add_compo(Container c,Component p,int x,int y,int w,int h)
	{
		p.setBounds(x,y,w,h);
		c.add(p);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		a.initi();
	}

	public static void main(String[] args) throws Exception
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try
		{
			UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		}
		catch (Exception ex)
		{
			System.out.println("Failed loading L&F: ");
			System.out.println(ex);
		}
		a.start();
		try {
			server = new ServerSocket(ports.cloudlet.get_port());
			System.out.println("cloudlet server waiting @ "+server.getLocalPort());
			while(true)
			{
				client = server.accept();
				System.out.println("accepted request from client");
				in = new DataInputStream(client.getInputStream());
				out = new DataOutputStream(client.getOutputStream());
				String type = in.readUTF();
				if(type.equals("upload dataset"))
				{
					t2.setText("Recieved request to upload dataset\n"+new Date()+"---------\n"+t2.getText());
					String path = in.readUTF();
					String command = "hdfs dfs -put -f "+path.trim();
					System.out.println(command);
					Process p1 = Runtime.getRuntime().exec(command);
					BufferedReader stdinput1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
					String s1 = "";
					while((s1 = stdinput1.readLine())!=null)
					{
						System.out.println(s1);
					}	
					System.out.println(new File(path).getName()+" file uploaded to HDFS");
					//					JOptionPane.showMessageDialog(null, new File(path).getName()+" file uploaded to HDFS");
					out.writeUTF("done");
				}
				else
				{
					String input = in.readUTF();
					String splits[] = input.split(",");
					if(splits.length == 2)
					{
						t2.setText("Recieved request to filter\nsending request to central server\n"+new Date()+"---------\n"+t2.getText());
						if(sendRequestToCentralServer(splits[0]).equals("done"))
						{
							t2.setText("Filter based on color completed!!!\n"+new Date()+"---------\n"+t2.getText());
							System.out.println("started to filter number");	
							String command = "hadoop jar Hadoop_jars/FilterBasedOnNumber.jar Client1 "+splits[1];
							System.out.println(command);
							Process p1 = Runtime.getRuntime().exec(command);

							BufferedReader stdinput1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
							String s1 = "";
							while((s1 = stdinput1.readLine())!=null)
							{
								System.out.println(s1);
							}
							out.writeUTF("done");

							t2.setText("Filter based on number completed!!!\n"+new Date()+"---------\n"+t2.getText());

							//get vehical info
							Process p = Runtime.getRuntime().exec("hdfs dfs -cat finalResult/part-00000");

							BufferedReader stdinput = new BufferedReader(new InputStreamReader(p.getInputStream()));
							String s = "";
							StringBuffer finalResult = new StringBuffer("");
							StringBuffer finalResultToDisplay = new StringBuffer("");
							while((s = stdinput.readLine())!=null)
							{
								finalResult.append(s);
							}
							if(finalResult.toString().trim().length() !=0)
							{
								String asd_splits[] = finalResult.toString().split(",");
								finalResultToDisplay.append("Vehical Information \n");
								finalResultToDisplay.append("Vehical name : "+asd_splits[0]+"\n");
								finalResultToDisplay.append("Color : "+asd_splits[1]+"\n");
								finalResultToDisplay.append("Date of registration : "+asd_splits[2]+"\n");
								finalResultToDisplay.append("Place of registration : "+asd_splits[3]+"\n");
								finalResultToDisplay.append("Charsi number : "+asd_splits[4]+"\n");
								finalResultToDisplay.append("Vehical number : "+asd_splits[5]+"\n");
								finalResultToDisplay.append("Owner name : "+asd_splits[6]+"\n");
								finalResultToDisplay.append("licence number : "+asd_splits[7]);

							}
							else
							{
								String inputs[] = input.toString().split(",");
								finalResultToDisplay.append("Result not available for vehical info\nColor : "+inputs[0]+"\nVehical number : "+inputs[1]);
							}

							String encrypted_data = ECC.encryption(finalResultToDisplay.toString());
							out.writeUTF(encrypted_data);
						}
						else
							out.writeUTF("failed");

					}
					else
					{
						out.writeUTF("failed");
					}
				}
				in.close();
				out.close();
				client.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	private static String sendRequestToCentralServer(String color) {
		// TODO Auto-generated method stub
		try {
			Socket clien = new Socket(IpAddress.cloud_server.get_ip(),ports.cloud_server.get_port());
			System.out.println("sending request to cloudserver");
			DataOutputStream ot = new DataOutputStream(clien.getOutputStream());
			DataInputStream inn = new DataInputStream(clien.getInputStream());
			ot.writeUTF("get info");
			ot.writeUTF(color);
			if(inn.readUTF().equals("done"))
				return "done";

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "notDone";
	}


}
