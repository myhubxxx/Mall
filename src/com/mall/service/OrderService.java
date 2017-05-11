package com.mall.service;

import java.util.Map;

import com.mall.domain.Orders;

public interface OrderService {

	boolean addOrder(Orders myOrder, String[] shopCarIdArray);

	Orders getOrderById(Map<String, Object> map);

}
