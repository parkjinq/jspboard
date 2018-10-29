package kr.or.ddit.login.service;

import kr.or.ddit.model.UserVO;

public interface ILoginService {
	/**
	 * 
	* Method : selectUser
	* 작성자 : jin
	* 변경이력 :
	* @param userVO
	* @return
	* Method 설명 : userId, pass를 만족하는 UserVO를 반환하는 메서드
	 */
	public UserVO selectUser(UserVO userVO);
}
