package kr.co.wizbrain.apRailroad.eventLog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.co.wizbrain.apRailroad.eventLog.mapper.EventLogMapper;
import kr.co.wizbrain.apRailroad.eventLog.service.EventLogService;
import kr.co.wizbrain.apRailroad.eventLog.vo.EventLogVO;
import kr.co.wizbrain.apRailroad.eventLog.vo.HseSndVO;
import kr.co.wizbrain.apRailroad.eventLog.vo.TcmsRcvVO;

@Service("eventLogService")
public class EventLogServiceImpl implements EventLogService{
	
	@Resource(name="eventLogMapper")
	private EventLogMapper eventLogMapper;

	//이벤트 로그  조회
	@Override
	public List<EventLogVO> selectEventLogList(EventLogVO cvo) throws Exception{
		return eventLogMapper.selectEventLogList(cvo);
	}
	
	//tcms 로그 조회
	@Override
	public List<TcmsRcvVO> selectTcmsRcvList(String sDate,String eDate, String tagId) throws Exception{
		return eventLogMapper.selectTcmsRcvList(sDate,eDate,tagId);
	}
	
	//hse 로그 조회
	@Override
	public List<HseSndVO> selectHseSndList(String sDate,String eDate, String tagId) throws Exception{
		return eventLogMapper.selectHseSndList(sDate,eDate,tagId);
	}

	@Override
	public EventLogVO selectEventLog(EventLogVO evo) {
		return eventLogMapper.selectEventLog(evo).get(0);
	}
	
}
