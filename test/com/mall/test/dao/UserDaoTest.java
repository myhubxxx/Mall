package com.mall.test.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import util.SessionFactoryUtils;

import com.mall.dao.UserDao;
import com.mall.domain.User;

public class UserDaoTest {
	SqlSession session = SessionFactoryUtils.getSqlSessionFactory()
			.openSession();
	UserDao dao = session.getMapper(UserDao.class);
	
	@Test
	public void addTest() throws SQLException{
		User u = new User();
			u.setUid("u2");
			u.setUsername("u2u");
			u.setPassword("u2p");
			u.setEmailAddress("xx.3@166.com");
			u.setActive(0);
			u.setActiveCode("u2uu");
		dao.add(u);
		session.commit();
	}
	@Test
	public void getByIdTest() throws SQLException{
		User u = dao.getById("u2");
		Assert.assertNotNull(u);
		System.out.println(u);
	}
	@Test
	public void getByUsernameTest(){
		User u = dao.getByUsername("u2u");
		Assert.assertNotNull(u);
		System.out.println(u);
	}
	@Test
	public void getByEmailTest(){
		User u = dao.getByEmail("xx.3@166.com");
		Assert.assertNotNull(u);
		System.out.println(u);
	}
	@Test
	public void getByActiveCodeTest(){
		User u = dao.getByActiveCode("u2uu");
		Assert.assertNotNull(u);
		System.out.println(u);
	}
	@Test
	public void getByPropertyTest(){
		Map map = new HashMap();
		map.put("activeCode", "u2uu");
		User u = dao.getByProperty(map);
		Assert.assertNotNull(u);
		System.out.println(u);
	}
}
