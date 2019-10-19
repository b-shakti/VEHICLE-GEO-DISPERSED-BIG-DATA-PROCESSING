package Geo_dispersed_bigData;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import Enums.SampleValues;
import Database.*;
import Enums.IpAddress;
import Enums.ports;
import utils.*;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.xml.soap.Text;

import utils.Utils;

public class User {

	//	JFrame jf;
	JLabel l1,l2;
	JTextField tf1;
	static HumanProgressBar progressBar;
	public static  JLabel text = null;
	public static int status = 0;
	JTextArea ta1;
	ServerSocket cs_server;
	static JFrame jft;
	int port=5001;
	static BigInteger sign;
	JButton b1,b2,b3,b4,b5,b6;
	public static String File_path, fileSize ;
	static Socket u_socket;
	static ServerSocket u_server;
	static DataInputStream in;
	static DataOutputStream out;
	String line,file_data,file_name,packet,u_name;
	Socket client;
	String user_num;
	static DefaultListModel files ;
	BigInteger c;
	JFrame jf=null;

	User(String username)
	{
		u_name=username;
		jf = new JFrame(u_name);
		jf.setLayout(null);

		ta1=new JTextArea();
		//ta1.setBorder(BorderFactory.createEtchedBorder(new Color(153,204,255),new Color(153,204,255)));
		final JScrollPane jsp1=new JScrollPane(ta1,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jsp1.setBounds(20, 20, 240, 280);
		ta1.setEditable(false);
		jf.add(jsp1);

		b1=new JButton("Insert Record");
		b1.setBounds(280,30,140,30);
		jf.add(b1);

		b2=new JButton("Generate Records");
		b2.setBounds(280,90,140,30);
		jf.add(b2);

		b3=new JButton("Upload Dataset");
		b3.setBounds(280,150,140,30);
		jf.add(b3);

		b4=new JButton("Get vehical info");
		b4.setBounds(280,210,140,30);
		jf.add(b4);

		b5=new JButton("Exit");
		b5.setBounds(280,270,140,30);
		jf.add(b5);

		l1=new JLabel(new ImageIcon("images/User.png"));
		l1.setBounds(0,0,450,350);
		jf.add(l1);

		jf.setVisible(true);
		jf.setSize(450, 350);

		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Action listener to insert a record manually
				new InsertVehicalInfo();
			}});
	
 
		b2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Action listener to randomly generate dataset
				displayProgressBar();
				Thread stat = new Thread() 
				{
					public void run()
					{
						final int numberOfRecords = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of records to generate automatically"));
						JOptionPane.showMessageDialog(null, "Generating "+numberOfRecords+" records");
						int size = 100/numberOfRecords;
						StringBuffer data = new StringBuffer("");
						Random random = new Random();
						String districts [] = Utils.getAllDistrictCodeInArray();
						for(int i=0;i<numberOfRecords;i++)
						{
							//generate bike name
							data.append(SampleValues.bikes[random.nextInt(SampleValues.bikes.length-1)]+",");
							//generate bike color
							data.append(SampleValues.colors[random.nextInt(SampleValues.colors.length-1)]+",");
							//generate random registration date
							data.append(Utils.generateRandamDate()+",");
							//generate random place of registration

							String selectedDistrict = districts[random.nextInt(districts.length-1)];
							data.append(selectedDistrict+",");
							StringBuffer tempData = new StringBuffer("");
							//generate charsi number
							do{
								tempData.append(random.nextInt(10));
							}while(tempData.length()<=10);
							data.append(tempData.toString().trim()+",");
							tempData.delete(0, tempData.length());
							//generate vehical number
							tempData.append(Utils.getDistrictCode(selectedDistrict)+" ");
							tempData.append((SampleValues.characters[random.nextInt(SampleValues.characters.length-1)]+"").toUpperCase()+" "+random.nextInt(5000));
							data.append(tempData.toString().trim().replaceAll(" ", "-")+",");
							tempData.delete(0, tempData.length());
							//generate owner name
							int number = new Random().nextInt(10);
							do
							{
								tempData.append((SampleValues.characters[random.nextInt(SampleValues.characters.length-1)]+"").toUpperCase());
							}while(tempData.length()<=number);
							data.append(tempData+",");
							tempData.delete(0, tempData.length());
							//generate licence number
							tempData.append("KA");
							for(int j=0;j<10;j++)
								tempData.append(random.nextInt(10));
							data.append(tempData+"\n");


							try
							{
								Thread.sleep(200);
							}
							catch(Exception e4)
							{
								e4.printStackTrace();
							}
							status = i * size;
							progressBar.setValue(status);
							System.out.println(data);
						}

						progressBar.setValue(100);
						text.setText(numberOfRecords+" records generated sucessfully");
						Utils.appendDataToFile(data.toString().replaceAll("\\r\\n", ""));
						JOptionPane.showMessageDialog(null, "Records generated sucessfully");
						jft.setVisible(false);
					}};
					stat.start();

			}});

		b3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Action listener to upload dataset to data server
				try {
					File file = new File("dataset/dataset.txt");
					if(file.exists())
					{
						Socket client = new Socket(IpAddress.cloudlet.get_ip(),ports.cloudlet.get_port());
						DataOutputStream out = new DataOutputStream(client.getOutputStream());
						DataInputStream in = new DataInputStream(client.getInputStream());
						String datasetPath = file.getCanonicalPath();
						System.out.println(datasetPath);
						//						Runtime.getRuntime().exec("hdfs dfs -rm dataset.txt");
						out.writeUTF("upload dataset");
						out.writeUTF(datasetPath);
						if(in.readUTF().equals("done"))
							JOptionPane.showMessageDialog(null, "data set uploaded sucessfully");
						in.close();
						out.close();
						client.close();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "dataset.txt does not exist in dataset folder");
					}
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}});


		b4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Action listener to mining button
				StringBuffer input = new StringBuffer("");
				input.append(JOptionPane.showInputDialog( "Enter the color of bike to get vehical info")+",");
				input.append(JOptionPane.showInputDialog( "Enter the Vehical number of bike to get vehical info"));
				try {
					client = new Socket(IpAddress.cloudlet.get_ip(),ports.cloudlet.get_port());
					System.out.println("sending request to cloudlet to get vehical info");
					in = new DataInputStream(client.getInputStream());
					out = new DataOutputStream(client.getOutputStream());
					Runtime.getRuntime().exec("hdfs dfs -rmr tempDataset");
					Runtime.getRuntime().exec("hdfs dfs -rmr finalResult");
					out.writeUTF("vehical info");
					out.writeUTF(input+"");
					if(in.readUTF().equals("done"))
					{
						String finalResultToDisplay = in.readUTF();
						JOptionPane.showMessageDialog(null, "Recieved encrypted data\n"+finalResultToDisplay+"\n Decrypting it");
						JOptionPane.showMessageDialog(null, ECC.decrypt(finalResultToDisplay));
					}
					else
						JOptionPane.showMessageDialog(null, "failed to get info");
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}

			}});


		b5.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				jf.setVisible(false);
			}});

	}



	protected void displayProgressBar() {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				jft = new JFrame("Progress on generating records");
				jft.setLayout(null);
				jft.setVisible(true); 
				jft.setSize(500,200);

				JPanel jp1 = new JPanel();
				jp1.setLayout(null);
				jp1.setBounds(10,10, 520,40);
				jft.add(jp1);

				progressBar = new HumanProgressBar();
				progressBar.setValue(0);
				progressBar.setStringPainted(true);
				Border border = BorderFactory.createTitledBorder("Battery indicator");
				progressBar.setBorder(border);
				progressBar.setBounds(50, 0, 340, 40);
				progressBar.setForeground(Color.green);
				progressBar.setFont(new Font("Serif", Font.BOLD, 16));
				jp1.add(progressBar, BorderLayout.NORTH);

				text = new JLabel("records to generate");
				text.setBounds(40, 80, 400, 30);
				text.setFont(new Font("Serif", Font.BOLD, 22));
				jft.add(text);

			}
		});

	}



	private void addcomponent(Container cp,Component c, int startx, int starty, int width, int height) {
		// TODO Auto-generated method stub
		c.setBounds(startx,starty,width,height);
		cp.add(c);

	}

	public static void main(String[] s){
		try
		{
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");

		}
		catch (Exception ex)

		{
			System.out.println("Failed loading L&F: ");
			System.out.println(ex);
		}
		new User("asd");
	}


}
