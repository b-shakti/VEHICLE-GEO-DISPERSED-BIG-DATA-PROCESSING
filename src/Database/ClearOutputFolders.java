package Database;

import java.io.File;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class ClearOutputFolders {
	
	public static void main(String[] args) {
		try {
			Runtime.getRuntime().exec("hdfs dfs -rmr tempDataset");

			Runtime.getRuntime().exec("hdfs dfs -rmr finalResult");
			
			System.out.println("Completed");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
//		try
//		{
//			ObjectMapper mapper = new ObjectMapper();
//			Map map = mapper.readValue(
//					new File("dataset/dataset.txt"),
//					new TypeReference<Map>() {
//					});
//			 map.clear();
//			mapper.writeValue(new File("dataset/dataset.txt"), map);
//		}
//		catch(Exception e1)
//		{
//			e1.printStackTrace();
//		}
	}
	

}
