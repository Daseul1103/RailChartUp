package kr.co.wizbrain.apRailroad.comm;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.co.wizbrain.apRailroad.statistic.service.StatisticService;
import kr.co.wizbrain.apRailroad.statistic.vo.ChkDateVO;
import kr.co.wizbrain.apRailroad.statistic.vo.StatisticVO;

@Service
public class ClientChkScheduler {
public static final Logger logger = LoggerFactory.getLogger(ClientChkScheduler.class);
	
	@Resource(name="statisticService")
	private StatisticService statisticService;
	
	/**
	 * 스케줄러-매일 새벽 3시에 실행
	 * */
	public void clientAutoChk(){
		logger.debug("$$$$$ 스케줄러 실행!!!!!!!!!!!!!!!!");
		logger.debug("$$$$$ tcms로그와 통계 테이블의 없는날짜 비교!!!!!!!!!!!!!!!!");
		try {
			
			//일일통계 관련
			List<String> dfDayList = statisticService.chkDt();
			logger.debug("$$$$$ 통계 테이블에 미 기재된 날짜 수: "+dfDayList.size());
			for (int i = 0; i < dfDayList.size(); i++) {
				//월간(일별)통계에 일일 데이터 삽입
				statisticService.insertMdStc(dfDayList.get(i));
			}
			
			//월간(일별)통계 관련
			List<String> dfMdList = statisticService.chkDt();
			logger.debug("$$$$$ 통계 테이블에 미 기재된 날짜 수: "+dfMdList.size());
			for (int i = 0; i < dfMdList.size(); i++) {
				//월간(일별)통계에 일일 데이터 삽입
				statisticService.insertMdStc(dfMdList.get(i));
			}

			//월별통계 관련
			List<ChkDateVO> dfMonList = statisticService.chkMon();
			logger.debug("$$$$$ 통계 테이블에 미 기재된 날짜 수: "+dfMonList.size());
			for (int i = 0; i < dfMonList.size(); i++) {
				//해당 년 월에 대한 데이터가 통계 테이블에 있는지 확인
				//넘겨줄 파라미터 : 년,월
				StatisticVO monStcData = statisticService.findMonData(dfMonList.get(i));
				//해당 년월의 검출한 첫일과 말일 범위로 로그데이터에서 통계 데이터를 산출
				//넘겨줄 파라미터 : 첫일,말일
				StatisticVO upt = statisticService.setTcmsMonStc(dfMonList.get(i));	
				
				//있으면 update add 하고 없으면 insert
				//없어서 insert 해야하는 경우
				if(monStcData==null) {
					statisticService.insertMonStc(upt);
					
				}else {//있으면 update add
					//먼저 선언한 통계 데이터에 불러온 값을 더함
					upt.setRlxCnt(upt.getRlxCnt()+monStcData.getRlxCnt());			
					upt.setUsCnt(upt.getUsCnt()+monStcData.getUsCnt()); 	
					upt.setCareCnt(upt.getCareCnt()+monStcData.getCareCnt());	
					upt.setCwdCnt(upt.getCwdCnt()+monStcData.getCwdCnt());
					//새로 add한 data 업데이트
					statisticService.updateMonStc(upt);
				}
			}	
			
			//연도별통계 관련
			List<ChkDateVO> dfYearList = statisticService.chkYear();
			logger.debug("$$$$$ 통계 테이블에 미 기재된 날짜 수: "+dfYearList.size());
			for (int i = 0; i < dfYearList.size(); i++) {
				StatisticVO yearStcData = statisticService.findYearData(dfYearList.get(i));
				StatisticVO upt = statisticService.setTcmsYearStc(dfYearList.get(i));	
				
				if(yearStcData==null) {
					statisticService.insertYearStc(upt);
					
				}else {//있으면 update add
					upt.setRlxCnt(upt.getRlxCnt()+yearStcData.getRlxCnt());			
					upt.setUsCnt(upt.getUsCnt()+yearStcData.getUsCnt()); 	
					upt.setCareCnt(upt.getCareCnt()+yearStcData.getCareCnt());	
					upt.setCwdCnt(upt.getCwdCnt()+yearStcData.getCwdCnt());
					statisticService.updateYearStc(upt);
				}
			}
			
			//계절별통계 관련
			List<ChkDateVO> dfSeaList = statisticService.chkSea();
			logger.debug("$$$$$ 통계 테이블에 미 기재된 날짜 수: "+dfSeaList.size());
			for (int i = 0; i < dfSeaList.size(); i++) {
				StatisticVO seaStcData = statisticService.findSeaData(dfSeaList.get(i));
				StatisticVO upt = statisticService.setTcmsSeaStc(dfSeaList.get(i));	
				if(seaStcData==null) {
					statisticService.insertSeaStc(upt);
				}else {//있으면 update add
					upt.setRlxCnt(upt.getRlxCnt()+seaStcData.getRlxCnt());			
					upt.setUsCnt(upt.getUsCnt()+seaStcData.getUsCnt()); 	
					upt.setCareCnt(upt.getCareCnt()+seaStcData.getCareCnt());	
					upt.setCwdCnt(upt.getCwdCnt()+seaStcData.getCwdCnt());
					statisticService.updateSeaStc(upt);
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
