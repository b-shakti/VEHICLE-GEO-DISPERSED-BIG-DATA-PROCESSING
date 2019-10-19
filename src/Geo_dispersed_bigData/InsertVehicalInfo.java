package Geo_dispersed_bigData;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Random;
import Database.*;
import Enums.SampleValues;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import utils.Utils;


public class InsertVehicalInfo 
{

	private JFrame fram;
	private JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10;
	private JTextField t1,t2,t4,t5,t6,t7,t8;
	private JComboBox t2combo,t3;
	private JButton b1,b2;
	private boolean valid=false;
	private boolean valid1=false;
	private boolean valid2;

	public InsertVehicalInfo() {



		fram = new JFrame("Vehical info");
		fram.setLayout(null);

		l1=new JLabel("Name : ");
		l1.setBounds(10, 60, 50, 20);
		l1.setFont(new Font("Serif",Font.BOLD,14));

		t1=new JTextField();
		t1.setBounds(130, 62,150, 20);

		l2=new JLabel("color : ");
		l2.setBounds(10, 90, 80, 20);
		l2.setFont(new Font("Serif",Font.BOLD,14));

		t3=new JComboBox(SampleValues.colors);
		t3.setBounds(130, 92,150, 20);

		l3=new JLabel("Date of registration : ");
		l3.setBounds(10, 120, 150, 20);
		l3.setFont(new Font("Serif",Font.BOLD,14));

		t4=new JTextField();
		t4.setBounds(130, 122,150, 20);

		l4=new JLabel("Place of registration : ");
		l4.setBounds(10, 150,150, 20);
		l4.setFont(new Font("Serif",Font.BOLD,14));

		String districts [] = Utils.getAllDistrictCodeInArray();
		t2combo=new JComboBox(districts);
		t2combo.setBounds(130, 152,150, 20);
		
		

		l5=new JLabel("Charsi number : ");
		l5.setBounds(10, 180, 150, 20);
		l5.setFont(new Font("Serif",Font.BOLD,14));

		t5=new JTextField();
		t5.setBounds(130, 182,150, 20);

		l8=new JLabel("vehical number : ");
		l8.setBounds(10, 210, 150, 20);
		l8.setFont(new Font("Serif",Font.BOLD,14));

		t6=new JTextField();
		t6.setBounds(130, 212,150, 20);

		l9=new JLabel("Owner name : ");
		l9.setBounds(10, 240, 150, 20);
		l9.setFont(new Font("Serif",Font.BOLD,14));

		t7=new JTextField();
		t7.setBounds(130, 242,150, 20);

		l10=new JLabel("licence number : ");
		l10.setBounds(10, 270, 150, 20);
		l10.setFont(new Font("Serif",Font.BOLD,14));

		t8=new JTextField();
		t8.setBounds(130, 272,150, 20);


		l6=new JLabel("Enter the vehical details ",JLabel.CENTER);
		l6.setBounds(0, 20, 300, 25);
		l6.setFont(new Font("Serif",Font.BOLD,18));

		b1=new JButton("Save");
		b1.setBounds(50, 305, 80, 20);

		b2=new JButton("Clear");
		b2.setBounds(150, 305, 80, 20);

		ImageIcon icon2= new ImageIcon("images/User_det.png");
		l7 = new JLabel(icon2,JLabel.CENTER);
		l7.setBounds(0,0, 300,350);

		fram.add(l8);
		fram.add(l1);
		fram.add(b1);
		fram.add(t2combo);
		fram.add(t3);
		fram.add(t6);
		fram.add(t4);
		fram.add(t5);
		fram.add(t1);
		fram.add(b2);
		fram.add(l2);
		fram.add(l3);
		fram.add(l4);
		fram.add(l5);
		fram.add(l6);
		fram.add(l8);
		fram.add(l9);fram.add(l10);fram.add(t6);fram.add(t7);fram.add(t8);

		fram.add(l7);
		fram.setSize(300, 380);
		fram.setVisible(true);

		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				t1.setText(null);	t2.setText(null);
		t4.setText(null);
				t5.setText(null);	t6.setText(null);
				t7.setText(null);
			}
		});
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				if(!t1.getText().equals("") &&  !t4.getText().equals("") && !t5.getText().equals("")&& !t6.getText().equals("")&& !t7.getText().equals(""))
				{
					if(valid1){

						StringBuffer data = new StringBuffer("");
						data.append(t1.getText()+",");
						data.append(t3.getSelectedItem()+",");
						data.append(t4.getText()+",");
						data.append(t2combo.getSelectedItem()+",");
						data.append(t5.getText()+",");
						data.append(t6.getText()+",");
						data.append(t7.getText()+",");
						data.append(t8.getText()+"\n");

						Utils.appendDataToFile(data.toString());
						fram.setVisible(false);
					

					}else
						JOptionPane.showMessageDialog(fram,"Enter Owner name correctly"," ",JOptionPane.WARNING_MESSAGE);

				}
				else{
					JOptionPane.showMessageDialog(fram,"Text fields can't be blank"," ",JOptionPane.WARNING_MESSAGE);
				}
			}});

		FocusAdapter fl = new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent fe) {
				super.focusGained(fe);
				((JTextComponent) fe.getSource()).setBackground(new Color(175,238,238));
			}

			@Override
			public void focusLost(FocusEvent fe) {
				super.focusLost(fe);
				if (fe.getSource().equals(t7)) {
					validationForname(t7);}


			}
		};

		t7.addFocusListener(fl);


	}

	private void validationForname(JTextField comp) {
		String text = comp.getText();
		if (text.matches("[A-Za-z][A-Za-z]*")) {
			setGreen(comp);
			valid1=true;
		} 
		else 
		{
			valid1=false;
			setRed(comp);
			JOptionPane.showMessageDialog(fram,"can't enter number in name column"," ",JOptionPane.WARNING_MESSAGE);

		}
	}


	private void setRed(JTextComponent comp) {
		comp.setBackground(Color.RED);
	}

	private void setGreen(JTextComponent comp) {
		comp.setBackground(Color.GREEN);
	}

	public static void main(String[] args) {
		new InsertVehicalInfo();
	}

}
