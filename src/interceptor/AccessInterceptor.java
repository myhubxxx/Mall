package interceptor;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AccessInterceptor extends AbstractInterceptor {
	private static final Logger log = Logger.getLogger(AccessInterceptor.class); 
	/**
	 *  user to validate the user is login
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ac = invocation.getInvocationContext();
		HttpSession session =  ServletActionContext.getRequest().getSession();

		Object sessionUser =  session.getAttribute("sessionUser");
		Object sessionAdmin =  session.getAttribute("sessionAdmin");
		
		log.info("login inceptor check login");
		log.info("sessionUser==null "+ (sessionUser==null) );
		log.info("sessionUser==null "+ (sessionUser==null) );
		if(sessionUser == null && sessionAdmin == null  ){
			log.info("not login");
			return invocation.invoke();
//			return "login";
			
		}else{
			log.info("has login");
			return invocation.invoke();
		}
		
	}

}
