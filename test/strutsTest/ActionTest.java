package strutsTest;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class ActionTest extends ActionSupport {
	private static final Logger log = Logger.getLogger(ActionTest.class);
	
	public String actionInvokeTest(){
		log.info("action test success");
		System.out.println("get in");
		return SUCCESS;
	}
	public void jsonTest() throws Exception{
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		String jsonString = "{\"user\":{\"id\":\"123\",\"name\":\"张三\",\"say\":\"Hello , i am a action to print a json!\",\"password\":\"JSON\"},\"success\":true}";
		out.print(jsonString);
		out.flush();
		out.close();
	}
		
}
