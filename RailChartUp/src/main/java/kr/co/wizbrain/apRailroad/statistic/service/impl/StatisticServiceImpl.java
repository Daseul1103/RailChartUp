package kr.co.wizbrain.apRailroad.statistic.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.media.jfxmedia.logging.Logger;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import kr.co.wizbrain.apRailroad.comm.KeyMapVo;
import kr.co.wizbrain.apRailroad.setting.vo.TimeRangeVO;
import kr.co.wizbrain.apRailroad.statistic.mapper.StatisticMapper;
import kr.co.wizbrain.apRailroad.statistic.service.StatisticService;
import kr.co.wizbrain.apRailroad.statistic.vo.StatisticVO;
import kr.co.wizbrain.apRailroad.statistic.vo.StkAreaVO;
import kr.co.wizbrain.apRailroad.terminal.vo.TerminalVO;
import kr.co.wizbrain.apRailroad.statistic.vo.ChkDateVO;
import kr.co.wizbrain.apRailroad.statistic.vo.LogDataVO;
import kr.co.wizbrain.apRailroad.statistic.vo.MainStVo;
import kr.co.wizbrain.apRailroad.statistic.vo.MainYTVo;
import kr.co.wizbrain.apRailroad.statistic.vo.ScatterVO;
import kr.co.wizbrain.apRailroad.user.mapper.UserMapper;
import kr.co.wizbrain.apRailroad.user.service.UserService;
import kr.co.wizbrain.apRailroad.user.vo.UserVO;

