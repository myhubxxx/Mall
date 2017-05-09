package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtils {
	private static final Logger log = Logger.getLogger(PropertiesUtils.class);
	private static Properties prop = null;
	
	static{
		
		try {
			
		InputStream in = PropertiesUtils.class.getClassLoader().getResourceAsStream("service.properties");
		
		prop = new Properties();
		
			prop.load(in);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getValue(String key){
		
		return prop.getProperty(key);
	}
	
	public static Object getBean(String key) {
		
		Object obj;
		try {
			String className = getValue(key);
			log.info(className);
			Class clazz = Class.forName(className);
			log.info(clazz);
			obj = clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return obj;
	}
}
