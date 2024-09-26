package kr.co.wizbrain.apRailroad.user.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import kr.co.wizbrain.apRailroad.user.vo.UserVO;

/**
 * 사용자 서비스 클래스
 * @author 미래전략사업팀 정다빈
 * @since 2020.07.23
 * @version 1.0
 * @see
 *
 * << 개정이력(Modification Information) >>
 *
 *   수정일            수정자              수정내용
 *  -------    -------- ---------------------------
 *  2020.07.23  정다빈           최초 생성
 */

public interface UserService {

	//전체 사용자 조회
	public List<UserVO> selectUserList(UserVO userVO) throws Exception;
	
	//사용자 등록
	public void insertUser(UserVO userVO) throws Exception;
	
	//특정 사용자 조회
	public UserVO selectUser(UserVO userVO) throws Exception;

	//guest 사용이력 조회
	public List<UserVO> guestHistoryList();

	//사용자 정보 수정
	public void updateUser(UserVO uvo);

	//사용자 삭제
	public void deleteUser(List<String> userArr);

}