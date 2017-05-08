package com.mall.dao;

import java.util.Map;

import com.mall.domain.User;

public interface UserDao extends BaseDao<User> {
	/**
	 * search by Username
	 * @param username
	 * @return
	 */
	public User getByUsername(String username);
	
	/**
	 * search by email
	 * @param email
	 * @return
	 */
	public User getByEmail(String email);
	/**
	 * search by ActiveCode
	 * @param activeCode
	 * @return
	 */
	public User getByActiveCode(String activeCode);
	/**
	 * update User
	 * @param user
	 */
	public void update(User user);
	
	/**
	 * get by user's property
	 * @param map
	 * @return
	 */
	public User getByProperty(Map map);
}
