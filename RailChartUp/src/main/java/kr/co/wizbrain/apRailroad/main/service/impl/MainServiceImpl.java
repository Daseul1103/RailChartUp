package kr.co.wizbrain.apRailroad.main.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import kr.co.wizbrain.apRailroad.main.mapper.MainMapper;
import kr.co.wizbrain.apRailroad.main.service.MainService;

/**
 * 메인 서비스 구현 클래스
 * @author 미래전략사업팀 정다빈
 * @since 2020.07.30
 * @version 1.0
 * @see
 *
 * << 개정이력(Modification Information) >>
 *
 *   수정일            수정자              수정내용
 *  -------    -------- ---------------------------
 *  2020.07.30  정다빈           최초 생성
 */

@Service("mainService")
public class MainServiceImpl implements MainService{
	
	@Resource(name="mainMapper")
	private MainMapper mainMapper;
	
//	public List<EgovMap> selectUserInfo() throws Exception{
//		return mainMapper.selectUserInfo();
//	}
//
//	public void insertUser(UserVO userVO) throws Exception {
//		mainMapper.insertUser(userVO);
//		
//	}
}
