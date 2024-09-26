package kr.co.wizbrain.apRailroad.terminal.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import kr.co.wizbrain.apRailroad.comm.KeyMapVo;
import kr.co.wizbrain.apRailroad.eventLog.vo.LogVO;
import kr.co.wizbrain.apRailroad.setting.vo.SettingVO;
import kr.co.wizbrain.apRailroad.terminal.mapper.TerminalMapper;
import kr.co.wizbrain.apRailroad.terminal.service.TerminalService;
import kr.co.wizbrain.apRailroad.terminal.vo.DirectRegularVO;
import kr.co.wizbrain.apRailroad.terminal.vo.TerminalVO;

@Service("terminalService")
public class TerminalServiceImpl implements TerminalService{
	
	public List<String> fieldArr=null;
	public List<String> nameArr=null;
	public KeyMapVo kvo=null;
	public List<KeyMapVo> klist=null;
	public List<KeyMapVo> upList=null;
	
	@Resource(name="terminalMapper")
	private TerminalMapper terminalMapper;

	@Override
	public List<TerminalVO> selectTerminalList(TerminalVO tvo) {
		return terminalMapper.selectTerminal(tvo);
	}

	@Override
	public void terminalInsert(TerminalVO terminalVO) {
		terminalMapper.terminalInsert(terminalVO);
	}

	@Override
	public TerminalVO selectTerminal(TerminalVO terminalVO) {
		TerminalVO tvo = (TerminalVO) terminalMapper.selectTerminal(terminalVO).get(0);
		return tvo;
	}

	@Override
	public void terminalUpdate(TerminalVO terminalVO) {
		terminalMapper.terminalUpdate(terminalVO);
		//순번 초기화
		//terminalMapper.idxAutoIncrement();
	}

	@Override
	public void terminalDelete(List<String> terminalArr) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("chkList",terminalArr);
		
		terminalMapper.terminalDelete(map);
		//순번 초기화
		terminalMapper.idxAutoIncrement();
	}
	//직통/일반/목록
	@Override
	public List<DirectRegularVO> selectDrtRgr(DirectRegularVO tvo) {
		return terminalMapper.selectDrtRgr(tvo);
	}
	//직통/일반 수정
	@Override
	public void drtRgrUpdate(DirectRegularVO preVo,DirectRegularVO svo,LogVO lvo) {
		getVoField(svo,preVo,lvo);
		terminalMapper.drtRgrUpdate(svo);
		//여기에 이벤트로그 인서트 로직 추가
		logHandling(lvo);
	}
	
	//변경 속성 추출 (오버로딩)
	public void getVoField(Object vo, Object preVo,LogVO lvo) {
		fieldArr = new ArrayList<String>();
		klist= new ArrayList<>();
		upList = new ArrayList<>();
		nameArr=new ArrayList<>();
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
		            		KeyMapVo nowkvo = new KeyMapVo();
		            		nameArr.add(nowName);
		            		nowkvo.setSetNameKey(nowName);
		            		nowkvo.setSetValValue(nowValue);
		            		upList.add(nowkvo);
		            	}
		            }
	            }
	        }
	    }catch(Exception e){
	        System.out.println(e);
	    }
	}
	
	//로그 핸들링
	public void logHandling(LogVO lvo){
		if(upList.size()!=0) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("chkList",nameArr);
			//변경된 속성의 속성코멘트와 속성값을 순서대로 추출
			List<KeyMapVo> columList = terminalMapper.findComm(map);
			String upCom=null;
			if(columList.size()!=0) {
				upCom="변경한 사용자 ID: "+lvo.getLoginId()+"<br>";
				for (int i = 0; i < upList.size(); i++) {
					for (int j = 0; j < columList.size(); j++) {
						if (upList.get(i).getSetNameKey()
								.equals(columList.get(j).getSetNameKey())) {
							upCom+=columList.get(j).getSetValValue()+" : "+upList.get(i).getSetValValue()+"<br>";
						}
					}
				}
			}
			lvo.getEvo().setComment(upCom);
			if(lvo!=null) {
				terminalMapper.insertDREvtLog(lvo.getEvo());
			}
		}
	}
}
