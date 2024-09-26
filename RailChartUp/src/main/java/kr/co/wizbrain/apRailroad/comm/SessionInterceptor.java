package kr.co.wizbrain.apRailroad.comm;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import kr.co.wizbrain.apRailroad.user.service.UserService;
import kr.co.wizbrain.apRailroad.user.vo.UserVO;



public class SessionInterceptor extends HandlerInterceptorAdapter{
	
	public static final String ACCESS = "access";
	private static final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);
	
	@Resource(name="userService")
	private UserService userService;
	
	/**
	 * 컨트롤러 수행전에 세션 정보가 있는지 확인하는 처리..
	 */
	@Override
	public boolean preHandle(
			HttpServletRequest  request, 
			HttpServletResponse response, 
			Object handler)
	throws Exception {
		logger.info("◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈. 프리핸들 request : "+request);
		logger.info("◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈. 프리핸들 session : "+request.getSession());
		String clientIp = request.getRemoteAddr();
		logger.debug("◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈.ip:{}",clientIp);
		//logger.debug("◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈.최초 사이트 진입시 mac 주소"+NetworkInfo.findPcId());
		
		HttpSession httpSession = request.getSession();
		
		Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
		
		logger.debug("◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈.httpSession : "+httpSession);
		logger.debug("◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈.httpSession : "+httpSession.getAttribute(ACCESS));
		
		if(httpSession.getAttribute(ACCESS)!=null) {
			logger.debug("◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈.이미 최초 접속 기록이 있음");
			//로그인 시
			//httpSession.removeAttribute(LOGIN);
		}else {
			//최초 접속의 경우
			//비 정상 종료로 인한 잔존 macAddress 정보 삭제
			//userService.logoutUpdate(NetworkInfo.findPcId());
		}
		return true;
	}

	/**
	 * 
	 */
	@Override
	public void postHandle(
			HttpServletRequest  request, 
			HttpServletResponse response, 
			Object handler, 
			ModelAndView modelAndView)
	throws Exception {
		logger.debug("◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈.postHandle 메소드 진입");
		logger.debug("◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈.사용자쿠키 : "+request.getCookies());
		HttpSession httpSession = request.getSession();
		ModelMap modelMap = modelAndView.getModelMap();
		Object uvo = modelMap.get("user");
		
		//최초 접속 시
		//mac과 세션을 매핑
		if(httpSession.getAttribute(ACCESS)==null) {
			logger.debug("◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈.최초 접속임");
			httpSession.setAttribute(ACCESS, "Y");
			//창 닫아도 세션 삭제 전까지 로그인 유지를 위해 쿠키 생성
			Cookie loginCookie = new Cookie("loginCookie", httpSession.getId());
			loginCookie.setPath("/");
			loginCookie.setMaxAge(60*60*12);
			//web으로 전송
			response.addCookie(loginCookie);
		}else {//최초 접속이 아닐 시
			logger.debug("◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈.이미 최초 접속 기록이 있음");
		}
	}

	/**
	 * 
	 */
	@Override
	public void afterCompletion(
			HttpServletRequest  request, 
			HttpServletResponse response, 
			Object handler, Exception ex)
	throws Exception {
		logger.info("★★★★★★★★★★★★★★★★★.Interceptor: afterCompletion");
		super.afterCompletion(request, response, handler, ex);
	}
	
} // end class