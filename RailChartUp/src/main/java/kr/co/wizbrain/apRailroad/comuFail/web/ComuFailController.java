package kr.co.wizbrain.apRailroad.comuFail.web;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.wizbrain.apRailroad.comm.SessionListener;
import kr.co.wizbrain.apRailroad.comuFail.service.ComuFailService;
import kr.co.wizbrain.apRailroad.comuFail.vo.ComuFailVO;

@Controller
public class ComuFailController {
	public static final Logger logger = LoggerFactory.getLogger(ComuFailController.class);
	
	@Resource(name="comuFailService")
	private ComuFailService comuFailService;
	public String url="";
	
	//주소에 맞게 매핑
	@RequestMapping(value="/comuFail/*.do")
	public String comuFailUrlMapping(HttpSession httpSession, HttpServletRequest request,Model model) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.user 최초 컨트롤러 진입 httpSession : "+httpSession);
		logger.debug("▶▶▶▶▶▶▶.request.getRequestURL() : "+request.getRequestURL());
		logger.debug("▶▶▶▶▶▶▶.request.getRequestURI() : "+request.getRequestURI());
		logger.debug("▶▶▶▶▶▶▶.request.getContextPath() : "+request.getContextPath());
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
	
	
	//통신장애 목록 조회
	@RequestMapping(value="/comuFail/comuFailList.ajax")
	public @ResponseBody ModelAndView comuFailList( @ModelAttribute("comuFailVO") ComuFailVO cmfVO,HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.회원정보 조회 목록!!!!!!!!!!!!!!!!");
		ComuFailVO cvo = new ComuFailVO();
		//url로 h,g 판별하여 해당하는 값만 조회
		String authUrl = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		ModelAndView mav = new ModelAndView("jsonView");
		List<ComuFailVO> comuList= null;
		try {
			comuList = comuFailService.selectComuFailList(cvo);
			logger.debug("▶▶▶▶▶▶▶.통신장애 결과값들:"+comuList);
			
			mav.addObject("data", comuList);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mav;
	}
}
