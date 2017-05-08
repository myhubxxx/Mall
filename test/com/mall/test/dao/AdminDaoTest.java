package com.mall.test.dao;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import util.SessionFactoryUtils;

import com.mall.dao.AdminDao;
import com.mall.dao.OrderInfoDao;
import com.mall.domain.Admin;

public class AdminDaoTest {
	SqlSession session = SessionFactoryUtils.getSqlSessionFactory()
			.openSession();
	AdminDao dao = session.getMapper(AdminDao.class);

	@Test
	public void getByIdTest() throws Exception {
		Admin o = dao.getById("a1");
		Assert.assertNotNull(o);
		System.out.println(o);
	}

	@Test
	public void getByUsernameTest() {
		Admin a = dao.getByUsername("a1");
		Assert.assertNotNull(a);
		System.out.println(a);
	}
}
