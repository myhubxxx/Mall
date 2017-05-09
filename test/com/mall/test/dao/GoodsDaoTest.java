package com.mall.test.dao;

import java.sql.SQLException;

import junit.framework.Assert;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import util.DateUtils;
import util.SessionFactoryUtils;

import com.mall.dao.GoodsDao;
import com.mall.domain.Category;
import com.mall.domain.Goods;

public class GoodsDaoTest {
	SqlSession session = SessionFactoryUtils.getSqlSessionFactory()
			.openSession();
	GoodsDao dao = session.getMapper(GoodsDao.class);
	
	@Test
	public void addTest()throws Exception{
		Goods g = new Goods();
			g.setGid("g1");
			g.setGname("computer");
			g.setPrice(25);
			g.setNowPrice(120);
			g.setDiscount(0.8);
			g.setProducetime("2017-04-04");
			g.setDesc_1("so beautiful");
			g.setImage_b("smaill img");
			g.setImage_w("big img");
		Category c = new Category();
			c.setCid("cs1");
			g.setCategory(c);
		dao.add(g);
		session.commit();
	}
	@Test
	public void getByIdTest() throws SQLException{
		Goods g = dao.getById("g1");
		Assert.assertNotNull(g);
		System.out.println(g);
	}
	@Test
	public void updateByIdTest() throws SQLException{
		Goods g = dao.getById("g1");
		Assert.assertNotNull(g);
			g.setGname("computer");
			g.setPrice(1);
			g.setNowPrice(1);
			g.setDiscount(10);
			g.setProducetime(DateUtils.getSimpleDate());
			g.setDesc_1("so beautiful_change");
			g.setDesc_3("null_change");
			g.setImage_b("smaill img_change");
			g.setImage_w("big img_change");
		Category c = new Category();
			c.setCid("cs1_change");
			g.setCategory(c);
		dao.updateById(g);
		session.commit();
	}
	@Test
	public void deleteByIdTest() throws SQLException{
		dao.deleteById("g1");
		session.commit();
	}
	@Test
	public void getAllCountTest() throws SQLException{
		Number n = dao.getAllCount(null);
		Assert.assertNotNull(n);
		System.out.println(n.intValue());
	}
	@Test
	public void insert120() throws Exception{
		Goods g = dao.getById("g1");
		Category category = new Category();
		category.setCid("cs1");
		g.setCategory(category);
		for (int i = 3; i < 121; i++) {
			g.setGid("g"+i);
			dao.add(g);
		}
		session.commit();
		System.out.println("fin");
		
	}
}
