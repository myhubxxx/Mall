package com.mall.test.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import util.DateUtils;
import util.SessionFactoryUtils;

import com.mall.dao.OrdersDao;
import com.mall.domain.OrderInfo;
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
			o.setPhoneNumber("12345678901");
			o.setAddress("北极");
		User u = new User();
			u.setUid("u1");
			o.setUser(u);
		dao.add(o);
		session.commit();
	}
	@Test
	public void getByIdTest() throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index", 1);
		map.put("oid", "B8A0A5E40C1C44A2851611A878A9AA86");
		Orders o = dao.getByIdMap(map);
		System.out.println(o);
//		Assert.assertNotNull(o);
	}
	@Test
	public void updateByIdTest()throws Exception{
		Orders o = dao.getById("o1");
		o.setStatus(2);
		dao.updateById(o);
		session.commit();
	}
	@Test
	public void getByGoodsPageTest()throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", "961957AFC18A4A3DB9860CF050492857");
		map.put("index", 1);
		map.put("lose", 6);
		map.put("pageSize", 1);
		List<Orders> list = dao.getByGoodsPage(map);
		System.out.println(list.size());
		System.out.println(list);
		session.commit();
	}
	@Test
	public void getByGoodsPageOidTest()throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("oid", "595562056A3145368364D20810A9C5A2");
		map.put("index", 1);
		map.put("lose", 0);
		map.put("pageSize", 5);
		Orders orders = dao.getByIdMap(map);
		System.out.println(orders);
//		System.out.println(list.size());
//		System.out.println(list);
		session.commit();
	}
	@Test
	public void getByGoodsPageTest2()throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", "961957AFC18A4A3DB9860CF050492857");
		map.put("index", 1);
		map.put("lose", 0);
		map.put("pageSize", 5);
		List<OrderInfo> list = session.selectList("com.mall.dao.OrdersDao.getByGoodsPage", map);
		System.out.println(list.size());
		System.out.println(list);
		session.commit();
	}
	
	
}