/**
 * code 서비스 구현 클래스
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

@Service("statisticService")
public class StatisticServiceImpl implements StatisticService{
	
	
	@Resource(name="statisticMapper")
	private StatisticMapper statisticMapper;

	public List<String> fieldArr=null;
	public KeyMapVo kvo=null;
	public List<KeyMapVo> klist=null;
	
	
	//전/금일 현시간 주의혼잡(메인화면)
	@Override
	public MainYTVo mainYesToday(String timeRange) {
		ArrayList<String> res = new ArrayList<>();
		List<MainStVo> resQry = new ArrayList<>();
		resQry=statisticMapper.mainYesToday(timeRange);
		
		MainYTVo mytVo = new MainYTVo();
		
		//편성번호
		ArrayList<String> ttNum = new ArrayList<>();
		ttNum.add("x");
		//전일 주의
		ArrayList<String> yCare = new ArrayList<>();
		yCare.add("전일 주의");
		//전일 혼잡
		ArrayList<String> yCwd = new ArrayList<>();
		yCwd.add("전일 혼잡");
		//금일 주의
		ArrayList<String> tCare = new ArrayList<>();
		tCare.add("금일 주의");
		//금일 혼잡
		ArrayList<String> tCwd = new ArrayList<>();
		tCwd.add("금일 혼잡");
		//편
		HashMap<String, Integer> dupMap = new HashMap<String, Integer>();
		try {
			for (MainStVo mvo : resQry) {
	        	dupMap = new HashMap<String, Integer>();
	        	for(Field field :mvo.getClass().getDeclaredFields()){
	                field.setAccessible(true);
	                String name = field.getName();//변수명 추출
	                String dataVal = (String) field.get(mvo);//변수에 할당된 값 추출
	                
	                if(name.equals("carNum")) {
	                	ttNum.add(dataVal);
	                }
	                
	                if (dupMap.containsKey(dataVal)) { // HashMap 내부에 이미 key 값이 존재하는지 확인
	    		        dupMap.put(dataVal, dupMap.get(dataVal) + 1);  // key가 이미 있다면 value에 +1
	    		    } else { // key값이 존재하지 않으면
	    		        dupMap.put(dataVal , 1); // key 값을 생성후 value를 1로 초기화
	    		    }
	            }//다음 필드 전환(편성 전환)
	        	System.out.println(dupMap);
	        	Iterator<String> keys = dupMap.keySet().iterator();
	        	
	        	//임시 비교 값
	    		ArrayList<String> spyCare = new ArrayList<>();
	    		ArrayList<String> spyCwd = new ArrayList<>();
	    		ArrayList<String> sptCare = new ArrayList<>();
	    		ArrayList<String> sptCwd = new ArrayList<>();
	    		
	    		while( keys.hasNext() ){
	    			String key= keys.next();
	    			if(key.equals("전일 주의")) {
	    				String valS = Integer.toString(dupMap.get(key));
	    				yCare.add(valS);
	    				spyCare.add(valS);
	    			}else if(key.equals("전일 혼잡")) {
	    				String valS = Integer.toString(dupMap.get(key));
	    				yCwd.add(valS);
	    				spyCwd.add(valS);
	    			}else if(key.equals("금일 주의")) {
	    				String valS = Integer.toString(dupMap.get(key));
	    				tCare.add(valS);
	    				sptCare.add(valS);
	    			}else if(key.equals("금일 혼잡")) {
	    				String valS = Integer.toString(dupMap.get(key));
	    				tCwd.add(valS);
	    				sptCwd.add(valS);
	    			}		
	    		}
	    		
	    		//없을 경우 0 주입
	    		if(spyCare.size()==0) {
    				yCare.add("0");
    			}
	    		if(spyCwd.size()==0) {
    				yCwd.add("0");
    			}
	    		if(sptCare.size()==0) {
    				tCare.add("0");
    			}
	    		if(sptCwd.size()==0) {
    				tCwd.add("0");
    			}
			}//다음 vo 전환
		} catch (Exception e) {
			System.out.println("포문 에러 : "+e);
		}
		
		mytVo.settNum(ttNum);
		mytVo.setYesCare(yCare);
		mytVo.setYesCwd(yCwd);
		mytVo.setToCare(tCare);
		mytVo.setToCwd(tCwd);
		
		System.out.println(mytVo);
		
		return mytVo;
	}

	//일일 통계
	@Override
	public List<StatisticVO> dayTermByRange(String sdate,String trainNum){
		List<StatisticVO> dlist = new ArrayList<>();
		TimeRangeVO vo = new TimeRangeVO();
		try{
	        for(Field field :vo.getClass().getDeclaredFields()){
	            field.setAccessible(true);
	            String name = field.getName();//변수명 추출
	            String stat = name.substring(name.length()-2, name.length());
	            String tagName = name.substring(0, name.length()-2);
	            if(!(tagName.equals("spring"))&&!(tagName.equals("summer"))
	             &&!(tagName.equals("autumn"))&&!(tagName.equals("winter"))
	            		&&stat.equals("St")) {
	            	String st = tagName+"St";
	            	String ed = tagName+"Ed";
	        		StatisticVO dvo = statisticMapper.dayTermByRange(st,ed,sdate,trainNum).get(0);
	            	dlist.add(dvo);
	            }
	        }
	        System.out.println("일일 : "+dlist);
	    }catch(Exception e){
	        System.out.println("VO 변수, 값 추출 에러"+e);
	    }
		return dlist;
	}
	
	//월별(일간) 통계(일괄 추출)
	@Override
	public List<StatisticVO> mdTermByRange(String sdate){
		return statisticMapper.mdTermByRange(sdate);
	}
		  
	//월간 통계(일괄 추출)
	@Override
	public List<StatisticVO> monTermByRange(String sdate){
		return statisticMapper.monTermByRange(sdate);
	}
	
	//연간 통계
	@Override
	public List<StatisticVO> yearTermByRange(String sdate, String edate){
		return statisticMapper.yearTermByRange(sdate,edate);
	}

	//계절 통계
	@Override
	public List<StatisticVO> seaTermByRange(String sdate){
		return statisticMapper.seaTermByRange(sdate);
	}

	//단일편성 테이블 조회
	@Override
	public List<TerminalVO> trainTable(String sDate) {
		return statisticMapper.trainTable(sDate);
	}

	//tcms 데이터 로그 목록
	@Override
	public List<LogDataVO> dataLogListTcms() {
		return statisticMapper.dataLogListTcms();
	}
	
	//hse 데이터 로그 목록
	@Override
	public List<LogDataVO> dataLogListHse() {
		return statisticMapper.dataLogListHse();
	}
	
	//tcms 데이터 로그 삭제
	@Override
	public void dataLogDeleteTcms(List<String> ldArr) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("chkList",ldArr);
		statisticMapper.dataLogDeleteTcms(map);
	}
	//Hse 데이터 로그 삭제
	@Override
	public void dataLogDeleteHse(List<String> ldArr) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("chkList",ldArr);
		statisticMapper.dataLogDeleteHse(map);
	}
	
	//스케줄러 - 로그와 통계 테이블의 없는날짜 비교
	@Override
	public List<String> chkDt(){
		return statisticMapper.chkDt();
	}
	
	//월간(일별) 통계에 로그 데이터 삽입
	@Override
	public void insertMdStc(String dt) {
		statisticMapper.insertMdStc(dt);
	}
	
	//월별 - 로그와 통계 테이블의 없는날짜 비교
	@Override
	public List<ChkDateVO> chkMon(){
		return statisticMapper.chkMon();
	}
	
	//로그와 통계의 비교된 년 월 가지고 통계 테이블에서 있는지 조회
	public StatisticVO findMonData(ChkDateVO cvo) {
		return statisticMapper.findMonData(cvo);
	}
	//tcms데이터를 통계에 넣을수 있도록 정리
	public StatisticVO setTcmsMonStc(ChkDateVO cvo) {
		return statisticMapper.setTcmsMonStc(cvo);
	}
	//월별 통계에 로그 데이터 삽입
	@Override
	public void insertMonStc(StatisticVO svo) {
		statisticMapper.insertMonStc(svo);
	}
	//새로 add한 data 업데이트
	@Override
	public void updateMonStc(StatisticVO svo) {
		statisticMapper.updateMonStc(svo);
	}
	
	
	
	@Override
	public List<ChkDateVO> chkYear(){
		return statisticMapper.chkYear();
	}
	//로그와 통계의 비교된 년 월 가지고 통계 테이블에서 있는지 조회
	public StatisticVO findYearData(ChkDateVO cvo) {
		return statisticMapper.findYearData(cvo);
	}
	//tcms데이터를 통계에 넣을수 있도록 정리
	public StatisticVO setTcmsYearStc(ChkDateVO cvo) {
		return statisticMapper.setTcmsYearStc(cvo);
	}
	//월별 통계에 로그 데이터 삽입
	@Override
	public void insertYearStc(StatisticVO svo) {
		statisticMapper.insertYearStc(svo);
	}
	//새로 add한 data 업데이트
	@Override
	public void updateYearStc(StatisticVO svo) {
		statisticMapper.updateYearStc(svo);
	}

	
	@Override
	public List<ChkDateVO> chkSea(){
		return statisticMapper.chkSea();
	}
	//로그와 통계의 비교된 년 월 가지고 통계 테이블에서 있는지 조회
	public StatisticVO findSeaData(ChkDateVO cvo) {
		return statisticMapper.findSeaData(cvo);
	}
	//tcms데이터를 통계에 넣을수 있도록 정리
	public StatisticVO setTcmsSeaStc(ChkDateVO cvo) {
		return statisticMapper.setTcmsSeaStc(cvo);
	}
	//월별 통계에 로그 데이터 삽입
	@Override
	public void insertSeaStc(StatisticVO svo) {
		statisticMapper.insertSeaStc(svo);
	}
	//새로 add한 data 업데이트
	@Override
	public void updateSeaStc(StatisticVO svo) {
		statisticMapper.updateSeaStc(svo);
	}

	@Override
	public List<ScatterVO> scatterChart(String sDate, String trainNum, String carWgt) {
		return statisticMapper.scatterChart(sDate,trainNum,carWgt);
	}

	@Override
	public List<StkAreaVO> stkAreaChart(String sDate, String trainNum) {
		return statisticMapper.stkAreaChart(sDate,trainNum);
	}

	@Override
	public List<StatisticVO> stkBarTod(String nowTR) {
		return statisticMapper.stkBarTod(nowTR);
	}

	@Override
	public List<StatisticVO> oneTrAllRyang(String cnum) {
		return statisticMapper.oneTrAllRyang(cnum);
	}

	@Override
	public List<StkAreaVO> yesTodStkAr() {
		return statisticMapper.yesTodStkAr();
	}

	@Override
	public String whenNowTime() {
		return statisticMapper.whenNowTime();
	}

	@Override
	public int bunmo() {
		return statisticMapper.bunmo();
	}
}
