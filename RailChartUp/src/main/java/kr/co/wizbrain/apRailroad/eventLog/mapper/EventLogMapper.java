package kr.co.wizbrain.apRailroad.eventLog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.wizbrain.apRailroad.eventLog.vo.EventLogVO;
import kr.co.wizbrain.apRailroad.eventLog.vo.HseSndVO;
import kr.co.wizbrain.apRailroad.eventLog.vo.TcmsRcvVO;

@Mapper("eventLogMapper")
public interface EventLogMapper {
	// 전체 통신장애 정보 조회
	public List<EventLogVO> selectEventLogList(EventLogVO cvo) throws Exception;

	// tcms 로그 조회
	public List<TcmsRcvVO> selectTcmsRcvList(@Param("sDate")String sDate,@Param("eDate")String eDate,@Param("tagId")String tagId) throws Exception;

	// hse 로그 조회
	public List<HseSndVO> selectHseSndList(@Param("sDate")String sDate,@Param("eDate")String eDate,@Param("tagId")String tagId) throws Exception;

	//이벤트로그 상세
	public List<EventLogVO> selectEventLog(EventLogVO evo);

}
