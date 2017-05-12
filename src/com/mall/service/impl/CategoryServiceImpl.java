package com.mall.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import util.SessionFactoryUtils;
import util.StringUtils;

import com.mall.dao.CategoryDao;
import com.mall.domain.Category;
import com.mall.service.CategoryService;
import com.opensymphony.xwork2.ActionContext;

public class CategoryServiceImpl implements CategoryService {

	SqlSessionFactory sf = SessionFactoryUtils.getSqlSessionFactory();
	
	public List<Category> getAll() {
		SqlSession session = sf.openSession();
		try {
			CategoryDao dao = session.getMapper(CategoryDao.class);
			return dao.getAllCategory();
		} finally{
			session.close();
		}
	}

	public void addCategoryFirst(Category form) {
		SqlSession session = sf.openSession();
		try {
			CategoryDao dao = session.getMapper(CategoryDao.class);
			dao.addFirst(form);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void addCategorySecond(Category form) {
		SqlSession session = sf.openSession();
		try {
			CategoryDao dao = session.getMapper(CategoryDao.class);
			dao.addSecond(form);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void updateCategory(Category form) {
		SqlSession session = sf.openSession();
		try {
			CategoryDao dao = session.getMapper(CategoryDao.class);
			if( StringUtils.hasText(form.getFid()) ){
				// update First
				dao.updateBySecondId(form);
			}else{
				// update Second
				dao.updateByFirstId(form);
			}
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			session.close();
		}
	}

	
	public void deleteByFirstId(String cid) {
		SqlSession session = sf.openSession();
		try {
			CategoryDao dao = session.getMapper(CategoryDao.class);
			dao.deleteByFirstId(cid);
			session.commit();
		} finally{
			session.close();
		}
	}
	public void deleteBySecondId(String cid) {
		SqlSession session = sf.openSession();
		try {
			CategoryDao dao = session.getMapper(CategoryDao.class);
			dao.deleteBySecondId(cid);
			session.commit();
		} finally{
			session.close();
		}
	}
	public Category getFirstCategoryId(String cid){
		SqlSession session = sf.openSession();
		try{
			CategoryDao dao = session.getMapper(CategoryDao.class);
			Category c = dao.getByFirstId(cid);
			return c;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			session.close();
		}
	}
	public Category getSecondCategoryId(String cid){
		SqlSession session = sf.openSession();
		try{
			CategoryDao dao = session.getMapper(CategoryDao.class);
			Category c = dao.getBySecondId(cid);
			return c;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			session.close();
		}
	}

}
