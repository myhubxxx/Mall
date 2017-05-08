package com.mall.service.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;

import javax.mail.MessagingException;
import javax.mail.Session;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import util.CommonUtils;
import util.EmailUtils;
import util.MD5Utils;
import util.MailUtils;
import util.SessionFactoryUtils;
import cn.itcast.mail.Mail;

import com.mall.dao.UserDao;
import com.mall.domain.User;
import com.mall.service.UserService;

public class UserServiceImpl implements UserService {
	private static final Logger log = Logger.getLogger(UserServiceImpl.class);
	SqlSessionFactory sf = SessionFactoryUtils.getSqlSessionFactory();
	
	public boolean existsUsername(String username){
		SqlSession session = sf.openSession();
		try {
			UserDao dao = session.getMapper(UserDao.class);
			User user = dao.getByUsername(username);
			if( user == null ) return false;
			return true;
		} finally {
			session.close();
		}
	}

	public boolean existsEmail(String email) {
		SqlSession session = sf.openSession();
		try {
			UserDao dao = session.getMapper(UserDao.class);
			User user = dao.getByEmail(email);
			if(user == null) return false;
			return true;
		} finally {
			session.close();
		}
	}


	public boolean registUser(User user) {
		// password must be MD5 encode
		user.setSurePassword(null);
		user.setPassword( MD5Utils.string2MD5( user.getPassword()));
		user.setActive(0);
		// uid
		user.setUid(CommonUtils.uuid());
		user.setActiveCode(CommonUtils.uuid()+CommonUtils.uuid());
		log.info("user:"+user);
		SqlSession session = sf.openSession();
		UserDao dao = session.getMapper(UserDao.class);
		try {
			// send email to verify
			String content = MessageFormat.format(EmailUtils.content, user.getActiveCode(), user.getActiveCode());
			log.debug("email content :" + content);
			Mail mail = new Mail(EmailUtils.from, user.getEmailAddress(), EmailUtils.subject, content);
			Session mailSession = MailUtils.createSession(EmailUtils.host, EmailUtils.username, EmailUtils.password); 
			MailUtils.send(mailSession, mail);
			log.info("send mail success");
			dao.add(user);
			session.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			log.error("send mail failure");
			e.printStackTrace();
			return false;
		} finally{
			session.close();
		}
	}

	@Override
	public String activeUser(String activeCode) {
		SqlSession session = sf.openSession();
		UserDao dao = session.getMapper(UserDao.class);
		User user = dao.getByActiveCode(activeCode);
		if(user == null){
			log.info("激活码无效");
			return "激活码无效";
		}
		if(user.getActive() != 0 ){
			log.info("用户无需多次激活");
			return "用户无需多次激活";
		}
		user.setActive(1);
		user.setPassword(null);
		try {
			dao.updateById(user);
			session.commit();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			log.info(e);
			return e.getMessage();
		}finally{
			session.close();
		}
	}

	@Override
	public User login(User user) {
		SqlSession session = sf.openSession();
		try {
			UserDao dao = session.getMapper(UserDao.class);
			User dbUser = dao.getByUsername(user.getUsername());
			return dbUser;
		} finally {
			session.close();
		}
	}

	@Override
	public void changePassword(User sessionUser) throws SQLException {
		SqlSession session = sf.openSession();
		try {
			UserDao dao = session.getMapper(UserDao.class);
			dao.updateById(sessionUser);
			session.commit();
		} finally {
			session.close();
		}
		
	}
	

}
