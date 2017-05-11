package com.mall.test.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import util.SessionFactoryUtils;

import com.mall.dao.OrderInfoDao;
import com.mall.domain.Goods;
import com.mall.domain.OrderInfo;
import com.mall.domain.Orders;

public class OrderInfoDaoImplTest {

	SqlSession session = SessionFactoryUtils.getSqlSessionFactory().openSession();
	OrderInfoDao dao = session.getMapper(OrderInfoDao.class);
	
	@Test
	public void getByOidTest() throws Exception{
		Map map = new HashMap();
		map.put("index", 3);
		map.put("oid", "o4n");
		OrderInfo o = dao.getByOid(map);
		System.out.println(o);
	}
	@Test
	public void addTestLoadBalance() throws Exception{
		Map map = new HashMap();
		map.put("index", 4);
		OrderInfo o = new OrderInfo();
		o.setOrid("or4l");
		o.setNumber(2);
		o.setTotal(20);
			Goods g= new Goods();
			g.setGid("g4l");
			g.setGname("g4name");
			g.setNowPrice(20);
			g.setImage_b("xx.jpg");
		o.setGid("g4l");
			Orders oder = new Orders();
			oder.setOid("o4l");
		o.setOrders(oder);
		map.put("or", o);
		dao.add(map);
		session.commit();
	}
	@Test
	public void addTestNotLoadBalance() throws Exception{
		Map map = new HashMap();
		OrderInfo o = new OrderInfo();
		o.setOrid("or4n");
		o.setNumber(2);
		o.setTotal(20);
			Goods g= new Goods();
			g.setGid("g4n");
			g.setGname("g4name");
			g.setNowPrice(20);
			g.setImage_b("xx.jpg");
//		o.setGoods(g);
			Orders oder = new Orders();
			oder.setOid("o4n");
		o.setOrders(oder);
		map.put("or", o);
		dao.add(map);
		session.commit();
	}
	@Test
	public void deleteByOrid(){
		// 
		Map map = new HashMap();
		
		map.put("orid", "or4n");
		dao.deleteByOrid(map);
		map.put("index", 4);
		dao.deleteByOrid(map);
		session.commit();
	}
}
