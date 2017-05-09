package com.mall.action;

import java.util.List;

import util.BeanFactory;

import com.mall.domain.Category;
import com.mall.service.CategoryService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ognl.OgnlValueStack;

public class CategoryAction extends ActionSupport {
	// get Service
	CategoryService service = (CategoryService) BeanFactory.get("CategoryService");
	
	
	public String categoryListAll(){
		ActionContext ac = ActionContext.getContext();
		OgnlValueStack vs = (OgnlValueStack) ActionContext.getContext().getValueStack();
		List<Category> list = service.getAll();
		
		ac.put("categorys", list);
		return "listAll";
	}
	
}
