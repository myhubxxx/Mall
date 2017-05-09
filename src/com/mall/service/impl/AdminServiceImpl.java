package com.mall.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import util.SessionFactoryUtils;

import com.mall.dao.AdminDao;
import com.mall.domain.Admin;
import com.mall.service.AdminService;

public class AdminServiceImpl implements AdminService {
	private static final Logger log = Logger.getLogger(AdminServiceImpl.class);
	SqlSessionFactory sf = SessionFactoryUtils.getSqlSessionFactory();
	
	public Admin login(String username) {
		SqlSession session = sf.openSession();
		try {
			AdminDao dao = session.getMapper(AdminDao.class);
			return dao.getByUsername(username);
		}finally{
			session.close();
		}
	}

}
