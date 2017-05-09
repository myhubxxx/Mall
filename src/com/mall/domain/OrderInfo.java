package com.mall.domain;

public class OrderInfo {

	private String orid;
	private int	number;
	private double total;// 数量为number的书的总价
	private Goods goods;
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
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public Orders getOrders() {
		return orders;
	}
	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	@Override
	public String toString() {
		return "OrderInfo [orid=" + orid + ", number=" + number + ", total="
				+ total + ", goods=" + goods + ", orders=" + orders + "]";
	}
	
	
	
}
