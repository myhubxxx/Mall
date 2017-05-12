package com.mall.service;

import java.util.Map;

import util.PageBean;

import com.mall.domain.Orders;

public interface OrderService {

	boolean addOrder(Orders myOrder, String[] shopCarIdArray);

	Orders getOrderById(Map<String, Object> map);

	PageBean<Orders> getPage(PageBean<Orders> page, Map<String, Object> map);

	void changeOrderStatus(Map<String, Object> map);

}
