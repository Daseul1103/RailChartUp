package kr.co.wizbrain.apRailroad.comuFail.service;

import java.util.List;

import kr.co.wizbrain.apRailroad.comuFail.vo.ComuFailVO;


public interface ComuFailService {

	List<ComuFailVO> selectComuFailList(ComuFailVO cvo) throws Exception;


}
