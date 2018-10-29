package kr.or.ddit.login.service;

import kr.or.ddit.login.dao.ILoginDao;
import kr.or.ddit.login.dao.LoginDao;
import kr.or.ddit.model.UserVO;

public class LoginService implements ILoginService {

	private ILoginDao dao = null;
	private static ILoginService service = null;
	
	private LoginService() {
		dao = LoginDao.getInstance();
	}
	
	public static ILoginService getInstance() {
		if(service == null){
			service = new LoginService();
		}
		return service;
	}
	
	@Override
	public UserVO selectUser(UserVO userVO) {
		UserVO userInfo = dao.selectUser(userVO);
		return userInfo;
	}

}
