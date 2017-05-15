package com.mall.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

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
	
	public void ajaxSecondCategory(){
		if(form== null || !StringUtils.hasText(form.getFid())){
			return;
		}
		List<Category> secondList = service.getCategoryByFid(form.getFid());
		String re = toJson(secondList);
		PrintWriter out = getPrintWriter();
		out.print(re);
		out.flush();
		out.close();
	}
	private String toJson(Category category) {
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"cid\"").append(":").append("\"").append(category.getCid()).append("\"");
		sb.append(",");
		sb.append("\"name\"").append(":").append("\"").append(category.getName()).append("\"");
		sb.append("}");
		return sb.toString();
	}
	
	// []
	private String toJson(List<Category> categoryList) {
		StringBuilder sb = new StringBuilder("[");
		for(int i = 0; i < categoryList.size(); i++) {
			sb.append(toJson(categoryList.get(i)));
			if(i < categoryList.size() - 1) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
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
	public String categoryListAllAdminleft(){
		ActionContext ac = ActionContext.getContext();
		OgnlValueStack vs = (OgnlValueStack) ActionContext.getContext().getValueStack();
		List<Category> list = service.getAll();
		
		ac.put("categorys", list);
		return "listLeft";
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
	public String addSecondCategoryPre(){
		// save pre category data in context
		categoryListAllAdmin();
		
		return "addSecondCategoryPre";
	}
	
	public String updateCategory(){
		if(form != null){
			service.updateCategory(form);
		}
		return categoryListAllAdmin();
	}
	public String updateFirstCategoryPre(){
		ActionContext ac = ActionContext.getContext();
		if(form != null && !StringUtils.hasText( form.getCid() )){
			log.info("category id null");	
		}
		Category category = service.getFirstCategoryId(form.getCid());
		ac.put("category", category);
		
		return "updateFirst";
	}
	public String updateSecondCategoryPre(){
		ActionContext ac = ActionContext.getContext();
		if(form != null && !StringUtils.hasText( form.getCid() )){
			log.info("category id null");	
		}
		List<Category> list = service.getAll();
		Category category = service.getSecondCategoryId(form.getCid());
		ac.put("category", category);
		ac.put("list", list);
		return "updateSecond";
	}
	public String deleteCategoryFirstId(){
		ActionContext ac = ActionContext.getContext();
		if(form != null && StringUtils.hasText( form.getCid() ) ){
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
		if(form != null && StringUtils.hasText( form.getCid() ) ){
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
