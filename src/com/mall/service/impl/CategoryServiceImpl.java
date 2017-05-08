package com.mall.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import util.SessionFactoryUtils;

import com.mall.dao.CategoryDao;
import com.mall.domain.Category;
import com.mall.service.CategoryService;
import com.opensymphony.xwork2.ActionContext;

public class CategoryServiceImpl implements CategoryService {

	SqlSessionFactory sf = SessionFactoryUtils.getSqlSessionFactory();
	@Override
	public List<Category> getAll() {
		SqlSession session = sf.openSession();
		try {
			CategoryDao dao = session.getMapper(CategoryDao.class);
			return dao.getAllCategory();
		} finally{
			session.close();
		}
	}

}
