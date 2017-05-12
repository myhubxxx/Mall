package com.mall.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.mall.domain.Orders;

public interface OrdersDao extends BaseDao<Orders> {
	
	public Orders getByIdMap(Map<String, Object> map)throws SQLException;
	public List<Orders> getByGoodsPage(Map<String, Object> map)throws SQLException;
	@Deprecated
	public List<Orders> getByGoodsListPage(Map<String, Object> map)throws SQLException;
	public void updateByIdMap(Map<String, Object> map);
}
