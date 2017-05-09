package com.mall.test.dao;

import java.util.List;

import junit.framework.Assert;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import util.SessionFactoryUtils;

import com.mall.dao.CategoryDao;
import com.mall.domain.Category;

public class CategoryDaoTest {
	SqlSession session = SessionFactoryUtils.getSqlSessionFactory()
			.openSession();
	CategoryDao dao = session.getMapper(CategoryDao.class);
	
	@Test
	public void addFirstDaoTest(){
		Category c = new Category();
			c.setCid("c2");
			c.setName("apple");
			c.setDesc("phone");
			c.setFid("f1");
		dao.addFirst(c);
		session.commit();
	}
	@Test
	public void addSecondDaoTest(){
		Category c = new Category();
			c.setCid("cs3");
			c.setName("apple_");
			c.setDesc("phone");
			c.setFid("f1");
		dao.addSecond(c);
		session.commit();
	}
	@Test
	public void getByFirstIdTest(){
		Category c = dao.getByFirstId("c1");
		Assert.assertNotNull(c);
		System.out.println(c);
	}
	@Test
	public void getBySecondIdTest(){
		Category c = dao.getBySecondId("cs1");
		Assert.assertNotNull(c);
		System.out.println(c);
	}
	@Test
	public void updateByFirstIdTest(){
		Category c = dao.getByFirstId("c2");
		//c.setCid("c2");
		c.setName("apple_chage");
		c.setDesc("phone_change");
		dao.updateByFirstId(c);
		session.commit();
	} 
	@Test
	public void updateBySecondIdTest(){
		Category c = dao.getBySecondId("cs3");
		//c.setCid("c2");
		c.setName("apple_cha");
		c.setDesc("phone_changes");
		c.setFid("f1_change");
		dao.updateBySecondId(c);
		session.commit();
	} 
	@Test
	public void deleteByFirstIdTest(){
		dao.deleteByFirstId("c2");
		session.commit();
	}
	@Test
	public void deleteBySecondIdTest(){
		dao.deleteBySecondId("cs2");
		session.commit();
	}
	@Test
	public void deleteBySecondFidTest(){
		dao.deleteBySecondFid("f1_change");
		session.commit();
	}
	@Test		
	public void getAllCategoryTest(){
		//						 getAllCategory
		List<Category> all = dao.getAllCategory();
		System.out.println(all);
		System.out.println(all.size());
	}
}
