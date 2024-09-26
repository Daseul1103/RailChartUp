package kr.co.wizbrain.apRailroad.setting.service;

import java.util.List;

import kr.co.wizbrain.apRailroad.eventLog.vo.LogVO;
import kr.co.wizbrain.apRailroad.setting.vo.DayCrowdRangeVO;
import kr.co.wizbrain.apRailroad.setting.vo.MdCrowdRangeVO;
import kr.co.wizbrain.apRailroad.setting.vo.MonCrowdRangeVO;
import kr.co.wizbrain.apRailroad.setting.vo.SeaCrowdRangeVO;
import kr.co.wizbrain.apRailroad.setting.vo.TimeRangeVO;
import kr.co.wizbrain.apRailroad.setting.vo.YearCrowdRangeVO;

/**
 * 설정 서비스 클래스
 * @author 미래전략사업팀 정다빈
 * @since 2020.08.23
 * @version 1.0
 * @see
 *
 * << 개정이력(Modification Information) >>
 *
 *   수정일            수정자              수정내용
 *  -------    -------- ---------------------------
 *  2020.08.23  정다빈           최초 생성
 */

public interface SettingService {
	
	//시간 기간 범위 목록 화면
	public TimeRangeVO timeRangeList();
	//일일 혼잡도 범위 목록
	public DayCrowdRangeVO dayCrowdRangeList();
	//월별 혼잡도 범위 목록
	public MonCrowdRangeVO monCrowdRangeList();
	//연도별 혼잡도 범위 목록
	public YearCrowdRangeVO yearCrowdRangeList();
	//계절별 혼잡도 범위 목록
	public SeaCrowdRangeVO seaCrowdRangeList();
	//월별(일간) 혼잡도 범위 목록
	public MdCrowdRangeVO mdCrowdRangeList();
	//시간 기간 범위 변경
	public void timeRangeUpdate(TimeRangeVO svo,TimeRangeVO preVo,LogVO lvo);
	//일일 혼잡도 범위 수정 화면
	public void dayCrowdRangeUpdate(DayCrowdRangeVO svo,DayCrowdRangeVO preVo,LogVO lvo);
	//월별 혼잡도 범위 수정 화면
	public void monCrowdRangeUpdate(MonCrowdRangeVO svo,MonCrowdRangeVO preVo,LogVO lvo);
	//연도별 혼잡도 범위 수정 화면
	public void yearCrowdRangeUpdate(YearCrowdRangeVO svo,YearCrowdRangeVO preVo,LogVO lvo);
	//계절별 혼잡도 범위 수정 화면
	public void seaCrowdRangeUpdate(SeaCrowdRangeVO svo,SeaCrowdRangeVO preVo,LogVO lvo);
	//계절별 혼잡도 범위 수정 화면
	public void mdCrowdRangeUpdate(MdCrowdRangeVO svo,MdCrowdRangeVO preVo,LogVO lvo);
	//시간 기간 범위 일괄변경
	public void settingUpdateAll(Object obo,Object preVo,LogVO lvo);
}
