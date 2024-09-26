package kr.co.wizbrain.apRailroad.statistic.service;

import java.util.HashMap;
import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import kr.co.wizbrain.apRailroad.statistic.vo.StatisticVO;
import kr.co.wizbrain.apRailroad.statistic.vo.StkAreaVO;
import kr.co.wizbrain.apRailroad.terminal.vo.TerminalVO;
import kr.co.wizbrain.apRailroad.statistic.vo.ChkDateVO;
import kr.co.wizbrain.apRailroad.statistic.vo.LogDataVO;
import kr.co.wizbrain.apRailroad.statistic.vo.MainStVo;
import kr.co.wizbrain.apRailroad.statistic.vo.MainYTVo;
import kr.co.wizbrain.apRailroad.statistic.vo.ScatterVO;
import kr.co.wizbrain.apRailroad.user.vo.UserVO;

/**
 * code 서비스 클래스
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

public interface StatisticService {
	
	//전/금일 현시간 주의혼잡(메인화면)
	MainYTVo mainYesToday(String timeRange);
	//일일 통계
	List<StatisticVO> dayTermByRange(String dt, String trainNum);
	//월별(일간) 통계
	List<StatisticVO> mdTermByRange(String sDate);
	//월간 통계
	List<StatisticVO> monTermByRange(String sDate);
	//연도별 통계
	List<StatisticVO> yearTermByRange(String sdate, String edate);
	//계절별 통계
	List<StatisticVO> seaTermByRange(String sdate);
	//단일편성 테이블 목록
	List<TerminalVO> trainTable(String sDate);
	
	//tcms 로그데이터 관리
	List<LogDataVO> dataLogListTcms();
	//hse 로그데이터 관리
	List<LogDataVO> dataLogListHse();
	//tcms 로그데이터 삭제
	void dataLogDeleteTcms(List<String> ldArr);
	//hse 로그데이터 삭제
	void dataLogDeleteHse(List<String> ldArr);
	
	//월간(일별) - 로그와 통계 테이블의 없는날짜 비교
	public List<String> chkDt();
	//월간(일별) 통계에 로그 데이터 삽입
	void insertMdStc(String dt);
	
	//월,년도별 - 로그와 통계 테이블의 없는날짜 비교
	public List<ChkDateVO> chkMon();
	//로그와 통계의 비교된 년 월 가지고 통계 테이블에서 있는지 조회
	public StatisticVO findMonData(ChkDateVO cvo);
	//tcms데이터를 통계에 넣을수 있도록 정리
	public StatisticVO setTcmsMonStc(ChkDateVO cvo);
	//월별 통계에 로그 데이터 삽입
	void insertMonStc(StatisticVO svo);
	//새로 add한 data 업데이트
	void updateMonStc(StatisticVO svo);
	
	public List<ChkDateVO> chkYear();
	StatisticVO findYearData(ChkDateVO chkDateVO);
	StatisticVO setTcmsYearStc(ChkDateVO chkDateVO);
	void insertYearStc(StatisticVO upt);
	void updateYearStc(StatisticVO upt);
	
	public List<ChkDateVO> chkSea();
	StatisticVO findSeaData(ChkDateVO cvo);
	StatisticVO setTcmsSeaStc(ChkDateVO cvo);
	void insertSeaStc(StatisticVO svo);
	void updateSeaStc(StatisticVO svo);
	
	List<ScatterVO> scatterChart(String sDate, String trainNum, String carWgt);
	List<StkAreaVO> stkAreaChart(String sDate, String trainNumt);
	List<StatisticVO> stkBarTod(String nowTR);
	List<StatisticVO> oneTrAllRyang(String cnum);
	List<StkAreaVO> yesTodStkAr();
	String whenNowTime();
	int bunmo();
	
}
