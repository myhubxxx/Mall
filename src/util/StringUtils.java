package util;

public class StringUtils {
	public static boolean hasText(String str){
		if(str != null && str.length() > 0){
			return true;
		}
		return false;
	}
}
