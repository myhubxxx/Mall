package com.mall.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import util.LoadBalance;
import util.PropertiesUtils;
import util.SessionFactoryUtils;

import com.mall.dao.OrderInfoDao;
import com.mall.dao.OrdersDao;
import com.mall.dao.ShopCarDao;
import com.mall.domain.Orders;
import com.mall.service.OrderService;

public class OrderServiceImpl implements OrderService {
	private static final Logger log = Logger.getLogger(OrderServiceImpl.class);
	SqlSessionFactory sf = SessionFactoryUtils.getSqlSessionFactory();
	
	public boolean addOrder(Orders myOrder, String[] shopCarIdArray) {
		SqlSession session = sf.openSession();
		try{
			OrdersDao dao = session.getMapper(OrdersDao.class);
			ShopCarDao shopCardao = session.getMapper(ShopCarDao.class);
			OrderInfoDao orderInfoDao = session.getMapper(OrderInfoDao.class);
			// add Order
			dao.add(myOrder);
			log.info("OrderServiceImpl: addOrder");
			// delete user's shopCar
			shopCardao.deleteByIdArray(shopCarIdArray);
			log.info("OrderServiceImpl: shopCar delete:" + shopCarIdArray);
			// add OrderInfo
			Map<String, Object> map = new HashMap<String, Object>();
			// check is loadBalance
			String isLoadBalance = PropertiesUtils.getValue("LoadBalance");
			log.info("OrderServiceImpl: isLoadBalance:" + isLoadBalance);
			if( isLoadBalance.equals("1") ){
				// loadBalance calc the table index;
				Integer index = LoadBalance.calculateTable(myOrder.getUser().getUid() );
				log.info("OrderServiceImpl: index:" + index);
				map.put("index", index);
			}
			map.put("ors", myOrder.getOrderInfoList() );
			orderInfoDao.addBatch(map);
			log.info("OrderServiceImpl: OrderInfo:addBatch");
			session.commit();
			log.info("OrderServiceImpl: session commit");
			return true;
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
			session.rollback();
			return false;
		}finally{
			session.close();
		}
	}

	public Orders getOrderById(Map<String, Object> map) {
		SqlSession session = sf.openSession();	
		try {
			OrdersDao dao = session.getMapper(OrdersDao.class);
			Orders order = dao.getByIdMap(map);
			session.commit();
			return order;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally{
			session.close();
		}
	}

}
