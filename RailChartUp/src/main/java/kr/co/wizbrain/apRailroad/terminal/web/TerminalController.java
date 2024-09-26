package kr.co.wizbrain.apRailroad.terminal.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
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
import kr.co.wizbrain.apRailroad.terminal.service.TerminalService;
import kr.co.wizbrain.apRailroad.terminal.vo.DirectRegularVO;
import kr.co.wizbrain.apRailroad.terminal.vo.TerminalVO;

@Controller
public class TerminalController {
public static final Logger logger = LoggerFactory.getLogger(TerminalController.class);
	
	@Resource(name="terminalService")
	private TerminalService terminalService;
	public String url="";
	public DirectRegularVO dPreVo;
	
	//주소에 맞게 매핑
	@RequestMapping(value="/terminal/*.do")
	public String terminalUrlMapping(HttpSession httpSession, HttpServletRequest request,Model model) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.terminal 최초 컨트롤러 진입 httpSession : "+httpSession);
		logger.debug("▶▶▶▶▶▶▶.request.getRequestURL() : "+request.getRequestURL());
		logger.debug("▶▶▶▶▶▶▶.request.getRequestURI() : "+request.getRequestURI());
		logger.debug("▶▶▶▶▶▶▶.request.getContextPath() : "+request.getContextPath());
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		logger.debug("▶▶▶▶▶▶▶.보내려는 url : "+url);
		return url;
	}

	//차상단말기 목록 조회
	@RequestMapping(value="/terminal/terminalList.ajax")
	public @ResponseBody ModelAndView terminalList( @ModelAttribute("TerminalVO") TerminalVO TerminalVO,HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.차상단말기 정보 조회 목록!!!!!!!!!!!!!!!!");
		TerminalVO tvo = new TerminalVO();
		//url로 h,g 판별하여 해당하는 값만 조회
		String authUrl = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		ModelAndView mav = new ModelAndView("jsonView");
		List<TerminalVO> terminalList= null;
		try {
			terminalList = terminalService.selectTerminalList(tvo);
			logger.debug("▶▶▶▶▶▶▶.차상단말기 리스트 결과값들 :"+terminalList);
			
			mav.addObject("data", terminalList);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mav;
	}
	

	//차상단말기 등록
	@RequestMapping(value="/terminal/terminalInsert.ajax", method=RequestMethod.POST)
	public ModelAndView terminalInsert(HttpServletRequest request, @ModelAttribute TerminalVO TerminalVO, RedirectAttributes redirectAttributes) throws Exception{
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			terminalService.terminalInsert(TerminalVO);
		} catch (Exception e) {
			logger.debug("에러메시지 : "+e);
			mav.addObject("msg", "이미 등록된 편성번호입니다.");
		}
		return mav;
	}
	
	//차상단말기 상세 조회
	@RequestMapping(value="/terminal/terminalDetail.do")
	public @ResponseBody ModelAndView terminalDetail( @ModelAttribute("TerminalVO") TerminalVO TerminalVO,HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.차상단말기 정보 조회 목록!!!!!!!!!!!!!!!!");
		
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		
		ModelAndView mav = new ModelAndView("jsonView");
		TerminalVO disterminal= null;
		try {
			disterminal = terminalService.selectTerminal(TerminalVO);
			logger.debug("▶▶▶▶▶▶▶.시험코드 결과값들:"+disterminal);
			
			mav.addObject("data", disterminal);
			mav.setViewName(url);
		} catch (Exception e) {
			logger.debug("에러메시지 : "+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}

	//차상단말기 수정 페이지 진입
	@RequestMapping(value="/terminal/terminalUpdate.do")
	public @ResponseBody ModelAndView terminalUpdate( @ModelAttribute("TerminalVO") TerminalVO TerminalVO,HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.차상단말기 정보 조회 목록!!!!!!!!!!!!!!!!");
		
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		
		ModelAndView mav = new ModelAndView("jsonView");
		TerminalVO disterminal= null;
		try {
			disterminal = terminalService.selectTerminal(TerminalVO);
			logger.debug("▶▶▶▶▶▶▶. 차상단말기 결과값들:"+disterminal);
			
			mav.addObject("data", disterminal);
			mav.setViewName(url);
		} catch (Exception e) {
			logger.debug("에러메시지 : "+e);
		}
		return mav;
	}
	
	//차상단말기 수정 반영
	@RequestMapping(value="/terminal/terminalUpdate.ajax")
	public @ResponseBody ModelAndView terminalUpdateForm( @ModelAttribute("TerminalVO") TerminalVO TerminalVO,HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.차상단말기 정보 수정!!!!!!!!!!!!!!!!");
		
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			terminalService.terminalUpdate(TerminalVO);
			
		} catch (Exception e) {
			logger.debug("에러메시지 : "+e);
			mav.addObject("msg","에러가 발생하였습니다");
		}
		return mav;
	}

	//차상단말기 삭제
	@RequestMapping(value="/terminal/terminalDelete.ajax")
	public @ResponseBody ModelAndView terminalDelete( @RequestParam(value="idArr[]")List<String> terminalArr,HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.차상단말기 정보 삭제!!!!!!!!!!!!!!!!");
		
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			terminalService.terminalDelete(terminalArr);
			
		} catch (Exception e) {
			logger.debug("에러메시지 : "+e);
			mav.addObject("msg","에러가 발생하였습니다");
		}
		return mav;
	}

	//직통 /일반 목록
	@RequestMapping(value="/terminal/directRegularStd.ajax")
	public @ResponseBody ModelAndView directRegularStd( @ModelAttribute("DirectRegularVO") DirectRegularVO DirectRegularVO,HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶.직통 일반 정보 조회 목록!!!!!!!!!!!!!!!!");
		DirectRegularVO tvo = new DirectRegularVO();
		//url로 h,g 판별하여 해당하는 값만 조회
		ModelAndView mav = new ModelAndView("jsonView");
		List<DirectRegularVO> terminalList= null;
		try {
			terminalList = terminalService.selectDrtRgr(tvo);
			logger.debug("▶▶▶▶▶▶▶.차상단말기 리스트 결과값들 :"+terminalList);
			
			mav.addObject("data", terminalList);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mav;
	}

	//직통 /일반 수정 페이지 진입
	@RequestMapping(value="/terminal/directRegularStdUpdate.do")
	public @ResponseBody ModelAndView directRegularStdUpdate( @ModelAttribute("DirectRegularVO") DirectRegularVO DirectRegularVO,HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.직통 수정페이지 진입!!!!!!!!!!!!!!!!");
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		ModelAndView mav = new ModelAndView("jsonView");
		DirectRegularVO dvo= null;
		try {
			dvo = terminalService.selectDrtRgr(DirectRegularVO).get(0);
			logger.debug("▶▶▶▶▶▶▶. 차상단말기 결과값들:"+dvo);
			dPreVo = dvo;
			mav.addObject("data", dvo);
			mav.setViewName(url);
		} catch (Exception e) {
			logger.debug("에러메시지 : "+e);
		}
		return mav;
	}
	
	//직통 /일반 수정 반영
	@RequestMapping(value="/terminal/directRegularStdUpdate/*.ajax")
	public @ResponseBody ModelAndView directRegularStdUpdateForm( @ModelAttribute("DirectRegularVO") DirectRegularVO drVo,HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.차상단말기 정보 수정!!!!!!!!!!!!!!!!");
		ModelAndView mav = new ModelAndView("jsonView");
		
		String logId = request.getRequestURI().substring(request.getContextPath().length()).split(".ajax")[0].split("/")[3];
		
		LogVO lvo = new LogVO();
		EventLogVO evo = new EventLogVO();
		lvo.setLoginId(logId);
		evo.setEvtTgt("직통/일반열차 관리");
		if(drVo.carType.equals("직통열차")) {
			evo.setEvtName("직통열차");
		}else {
			evo.setEvtName("일반열차");
		}
		try {
			lvo.setEvo(evo);
			terminalService.drtRgrUpdate(dPreVo,drVo,lvo);
		} catch (Exception e) {
			logger.debug("에러메시지 : "+e);
			mav.addObject("msg","에러가 발생하였습니다");
		}
		return mav;
	}
}
