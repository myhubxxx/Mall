package com.mall.test.dao;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import util.SessionFactoryUtils;

import com.mall.dao.ShopCarDao;
import com.mall.domain.ShopCar;

public class ShopCarDaoTest {
	SqlSession session = SessionFactoryUtils.getSqlSessionFactory()
			.openSession();
	ShopCarDao dao = session.getMapper(ShopCarDao.class);
	
	@Test
	public void getBySidGoods(){
//		http://127.0.0.1:8080/Mall/Mall/deleteShopCar.action?cartItemIds==6A8EAEEA2DE74F44BF5EDFBC0B9E0815
		ShopCar sc = dao.getBySidGoods("6A8EAEEA2DE74F44BF5EDFBC0B9E0815");
		System.out.println(sc);
	}
}
