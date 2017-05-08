package util; 

public class BeanFactory {
	
	public static Object get(String str){
		
		
		return PropertiesUtils.getBean(str);
	}

}
