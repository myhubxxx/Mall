package com.mall.domain;

import java.math.BigDecimal;

public class ShopCar {
	private String sid;
	private int number;
	
	private Goods goods;
	private User user;
	
	public double getTotal(){
		BigDecimal b1 = new BigDecimal("" + number);
		BigDecimal b2 = new BigDecimal("" + goods.getNowPrice());
		return b1.multiply(b2).doubleValue();
	}
	
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "ShopCar [sid=" + sid + ", number=" + number + ", goods="
				+ goods + ", user=" + user + "]";
	}
	
	
}
