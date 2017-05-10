package com.mall.action;

import java.util.List;

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
		return null;
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

}
