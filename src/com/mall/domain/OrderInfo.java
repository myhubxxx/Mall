package com.mall.domain;

public class OrderInfo {

	private String orid;
	private int	number;
	private double total;
	
	private String gid;
	private String g_name;
	private double nowPrice;
	private String image_b;
	
//	private Goods goods;
	private Orders orders;
	public String getOrid() {
		return orid;
	}
	public void setOrid(String orid) {
		this.orid = orid;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}

	public Orders getOrders() {
		return orders;
	}
	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getG_name() {
		return g_name;
	}
	public void setG_name(String g_name) {
		this.g_name = g_name;
	}
	public double getNowPrice() {
		return nowPrice;
	}
	public void setNowPrice(double nowPrice) {
		this.nowPrice = nowPrice;
	}
	public String getImage_b() {
		return image_b;
	}
	public void setImage_b(String image_b) {
		this.image_b = image_b;
	}
	@Override
	public String toString() {
		return "OrderInfo [orid=" + orid + ", number=" + number + ", total="
				+ total + ", gid=" + gid + ", g_name=" + g_name + ", nowPrice="
				+ nowPrice + ", image_b=" + image_b + ", orders=" + orders
				+ "]";
	}

	
	
	
}
