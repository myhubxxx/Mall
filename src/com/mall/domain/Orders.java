package com.mall.domain;

import java.util.List;

public class Orders {
	private String oid;
	private String time;//订单生产时间
	private double count;//总计
	private int status;//订单状态：1未付款, 2已付款但未发货, 3已发货未确认收货, 4确认收货了交易成功, 5已取消(未付款才能取消)
	private String address;
	
	private String expressNumber;
	
	private User user;

	private List<OrderInfo> orderInfoList;// 订单细节

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderInfo> getOrderInfoList() {
		return orderInfoList;
	}

	public void setOrderInfoList(List<OrderInfo> orderInfoList) {
		this.orderInfoList = orderInfoList;
	}

	public String getExpressNumber() {
		return expressNumber;
	}

	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}

	@Override
	public String toString() {
		return "Orders [oid=" + oid + ", time=" + time + ", count=" + count
				+ ", status=" + status + ", address=" + address
				+ ", expressNumber=" + expressNumber + ", user=" + user
				+ ", orderInfoList=" + orderInfoList + "]";
	}

	
	
	
	
}
