package util;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SessionFactoryUtils {
	
	private static SqlSessionFactory factory;
	
	public static ThreadLocal<SqlSession> sqlSession = new ThreadLocal<SqlSession>();
	
	static{
		
		InputStream in = SessionFactoryUtils.class.getClassLoader().getResourceAsStream("SqlMapConfig.xml");
		
		factory = new SqlSessionFactoryBuilder().build(in);
		
	}
	public static SqlSessionFactory getSqlSessionFactory(){
		return factory;
	}
	
	public static SqlSession getSession(){
		SqlSession session = sqlSession.get();
		if(session == null){
			openSession();
			return sqlSession.get();
		}
		return session;
	}
	public void closeSession(){
		SqlSession session = sqlSession.get();
		if(session != null){
			sqlSession.set(null);
			session.close();
		}
		
	}
	
	
	private static void openSession() {
		
		sqlSession.set(factory.openSession());
	}
	
	

}
