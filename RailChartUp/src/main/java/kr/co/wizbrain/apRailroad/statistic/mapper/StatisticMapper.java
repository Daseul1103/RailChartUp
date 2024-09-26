package kr.co.wizbrain.apRailroad.statistic.mapper;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import kr.co.wizbrain.apRailroad.user.vo.UserVO;
import kr.co.wizbrain.apRailroad.statistic.vo.StatisticVO;
import kr.co.wizbrain.apRailroad.statistic.vo.StkAreaVO;
import kr.co.wizbrain.apRailroad.terminal.vo.TerminalVO;
import kr.co.wizbrain.apRailroad.statistic.vo.ChkDateVO;
import kr.co.wizbrain.apRailroad.statistic.vo.LogDataVO;
import kr.co.wizbrain.apRailroad.statistic.vo.MainStVo;
import kr.co.wizbrain.apRailroad.statistic.vo.ScatterVO;

/**
 * code 매퍼 클래스
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

@Mapper("statisticMapper")
public interface StatisticMapper{

	//전/금일 현시간 주의혼잡(메인화면)
	List<MainStVo> mainYesToday(String timeRange);
	//일일 통계
	List<StatisticVO> dayTermByRange(@Param("st") String st, @Param("ed") String ed, @Param("sdate") String sdate, @Param("trainNum") String trainNum);
	//월간(일별) 통계
	List<StatisticVO> mdTermByRange(String sdate);
	//월별 통계
	List<StatisticVO> monTermByRange(String sdate);
	//연도별 통계
	List<StatisticVO> yearTermByRange(@Param("sdate") String sdate, @Param("edate") String edate);
	//계절별 통계
	List<StatisticVO> seaTermByRange(String sdate);
	//단일편성 테이블 목록
	List<TerminalVO> trainTable(String sDate);
	
	//tcms 로그데이터 관리
	List<LogDataVO> dataLogListTcms();
	//hse 로그데이터 관리
	List<LogDataVO> dataLogListHse();
	//tcms 로그데이터 삭제
	void dataLogDeleteTcms(HashMap<String, Object> map);
	//hse 로그데이터 삭제
	void dataLogDeleteHse(HashMap<String, Object> map);
	
	//tcms로그와 통계 테이블 시간 비교
	List<String> chkDt();
	//월간(일별) 통계에 로그 데이터 삽입
	void insertMdStc(String dt);
	
	//월별 - 로그와 통계 테이블의 없는날짜 비교
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
	StatisticVO findYearData(ChkDateVO cvo);
	StatisticVO setTcmsYearStc(ChkDateVO cvo);
	void insertYearStc(StatisticVO svo);
	void updateYearStc(StatisticVO svo);
	
	public List<ChkDateVO> chkSea();
	StatisticVO findSeaData(ChkDateVO cvo);
	StatisticVO setTcmsSeaStc(ChkDateVO cvo);
	void insertSeaStc(StatisticVO svo);
	void updateSeaStc(StatisticVO svo);
	
	List<ScatterVO> scatterChart(@Param("sDate") String sDate, @Param("trainNum") String trainNum, @Param("carWgt") String carWgt);
	List<StkAreaVO> stkAreaChart(@Param("sDate") String sDate, @Param("trainNum") String trainNum);
	//현재시간이 어떤 시간대인지 판별
	public String whenNowTime();
	//메인 1. 금일 5분전 현재 열차별 여보주혼
	public List<StatisticVO> stkBarTod(String nowTR);
	//메인 2. 선택한 열차의 1~6량의 각 량별 실제 혼잡도 수치
	public List<StatisticVO> oneTrAllRyang(String cnum);
	//메인 3. 전/금일 주혼 누적그래프 비교
	public List<StkAreaVO> yesTodStkAr();
	int bunmo();
}
