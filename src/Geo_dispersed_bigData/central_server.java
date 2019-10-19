package Geo_dispersed_bigData;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;

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
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

import utils.Utils;

import Database.JDBC;
import Enums.IpAddress;
import Enums.ports;

public class central_server implements Runnable {

	static JFrame jf;
	JFrame jf1;
	JLabel jl,j2,j3,l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13,l14;
	JPanel jp,jp1,jp2;
	JButton b1,b2,b3;
	static Socket cs_socket;
	public static String ticket;
	static ServerSocket cs_server;
	static DataInputStream in;
	static DataOutputStream out;
	static String file_name ;
	static String file_data;
	String line,name;
	static int i=0;



	@Override
	public void run() {
		// TODO Auto-generated method stub
		start_application();
	}

	private void start_application() {
		// TODO Auto-generated method stub

		jf=new JFrame("Data Server");
		jf.setLayout(null);
		jf.setSize(750,550);
		jf.setVisible(true);

		jp=new JPanel();
		jp.setBounds(5,8,732,50);
		jp.setBackground(new Color(165,42,42));
		jp.setLayout(null);
		jf.add(jp);


		jl=new JLabel("Processing Geo-Dispersed Big Data in an Advanced MapReduce Framework",JLabel.CENTER);
		jl.setForeground(Color.YELLOW);
		//	jl.setBackground(Color.CYAN);
		jl.setFont(new Font("serial",Font.BOLD,18));
		jl.setBounds(10, 0, 732,30);
		jp.add(jl);

		l1=new JLabel("--Central Server--",JLabel.CENTER);
		l1.setForeground(Color.YELLOW);
		l1.setFont(new Font("serial",Font.BOLD,18));
		l1.setBounds(10, 20, 732,30);
		jp.add(l1);

		j2=new JLabel(new ImageIcon("images/cloaud.png"));
		j2.setBounds(80,110,200,75);
		jf.add(j2);

		l3=new JLabel(new ImageIcon("images/cloaud.png"));
		l3.setBounds(450,110,200,75);
		jf.add(l3);

		l4=new JLabel(new ImageIcon("images/member.png"));
		l4.setBounds(0,250,100          ,100);
		jf.add(l4);

		l11=new JLabel(new ImageIcon("images/member.png"));
		l11.setBounds(390,250,100,100);
		jf.add(l11);

		l5=new JLabel(new ImageIcon("images/Download.png"));
		l5.setBounds(670,190,70,70);
		jf.add(l5);

		l6=new JLabel(new ImageIcon("images/Download.png"));
		l6.setBounds(670,290,70,70);
		jf.add(l6);

		l7=new JLabel(new ImageIcon("images/Download.png"));
		l7.setBounds(670,390,70,70);
		jf.add(l7);

		l8=new JLabel(new ImageIcon("images/upload.png"));
		l8.setBounds(320,190,70,70);
		jf.add(l8);

		l9=new JLabel(new ImageIcon("images/upload.png"));
		l9.setBounds(320,290,70,70);
		jf.add(l9);

		l10=new JLabel(new ImageIcon("images/upload.png"));
		l10.setBounds(320,390,70,70);
		jf.add(l10);

		l12=new JLabel(new ImageIcon("images/a.png"));
		l12.setBounds(50,140,100,150);
		jf.add(l12);

		l13=new JLabel(new ImageIcon("images/a.png"));
		l13.setBounds(440,150,100,150);
		jf.add(l13);


		j3=new JLabel("File upload");
		//jl.setForeground(Color.YELLOW);
		//jl.setBackground(Color.CYAN);
		j3.setFont(new Font("serial",Font.BOLD,16));
		j3.setBounds(45, 65, 700,30);
		jf.add(j3);

		l2=new JLabel("File download");
		//jl.setForeground(Color.YELLOW);
		//jl.setBackground(Color.CYAN);
		l2.setFont(new Font("serial",Font.BOLD,16));
		l2.setBounds(380, 65, 700,30);
		jf.add(l2);

		b3=new JButton("Exit");
		b3.setBounds(650, 475, 70,25);
		jf.add(b3);

		JButton b6=new JButton("Extract from file");
		b6.setBounds(490, 475, 135,25);
		b6.setToolTipText("select file which has vechical registration codes to extract");
		jf.add(b6);


		b2=new JButton("Add District code");
		b2.setBounds(30,475, 130,25);
		jf.add(b2);

		JButton b4=new JButton("View District code");
		b4.setBounds(180, 475, 135,25);
		jf.add(b4);

		JButton b5=new JButton("Delete District code");
		b5.setBounds(330, 475, 145,25);
		jf.add(b5);


		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});

		b6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				JFileChooser fc=new JFileChooser("pradeep");
				int option = fc.showOpenDialog(null);
				if(option == JFileChooser.APPROVE_OPTION)
				{
					try{
						int b;
						file_data="";
						String sf=fc.getSelectedFile().getAbsolutePath();
						FileInputStream in = new FileInputStream(sf);
						byte str[] = new byte[in.available()];
						in.read(str,0,str.length);
						String data = new String (str);
						Utils.insertDistrictInfoFromFile(data);
					}
					catch (Exception ee)
					{
						ee.printStackTrace();
						JOptionPane.showMessageDialog(null,"Reload File Again..");
					}
				}
			}});

		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){

				final JFrame jft = new JFrame("Insert district code info");
				jft.setLayout(null);
				jft.setVisible(true); 
				jft.setSize(400,200);

				JLabel l1 = new JLabel("District Name");
				addcomponent(jft, l1, 10, 10, 100, 30);

				final JTextField name = new JTextField();
				addcomponent(jft, name, 100, 10, 200, 30);

				JLabel l2 = new JLabel("District Code");
				addcomponent(jft, l2, 10, 50, 100, 30);

				final JTextField code = new JTextField();
				addcomponent(jft, code, 100, 50, 200, 30);

				JButton save = new JButton("Save");
				addcomponent(jft, save, 40, 90, 100, 30);

				JButton close = new JButton("Close");
				addcomponent(jft, close, 150, 90, 100, 30);

				close.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						jft.setVisible(false);
					}});

				save.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						String dcode = code.getText();
						String dname = name.getText();

						if(	Utils.insertDistrictInfo(dname, dcode))
							JOptionPane.showMessageDialog(null, dname+" district info saved sucessfully");
						jft.setVisible(false);
					}});
			}});




		b4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				//Action listener to display all items available
				DefaultListModel district = Utils.getAllDistrictCode();

				final JFrame jf1 = new JFrame();
				jf1.setLayout(null);

				ImageIcon icon2= new ImageIcon("images/server_info.jpg");
				JLabel l6 = new JLabel(icon2,JLabel.CENTER);

				JLabel l1 = new JLabel("List of District:",JLabel.CENTER);
				l1.setFont(new Font("Serif",Font. BOLD,20));

				final JList ta10 = new JList();
				JScrollPane jsp = new JScrollPane(ta10,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
				ta10.setModel(district);

				final JButton b11 = new JButton("Exit");

				final JButton b12 = new JButton("Get District code");


				addcomponent(jf1,l1,10,20,300,20);
				addcomponent(jf1,jsp,10,50,250,180);
				addcomponent(jf1,b12,20,235,150,25);
				addcomponent(jf1,b11,180,235,80,25);
				addcomponent(jf1,l6,0,0,300,300);
				jf1.setSize(300, 300);
				jf1.setVisible(true);

				b11.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						jf1.setVisible(false);
					}});
				b12.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						String districtName = ta10.getSelectedValue().toString();
						JOptionPane.showMessageDialog(null, districtName+" : "+Utils.getDistrictCode(districtName).toUpperCase());
					}});
			}
		});

		b5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				//Action listener to delete item
				DefaultListModel items = Utils.getAllDistrictCode();

				final JFrame jf1 = new JFrame();
				jf1.setLayout(null);

				ImageIcon icon2= new ImageIcon("images/server_info.jpg");
				JLabel l6 = new JLabel(icon2,JLabel.CENTER);

				JLabel l1 = new JLabel("List of Districts:",JLabel.CENTER);
				l1.setFont(new Font("Serif",Font. BOLD,20));

				final JList ta10 = new JList();
				JScrollPane jsp = new JScrollPane(ta10,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
				ta10.setModel(items);

				final JButton b11 = new JButton("Exit");

				final JButton b12 = new JButton("Delete District info");

				addcomponent(jf1,l1,10,20,300,20);
				addcomponent(jf1,jsp,10,50,250,180);
				addcomponent(jf1,b12,20,235,150,25);
				addcomponent(jf1,b11,180,235,80,25);
				addcomponent(jf1,l6,0,0,300,300);
				jf1.setSize(300, 300);
				jf1.setVisible(true);

				b11.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						jf1.setVisible(false);
					}});

				b12.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						String districtInfoToDelete = "";
						districtInfoToDelete = ta10.getSelectedValue().toString();
						jf1.setVisible(false);
						if(Utils.deleteDistrictInfo(districtInfoToDelete))
							JOptionPane.showMessageDialog(null, "District info Deleted sucessfully");

					}});
			}});


		Runnable run = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					ServerSocket serer = new ServerSocket(ports.cloud_server.get_port());
					while(true)
					{
						System.out.println("Server waiting @ "+serer.getLocalPort());
						Socket client = serer.accept();
						out = new DataOutputStream(client.getOutputStream());
						in = new DataInputStream(client.getInputStream());
						String type = in.readUTF();
						if(type.equals("get info"))
						{
						
							String color = in.readUTF();
							System.out.println("calling ./Hadoop_jars/FilterBasedOnColor.jar");
							File file = new File("./Hadoop_jars/FilterBasedOnColor.jar");
							String command = "hadoop jar Hadoop_jars/FilterBasedOnColor.jar Client1 "+color;
							System.out.println(command);
							Process p1 = Runtime.getRuntime().exec(command);

							BufferedReader stdinput1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
							String s1 = "";
							while((s1 = stdinput1.readLine())!=null)
							{
								System.out.println(s1);
							}				
							JOptionPane.showMessageDialog(null, "Filter based on color is completed sucessfully!!!");
							out.writeUTF("done");
							System.out.println("completed");
						}
						

					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		new Thread(run).start();
	}

	public static void addcomponent(Container cp,Component c, int startx, int starty, int width, int height) {
		// TODO Auto-generated method stub
		c.setBounds(startx,starty,width,height);
		cp.add(c);
	}


	public static void main(String[] args) {
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
		central_server obj = new central_server();
		new Thread(obj).start();
	}
}
