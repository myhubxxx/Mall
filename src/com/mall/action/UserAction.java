package com.mall.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import util.BeanFactory;
import util.MD5Utils;
import util.StringUtils;

import com.mall.domain.User;
import com.mall.service.UserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport {
	private static final Logger log = Logger.getLogger(UserAction.class);
	private User user;
	private UserService service = (UserService) BeanFactory.get("UserService");
	
	public String actionTest(){
		return "success";
	}
	
	private PrintWriter getPrintWriter(){
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = null;
		try {
			out = resp.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}
//	public void jsonTest() throws Exception{
//		HttpServletResponse resp = ServletActionContext.getResponse();
//		resp.setContentType("text/html;charset=utf-8");
//		PrintWriter out = resp.getWriter();
//		String jsonString = "{\"user\":{\"id\":\"123\",\"name\":\"张三\",\"say\":\"Hello , i am a action to print a json!\",\"password\":\"JSON\"},\"success\":true}";
//		out.print(jsonString);
//		out.flush();
//		out.close();
//	}
	
	
	public void existsUsername(){
		if( user == null || !StringUtils.hasText(user.getUsername())) {
			log.info("username null");
			return;
		}
		boolean hasName = service.existsUsername(user.getUsername());
		log.info("hasName : " + hasName);
		PrintWriter out = getPrintWriter();
		out.print(hasName);
		out.flush();
		out.close();
	}
	public void existsEmail(){
		if( user == null || !StringUtils.hasText(user.getEmailAddress())){
			log.info("email null");
			return;
		}
		boolean hasEmail = service.existsEmail(user.getEmailAddress());
		log.info("hasEmail : " + hasEmail);
		PrintWriter out = getPrintWriter();
		out.print(hasEmail);
		out.flush();
		out.close();
	}
	public void checkVerifyCode(){
		if( user == null || !StringUtils.hasText(user.getVerifyCode())){
			log.info("verifyCode null");
			return;
		}
		String vCode = (String) ServletActionContext.getRequest().getSession().getAttribute("verifyCode");
		log.info("session verifycode : "+ vCode);
		boolean equalCode = user.getVerifyCode().equalsIgnoreCase(vCode); 
		PrintWriter out = getPrintWriter();
		out.print(equalCode);
		out.flush();
		out.close();
	}
	public String regist(){
		if( user == null ){
			log.info("user regist null");
			return "regist";
		}
		Map<String, String> errors = validateRegist(user);
		ActionContext ac = ActionContext.getContext();
		if(errors.size() > 0) {
			ac.put("form", user);
			ac.put("errors", errors);
			log.error("regist has error:" + errors);
			return "regist";
		}
		log.info("regist check access!");
		if( !service.registUser(user)){
			ac.put("form", user);
			ac.put("errors", errors);
			log.error("regist failure!");
			return "regist";
		}
		log.info("user regist success");
		ac.put("code", "success");
		ac.put("msg", "完成注册,请在邮箱中激活");
		return "success";
	} 
	public String activeUser(){
		if( user == null || !StringUtils.hasText(user.getActiveCode())){
			log.info("activecode null");
			return "failure";
		}
		String re = service.activeUser(user.getActiveCode());
		ActionContext ac = ActionContext.getContext();
		ac.put("code", "success");
		ac.put("msg", "激活成功，请登录");
		if(StringUtils.hasText(re)){
			log.info("active user :" + re);
			ac.put("msg", re);
			ac.put("code", "error");
		}
		return "active";
	} 
	public String login(){
		if(user == null || !StringUtils.hasText(user.getUsername())||!StringUtils.hasText(user.getPassword())){
			log.info("username,password null");
			return "login";
		}
		User dbUser = service.login(user);
		String encodePass = MD5Utils.string2MD5(user.getPassword());
		String re = null;
		if( dbUser == null || !encodePass.equals(dbUser.getPassword())){
			re = "用户名或密码错误";
		}else{
			if(dbUser.getActive() == 0){
				re = "用户未激活";
			}
		}
		String vCode = (String) ServletActionContext.getRequest().getSession().getAttribute("verifyCode");
		log.info("session verifycode : "+ vCode);
		boolean equalCode = user.getVerifyCode().equalsIgnoreCase(vCode);
		if( !equalCode){
			re = "验证码错误";
		}
		ActionContext ac = ActionContext.getContext();
		if(re != null){
			log.info("error info:" + re);
			ac.put("msg", re);
			ac.put("user", user);
			return "login";
		}
		// login success
		log.info("login success:" + dbUser);
		ServletActionContext.getRequest().getSession().setAttribute("sessionUser", dbUser);
		{// save to cookie
			String username = dbUser.getUsername();
			try {
				username = URLEncoder.encode(username, "utf-8");
			} catch (UnsupportedEncodingException ignore) {}
			Cookie cookie = new Cookie("username", username);
			cookie.setMaxAge(60 * 60 * 24 * 7);
			ServletActionContext.getResponse().addCookie(cookie);
		}
		
		return "success";
	}
	public String changePass(){
		User sessionUser = (User) ServletActionContext.getRequest().getSession().getAttribute("sessionUser");
		if(sessionUser == null) {
			return "login";
		}
		if(user == null || !StringUtils.hasText(user.getVerifyCode())||!StringUtils.hasText(user.getNewPassword())||!StringUtils.hasText(user.getSurePassword())){
			return "changePassword";
		}
		//check verifycode
		String vCode = (String) ServletActionContext.getRequest().getSession().getAttribute("verifyCode");
		log.info("session verifycode : "+ vCode);
		boolean equalCode = user.getVerifyCode().equalsIgnoreCase(vCode);
		log.info("equalCode:"+equalCode);
		// check password, 
		boolean equalPass = (user.getNewPassword().equals(user.getSurePassword()) && sessionUser.getPassword().equals(MD5Utils.string2MD5(user.getNewPassword())));
		log.info("equalPass:"+equalPass);
		ActionContext ac = ActionContext.getContext();
		ac.put("msg", "密码修改失败");
		ac.put("code", "error");
		if(equalCode && equalPass){
			// change password
			sessionUser.setPassword(MD5Utils.string2MD5(user.getNewPassword()));
			try {
				service.changePassword(sessionUser);
				ServletActionContext.getRequest().getSession().setAttribute("sessionUser", sessionUser);
				ac.put("msg", "密码修改成功");
				ac.put("code", "success");
				return "success";
			} catch (SQLException e) {
				// change pass failure
				log.error(e);
				return "changePassword";
			}
		}
		return "changePassword";
	}
	public String exitLogin(){
		// invalidate session
		ServletActionContext.getRequest().getSession().invalidate();
		return "login";
	}
	
	private Map<String,String> validateRegist(User formUser) {
		Map<String,String> errors = new HashMap<String,String>();
		/*
		 * 1. 校验登录名
		 */
		String loginname = formUser.getUsername();
		if(loginname == null || loginname.trim().isEmpty()) {
			errors.put("loginname", "用户名不能为空！");
		} else if(loginname.length() < 3 || loginname.length() > 20) {
			errors.put("loginname", "用户名长度必须在3~20之间！");
		} else if(service.existsUsername(loginname)) {
			errors.put("loginname", "用户名已被注册！");
		}
		
		/*
		 * 2. 校验登录密码
		 */
		String loginpass = formUser.getPassword();
		if(loginpass == null || loginpass.trim().isEmpty()) {
			errors.put("loginpass", "密码不能为空！");
		} else if(loginpass.length() < 3 || loginpass.length() > 20) {
			errors.put("loginpass", "密码长度必须在3~20之间！");
		}
		
		/*
		 * 3. 确认密码校验
		 */
		String reloginpass = formUser.getSurePassword();
		if(reloginpass == null || reloginpass.trim().isEmpty()) {
			errors.put("reloginpass", "确认密码不能为空！");
		} else if(!reloginpass.equals(loginpass)) {
			errors.put("reloginpass", "两次输入不一致！");
		}
		
		/*
		 * 4. 校验email
		 */
		String email = formUser.getEmailAddress();
		if(email == null || email.trim().isEmpty()) {
			errors.put("email", "Email不能为空！");
		} else if(!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
			errors.put("email", "Email格式错误！");
		} else if(service.existsEmail(email)) {
			errors.put("email", "Email已被注册！");
		}
		
		/*
		 * 5. 验证码校验
		 */
		String verifyCode = formUser.getVerifyCode();
		String vcode = (String) ServletActionContext.getRequest().getSession().getAttribute("verifyCode");
		if(verifyCode == null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCode", "验证码不能为空！");
		} else if(!verifyCode.equalsIgnoreCase(vcode)) {
			errors.put("verifyCode", "验证码错误！");
		}
		
		return errors;
	}
	
	 public void setUser(User user){this.user=user;}
	 public User getUser(){return user;}
}
