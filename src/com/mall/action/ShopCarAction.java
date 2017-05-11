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

import com.mall.domain.Goods;
import com.mall.domain.ShopCar;
import com.mall.domain.User;
import com.mall.service.ShopCarService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ShopCarAction extends ActionSupport {
	private static final Logger log = Logger.getLogger(ShopCarAction.class);
	private ShopCarService service = (ShopCarService) BeanFactory
			.get("ShopCarService");
	private String goodsId;
	private int count;
	private String shopCarIds; 
	private ShopCar shopCar;
	
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
	
	public String actionTest() {
		return "success";
	}

	public String addShopCar() {
		// check Login
		User sessionUser = (User) ServletActionContext.getRequest()
				.getSession().getAttribute("sessionUser");
		if (sessionUser == null) {
			log.info("not login");
			return "login";
		}
		if (StringUtils.hasText(goodsId) && count > 0) {
			// add good to shopcar
			ShopCar sc = new ShopCar();
			sc.setSid(CommonUtils.uuid());
			sc.setNumber(count);
			Goods goods = new Goods();
			goods.setGid(goodsId);
			sc.setGoods(goods);
			sc.setUser(sessionUser);

			if (service.addShopCar(sc)) {
				//
				return getMyShopCar();
			}
		}
		return getMyShopCar();
	}
	public String deleteShopCar(){
		if( ! StringUtils.hasText(shopCarIds) ){
			return getMyShopCar();
		}
		String[] shopCarIdArray = shopCarIds.split(",");
		service.deleteByIdArray(shopCarIdArray);
		
		return getMyShopCar();
	}
	public void updateNumberShopCar(){
		if( shopCar == null || !StringUtils.hasText(shopCar.getSid()) || shopCar.getNumber() <1 ){
			log.error("shopCar error info:" + shopCar);
			return;
		}
		
		ShopCar dbSc = service.updateNumber(shopCar.getSid(), shopCar.getNumber());
		if(dbSc != null){
			// return json
			StringBuilder sb = new StringBuilder("{");
			sb.append("\"number\"").append(":").append(dbSc.getNumber());
			sb.append(",");
			sb.append("\"total\"").append(":").append(dbSc.getTotal());
			sb.append("}");
			PrintWriter out = getPrintWriter();
			out.print(sb);
			out.flush();
			out.close();
		}
		return;
	}
	public String getMyShopCar() {
		// check Login
		User sessionUser = (User) ServletActionContext.getRequest()
				.getSession().getAttribute("sessionUser");
		if (sessionUser == null) {
			log.info("not login");
			return "login";
		}
		List<ShopCar> list = service.getShopCarByUser(sessionUser.getUid());
		ActionContext.getContext().put("shopCarList", list);
		
		// /jsps/cart/list.jsp
		return "shopCarList";
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getShopCarIds() {
		return shopCarIds;
	}

	public void setShopCarIds(String shopCarIds) {
		this.shopCarIds = shopCarIds;
	}

	public ShopCar getShopCar() {
		return shopCar;
	}

	public void setShopCar(ShopCar shopCar) {
		this.shopCar = shopCar;
	}

}
