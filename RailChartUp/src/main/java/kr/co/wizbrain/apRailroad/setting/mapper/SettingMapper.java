package kr.co.wizbrain.apRailroad.setting.mapper;

import java.util.HashMap;
import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.wizbrain.apRailroad.eventLog.vo.EventLogVO;
import kr.co.wizbrain.apRailroad.setting.vo.DayCrowdRangeVO;
import kr.co.wizbrain.apRailroad.setting.vo.MdCrowdRangeVO;
import kr.co.wizbrain.apRailroad.setting.vo.MonCrowdRangeVO;
import kr.co.wizbrain.apRailroad.setting.vo.SeaCrowdRangeVO;
import kr.co.wizbrain.apRailroad.setting.vo.SettingVO;
import kr.co.wizbrain.apRailroad.setting.vo.TimeRangeVO;
import kr.co.wizbrain.apRailroad.setting.vo.YearCrowdRangeVO;
import kr.co.wizbrain.apRailroad.user.vo.UserVO;

/**
 * 설정 매퍼 클래스
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

@Mapper("settingMapper")
public interface SettingMapper {

	//시간 기간 범위 목록 화면
	public TimeRangeVO timeRangeList(HashMap<String, Object> map);
	//일일 혼잡도 범위 목록
	public DayCrowdRangeVO dayCrowdRangeList(HashMap<String, Object> map);
	//월별 혼잡도 범위 목록
	public MonCrowdRangeVO monCrowdRangeList(HashMap<String, Object> map);
	//연도별 혼잡도 범위 목록
	public YearCrowdRangeVO yearCrowdRangeList(HashMap<String, Object> map);
	//계절별 혼잡도 범위 목록
	public SeaCrowdRangeVO seaCrowdRangeList(HashMap<String, Object> map);
	//월별(일간) 혼잡도 범위 목록
	public MdCrowdRangeVO mdCrowdRangeList(HashMap<String, Object> map);
	//시간 기간 범위 수정 화면
	public void settingUpdate(HashMap<String, Object> map);
	//혼잡도 범위 변경 일괄 적용
	public void settingUpdateAll(HashMap<String, Object> map);
	//설명 속성값 추출
	public List<SettingVO> chUpList(HashMap<String, Object> map);
	//이벤트 로그 생성
	public void insertEvtLog(EventLogVO evo);
}
