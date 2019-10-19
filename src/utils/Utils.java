package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import Enums.SampleValues;

public class Utils {

	public static DefaultListModel getAllDistrictCode()
	{
		DefaultListModel items = new DefaultListModel();
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> map = mapper.readValue(
					new File("./json_files/district_code.json"),
					new TypeReference<Map<String, Object>>() {
					});
			Set users = map.keySet();
			Set itemsInList = new TreeSet();

			for(Object item:users)
				itemsInList.add(item);
			for(Object item:itemsInList)
				items.addElement(item);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return items;
	}

	public static String[] getAllDistrictCodeInArray()
	{

		try
		{
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> map = mapper.readValue(
					new File("./json_files/district_code.json"),
					new TypeReference<Map<String, Object>>() {
					});
			Set users = map.keySet();
			String districts[] = new String[users.size()];
			Set itemsInList = new TreeSet();

			for(Object item:users)
				itemsInList.add(item);
			int i =0;
			for(Object item:itemsInList)
			{
				districts[i] = item.toString();
				i++;
			}
			return districts;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;


	}

	public static void appendDataToFile(String data)
	{
		try
		{
			File file = new File("./dataset/dataset.txt");
			if(!file.exists())
				file.createNewFile();

			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);

			FileWriter fileWritter = new FileWriter(file,true);
			BufferedWriter writer = new BufferedWriter(fileWritter);


			writer.write(data);
			writer.close();
			JOptionPane.showMessageDialog(null, "Record insterted into dataset");
		}
		catch (Exception e1) {
			// TODO: handle exception
			e1.printStackTrace();
		}
	}

	public static boolean insertDistrictInfo(String name, String code)
	{

		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> map = mapper.readValue(
					new File("./json_files/district_code.json"),
					new TypeReference<Map<String, Object>>() {
					});
			if(map.containsKey(name))
			{
				JOptionPane.showMessageDialog(null, "District Code already exist in list");
				return false;
			}
			map.put(name, code);
			mapper.writeValue(new File("./json_files/district_code.json"), map);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return false;

		}
		return true;
	}

	public static boolean deleteDistrictInfo(String district)
	{

		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> map = mapper.readValue(
					new File("./json_files/district_code.json"),
					new TypeReference<Map<String, Object>>() {
					});
			map.remove(district);
			mapper.writeValue(new File("./json_files/district_code.json"), map);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return false;

		}
		return true;
	}

	public static String getDistrictCode(String districtName) {
		String district_code = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> map = mapper.readValue(
					new File("./json_files/district_code.json"),
					new TypeReference<Map<String, Object>>() {
					});
			district_code = map.get(districtName).toString();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return district_code;
	}

	public static void insertDistrictInfoFromFile(String data) {
		// TODO Auto-generated method stub
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> map = mapper.readValue(
					new File("./json_files/district_code.json"),
					new TypeReference<Map<String, Object>>() {
					});
			String district_info[]  = null;
			String districtInArray[] = data.split(";");
			for(String district : districtInArray)
			{
				if(district.trim().length()>0)
				{
					district_info = district.split(",");
					if(!map.containsKey(district_info[1].trim()))
						map.put(district_info[1], district_info[0]);
				}
			}

			mapper.writeValue(new File("./json_files/district_code.json"), map);
			JOptionPane.showMessageDialog(null, "District code info inserted sucessfully");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);

		}
	}

	public static String generateRandamDate()
	{
		String date = "";
		GregorianCalendar gc = new GregorianCalendar();

		int year = randBetween(1995, 2016);

		gc.set(gc.YEAR, year);

		int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));

		gc.set(gc.DAY_OF_YEAR, dayOfYear);

		date = gc.get(gc.DAY_OF_MONTH)+"-"+(gc.get(gc.MONTH) + 1) + "-"+gc.get(gc.YEAR);

		return date;
	}

	public static int randBetween(int start, int end) {
		return start + (int)Math.round(Math.random() * (end - start));
	}

}
