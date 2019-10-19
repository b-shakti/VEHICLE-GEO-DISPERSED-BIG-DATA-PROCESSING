package Database;


import java.io.File;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference; 
import javax.swing.DefaultListModel;



import Enums.IpAddress;

public class JDBC 

{

	static String update_string;
	static PreparedStatement ps;
	static Connection con;


	public static boolean authenticate(String uname,String pass ) {

		try
		{
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> map = mapper.readValue(
					new File("json_files/user_info.json"),
					new TypeReference<Map<String, Object>>() {
					});
			Set users = map.keySet();
			if(!users.contains(uname))
				return false;
			Map user_info = (Map)map.get(uname);
			if(user_info.get("password").equals(pass))
				return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public static void update_sts(String name, String username,
			String password, String email) {
		String query = null ;
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> map = mapper.readValue(
					new File("json_files/user_info.json"),
					new TypeReference<Map<String, Object>>() {
					});
			Map user_info = new HashMap();
			user_info.put("name", name);
			user_info.put("password", password);
			user_info.put("email", email);
			map.put(username, user_info);
			mapper.writeValue(new File("json_files/user_info.json"), map);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}


	public static boolean usernameAvaliable(String uname) {
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> map = mapper.readValue(
					new File("json_files/user_info.json"),
					new TypeReference<Map<String, Object>>() {
					});
			Set users = map.keySet();
			if(users.contains(uname))
				return false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}
	

	public static DefaultListModel getAllUserList()
	{
		DefaultListModel users = new DefaultListModel();
		
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> map = mapper.readValue(
					new File("json_files/user_info.json"),
					new TypeReference<Map<String, Object>>() {
					});
			for(String user:map.keySet())
				users.addElement(user);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return users;
	}
	public static void main(String[] args) {
		System.out.println(getAllUserList());
	}
	
	public static Map user_detail(String username) {
		Map user_info = null;
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			Map map = mapper.readValue(
					new File("json_files/user_info.json"),
					new TypeReference<Map>() {
					});
			user_info = (Map)map.get(username);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return user_info;
	}
}
