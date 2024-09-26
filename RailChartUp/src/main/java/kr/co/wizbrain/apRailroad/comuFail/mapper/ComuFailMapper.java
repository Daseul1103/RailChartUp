package kr.co.wizbrain.apRailroad.comuFail.mapper;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.wizbrain.apRailroad.comuFail.vo.ComuFailVO;

@Mapper("comuFailMapper")
public interface ComuFailMapper {
	//전체 통신장애 정보 조회
		public List<ComuFailVO> selectComuFailList(ComuFailVO cvo) throws Exception;
}
