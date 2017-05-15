package com.mall.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import util.BeanFactory;
import util.CommonUtils;
import util.PageBean;
import util.StringUtils;

import com.mall.domain.Category;
import com.mall.domain.Goods;
import com.mall.service.CategoryService;
import com.mall.service.GoodsService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ognl.OgnlValueStack;

public class GoodsAction extends ActionSupport {
	private static final Logger log = Logger.getLogger(GoodsAction.class);
	private Goods good;
	private PageBean<Goods> page;
	private GoodsService service = (GoodsService) BeanFactory.get("GoodsService");
	CategoryService categoryService = (CategoryService) BeanFactory.get("CategoryService");
	// form argument
	private String goodsname;
	private String categoryId;
	private String pageNow;
	// form
	private String op;
	// file upload
	private List<File> image;
	private List<String> imageFileName;
	private List<String> iamgeContentType;
	
	public String actionTest(){
		return "success";
	}
	
	public String addGood(){
		ActionContext ac = ActionContext.getContext();
		if(good == null){
			ac.put("msg", "商品信息不能为空");
			return "addGood";
		}
		good.setGid(CommonUtils.uuid());
		try {
			String path = ServletActionContext.getServletContext().getRealPath("goods_img");
			// image 1, big
			String imageName = CommonUtils.uuid() + ".jpg";
			File image1 = new File(path +"/" + imageName );
			FileUtils.copyFile(image.get(0), image1);
			good.setImage_w("goods_img/" + imageName);
			// image 2 small
			imageName = CommonUtils.uuid() + ".jpg";
			File image2 = new File(path +"/"+ imageName);
			FileUtils.copyFile(image.get(1), image2);
			good.setImage_b("goods_img/" + imageName);
			
			// add good
			service.addGood(good);
			
			ac.put("msg", "添加商品成功");
			return "msg";
		} catch (IOException e) {
			log.error("upload file fail:" + e);
			e.printStackTrace();
		}
		ac.put("msg", "添加商品成功");
		return "msg";
	}
	
	public void getCategoryAll(){
		ActionContext ac = ActionContext.getContext();
		OgnlValueStack vs = (OgnlValueStack) ActionContext.getContext().getValueStack();
		List<Category> list = categoryService.getAll();
		
		ac.put("categorys", list);
	}
	
	public String listByCategory(){
		ActionContext ac = ActionContext.getContext();
		Map<String, Object> map = new HashMap<String, Object>();
		page = new PageBean<Goods>();
		// set the current page
		try{
			page.setCurrentPage( Integer.parseInt(pageNow) );
		}catch(NumberFormatException e){
			page.setCurrentPage(1);
		}
		
		map.put("gname", goodsname);
		map.put("cid", categoryId);
		
		
		page = service.getPage(page, map);
		ac.put("pb", page);
		ac.put("goodsname", goodsname);
		ac.put("categoryId", categoryId);
		
		return "goodsList";
	}
	public String listByCategoryAdmin(){
		ActionContext ac = ActionContext.getContext();
		Map<String, Object> map = new HashMap<String, Object>();
		page = new PageBean<Goods>();
		// set the current page
		try{
			page.setCurrentPage( Integer.parseInt(pageNow) );
		}catch(NumberFormatException e){
			page.setCurrentPage(1);
		}
		
		map.put("gname", goodsname);
		map.put("cid", categoryId);
		
		
		page = service.getPage(page, map);
		ac.put("pb", page);
		ac.put("goodsname", goodsname);
		ac.put("categoryId", categoryId);
		
		return "goodsAdminList";
	}
	public String goodsInfo(){
		if(good == null || !StringUtils.hasText( good.getGid())){
			//no the goods
			log.info("goods:" + good.getGid() );
			return "goodsList";
		}
		Goods dbGood = service.getByGid(good.getGid());
		if(dbGood == null){
			log.info("not found the goods:"+good.getGid());
			return "goodsList";
		}
		ActionContext ac = ActionContext.getContext();
		ac.put("goodInfo", dbGood);
		return "desc";
	}
	public String goodsInfoAdmin(){
		if(good == null || !StringUtils.hasText( good.getGid())){
			//no the goods
			log.info("goods:" + good.getGid() );
			return "goodsList";
		}
		// put list to ac
		getCategoryAll();
		Goods dbGood = service.getByGid(good.getGid());
		if(dbGood == null){
			log.info("not found the goods:"+good.getGid());
			return "goodsList";
		}
		ActionContext ac = ActionContext.getContext();
		ac.put("goodInfo", dbGood);
		return "descAdmin";
	}
	public String goodAdmin(){
		ActionContext ac = ActionContext.getContext();
		if(!StringUtils.hasText(op)){
			log.error("op null");
		}
		if("delete".equals(op)){
			// delete
			if( StringUtils.hasText(good.getGid() )){
				try {
					service.deleteByGid(good.getGid());
					ac.put("msg", "删除商品成功");
				} catch (Exception e) {
					log.error(e);
					ac.put("msg", "删除失败");	
				}
			}
		}else if("edit".equals(op)){
			// update
			service.updateGood(good);
			ac.put("msg", "修改商品成功");
		}else{
			log.error("wrong op:" + op );
			ac.put("msg", "错误的操作");	
		}
		
		return "msg";
	}
	public String addPre(){
		getCategoryAll();
		return "add";
	}
	
	public PageBean<Goods> getPage() {
		return page;
	}

	public void setPage(PageBean<Goods> page) {
		this.page = page;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getPageNow() {
		return pageNow;
	}

	public void setPageNow(String pageNow) {
		this.pageNow = pageNow;
	}

	public Goods getGood() {
		return good;
	}
	public void setGood(Goods good) {
		this.good = good;
	}


	public String getOp() {
		return op;
	}


	public void setOp(String op) {
		this.op = op;
	}

	public GoodsService getService() {
		return service;
	}

	public void setService(GoodsService service) {
		this.service = service;
	}

	public List<File> getImage() {
		return image;
	}

	public void setImage(List<File> image) {
		this.image = image;
	}

	public List<String> getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(List<String> imageFileName) {
		this.imageFileName = imageFileName;
	}
	
}
