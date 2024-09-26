package kr.co.wizbrain.apRailroad.setting.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.co.wizbrain.apRailroad.comm.KeyMapVo;
import kr.co.wizbrain.apRailroad.eventLog.vo.EventLogVO;
import kr.co.wizbrain.apRailroad.eventLog.vo.LogVO;
import kr.co.wizbrain.apRailroad.setting.mapper.SettingMapper;
import kr.co.wizbrain.apRailroad.setting.service.SettingService;
import kr.co.wizbrain.apRailroad.setting.vo.DayCrowdRangeVO;
import kr.co.wizbrain.apRailroad.setting.vo.MdCrowdRangeVO;
import kr.co.wizbrain.apRailroad.setting.vo.SeaCrowdRangeVO;
import kr.co.wizbrain.apRailroad.setting.vo.SettingVO;
import kr.co.wizbrain.apRailroad.setting.vo.MonCrowdRangeVO;
import kr.co.wizbrain.apRailroad.setting.vo.TimeRangeVO;
import kr.co.wizbrain.apRailroad.setting.vo.YearCrowdRangeVO;

/**
 * 설정 서비스 구현 클래스
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

@Service("settingService")
public class SettingServiceImpl implements SettingService{
	
	@Resource(name="settingMapper")
	private SettingMapper settingMapper;
	
	public List<String> fieldArr=null;
	public KeyMapVo kvo=null;
	public List<KeyMapVo> klist=null;
	
	public DayCrowdRangeVO dayVo;
	public MdCrowdRangeVO mdVo;
	public MonCrowdRangeVO monVo;
	public YearCrowdRangeVO yrVo;
	public SeaCrowdRangeVO seaVo;
	
	public List<String> upList=null;
	//public LogVO logVo=null;
	public EventLogVO elogVo=null;
	
	
	//Jave class의 변수명과 값을 출력하는 방법 
	public void getVoField(Object vo) {
		fieldArr = new ArrayList<String>();
		klist= new ArrayList<>();
		try{
	        for(Field field :vo.getClass().getDeclaredFields()){
	            field.setAccessible(true);
	            String name = field.getName();//변수명 추출
	            if(field.get(vo)!=null) {//update 변수값이 있을때
	            	kvo = new KeyMapVo();
	            	String value = (String) field.get(vo);//이거 변수값 없을때 받으면 에러남
	            	kvo.setSetNameKey(name);
	            	kvo.setSetValValue(value);
	            	klist.add(kvo);
	            }else {//select 변수값이 없을때
	            	fieldArr.add(name);
	            }
	        }    
	    }catch(Exception e){
	        System.out.println("VO 변수, 값 추출 에러");
	    }
	}
	
	//시간 기간 범위 목록 화면
	@Override
	public TimeRangeVO timeRangeList() {
		TimeRangeVO vo = new TimeRangeVO();
		getVoField(vo);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("chkList",fieldArr);
		
		return settingMapper.timeRangeList(map);
	}

	@Override
	public DayCrowdRangeVO dayCrowdRangeList() {
		DayCrowdRangeVO vo = new DayCrowdRangeVO();
		getVoField(vo);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("chkList",fieldArr);
		
		return settingMapper.dayCrowdRangeList(map);
	}

	@Override
	public MonCrowdRangeVO monCrowdRangeList() {
		MonCrowdRangeVO vo = new MonCrowdRangeVO();
		getVoField(vo);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("chkList",fieldArr);
		
		return settingMapper.monCrowdRangeList(map);
	}

	@Override
	public YearCrowdRangeVO yearCrowdRangeList() {
		YearCrowdRangeVO vo = new YearCrowdRangeVO();
		getVoField(vo);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("chkList",fieldArr);
		
		return settingMapper.yearCrowdRangeList(map);
	}

	@Override
	public SeaCrowdRangeVO seaCrowdRangeList() {
		SeaCrowdRangeVO vo = new SeaCrowdRangeVO();
		getVoField(vo);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("chkList",fieldArr);
		
		return settingMapper.seaCrowdRangeList(map);
	}
	
	@Override
	public MdCrowdRangeVO mdCrowdRangeList() {
		MdCrowdRangeVO vo = new MdCrowdRangeVO();
		getVoField(vo);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("chkList",fieldArr);
		
		return settingMapper.mdCrowdRangeList(map);
	}
	
	//시간 기간 범위 변경
	@Override
	public void timeRangeUpdate(TimeRangeVO svo,TimeRangeVO preVo,LogVO lvo) {
		getVoField(svo,preVo,lvo);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("chkList",klist);
		settingMapper.settingUpdate(map);
		//여기에 이벤트로그 인서트 로직 추가
		logHandling(lvo);
	}
	
	//일일 일반 범위 수정
	@Override
	public void dayCrowdRangeUpdate(DayCrowdRangeVO svo, DayCrowdRangeVO preVo,LogVO lvo) {
		getVoField(svo,preVo,lvo);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("chkList",klist);
		settingMapper.settingUpdate(map);
		//여기에 이벤트로그 인서트 로직 추가
		logHandling(lvo);
	}

	@Override
	public void monCrowdRangeUpdate(MonCrowdRangeVO svo, MonCrowdRangeVO preVo,LogVO lvo) {
		getVoField(svo,preVo,lvo);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("chkList",klist);
		settingMapper.settingUpdate(map);
		//여기에 이벤트로그 인서트 로직 추가
		logHandling(lvo);
	}

	@Override
	public void yearCrowdRangeUpdate(YearCrowdRangeVO svo, YearCrowdRangeVO preVo,LogVO lvo) {
		getVoField(svo,preVo,lvo);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("chkList",klist);
		settingMapper.settingUpdate(map);
		//여기에 이벤트로그 인서트 로직 추가
		logHandling(lvo);
	}

	@Override
	public void seaCrowdRangeUpdate(SeaCrowdRangeVO svo,SeaCrowdRangeVO preVo,LogVO lvo) {
		getVoField(svo,preVo,lvo);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("chkList",klist);
		settingMapper.settingUpdate(map);
		//여기에 이벤트로그 인서트 로직 추가
		logHandling(lvo);
	}
	
	@Override
	public void mdCrowdRangeUpdate(MdCrowdRangeVO svo,MdCrowdRangeVO preVo,LogVO lvo) {
		getVoField(svo,preVo,lvo);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("chkList",klist);
		settingMapper.settingUpdate(map);
		//여기에 이벤트로그 인서트 로직 추가
		logHandling(lvo);
	}
	

	//변경 속성 추출 (오버로딩)
	public void getVoField(Object vo, Object preVo,LogVO lvo) {
		fieldArr = new ArrayList<String>();
		klist= new ArrayList<>();
		upList = new ArrayList<>();
		try{
	        for(Field field :vo.getClass().getDeclaredFields()){
	            field.setAccessible(true);
	            String name = field.getName();//변수명 추출
	            if(field.get(vo)!=null) {//update 변수값이 있을때
	            	kvo = new KeyMapVo();
	            	String value = (String) field.get(vo);//이거 변수값 없을때 받으면 에러남
	            	kvo.setSetNameKey(name);
	            	kvo.setSetValValue(value);
	            	/*여기에 이전  vo와 비교하는 메소드 추가*/
	            	setPreVo(preVo,name,value,lvo);
	            	klist.add(kvo);
	            }else {//select 변수값이 없을때
	            	fieldArr.add(name);
	            }
	        }    
	    }catch(Exception e){
	        System.out.println("VO 변수, 값 추출 에러");
	    }
	}
	
	//혼잡도 범위 일괄 변경
	@Override
	public void settingUpdateAll(Object obo, Object preVo,LogVO lvo) {
		fieldArr = new ArrayList<String>();
		String res="";
		upList = new ArrayList<>();
		klist= new ArrayList<>();
		try{
	        for(Field field :obo.getClass().getDeclaredFields()){
	            field.setAccessible(true);
	            String name = field.getName();//변수명 추출
	            if(field.get(obo)!=null&&!(field.get(obo).equals(""))) {//update 변수값이 있을때
	            	kvo = new KeyMapVo();
	            	String value = (String) field.get(obo);//이거 변수값 없을때 받으면 에러남
	            	kvo.setSetNameKey(name);
	            	kvo.setSetValValue(value);
	            	/*여기에 이전  vo와 비교하는 메소드 추가*/
	            	setPreVo(preVo,name,value,lvo);
	            	klist.add(kvo);
	            }
	        }
	    }catch(Exception e){
	        System.out.println("VO 변수, 값 추출 에러");
	    }
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("chkList",klist);
		settingMapper.settingUpdateAll(map);
		//여기에 이벤트로그 인서트 로직 추가
		logHandling(lvo);
	}
	
	//현재 수정값과 이전 값을 비교하여 변경 값 추출
	public void setPreVo(Object pvo,String nowName,String nowValue,LogVO lvo) {
		try{
	        for(Field field :pvo.getClass().getDeclaredFields()){
	            field.setAccessible(true);
	            String preName = field.getName();//변수명 추출
	            //변수명이 일치할 때
	            if(nowName.equals(preName)) {
	            	if(field.get(pvo)!=null) {//update 변수값이 있을때
		            	String preValue = (String) field.get(pvo);
		            	if(!(nowValue.equals(preValue))){//이전 값과 서로 다를 때(값이 변경됬을 때)
		            		//내용부분에  추가
		            		upList.add(nowName);
		            	}
		            }
	            }
	        }
	    }catch(Exception e){
	        System.out.println("VO 변수, 값 추출 에러");
	    }
	}
	
	//로그 핸들링
	public void logHandling(LogVO lvo){
		if(upList.size()!=0) {
			//변경된 속성의 속성코멘트와 속성값을 순서대로 추출
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("chkList",upList);
			List<SettingVO> columList = settingMapper.chUpList(map);
			String upCom=null;
			if(columList.size()!=0) {
				upCom="변경한 사용자 ID: "+lvo.getLoginId()+"<br>";
				for (int i = 0; i < columList.size(); i++) {
					upCom+=columList.get(i).getComment()+" : "+columList.get(i).getSetVal()+"<br>";
				}
			}
			lvo.getEvo().setComment(upCom);
			if(lvo!=null) {
				settingMapper.insertEvtLog(lvo.getEvo());
			}
		}
	}
}
