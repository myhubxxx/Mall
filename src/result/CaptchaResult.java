package result;

import java.awt.image.BufferedImage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import util.VerifyCode;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;



public class CaptchaResult implements Result{

	@Override
	public void execute(ActionInvocation arg0) throws Exception {
		
		VerifyCode vc = new VerifyCode();
		
		BufferedImage bi = vc.getImage();
		
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		
		req.getSession().setAttribute("verifyCode", vc.getText());

		resp.setHeader("Expires", "-1");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setHeader("Pragma", "no-cache");
		
		vc.output(bi, resp.getOutputStream());
		
		
	}

}
