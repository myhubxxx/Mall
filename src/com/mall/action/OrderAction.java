package com.mall.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import util.BeanFactory;
import util.CommonUtils;
import util.DateUtils;
import util.LoadBalance;
import util.StringUtils;

import com.mall.domain.OrderInfo;
import com.mall.domain.Orders;
import com.mall.domain.ShopCar;
import com.mall.domain.User;
import com.mall.service.OrderService;
import com.mall.service.ShopCarService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class OrderAction extends ActionSupport {
	private static final Logger log = Logger.getLogger(OrderAction.class);
	private static final OrderService service = (OrderService) BeanFactory.get("OrderService");
	private static final ShopCarService shopCarService = (ShopCarService) BeanFactory.get("ShopCarService");
	private String orderId;
	private String shopCarIds;
	private String address;
	private String phoneNumber;
	private int pageNow;
	private String status;
	
	public String actionTest(){
		return "success";
	}
	
	public String addOrder(){
		ActionContext ac = ActionContext.getContext();
		if( !StringUtils.hasText( shopCarIds) ){
			log.error("shopCarIds:" + null );
			ac.put("code", "error");
			ac.put("msg", "没有商品，无法下单");
			return "msg";
		}
		if( !StringUtils.hasText( address) ){
			log.error("address:" + null );
			ac.put("code", "error");
			ac.put("msg", "没有收货地址，无法下单");
			return "msg";
		}
		if( !StringUtils.hasText( phoneNumber) || phoneNumber.length() < 7 || phoneNumber.length() > 11 ){
			log.error("phoneNumber:" + null );
			ac.put("code", "error");
			ac.put("msg", "联系方式格式不正确，无法下单");
			return "msg";
		}
		String[] shopCarIdArray = shopCarIds.split(",");
		List<ShopCar> shopCarList = shopCarService.loadBySidArray(shopCarIdArray);
		if(shopCarList == null || shopCarList.size() == 0){
			log.error("shopCarIds:" + shopCarIds + "; but shopCarList null" );
			ac.put("code", "error");
			ac.put("msg", "没有商品，无法下单");
			return "msg";
		}
		// add order
		Orders myOrder = new Orders(); 
			myOrder.setOid(CommonUtils.uuid());
			myOrder.setTime(DateUtils.getTime());
			myOrder.setStatus(1);
			myOrder.setAddress(address);
			myOrder.setPhoneNumber(phoneNumber);
		User sessionUser = (User) ServletActionContext.getRequest().getSession().getAttribute("sessionUser");
		if(sessionUser == null){
			// go to login
			return "login";
		}	
			myOrder.setUser(sessionUser);
			// calc total
			BigDecimal total = new BigDecimal("0");
			for (ShopCar shopCar : shopCarList) {
				total = total.add(new BigDecimal( "" + shopCar.getTotal() ) );
			}
			myOrder.setCount(total.doubleValue());
		// trans ShopCar to OrderInfo
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();	
		for (ShopCar shopCar : shopCarList) {
			OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrid(CommonUtils.uuid());
				orderInfo.setNumber(shopCar.getNumber());
				orderInfo.setTotal( shopCar.getTotal() );
				
//				orderInfo.setGoods( shopCar.getGoods() );
				orderInfo.setGid( shopCar.getGoods().getGid() );
				orderInfo.setG_name( shopCar.getGoods().getGname() );
				orderInfo.setNowPrice( shopCar.getGoods().getNowPrice() );
				orderInfo.setImage_b( shopCar.getGoods().getImage_b() );
				
				orderInfo.setOrders( myOrder );
			orderInfoList.add(orderInfo);
		}	
		myOrder.setOrderInfoList(orderInfoList);
		//创建订单
		boolean flag = service.addOrder(myOrder, shopCarIdArray);
		if( !flag){
			// add order failure
			log.error("add order failure");
			ac.put("code", "error");
			ac.put("msg", "创建订单失败");
			return "msg";
		}
		ac.put("order", myOrder);
		
		return "addOrder";
	}
	public String preparePay(){
		ActionContext ac = ActionContext.getContext();
		if( !StringUtils.hasText(orderId) ){
			log.info("orderId null");
			ac.put("code", "error");
			ac.put("msg", "错误的订单号");
			return "msg";
		}
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("sessionUser");
		if( user == null ){
			log.info("user null");
			return "login";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index", LoadBalance.calculateTable(user.getUid()));
		map.put("oid", orderId);
		Orders order = service.getOrderById(map);
		if( !StringUtils.hasText(orderId) ){
			log.info("orderId null");
			ac.put("code", "error");
			ac.put("msg", "错误的订单号");
			return "msg";
		}	
		ac.put("order", order);
		
		return "pay";
	}
	public String listOrders(){
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("sessionUser"); 
		if( user == null ){
			log.info("user null");
			return "login";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index", LoadBalance.calculateTable(user.getUid()));
		map.put("uid", user.getUid());
		pageNow = pageNow < 1 ? 1: pageNow;
		
		//TODO  service.getPage();
		
		return null;
	}
	
	public String getShopCarIds() {
		return shopCarIds;
	}

	public void setShopCarIds(String shopCarIds) {
		this.shopCarIds = shopCarIds;
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
