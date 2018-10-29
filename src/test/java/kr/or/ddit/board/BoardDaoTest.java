package kr.or.ddit.board;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.or.ddit.board.dao.BoardDao;
import kr.or.ddit.board.dao.IBoardDao;
import kr.or.ddit.model.PageVO;
import kr.or.ddit.model.PostsVO;

import org.junit.After;
import org.junit.Test;

public class BoardDaoTest {

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void boardDaoTest() {
		IBoardDao dao = BoardDao.getInstance();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageVO", new PageVO(1, 10));
		params.put("bd_id", "bd001");
		
		List<PostsVO> psList = dao.selectBoardPostsPage(params);
		assertEquals(10, psList.size());
	}

}
