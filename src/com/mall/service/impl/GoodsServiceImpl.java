package com.mall.service.impl;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import util.PageBean;
import util.SessionFactoryUtils;

import com.mall.dao.GoodsDao;
import com.mall.domain.Goods;
import com.mall.service.GoodsService;

public class GoodsServiceImpl implements GoodsService {
	private static final Logger log = Logger.getLogger(GoodsServiceImpl.class);
	SqlSessionFactory sf = SessionFactoryUtils.getSqlSessionFactory();

	/**
	 * goods search by page
	 */
	public PageBean<Goods> getPage(PageBean<Goods> page, Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		SqlSession session = sf.openSession();
		try{
			GoodsDao dao = session.getMapper(GoodsDao.class);
//			// check atturl argument is null
//			if( StringUtils.hasText( page.getAttrUrl() )){
//				// not null: check atturl has and
//				if(page.getAttrUrl().indexOf("and") == -1){
//					// not found "and" then append
//					sb.append(" and " + page.getAttrUrl());
//				}else{
//					// found "and"
//					sb.append(" " + page.getAttrUrl());
//				}
//			}// end if
//			String attrUrl = null;
//			//get search sql 
//			if(sb.length() != 0){
//				attrUrl = sb.toString();
//			}
			// search the number match the argument
			Number  number = dao.getAllCount( map);
			int record = number == null ? 0 : number.intValue();
			page.setTotalRecord(record);
			// get the data
			int lose = (page.getCurrentPage() == 0)?0 : ( (page.getCurrentPage()-1)*page.getPageSize() );
//			Map<String , Object> attrMap = new HashMap<String, Object>();
//				attrMap.put("attrUrl", attrUrl);
				map.put("lose", lose);
				map.put("pageSize", page.getPageSize());
//			page.setAttrUrl(attrUrl);
			page.setPage( dao.getCurrentPage(map) );
			page.getStart();
			
			return page;
		}catch(Exception e){
			log.error("get page:"+ e);
			return null;
		}finally{
			session.close();
		}
	}

	@Override
	public Goods getByGid(String gid) {
		SqlSession session = sf.openSession();
		try {
			GoodsDao dao = session.getMapper(GoodsDao.class);
			Goods good = dao.getById(gid);
			return good;
		} catch (SQLException e) {
			log.info(e);
			e.printStackTrace();
			return null;
		}finally{
			session.close();
		}
	}


	public void deleteByGid(String gid) {
		SqlSession session = sf.openSession();
		try {
			GoodsDao dao = session.getMapper(GoodsDao.class);
			dao.deleteById(gid);
			session.commit();
		} catch (SQLException e) {
			log.info(e);
			e.printStackTrace();
		}finally{
			session.close();
		}
	}

	public void updateGood(Goods good) {
		SqlSession session = sf.openSession();
		try {
			GoodsDao dao = session.getMapper(GoodsDao.class);
			dao.updateById(good);
			session.commit();
		} catch (Exception e) {
			log.info(e);
			e.printStackTrace();
		}finally{
			session.close();
		}
	}

	public void addGood(Goods good) {
		SqlSession session = sf.openSession();
		try {
			GoodsDao dao = session.getMapper(GoodsDao.class);
			dao.add(good);
			session.commit();
		} catch (Exception e) {
			log.info(e);
			e.printStackTrace();
		}finally{
			session.close();
		}
		
	}

}
