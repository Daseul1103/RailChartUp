package kr.co.wizbrain.apRailroad.main.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import kr.co.wizbrain.apRailroad.comm.SessionListener;
import kr.co.wizbrain.apRailroad.main.service.MainService;
import kr.co.wizbrain.apRailroad.user.service.UserService;
import kr.co.wizbrain.apRailroad.user.vo.UserVO;
import net.sf.json.JSON;
import net.sf.json.JSONArray;

import org.json.simple.JSONObject; 
import org.json.simple.parser.JSONParser; 
import org.json.simple.parser.ParseException;



import javax.annotation.Resource;

import org.springframework.ui.Model;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.omg.SendingContext.RunTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 메인 컨트롤러 클래스
 * @author 미래전략사업팀 정다빈
 * @since 2020.07.30
 * @version 1.0
 * @see
 *
 * << 개정이력(Modification Information) >>
 *
 *   수정일            수정자              수정내용
 *  -------    -------- ---------------------------
 *  2020.07.30  정다빈           최초 생성
 */

@Controller
public class MainController {
	
	public static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	//private static String OS = System.getProperty("os.name").toLowerCase();
	
	@Resource(name="mainService")
	private MainService mainService;
	
	@Resource(name="userService")
	private UserService userService;
	
	public String url="";
	
	@RequestMapping(value = "/main/*.do")
	public String mainUrlMapping(HttpServletRequest request,Model model) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.main 최초 컨트롤러 진입");
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		logger.debug("▶▶▶▶▶▶▶.보내려는 url : "+url);
		SessionListener.getInstance().printloginUsers();
		return url;
	}
	
	@RequestMapping(value = "/sidebar/*.do")
	public String sideUrlMapping(HttpServletRequest request,Model model) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.sidebar 최초 컨트롤러 진입");
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		logger.debug("▶▶▶▶▶▶▶.보내려는 url : "+url);
		return url;
	}
	
//	//c#연동 데이터 송신
	/*@PostMapping(value="/main/jsonToCs.ajax")
	@ResponseBody
	public ModelAndView jsonToCs(@RequestBody String jsonVal){
		logger.debug("▶▶▶▶▶▶▶.app로부터 받아온 값 : "+jsonVal);//json
		String resVal=null;// json value
		//logger.debug("▶▶▶▶▶▶▶. json 변형하는 스트링 변수 resVal 초기 값 : "+resVal);
		LoginVO lvo = new LoginVO();
		ModelAndView mav = new ModelAndView("jsonView");
		//logger.debug(mav.toString());
		//db로 전송하는 파라미터
		List<String> valList = new ArrayList<>();
		//logger.debug("▶▶▶▶▶▶▶. db에 ip배열을 전송하는 valList 초기 값 : "+valList);
		
		//c#으로부터 같을 받았는지 확인
		if(jsonVal==null||jsonVal.equals("")) {
			mav.addObject("msg","app로부터 받아온 ip값이 존재하지 않습니다.");
			logger.debug("app로부터 받아온 ip값이 존재하지 않습니다.");
		}else {//값 받아옴
			
			//JSONParse에 json데이터를 넣어 파싱한 다음 JSONObject로 변환한다. 
			JSONParser jsonParse = new JSONParser();
			
			try {
				//gis simulator(json 배열) 과는 다르게 단일 key value만 받아온 경우
				JSONObject jsonObj = (JSONObject) jsonParse.parse(jsonVal);
				resVal = jsonObj.get("gr_ip_id").toString();
			} catch (ParseException e) {
				e.printStackTrace();
			} 
			
			//다중 ip의 경우
			if(resVal.indexOf("|")!=-1) {
				//split 구분자가 '|' 일 경우 -> '\\|'로 사용해야함
				String[] arr = resVal.split("\\|");
				//logger.debug("▶▶▶▶▶▶▶. 구분자가 분할된 배열 arr값 : "+arr);
				for (String str : arr) {
					valList.add(str);
				}
				//logger.debug("▶▶▶▶▶▶▶. db에 ip배열을 전송하는 valList 전체 값 : "+valList);
				
			}else {//ip 1개의 경우
				//C#으로부터 받아온 IP로 현재 로그인 사용자 조회
				valList.add(resVal);
			}
			lvo=userService.jsonToCs(valList);
		}
		if (lvo.getUserId()==null||lvo.getUserId().equals("")) {
			mav.addObject("msg","해당 ip로 조회된 로그인 사용자가 존재하지 않습니다.");
			//logger.debug("▶▶▶▶▶▶▶. 해당 ip로 조회된 로그인 사용자가 존재하지 않습니다.");
		} else {
			logger.debug("db에서 받아온 존재하는 사용자 값 : "+lvo.toString());
			
			//mav.addObject("ipId", lvo.getIpId());
			//mav.addObject("userPw", lvo.getUserPw());
			mav.addObject("grIpId", lvo.getGrIpId());
			mav.addObject("userAuth", lvo.getUserAuth());
			mav.addObject("userId", lvo.getUserId());
			mav.addObject("testCode", lvo.getTestCode());
			mav.addObject("startDt", lvo.getStartDt());
			mav.addObject("endDt", lvo.getEndDt());
			mav.addObject("acYn", lvo.getAcYn());
			//logger.debug("▶▶▶▶▶▶▶. 전송하는 mav 최종 값 : "+mav);
			
			//mav.addObject(lvo.toString());
		}
		resVal=null;
		jsonVal=null;
//		logger.debug("▶▶▶▶▶▶▶. json 변형하는 스트링 변수 resVal 초기화 값 : "+resVal);
//		logger.debug("▶▶▶▶▶▶▶. app 전송 직전에 초기화된 jsonVal 값 : "+jsonVal);
		return mav;
	}*/
}
