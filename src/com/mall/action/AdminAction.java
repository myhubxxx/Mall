package com.mall.action;

import org.apache.log4j.Logger;

import util.BeanFactory;

import com.mall.domain.Admin;
import com.mall.service.AdminService;
import com.opensymphony.xwork2.ActionSupport;

public class AdminAction extends ActionSupport {
	private static final Logger log = Logger.getLogger(AdminAction.class);
	private Admin admin;
	private AdminService service = (AdminService) BeanFactory.get("AdminService");
	
	public String login(){
		//TODO
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
}
