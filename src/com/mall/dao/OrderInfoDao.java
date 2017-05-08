package com.mall.dao;

import java.util.Map;

import com.mall.domain.OrderInfo;

/**
 * there are 5 table(orderinfo,orderinfo1,orderinfo2,orderinfo3,orderinfo4).
 * there are 2 operate for the table.
 * 1. check the [loadBalance] argument is 0:close, 1:open; 
 * 		if 0 operate table [orderinfo].
 * 2. get [uid] to calculate the table,then get data from the two tables
 * 
 */
public interface OrderInfoDao extends BaseDao<OrderInfo> {
	public OrderInfo getByOid(Map map);
	public void add(Map map);
	@Deprecated
	public void deleteByOrid(Map map);

}
