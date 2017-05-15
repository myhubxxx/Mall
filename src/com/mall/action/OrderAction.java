package com.mall.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import util.BeanFactory;
import util.CommonUtils;
import util.DateUtils;
import util.LoadBalance;
import util.PageBean;
import util.PaymentUtil;
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
	private Integer status;
	private String button;
	private String expressNumber;
	// pay type
	private String yh;
	
	public String actionTest(){
		return "success";
	}
	public String cancelOrder(){
		ActionContext ac = ActionContext.getContext();
		if( !StringUtils.hasText(orderId)){
			log.error("shopCarIds:" + null );
			ac.put("code", "error");
			ac.put("msg", "没有找到订单");
			return "msg";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("oid", orderId);
		map.put("status", 5);
		service.changeOrderStatus(map);
			
		return loadOrderId();
	}
	public String cancelOrderAdmin(){
		ActionContext ac = ActionContext.getContext();
		if( !StringUtils.hasText(orderId)){
			log.error("shopCarIds:" + null );
			ac.put("code", "error");
			ac.put("msg", "没有找到订单");
			return "msg";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("oid", orderId);
		map.put("status", 5);
		service.changeOrderStatus(map);
		ac.put("msg", "订单成功取消");	
		ac.put("button", button);
		return "msg";
	}
	
	public String confirmOrder(){
		ActionContext ac = ActionContext.getContext();
		if( !StringUtils.hasText(orderId)){
			log.error("shopCarIds:" + null );
			ac.put("code", "error");
			ac.put("msg", "没有找到订单");
			return "msg";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("oid", orderId);
		map.put("status", 4);
		service.changeOrderStatus(map);
			
		return loadOrderId();
	}
	/**
	 * express
	 * @return
	 */
	public String expressNumber(){
		ActionContext ac = ActionContext.getContext();
		if( !StringUtils.hasText(orderId) || !StringUtils.hasText(expressNumber) ){
			log.error("shopCarIds:" + null );
			ac.put("code", "error");
			ac.put("msg", "没有找到订单");
			return "msg";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("oid", orderId);
		map.put("expressNumber", expressNumber);
		map.put("status", 3);
		service.changeOrderStatus(map);
		// express
		ac.put("msg", "发货成功");
		return "express";
	}
	
	public String loadOrderId(){
		ActionContext ac = ActionContext.getContext();
		if( !StringUtils.hasText(orderId)){
			log.error("shopCarIds:" + null );
			ac.put("code", "error");
			ac.put("msg", "没有找到订单");
			return "msg";
		}
		User sessionUser = (User) ServletActionContext.getRequest().getSession().getAttribute("sessionUser");
		if(sessionUser == null){
			// go to login
			return "login";
		}
		if( StringUtils.hasText( button ) ){
			ac.put("button", button);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index", LoadBalance.calculateTable(sessionUser.getUid()));
		map.put("oid", orderId);
		Orders order = service.getOrderById(map);
		if( order == null ){
			log.error("order null"  );
			ac.put("code", "error");
			ac.put("msg", "不存在该订单");
			return "msg";
		}
		ac.put("order", order);
		
		return "orderDesc";
	}
	public String loadOrderIdAdmin(){
		ActionContext ac = ActionContext.getContext();
		if( !StringUtils.hasText(orderId)){
			log.error("shopCarIds:" + null );
			ac.put("code", "error");
			ac.put("msg", "没有找到订单");
			return "msg";
		}
		if( StringUtils.hasText( button ) ){
			ac.put("button", button);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("oid", orderId);
		Orders order = service.getOrderByIdAdmin(map);
		if( order == null ){
			log.error("order null"  );
			ac.put("code", "error");
			ac.put("msg", "不存在该订单");
			return "msg";
		}
		ac.put("order", order);
		
		return "descOrderAdmin";
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
		map.put("status", status);
		pageNow = pageNow < 1 ? 1: pageNow;
		
		// page
		PageBean<Orders> page = new PageBean<Orders>();
		page.setCurrentPage(pageNow);
		page.setPageSize(4);
		
		PageBean<Orders> dbPage =  service.getPage(page, map);
		// ActionContext
		ActionContext ac = ActionContext.getContext();
		if(status != null){
			ac.put("status", status);
		}
		ac.put("page", dbPage);
		
		return "orderList";
	}
	
	public String listOrdersAdmin(){
		
		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("index", LoadBalance.calculateTable(user.getUid()));
//		map.put("uid", user.getUid());
		map.put("status", status);
		pageNow = pageNow < 1 ? 1: pageNow;
		
		// page
		PageBean<Orders> page = new PageBean<Orders>();
		page.setCurrentPage(pageNow);
		page.setPageSize(4);
		
		PageBean<Orders> dbPage =  service.getPageAdmin(page, map);
		// ActionContext
		ActionContext ac = ActionContext.getContext();
		if(status != null){
			ac.put("status", status);
		}
		ac.put("page", dbPage);
		
		return "orderList";
	}
	
	/**
	 * pay
	 * @return
	 */
	public void pay(){
		Properties props = new Properties();
		try {
			props.load(this.getClass().getClassLoader().getResourceAsStream("payment.properties"));
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
		/*
		 * 1. 准备13个参数
		 */
		String p0_Cmd = "Buy";//业务类型，固定值Buy
		String p1_MerId = props.getProperty("p1_MerId");//商号编码，在易宝的唯一标识
		String p2_Order = orderId;//订单编码
		// all pay ￥0.01
		String p3_Amt = "0.01";//支付金额
		String p4_Cur = "CNY";//交易币种，固定值CNY
		String p5_Pid = "";//商品名称
		String p6_Pcat = "";//商品种类
		String p7_Pdesc = "";//商品描述
		String p8_Url = props.getProperty("p8_Url");//在支付成功后，易宝会访问这个地址。
		String p9_SAF = "";//送货地址
		String pa_MP = "";//扩展信息
		String pd_FrpId = yh;//支付通道
		String pr_NeedResponse = "1";//应答机制，固定值1
		/*
		 * 2. 计算hmac
		 * 需要13个参数
		 * 需要keyValue
		 * 需要加密算法
		 */
		String keyValue = props.getProperty("keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue);
		/*
		 * 3. 重定向到易宝的支付网关
		 */
		StringBuilder sb = new StringBuilder("https://www.yeepay.com/app-merchant-proxy/node");
		sb.append("?").append("p0_Cmd=").append(p0_Cmd);
		sb.append("&").append("p1_MerId=").append(p1_MerId);
		sb.append("&").append("p2_Order=").append(p2_Order);
		sb.append("&").append("p3_Amt=").append(p3_Amt);
		sb.append("&").append("p4_Cur=").append(p4_Cur);
		sb.append("&").append("p5_Pid=").append(p5_Pid);
		sb.append("&").append("p6_Pcat=").append(p6_Pcat);
		sb.append("&").append("p7_Pdesc=").append(p7_Pdesc);
		sb.append("&").append("p8_Url=").append(p8_Url);
		sb.append("&").append("p9_SAF=").append(p9_SAF);
		sb.append("&").append("pa_MP=").append(pa_MP);
		sb.append("&").append("pd_FrpId=").append(pd_FrpId);
		sb.append("&").append("pr_NeedResponse=").append(pr_NeedResponse);
		sb.append("&").append("hmac=").append(hmac);
		
		try {
			ServletActionContext.getResponse().sendRedirect(sb.toString());
			return;
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
	}
	/**
	 * pay back
	 * @return
	 */
	public String payBack(){
		ActionContext ac = ActionContext.getContext();
		Properties props = new Properties();
		try {
			props.load(this.getClass().getClassLoader().getResourceAsStream("payment.properties"));
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
		String keyValue = props.getProperty("keyValue");
		boolean bool = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId,
				r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType,
				keyValue);
		if(!bool) {
			ac.put("code", "error");
			ac.put("msg", "签名错误，支付失败");
			return "msg";
		}
		if(r1_Code.equals("1")) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("oid", r6_Order);
			map.put("status", 2);
			service.changeOrderStatus(map);
			
			if(r9_BType.equals("1")) {
				ac.put("code", "success");
				ac.put("msg", "支付成功");
				return "msg";				
			} else if(r9_BType.equals("2")) {
				ac.put("code", "success");
				ac.put("msg", "支付成功");
				return "msg";
			}
		}
		ac.put("code", "error");
		ac.put("msg", "未知名错误");
		return "msg";
		
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getButton() {
		return button;
	}
	public void setButton(String button) {
		this.button = button;
	}
	public String getExpressNumber() {
		return expressNumber;
	}
	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}
	public String getYh() {
		return yh;
	}
	public void setYh(String yh) {
		this.yh = yh;
	}

 	public String p1_MerId;
 	public String r0_Cmd;
 	public String r1_Code;
 	public String r2_TrxId;
 	public String r3_Amt;
 	public String r4_Cur;
 	public String r5_Pid;
 	public String r6_Order; 
 	public String r7_Uid ;
 	public String r8_MP ;
 	public String r9_BType; 
 	public String hmac ;

	public String getP1_MerId() {
		return p1_MerId;
	}
	public void setP1_MerId(String p1_MerId) {
		this.p1_MerId = p1_MerId;
	}
	public String getR0_Cmd() {
		return r0_Cmd;
	}
	public void setR0_Cmd(String r0_Cmd) {
		this.r0_Cmd = r0_Cmd;
	}
	public String getR1_Code() {
		return r1_Code;
	}
	public void setR1_Code(String r1_Code) {
		this.r1_Code = r1_Code;
	}
	public String getR2_TrxId() {
		return r2_TrxId;
	}
	public void setR2_TrxId(String r2_TrxId) {
		this.r2_TrxId = r2_TrxId;
	}
	public String getR3_Amt() {
		return r3_Amt;
	}
	public void setR3_Amt(String r3_Amt) {
		this.r3_Amt = r3_Amt;
	}
	public String getR4_Cur() {
		return r4_Cur;
	}
	public void setR4_Cur(String r4_Cur) {
		this.r4_Cur = r4_Cur;
	}
	public String getR5_Pid() {
		return r5_Pid;
	}
	public void setR5_Pid(String r5_Pid) {
		this.r5_Pid = r5_Pid;
	}
	public String getR6_Order() {
		return r6_Order;
	}
	public void setR6_Order(String r6_Order) {
		this.r6_Order = r6_Order;
	}
	public String getR7_Uid() {
		return r7_Uid;
	}
	public void setR7_Uid(String r7_Uid) {
		this.r7_Uid = r7_Uid;
	}
	public String getR8_MP() {
		return r8_MP;
	}
	public void setR8_MP(String r8_MP) {
		this.r8_MP = r8_MP;
	}
	public String getR9_BType() {
		return r9_BType;
	}
	public void setR9_BType(String r9_BType) {
		this.r9_BType = r9_BType;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
	
	
	
}
