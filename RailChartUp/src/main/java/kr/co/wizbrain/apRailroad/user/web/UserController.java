package kr.co.wizbrain.apRailroad.user.web;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.registry.infomodel.User;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import kr.co.wizbrain.apRailroad.comm.SessionListener;
import kr.co.wizbrain.apRailroad.statistic.vo.StatisticVO;
import kr.co.wizbrain.apRailroad.user.service.UserService;
import kr.co.wizbrain.apRailroad.user.vo.UserVO;

/**
 * 사용자 컨트롤러 클래스
 * @author 미래전략사업팀 정다빈
 * @since 2020.07.23
 * @version 1.0
 * @see
 *
 * << 개정이력(Modification Information) >>
 *
 *   수정일            수정자              수정내용
 *  -------    -------- ---------------------------
 *  2020.07.23  정다빈           최초 생성
 */

@Controller
public class UserController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Resource(name="userService")
	private UserService userService;
	public String url="";
	public boolean isClose = false;
	
	
	//주소에 맞게 매핑
	@RequestMapping(value="/user/*.do")
	public String userUrlMapping(HttpSession httpSession, HttpServletRequest request,Model model) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.user 최초 컨트롤러 진입 httpSession : "+httpSession);
		logger.debug("▶▶▶▶▶▶▶.request.getRequestURL() : "+request.getRequestURL());
		logger.debug("▶▶▶▶▶▶▶.request.getRequestURI() : "+request.getRequestURI());
		logger.debug("▶▶▶▶▶▶▶.request.getContextPath() : "+request.getContextPath());
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		logger.debug("▶▶▶▶▶▶▶.보내려는 url : "+url);
		return url;
	}
	
	//권한 별 리다이렉트 처리
	/*public String urlRedirect(HttpServletRequest request) {
		String authUrl = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		String res="";
		if(authUrl.indexOf("host")>-1) {
			res="redirect:/by_hyo-20201001.jsp";
		}else {//일반 사이트
			res="redirect:/";
		}
		return res;
	}*/
	
	//새로고침 / 창 닫기 분기 
	@RequestMapping(value = "/user/reloadOrKill.do")
	public String reloadOrKill(@RequestParam(required=false, value="tagId")boolean tagId, HttpSession httpSession, HttpServletRequest request,Model model) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.새로고침 or 창닫기 tag식별 : "+tagId);
		this.isClose = tagId;
		//refresh
	    if ( isClose ) {
	    	logger.debug("▶▶▶▶▶▶▶.refresh...");
	    	return "redirect:/";
	    }

	    Thread.sleep(10000);
	    if ( !this.isClose ) {
	    	logger.debug("▶▶▶▶▶▶▶.kill session!!!");
	    	httpSession.removeAttribute("access");
	    	
			httpSession.removeAttribute("login");
			SessionListener.getInstance().removeSession(httpSession);
	    }
	    return "redirect:/";
	}
	
	//로그인 처리
	
	//inputVo : 사용자가 화면에서 입력한 uservo 정보
	//lvo : db에 insert할 실제 loginvo
	
	@RequestMapping(value="/user/loginPost.do")
	public @ResponseBody ModelAndView loginPost
	(@ModelAttribute UserVO inputVo,HttpSession httpSession
	,HttpServletRequest request,Model model) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.loginPost 진입 세션 : "+httpSession);
		logger.debug("▶▶▶▶▶▶▶.받아온 loginVo 값 : "+inputVo.toString());
		
		String msg="";
		UserVO userVo = new UserVO();
		ModelAndView mav = new ModelAndView("jsonView");
		boolean res=true;
		//주소 체크로 관리자나 일반 사용자가 다른 권한의 web에서 로그인 방지
		String authUrl = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		
		try {
			userVo = userService.selectUser(inputVo);
			
			if(userVo ==null || !(BCrypt.checkpw(inputVo.getUserPw(), userVo.getUserPw()))) {
				logger.debug("▶▶▶▶▶▶▶.가입되지 않은 사용자이거나 정보를 잘못 입력하셨습니다");
				msg="가입되지 않은 사용자이거나 정보를 잘못 입력하셨습니다";
				userVo =null;
			}else {
                //기 로그인 중 사용자 체크
                if(SessionListener.getInstance().isUsing(inputVo.getUserId())) {
                	logger.debug("▶▶▶▶▶▶▶.이미 로그인 중인 사용자입니다.");
                	msg="이미 로그인 중인 사용자입니다";
                	userVo =null;
                }else {
                	if (res==false) {//권한에 맞지 않는 로그인
						msg="유효하지 않은 접근입니다.";
					} else {//로그인 성공
						//로그인 세션 관련 설정//
	    				HttpSession loginSession = request.getSession(true);
	    				
	    				//세션시간 설정 0*30*60 30분 (시간/분/초 단위 : 초)
		                loginSession.setMaxInactiveInterval(30*60);
		                logger.debug("로그인vo 세션시간 : "+loginSession.getMaxInactiveInterval());
		                //세션에 값 주입
		                httpSession.setAttribute("login", inputVo);
		                //세션 + 시간 해쉬맵에 로그인 세션과 현 시간을 저장
		                SessionListener.getInstance().setSession(loginSession, userVo.getUserId());
						
					}
                }
			}
			
			//${}세션
			model.addAttribute("user",userVo);
		} catch (Exception e) {
			msg="ID 또는 PASSWORD가 일치하지 않습니다.";
			userVo=null;
			logger.debug("▶▶▶▶▶▶▶.캐치 에러 : "+e);
		} finally {
			mav.addObject("msg", msg);
			mav.addObject("uvo", userVo);
		}
		//ajax
		
		return mav;
	}
	
	//로그아웃 처리
	@RequestMapping(value="/user/logout.do")
	public String logout(UserVO loginVo, HttpSession httpSession, Model model, HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.logout 메소드 진입");
		httpSession.removeAttribute("access");
		//String res=urlRedirect(request);
		return "redirect:/";
	}
	
	//회원가입, 사용자 등록
	@RequestMapping(value="/user/insertUser.ajax", method=RequestMethod.POST)
	public ModelAndView insertUser(HttpServletRequest request, @ModelAttribute UserVO userVO, RedirectAttributes redirectAttributes) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.request.getRequestURL() : "+request.getRequestURL());
		logger.debug("▶▶▶▶▶▶▶.request.getRequestURI() : "+request.getRequestURI());
		logger.debug("▶▶▶▶▶▶▶.request.getContextPath() : "+request.getContextPath());
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			String authUrl = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
			//패스워드 암호화 처리
			logger.debug("변환전 uservo: "+userVO.toString());
			String hashedPw = BCrypt.hashpw(userVO.getUserPw(), BCrypt.gensalt());
			userVO.setUserPw(hashedPw);
			logger.debug("변환후 uservo: "+userVO.toString());
			userService.insertUser(userVO);
		} catch (Exception e) {
			mav.addObject("msg", "입력하신 정보가 올바르지 않습니다.");
		}
		return mav;
	}
	
	//사용자 목록 조회
	@RequestMapping(value="/user/userList.ajax")
	public @ResponseBody ModelAndView userList( @ModelAttribute("userVO") UserVO userVO,HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.회원정보 조회 목록!!!!!!!!!!!!!!!!");
		UserVO uvo = new UserVO();
		//url로 h,g 판별하여 해당하는 값만 조회
		String authUrl = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		ModelAndView mav = new ModelAndView("jsonView");
		List<UserVO> userList= null;
		try {
			userList = userService.selectUserList(uvo);
			logger.debug("▶▶▶▶▶▶▶.사용자 리스트 결과값들 :"+userList);
			
			mav.addObject("data", userList);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mav;
	}
	

	//검색한 id 조회
	@RequestMapping(value="/user/findUserId.do")
	public @ResponseBody ModelAndView findUserId( @ModelAttribute("userVO") UserVO userVO,HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.회원정보 조회 목록!!!!!!!!!!!!!!!!");
		
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		
		ModelAndView mav = new ModelAndView("jsonView");
		UserVO disUser= null;
		try {
			disUser = userService.selectUser(userVO);
			mav.addObject("data", disUser.getUserId());
		} catch (Exception e) {
			logger.debug(""+e);
			mav.addObject("msg","search_not");
		}
		return mav;
	}
	
	//사용자 상세 조회
	@RequestMapping(value="/user/userDetail.do")
	public @ResponseBody ModelAndView userDetail( @ModelAttribute("userVO") UserVO userVO,HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.회원정보 조회 목록!!!!!!!!!!!!!!!!");
		
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		
		ModelAndView mav = new ModelAndView("jsonView");
		UserVO disUser= null;
		try {
			disUser = userService.selectUser(userVO);
			logger.debug("▶▶▶▶▶▶▶.시험코드 결과값들:"+disUser);
			
			mav.addObject("data", disUser);
			mav.setViewName(url);
		} catch (Exception e) {
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	

	//사용자 수정 페이지 진입
	@RequestMapping(value="/user/userUpdate.do")
	public @ResponseBody ModelAndView userUpdate( @ModelAttribute("userVO") UserVO userVO,HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.회원정보 조회 목록!!!!!!!!!!!!!!!!");
		
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		
		ModelAndView mav = new ModelAndView("jsonView");
		UserVO disUser= null;
		try {
			disUser = userService.selectUser(userVO);
			logger.debug("▶▶▶▶▶▶▶.시험코드 결과값들:"+disUser);
			
			mav.addObject("data", disUser);
			mav.setViewName(url);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mav;
	}
	
	//사용자 수정 반영
	@RequestMapping(value="/user/userUpdate.ajax")
	public @ResponseBody ModelAndView userUpdateForm( @ModelAttribute("userVO") UserVO userVO,HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.회원정보 수정!!!!!!!!!!!!!!!!");
		
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		//비밀번호 암호화
		if(!(userVO.getUserPw().equals(""))&&!(userVO.getUserPw()==null)) {
			String hashedPw = BCrypt.hashpw(userVO.getUserPw(), BCrypt.gensalt());
			userVO.setUserPw(hashedPw);
		}
		
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			userService.updateUser(userVO);
			
		} catch (Exception e) {
			mav.addObject("msg","에러가 발생하였습니다");
		}
		return mav;
	}

	//사용자 삭제
	@RequestMapping(value="/user/userDelete.ajax")
	public @ResponseBody ModelAndView userDelete( @RequestParam(value="idArr[]")List<String> userArr,HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.회원정보 삭제!!!!!!!!!!!!!!!!");
		
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			userService.deleteUser(userArr);
			
		} catch (Exception e) {
			mav.addObject("msg","에러가 발생하였습니다");
		}
		return mav;
	}
}
