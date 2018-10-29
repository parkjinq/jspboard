package kr.or.ddit.login.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.db.SqlFactoryBuilder;
import kr.or.ddit.model.UserVO;

public class LoginDao implements ILoginDao {
	
	private static ILoginDao dao = null;
	private SqlSessionFactory factory = null;
	private SqlSession session = null;
	
	private LoginDao() {
		factory = SqlFactoryBuilder.getSqlSessionFactory();
		session = factory.openSession();
	}
	
	public static ILoginDao getInstance() {
		if(dao == null){
			dao = new LoginDao();
		}
		return dao;
	}

	@Override
	public UserVO selectUser(UserVO userVO) {
		UserVO userInfo = session.selectOne("user.selectUser", userVO);
		return userInfo;
	}

}
