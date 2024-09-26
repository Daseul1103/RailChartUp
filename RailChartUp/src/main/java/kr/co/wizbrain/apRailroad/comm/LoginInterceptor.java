package kr.co.wizbrain.apRailroad.comm;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.co.wizbrain.apRailroad.user.service.UserService;
import kr.co.wizbrain.apRailroad.user.vo.UserVO;

//로그인 로그아웃 처리시 처음과 끝을 관장하며
//세션, 세션 어트리뷰트 값 생성 및 삭제를 담당
public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	public static final String LOGIN = "login";
	public static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Resource(name="userService")
	private UserService userService;
	
	//로그인 로그아웃시 본 메소드 진행 전 접속
	//세션 값이 존재한다면 삭제 (로그아웃 처리)
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)
			throws Exception {
		logger.debug("▶▶▶▶▶▶▶.preHandle 메소드 진입");
		HttpSession httpSession = request.getSession();
		
		logger.debug("▶▶▶▶▶▶▶.httpSession : "+httpSession);
		logger.debug("▶▶▶▶▶▶▶.httpSession.getId : "+httpSession.getId());
		logger.debug("▶▶▶▶▶▶▶.httpSession : "+httpSession.getAttribute(LOGIN));
		//기존 세션 정보 제거
		if(httpSession.getAttribute("access")!=null) {
			httpSession.removeAttribute("access");
		}
		//기존의 로그인 정보 제거
		if(httpSession.getAttribute(LOGIN)!=null) {
			logger.debug("▶▶▶▶▶▶▶.clear login data before");
			//접속정보 1->0
			String uid = SessionListener.getInstance().getUserID(httpSession);
			httpSession.removeAttribute(LOGIN);
			//StimeListener.getInstance().removeSession(httpSession);
			SessionListener.getInstance().removeSession(httpSession);
		}
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {
		logger.debug("▶▶▶▶▶▶▶.postHandle 메소드 진입");
		logger.debug("▶▶▶▶▶▶▶.httpSession.getId : "+request.getSession().getId());
		HttpSession httpSession = request.getSession();
		ModelMap modelMap = modelAndView.getModelMap();
		//UserController에서 받은 모델 어트리뷰트 값
		Object userVo = modelMap.get("user");
		
		//정상적으로 로그인이 된 경우
		if(userVo!=null) {
			logger.debug("▶▶▶▶▶▶▶.new login success");
			UserVO uvo = (UserVO) userVo;
			//web으로 어트리뷰트값 전송
			httpSession.setAttribute(LOGIN, userVo);
		}
		
	}
	
}
