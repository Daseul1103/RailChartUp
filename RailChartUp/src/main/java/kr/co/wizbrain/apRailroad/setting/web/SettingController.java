package kr.co.wizbrain.apRailroad.setting.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.wizbrain.apRailroad.comm.SessionListener;
import kr.co.wizbrain.apRailroad.eventLog.vo.EventLogVO;
import kr.co.wizbrain.apRailroad.eventLog.vo.LogVO;
import kr.co.wizbrain.apRailroad.setting.service.SettingService;
import kr.co.wizbrain.apRailroad.setting.vo.DayCrowdRangeVO;
import kr.co.wizbrain.apRailroad.setting.vo.MdCrowdRangeVO;
import kr.co.wizbrain.apRailroad.setting.vo.MonCrowdRangeVO;
import kr.co.wizbrain.apRailroad.setting.vo.SeaCrowdRangeVO;
import kr.co.wizbrain.apRailroad.setting.vo.TimeRangeVO;
import kr.co.wizbrain.apRailroad.setting.vo.YearCrowdRangeVO;
import kr.co.wizbrain.apRailroad.terminal.vo.TerminalVO;


/**
 * 설정 컨트롤러 클래스
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

@Controller
public class SettingController {


	public static final Logger logger = LoggerFactory.getLogger(SettingController.class);
	
	@Resource(name="settingService")
	private SettingService settingService;
	public String url="";
	
	public DayCrowdRangeVO dayVo;
	public MdCrowdRangeVO mdVo;
	public MonCrowdRangeVO monVo;
	public YearCrowdRangeVO yrVo;
	public SeaCrowdRangeVO seaVo;
	public TimeRangeVO timeVo;
	
	
	//주소에 맞게 매핑
	@RequestMapping(value="/setting/*.do")
	public String settingUrlMapping(HttpServletRequest request,Model model) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.setting 최초 컨트롤러 진입 httpSession : "+request.getSession());
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		logger.debug("▶▶▶▶▶▶▶.보내려는 url : "+url);
		try {
			HttpSession chkSession = request.getSession(false);
			logger.debug("▶▶▶▶▶▶▶.체크세션 : "+chkSession);
			// 이미 접속한 아이디인지 체크
            // 현재 접속자들 보여주기
            SessionListener.getInstance().printloginUsers();
		} catch (Exception e) {
			System.out.println("에러메시지"+e.getMessage());
		}
		return url;
	}

	//시간 기간 범위 목록 화면
	@RequestMapping(value="/setting/timeRange.do")
	public @ResponseBody ModelAndView timeRange(HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.시간 기간범위 설정 화면(상세같은 목록화면)!!!!!!!!!!!!!!!!");
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		ModelAndView mav = new ModelAndView("jsonView");
		TimeRangeVO tvo = new TimeRangeVO();
		try {
			tvo = settingService.timeRangeList();
			logger.debug("▶▶▶▶▶▶▶.시간기간목록 결과값들:"+tvo);
			mav.addObject("data", tvo);
			mav.setViewName(url);
		} catch (Exception e) {
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	

	//일일 혼잡도 범위 목록
	@RequestMapping(value="/setting/dayCrowdRange.do")
	public @ResponseBody ModelAndView dayCrowdRange(HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.일일 혼잡도 범위 설정 화면(상세같은 목록화면)!!!!!!!!!!!!!!!!");
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		ModelAndView mav = new ModelAndView("jsonView");
		DayCrowdRangeVO svo = new DayCrowdRangeVO();
		try {
			svo = settingService.dayCrowdRangeList();
			logger.debug("▶▶▶▶▶▶▶.일일 혼잡도 범위 결과값들:"+svo);
			mav.addObject("data", svo);
			mav.setViewName(url);
		} catch (Exception e) {
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	

	//월별 혼잡도 범위 목록
	@RequestMapping(value="/setting/monCrowdRange.do")
	public @ResponseBody ModelAndView monCrowdRange(HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.월별 혼잡도 범위 설정 화면(상세같은 목록화면)!!!!!!!!!!!!!!!!");
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		ModelAndView mav = new ModelAndView("jsonView");
		MonCrowdRangeVO svo = new MonCrowdRangeVO();
		try {
			svo = settingService.monCrowdRangeList();
			logger.debug("▶▶▶▶▶▶▶.월별 혼잡도 범위 결과값들:"+svo);
			mav.addObject("data", svo);
			mav.setViewName(url);
		} catch (Exception e) {
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	

	//연도별 혼잡도 범위 목록
	@RequestMapping(value="/setting/yearCrowdRange.do")
	public @ResponseBody ModelAndView yearCrowdRange(HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.연도별 혼잡도 범위 설정 화면(상세같은 목록화면)!!!!!!!!!!!!!!!!");
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		ModelAndView mav = new ModelAndView("jsonView");
		YearCrowdRangeVO svo = new YearCrowdRangeVO();
		try {
			svo = settingService.yearCrowdRangeList();
			logger.debug("▶▶▶▶▶▶▶.연도별 혼잡도 범위 결과값들:"+svo);
			mav.addObject("data", svo);
			mav.setViewName(url);
		} catch (Exception e) {
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	

	//계절별 혼잡도 범위 목록
	@RequestMapping(value="/setting/seaCrowdRange.do")
	public @ResponseBody ModelAndView seaCrowdRange(HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.계절별 혼잡도 범위 설정 화면(상세같은 목록화면)!!!!!!!!!!!!!!!!");
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		ModelAndView mav = new ModelAndView("jsonView");
		SeaCrowdRangeVO svo = new SeaCrowdRangeVO();
		try {
			svo = settingService.seaCrowdRangeList();
			logger.debug("▶▶▶▶▶▶▶.계절별 혼잡도 범위 결과값들:"+svo);
			mav.addObject("data", svo);
			mav.setViewName(url);
		} catch (Exception e) {
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	
	//월간(일별) 혼잡도 범위 목록
	@RequestMapping(value="/setting/mdCrowdRange.do")
	public @ResponseBody ModelAndView mdCrowdRange(HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.계절별 혼잡도 범위 설정 화면(상세같은 목록화면)!!!!!!!!!!!!!!!!");
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		ModelAndView mav = new ModelAndView("jsonView");
		MdCrowdRangeVO svo = new MdCrowdRangeVO();
		try {
			svo = settingService.mdCrowdRangeList();
			logger.debug("▶▶▶▶▶▶▶.계절별 혼잡도 범위 결과값들:"+svo);
			mav.addObject("data", svo);
			mav.setViewName(url);
		} catch (Exception e) {
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	
	//시기간 범위 수정
	@RequestMapping(value="/setting/timeRangeUpdate.do")
	public @ResponseBody ModelAndView timeRangeUpdate(HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.시간 기간범위 설정 화면(상세같은 목록화면)!!!!!!!!!!!!!!!!");
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		ModelAndView mav = new ModelAndView("jsonView");
		TimeRangeVO tvo = new TimeRangeVO();
		try {
			tvo = settingService.timeRangeList();
			logger.debug("▶▶▶▶▶▶▶.시간기간목록 결과값들:"+tvo);
			mav.addObject("data", tvo);
			mav.setViewName(url);
			
			timeVo = new TimeRangeVO();
			timeVo = tvo;
					
		} catch (Exception e) {
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	
	//시기간 범위 수정 로직
	@RequestMapping(value="/setting/timeRangeUpdate/*.ajax")
	public @ResponseBody ModelAndView timeRangeUpdateForm( @ModelAttribute("TimeRangeVO") TimeRangeVO svo, HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.시 기 간 수 정 화 면(상세같은 목록화면)!!!!!!!!!!!!!!!!");
		String logId = request.getRequestURI().substring(request.getContextPath().length()).split(".ajax")[0].split("/")[3];
		
		LogVO lvo = new LogVO();
		EventLogVO evo = new EventLogVO();
		lvo.setLoginId(logId);
		evo.setEvtTgt("시/기간 범위수정");
		
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			
			evo.setEvtName("항목 변경");
			lvo.setEvo(evo);
			
			settingService.timeRangeUpdate(svo,timeVo,lvo);
			
		} catch (Exception e) {
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}

	//일일 혼잡도 범위 수정 화면
	@RequestMapping(value="/setting/dayCrowdRangeUpdate.do")
	public @ResponseBody ModelAndView dayCrowdRangeUpdate(HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.일일 혼잡도 범위 수정 화면!!!!!!!!!!!!!!!!");
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		ModelAndView mav = new ModelAndView("jsonView");
		DayCrowdRangeVO svo = new DayCrowdRangeVO();
		try {
			svo = settingService.dayCrowdRangeList();
			logger.debug("▶▶▶▶▶▶▶.일일 혼잡도 범위 결과값들:"+svo);
			mav.addObject("data", svo);
			mav.setViewName(url);
			dayVo=new DayCrowdRangeVO();
			dayVo=svo;
		} catch (Exception e) {
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	
	//일일 혼잡도 범위 수정 화면 로직
	@RequestMapping(value="/setting/dayCrowdRangeUpdate/*.ajax")
	public @ResponseBody ModelAndView dayCrowdRangeUpdateForm( @ModelAttribute("DayCrowdRangeVO") DayCrowdRangeVO svo, HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.일일 혼잡도 범위 수정 화면 로직!!!!!!!!!!!!!!!!");
		String[] arr = request.getRequestURI().substring(request.getContextPath().length()).split(".ajax")[0].split("/")[3].split("_");
		String qKey = arr[0];
		String logId = arr[1];
		ModelAndView mav = new ModelAndView("jsonView");
		
		LogVO lvo = new LogVO();
		EventLogVO evo = new EventLogVO();
		lvo.setLoginId(logId);
		evo.setEvtTgt("일일 혼잡도 범위");
		
		try {
			if(qKey.equals("all")) {//전체 일괄
				
				evo.setEvtName("전체 변경");
				lvo.setEvo(evo);
				
				settingService.settingUpdateAll(svo,dayVo,lvo);
			}else {
				if(qKey.equals("dayA")) {//일일 일괄
					evo.setEvtName("일일 일괄 변경");
				}else {//일반
					evo.setEvtName("단일화면 변경");
				}
				lvo.setEvo(evo);
				settingService.dayCrowdRangeUpdate(svo,dayVo,lvo);
			}
		} catch (Exception e) {
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	

	//월별 혼잡도 범위 수정 화면
	@RequestMapping(value="/setting/monCrowdRangeUpdate.do")
	public @ResponseBody ModelAndView monCrowdRangeUpdate(HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.월별 혼잡도 범위 수정 화면!!!!!!!!!!!!!!!!");
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		ModelAndView mav = new ModelAndView("jsonView");
		MonCrowdRangeVO svo = new MonCrowdRangeVO();
		try {
			svo = settingService.monCrowdRangeList();
			logger.debug("▶▶▶▶▶▶▶.일일 혼잡도 범위 결과값들:"+svo);
			mav.addObject("data", svo);
			mav.setViewName(url);
			monVo = new MonCrowdRangeVO();
			monVo=svo;
		} catch (Exception e) {
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	
	//월별 혼잡도 범위 수정 화면 로직
	@RequestMapping(value="/setting/monCrowdRangeUpdate/*.ajax")
	public @ResponseBody ModelAndView monCrowdRangeUpdateForm( @ModelAttribute("MonCrowdRangeVO") MonCrowdRangeVO svo, HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.월별 혼잡도 범위 수정 화면 로직!!!!!!!!!!!!!!!!");
		String[] arr = request.getRequestURI().substring(request.getContextPath().length()).split(".ajax")[0].split("/")[3].split("_");
		String qKey = arr[0];
		String logId = arr[1];
		ModelAndView mav = new ModelAndView("jsonView");
		
		LogVO lvo = new LogVO();
		EventLogVO evo = new EventLogVO();
		lvo.setLoginId(logId);
		evo.setEvtTgt("월별 혼잡도 범위");
		
		try {
			if(qKey.equals("all")) {//전체 일괄
				
				evo.setEvtName("전체 변경");
				lvo.setEvo(evo);
				
				settingService.settingUpdateAll(svo,monVo,lvo);
			}else {
				evo.setEvtName("단일 변경");
				lvo.setEvo(evo);
				settingService.monCrowdRangeUpdate(svo,monVo,lvo);
			}
		} catch (Exception e) {
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	

	//연도별 혼잡도 범위 수정 화면 화면
	@RequestMapping(value="/setting/yearCrowdRangeUpdate.do")
	public @ResponseBody ModelAndView yearCrowdRangeUpdate(HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.연도별 혼잡도 범위 수정 화면 화면!!!!!!!!!!!!!!!!");
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		ModelAndView mav = new ModelAndView("jsonView");
		YearCrowdRangeVO svo = new YearCrowdRangeVO();
		try {
			svo = settingService.yearCrowdRangeList();
			logger.debug("▶▶▶▶▶▶▶.일일 혼잡도 범위 결과값들:"+svo);
			mav.addObject("data", svo);
			mav.setViewName(url);
			yrVo = new YearCrowdRangeVO();
			yrVo = svo;
		} catch (Exception e) {
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	
	//연도별 혼잡도 범위 수정 화면 로직
	@RequestMapping(value="/setting/yearCrowdRangeUpdate/*.ajax")
	public @ResponseBody ModelAndView yearCrowdRangeUpdateForm( @ModelAttribute("YearCrowdRangeVO") YearCrowdRangeVO svo, HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.연도별 혼잡도 범위 수정 화면 로직!!!!!!!!!!!!!!!!");
		String[] arr = request.getRequestURI().substring(request.getContextPath().length()).split(".ajax")[0].split("/")[3].split("_");
		String qKey = arr[0];
		String logId = arr[1];
		ModelAndView mav = new ModelAndView("jsonView");
		
		LogVO lvo = new LogVO();
		EventLogVO evo = new EventLogVO();
		lvo.setLoginId(logId);
		evo.setEvtTgt("연도별 혼잡도 범위");
		
		try {
			if(qKey.equals("all")) {//전체 일괄
				
				evo.setEvtName("전체 변경");
				lvo.setEvo(evo);
				
				settingService.settingUpdateAll(svo,yrVo,lvo);
			}else {
				evo.setEvtName("단일 변경");
				lvo.setEvo(evo);
				settingService.yearCrowdRangeUpdate(svo,yrVo,lvo);
			}
		} catch (Exception e) {
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}

	//계절별 혼잡도 범위 수정 화면
	@RequestMapping(value="/setting/seaCrowdRangeUpdate.do")
	public @ResponseBody ModelAndView seaCrowdRangeUpdate(HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.연도별 혼잡도 범위 수정 화면 화면!!!!!!!!!!!!!!!!");
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		ModelAndView mav = new ModelAndView("jsonView");
		SeaCrowdRangeVO svo = new SeaCrowdRangeVO();
		try {
			svo = settingService.seaCrowdRangeList();
			logger.debug("▶▶▶▶▶▶▶.일일 혼잡도 범위 결과값들:"+svo);
			mav.addObject("data", svo);
			mav.setViewName(url);
			seaVo = new SeaCrowdRangeVO();
			seaVo = svo;
		} catch (Exception e) {
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		
		
		return mav;
	}
	
	//계절별 혼잡도 범위 수정 화면 로직
	@RequestMapping(value="/setting/seaCrowdRangeUpdate/*.ajax")
	public @ResponseBody ModelAndView seaCrowdRangeUpdateForm( @ModelAttribute("SeaCrowdRangeVO") SeaCrowdRangeVO svo, HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.연도별 혼잡도 범위 수정 화면 로직!!!!!!!!!!!!!!!!");
		String[] arr = request.getRequestURI().substring(request.getContextPath().length()).split(".ajax")[0].split("/")[3].split("_");
		String qKey = arr[0];
		String logId = arr[1];
		ModelAndView mav = new ModelAndView("jsonView");
		
		LogVO lvo = new LogVO();
		EventLogVO evo = new EventLogVO();
		lvo.setLoginId(logId);
		evo.setEvtTgt("계절별 혼잡도 범위");
		
		try {
			if(qKey.equals("all")) {//전체 일괄
				
				evo.setEvtName("전체 변경");
				lvo.setEvo(evo);
				
				settingService.settingUpdateAll(svo,seaVo,lvo);
			}else {
				evo.setEvtName("단일 변경");
				lvo.setEvo(evo);
				settingService.seaCrowdRangeUpdate(svo,seaVo,lvo);
			}
		} catch (Exception e) {
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	
	//월별(일간) 혼잡도 범위 수정 화면
	@RequestMapping(value="/setting/mdCrowdRangeUpdate.do")
	public @ResponseBody ModelAndView mdCrowdRangeUpdate(HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.연도별 혼잡도 범위 수정 화면 화면!!!!!!!!!!!!!!!!");
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		ModelAndView mav = new ModelAndView("jsonView");
		MdCrowdRangeVO svo = new MdCrowdRangeVO();
		try {
			svo = settingService.mdCrowdRangeList();
			logger.debug("▶▶▶▶▶▶▶.일일 혼잡도 범위 결과값들:"+svo);
			mav.addObject("data", svo);
			mav.setViewName(url);
			mdVo = new MdCrowdRangeVO();
			mdVo = svo;
		} catch (Exception e) {
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	
	//월별(일간) 혼잡도 범위 수정 화면 로직
	@RequestMapping(value="/setting/mdCrowdRangeUpdate/*.ajax")
	public @ResponseBody ModelAndView mdCrowdRangeUpdateForm( @ModelAttribute("MdCrowdRangeVO") MdCrowdRangeVO svo, HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.연도별 혼잡도 범위 수정 화면 로직!!!!!!!!!!!!!!!!");
		String[] arr = request.getRequestURI().substring(request.getContextPath().length()).split(".ajax")[0].split("/")[3].split("_");
		String qKey = arr[0];
		String logId = arr[1];
		ModelAndView mav = new ModelAndView("jsonView");
		
		LogVO lvo = new LogVO();
		EventLogVO evo = new EventLogVO();
		lvo.setLoginId(logId);
		evo.setEvtTgt("월간 혼잡도 범위");
		
		try {
			if(qKey.equals("all")) {//전체 일괄
				
				evo.setEvtName("전체 변경");
				lvo.setEvo(evo);
				
				settingService.settingUpdateAll(svo,mdVo,lvo);
			}else {
				evo.setEvtName("단일 변경");
				lvo.setEvo(evo);
				settingService.mdCrowdRangeUpdate(svo,mdVo,lvo);
			}
		} catch (Exception e) {
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
}
