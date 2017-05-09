package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadBalance {

	public static boolean isLoadBalance(){
		try {
			InputStream in = LoadBalance.class.getClassLoader().getResourceAsStream("service.properties");
			Properties prop = new Properties();
			prop.load(in);
			String loadBalance = prop.getProperty("loadBalance");
			return loadBalance.equals("1") ? true : false;
		} catch (IOException e) {
			return true;// default 1:true
		}
	}
	/**
	 * return one of them(1,2,3,4)
	 * @param uid
	 * @return
	 */
	public static int calculateTable(String oid){
		int i = 1;
		if( !StringUtils.hasText(oid) ){
			return 1;// no text in 1
		}
		try{
			String str = oid.substring(0, 8);
			int sum = sum(str);
			//simple mode
			return (i + sum%4 );
		}catch (Exception e) { 
			return 1; 
		}
	} 
	private static int sum(String str){
		if( !StringUtils.hasText(str)){
			return 0;
		}
		int len = str.length();
		int sum = 0;
		for (int i = 0; i < len; i++) {
			sum += str.charAt(i);
		}
		return sum;
	}
}
