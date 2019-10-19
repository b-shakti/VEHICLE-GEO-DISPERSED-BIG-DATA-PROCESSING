package utils; 

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class test {

	 public static void main(String[] args) {
		 try
		 {
		 String splits = "KA-10-B-508";
		 String command = "hadoop jar Hadoop_jars/FilterBasedOnNumber.jar Client1 "+splits;
		 System.out.println(command);
			Process p1 = Runtime.getRuntime().exec(command);

			BufferedReader stdinput1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
			String s1 = "";
			while((s1 = stdinput1.readLine())!=null)
			{
				System.out.println(s1);
			}
			 System.out.println("done");
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }

	    }

	
}