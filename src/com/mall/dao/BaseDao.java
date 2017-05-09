package com.mall.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;



public interface BaseDao<T> {
	
	public void add(T t)throws SQLException;
	
	@Deprecated
	public void deleteBySort(int order)throws SQLException;
	
	public void deleteById(String id)throws SQLException;
	
	@Deprecated
	public void updateBySort(T t) throws SQLException;
	
	public void updateById(T t) throws SQLException;
	
	public T getById(String id)throws SQLException;
	
	@Deprecated
	public T getBySort(int order)throws SQLException;
	
	/**
	 * use to describe the page
	 * get all record that much the info
	 */
	public Number getAllCount(String attrUrl)throws SQLException;
	
	/**
	 * get the some page info
	 * @param sql
	 * @param attrMap
	 * @return
	 * @throws SQLException
	 */
	public List<T> getCurrentPage(Map<String,Object> attrMap)throws SQLException;

}
