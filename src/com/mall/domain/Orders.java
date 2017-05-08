package com.mall.domain;

import java.util.List;

public class Orders {
	private String oid;
	private String time;//��������ʱ��
	private double count;//�ܼ�
	private int status;//����״̬��1δ����, 2�Ѹ��δ����, 3�ѷ���δȷ���ջ�, 4ȷ���ջ��˽��׳ɹ�, 5��ȡ��(δ�������ȡ��)
	private String address;
	
	private String expressNumber;
	
	private User user;

	private List<OrderInfo> orderInfoList;// ����ϸ��

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
