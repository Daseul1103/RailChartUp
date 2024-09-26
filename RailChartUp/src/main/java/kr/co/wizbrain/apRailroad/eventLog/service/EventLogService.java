package kr.co.wizbrain.apRailroad.eventLog.service;

import java.util.List;

import kr.co.wizbrain.apRailroad.eventLog.vo.EventLogVO;
import kr.co.wizbrain.apRailroad.eventLog.vo.HseSndVO;
import kr.co.wizbrain.apRailroad.eventLog.vo.TcmsRcvVO;
import kr.co.wizbrain.apRailroad.user.vo.UserVO;

public interface EventLogService {
	// 이벤트 로그 조회
	public List<EventLogVO> selectEventLogList(EventLogVO cvo) throws Exception;

	// tcms 로그 조회
	public List<TcmsRcvVO> selectTcmsRcvList(String sDate,String eDate, String tagId) throws Exception;

	// hse 로그 조회
	public List<HseSndVO> selectHseSndList(String sDate,String eDate, String tagId) throws Exception;

	public EventLogVO selectEventLog(EventLogVO evo);

}
