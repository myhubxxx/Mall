package com.mall.dao;

import com.mall.domain.Admin;

public interface AdminDao extends BaseDao<Admin> {
	public Admin getByUsername(String username); 
}
