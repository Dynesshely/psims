package entity;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {
	public static Map<String, String> map = new HashMap<String, String>();
	static{
		load();
	}
	
	public static String loadConfig(String key){
		if(map.isEmpty())
			load();
		return PropertiesUtil.map.get(key);
	}
	
	public static String getPwd(){
		return PropertiesUtil.map.get("pwd");
	}
	
	
	public static void load(){
		Properties prop = new Properties();
		InputStream in = PropertiesUtil.class.getResourceAsStream("/conf.properties");
		try {
			prop.load(in);
			PropertiesUtil.map.put("pwd", prop.getProperty("pwd"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
