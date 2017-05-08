package com.mall.test.dao;

import java.sql.SQLException;

import junit.framework.Assert;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import util.DateUtils;
import util.SessionFactoryUtils;

import com.mall.dao.OrdersDao;
import com.mall.domain.Orders;
import com.mall.domain.User;

public class OrdersDaoTest {
	SqlSession session = SessionFactoryUtils.getSqlSessionFactory()
			.openSession();
	OrdersDao dao = session.getMapper(OrdersDao.class);
	
	@Test
	public void testAdd() throws Exception{
		Orders o = new Orders();
			o.setOid("o1");
			o.setTime(DateUtils.getTime());
			o.setCount(200);
			o.setStatus(1);
			o.setAddress("Î÷ÄÏ²ËÓÍ");
		User u = new User();
			u.setUid("u22");
			o.setUser(u);
		dao.add(o);
		session.commit();
	}
	@Test
	public void getByIdTest() throws Exception{
		Orders o = dao.getById("o1");
		Assert.assertNotNull(o);
		System.out.println(o);
	}
	@Test
	public void updateByIdTest()throws Exception{
		Orders o = dao.getById("o1");
		o.setStatus(2);
		dao.updateById(o);
		session.commit();
	}
}
