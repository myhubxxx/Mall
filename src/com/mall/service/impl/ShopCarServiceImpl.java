package com.mall.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import util.SessionFactoryUtils;

import com.mall.dao.ShopCarDao;
import com.mall.domain.ShopCar;
import com.mall.service.ShopCarService;

public class ShopCarServiceImpl implements ShopCarService {
	SqlSessionFactory sf = SessionFactoryUtils.getSqlSessionFactory();

	public boolean addShopCar(ShopCar sc) {
		SqlSession session = sf.openSession();
		try {
			ShopCarDao dao = session.getMapper(ShopCarDao.class);
			// now first to check the user aleady has the goods
			Map<String, String> map = new HashMap<String, String>();
			map.put("uid", sc.getUser().getUid());
			map.put("gid", sc.getGoods().getGid());
			ShopCar dbShopCar = dao.getByUidGid(map);
			if(dbShopCar == null){
				// the goods not in user shopcar
				dao.add(sc);
			}else{
				// the user aleady has the goods
				dbShopCar.setNumber( dbShopCar.getNumber() + sc.getNumber() );
				dao.updateById(dbShopCar);
			}
			session.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			session.close();
		}
	}

	@Override
	public List<ShopCar> getShopCarByUser(String uid) {
		SqlSession session = sf.openSession();
		try {
			ShopCarDao dao = session.getMapper(ShopCarDao.class);
			return dao.getByUserId(uid);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
		
	}
	
}
