package com.mall.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import util.BeanFactory;
import util.PageBean;
import util.StringUtils;

import com.mall.domain.Goods;
import com.mall.service.GoodsService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GoodsAction extends ActionSupport {
	private static final Logger log = Logger.getLogger(GoodsAction.class);
	private Goods good;
	private PageBean<Goods> page;
	private GoodsService service = (GoodsService) BeanFactory.get("GoodsService");
	
	// form argument
	private String goodsname;
	private String categoryId;
	private String pageNow;
	
	public String actionTest(){
		return "success";
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
}
