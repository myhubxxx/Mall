package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class EmailUtils {
	private static final Logger log = Logger.getLogger(EmailUtils.class);
	private static Properties prop = null;
	
	static{
		
		try {
			
			InputStream in = EmailUtils.class.getClassLoader().getResourceAsStream("emailConfig.properties");
		
			prop = new Properties();
		
			prop.load(in);
		} catch (IOException e) {
			log.info(e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	// this must be init after the static block
	public static  String subject	 = getValue("subject");
	public static  String content	 = getValue("content");
	public static  String from	 	 = getValue("from");
	public static  String host	 	 = getValue("host");
	public static  String username 	 = getValue("username");
	public static  String password   = getValue("password");

	public static String getValue(String key){
		
		return prop.getProperty(key);
	}
}
