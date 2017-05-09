package com.mall.action;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import util.BeanFactory;
import util.MD5Utils;
import util.StringUtils;

import com.mall.domain.Admin;
import com.mall.service.AdminService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AdminAction extends ActionSupport {
	private static final Logger log = Logger.getLogger(AdminAction.class);
	private Admin admin;
	private AdminService service = (AdminService) BeanFactory.get("AdminService");
	private String verifyCode;
	



	public String adminLogin(){
		ActionContext ac = ActionContext.getContext();
		ac.put("msg", "请确认用户名和密码");
		//check admin,username,password
		if(admin == null || !StringUtils.hasText(admin.getUsername()) || !StringUtils.hasText(admin.getPassword())){
			log.info("admin:"+ admin);
			return "login";
		}
		// get from db
		Admin dbAdmin = service.login(admin.getUsername());
		if(dbAdmin == null){
			log.info("db admin:"+ dbAdmin);
			return "login";
		}
		log.info("login admin:" + admin);
		// check pass
		if(dbAdmin.getPassword().equals( MD5Utils.string2MD5(admin.getPassword()))){
			// log success
			ServletActionContext.getRequest().getSession().setAttribute("sessionAdmin", dbAdmin);
			return "success";
		}
		return "login";
	}
	
	
	
	
	
	
	
	
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
}
