package kr.co.wizbrain.apRailroad.comuFail.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.co.wizbrain.apRailroad.comuFail.mapper.ComuFailMapper;
import kr.co.wizbrain.apRailroad.comuFail.service.ComuFailService;
import kr.co.wizbrain.apRailroad.comuFail.vo.ComuFailVO;

@Service("comuFailService")
public class ComuFailServiceImpl implements ComuFailService{
	
	@Resource(name="comuFailMapper")
	private ComuFailMapper comuFailMapper;

	//전체 장애정보 조회
	@Override
	public List<ComuFailVO> selectComuFailList(ComuFailVO cvo) throws Exception{
		return comuFailMapper.selectComuFailList(cvo);
	}
	
}
