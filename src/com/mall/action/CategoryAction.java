package com.mall.action;

import java.util.List;

import org.apache.log4j.Logger;

import util.BeanFactory;
import util.CommonUtils;
import util.StringUtils;

import com.mall.domain.Category;
import com.mall.service.CategoryService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ognl.OgnlValueStack;

public class CategoryAction extends ActionSupport {
	private static final Logger log = Logger.getLogger(CategoryAction.class);
	private Category form;
	// get Service
	CategoryService service = (CategoryService) BeanFactory.get("CategoryService");
	
	public String actionTest(){
		return "success";
	}
	
	public String categoryListAll(){
		ActionContext ac = ActionContext.getContext();
		OgnlValueStack vs = (OgnlValueStack) ActionContext.getContext().getValueStack();
		List<Category> list = service.getAll();
		
		ac.put("categorys", list);
		return "listAll";
	}
	public String categoryListAllAdmin(){
		ActionContext ac = ActionContext.getContext();
		OgnlValueStack vs = (OgnlValueStack) ActionContext.getContext().getValueStack();
		List<Category> list = service.getAll();
		
		ac.put("categorys", list);
		return "listAll";
	}
	public String addCategory(){
		if(form == null){
			log.info("form null");
			return categoryListAllAdmin();
		}
		form.setCid(CommonUtils.uuid());
		if( !StringUtils.hasText( form.getFid() )){
			// add FirstCategory
			service.addCategoryFirst(form);
		}else{
			// add add SecondCategory
			service.addCategorySecond(form);
		}
		return categoryListAllAdmin();
	}
	public String updateCategory(){
		if(form != null){
			service.updateCategory(form);
		}
		return categoryListAllAdmin();
	}
	public String updateSecondCategoryPre(){
		ActionContext ac = ActionContext.getContext();
		if(form != null && !StringUtils.hasText( form.getCid() )){
			ac.put("category", null);	
		}
		List<Category> list = service.getAll();
		
		ac.put("list", list);
		return "editCategory";
	}
	public String deleteCategoryFirstId(){
		ActionContext ac = ActionContext.getContext();
		if(form != null && !StringUtils.hasText( form.getCid() ) ){
			try{
				service.deleteByFirstId(form.getCid());
			}catch(Exception e){
				ac.put("msg", "该一级分类下存在二级分类，无法直接删除");
				return "msg";
			}	
		}
		return categoryListAllAdmin();
	}
	public String deleteCategorySecondId(){
		if(form != null && !StringUtils.hasText( form.getCid() ) ){
			service.deleteBySecondId(form.getCid());
		}
		return categoryListAllAdmin();
	}

	public Category getForm() {
		return form;
	}

	public void setForm(Category form) {
		this.form = form;
	}
	
}
