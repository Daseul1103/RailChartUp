package kr.co.wizbrain.apRailroad.eventLog.web;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

import kr.co.wizbrain.apRailroad.comm.SessionListener;
import kr.co.wizbrain.apRailroad.eventLog.service.EventLogService;
import kr.co.wizbrain.apRailroad.eventLog.vo.EventLogVO;
import kr.co.wizbrain.apRailroad.eventLog.vo.TcmsRcvVO;
import kr.co.wizbrain.apRailroad.user.vo.UserVO;
import kr.co.wizbrain.apRailroad.eventLog.vo.HseSndVO;

@Controller
public class EventLogController {
	public static final Logger logger = LoggerFactory.getLogger(EventLogController.class);
	
	@Resource(name="eventLogService")
	private EventLogService eventLogService;
	
	public String url="";
	
	public List<EventLogVO> stcEvoList=null;
	public List<TcmsRcvVO> stcTcmsList=null;
	public List<HseSndVO> stcHseList=null;
	
	
	//주소에 맞게 매핑
	@RequestMapping(value="/eventLog/*.do")
	public String eventLogUrlMapping(HttpSession httpSession, HttpServletRequest request,Model model) throws Exception{
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
	
	
	//이벤트 로그 목록 조회
	@RequestMapping(value="/eventLog/eventLogList.ajax")
	public @ResponseBody ModelAndView eventLogList( @ModelAttribute("eventLogVO") EventLogVO cmfVO,HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.이벤트 로그 조회 목록!!!!!!!!!!!!!!!!");
		EventLogVO cvo = new EventLogVO();
		//url로 h,g 판별하여 해당하는 값만 조회
		String authUrl = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		ModelAndView mav = new ModelAndView("jsonView");
		List<EventLogVO> comuList= null;
		try {
			comuList = eventLogService.selectEventLogList(cvo);
			if(comuList.size()!=0) {
				logger.debug("▶▶▶▶▶▶▶.통신장애 결과값들:"+comuList.get(0));
			}
			
			mav.addObject("data", comuList);
		} catch (Exception e) {
			logger.debug("이벤트로그 에러메시지 : "+e);
		}
		return mav;
	}
	
	//이벤트로그 상세 조회
	@RequestMapping(value="/eventLog/eventLogDetail.do")
	public @ResponseBody ModelAndView eventLogDetail( @ModelAttribute("eventLogVO") EventLogVO evo,HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.회원정보 조회 목록!!!!!!!!!!!!!!!!");
		
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		
		ModelAndView mav = new ModelAndView("jsonView");
		EventLogVO evoDetail= null;
		try {
			evoDetail = eventLogService.selectEventLog(evo);
			logger.debug("▶▶▶▶▶▶▶.시험코드 결과값들:"+evoDetail);
			
			mav.addObject("data", evoDetail);
			mav.setViewName(url);
		} catch (Exception e) {
			logger.debug(""+e);
			mav.addObject("msg","에러가 발생했습니다.");
		}
		return mav;
	}
	
	//tcms 로그 목록 조회
	@RequestMapping(value="/eventLog/tcmsRcvList.ajax")
	public @ResponseBody ModelAndView tcmsRcvList(  
			 @RequestParam(required=false, value="sDate")String sDate
			,@RequestParam(required=false, value="eDate")String eDate
			,@RequestParam(required=false, value="tagId")String tagId
			)throws Exception{
		logger.debug("▶▶▶▶▶▶▶.tcms 조회 목록!!!!!!!!!!!!!!!! : "+sDate);
		
		stcTcmsList = new ArrayList<TcmsRcvVO>();
		ModelAndView mav = new ModelAndView("jsonView");
		List<TcmsRcvVO> comuList= null;
		try {
			comuList = eventLogService.selectTcmsRcvList(sDate,eDate,tagId);
			if(comuList.size()!=0) {
				logger.debug("▶▶▶▶▶▶▶.통신장애 결과값들:"+comuList.get(0));
			}
			stcTcmsList = comuList;
			mav.addObject("data", comuList);
		} catch (Exception e) {
			logger.debug("tcms 에러메시지 : "+e);
		}
		return mav;
	}
	
	//hse 로그 목록 조회
	@RequestMapping(value="/eventLog/hseSndList.ajax")
	public @ResponseBody ModelAndView hseSndList( 
			@RequestParam(required=false, value="sDate")String sDate
			,@RequestParam(required=false, value="eDate")String eDate
			,@RequestParam(required=false, value="tagId")String tagId
		)throws Exception{
		logger.debug("▶▶▶▶▶▶▶.hse 조회 목록!!!!!!!!!!!!!!!!");
		stcHseList = new ArrayList<HseSndVO>();
		ModelAndView mav = new ModelAndView("jsonView");
		List<HseSndVO> comuList= null;
		try {
			comuList = eventLogService.selectHseSndList(sDate,eDate,tagId);
			if(comuList.size()!=0) {
				logger.debug("▶▶▶▶▶▶▶.통신장애 결과값들:"+comuList.get(0));
			}
			stcHseList = comuList;
			mav.addObject("data", comuList);
		} catch (Exception e) {
			logger.debug("tcms 에러메시지 : "+e);
		}
		return mav;
	}

	//tcms,hse 로그데이터 다운로드
	@RequestMapping(value = "/eventLog/download/*.ajax", method = RequestMethod.POST)
	public void slip(Map<String, Object> model, XSSFWorkbook workbook, 
			HttpServletRequest req, HttpServletResponse res)
            throws Exception {
		logger.debug("로그 엑셀 다운로드");
		url = req.getRequestURI().substring(req.getContextPath().length()).split(".ajax")[0].split("/")[3];
		
		//일월연계 분기
		String title=url.split("_")[0];
		//받아온 일시
		String sndDt=url.split("_")[1];
		//다운로드 파일명
		String chartTitle="";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String nowDt = sdf.format(date);
		
		
		if(title.equals("eventLog")) {
			chartTitle="이벤트 로그";
		}else if(title.equals("tcms")) {
			chartTitle="TCMS 수신 로그";
		}else {
			chartTitle="HSE 송신 로그";
		}
		
		req.setCharacterEncoding("UTF-8");
		
		XSSFSheet sheet1 = workbook.createSheet("image_sheet");
		
		logger.debug("▶▶▶▶▶▶▶.web으로부터 차트이미지 전송받음 : "+title);
	    try {
            
            XSSFRow objRow = null;
            XSSFCell objCell = null;
            
            // Cell style(제목 부분)
            XSSFCellStyle ctStyle = workbook.createCellStyle();
	        //정렬
	        ctStyle.setAlignment(CellStyle.ALIGN_CENTER); //가운데 정렬
	        ctStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //높이 가운데 정렬
	        XSSFFont ctFont = workbook.createFont();
	        ctFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	        ctFont.setFontHeight((short)(18*20));//18포인트
            ctStyle.setFont(ctFont);
            
         // Cell style(제목 부분-일시)
            XSSFCellStyle dtStyle = workbook.createCellStyle();
	        //정렬
	        dtStyle.setAlignment(CellStyle.ALIGN_CENTER); //가운데 정렬
	        dtStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //높이 가운데 정렬
	        XSSFFont dtFont = workbook.createFont();
	        dtFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	        dtFont.setFontHeight((short)(14*20));//18포인트
            dtStyle.setFont(dtFont);
            
            // Cell style(th 부분)
            XSSFCellStyle titleStyle = workbook.createCellStyle();
	        //정렬
	        titleStyle.setAlignment(CellStyle.ALIGN_CENTER); //가운데 정렬
	        titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //높이 가운데 정렬
	        //배경색
	        titleStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
	        titleStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	        //테두리 선 (우,좌,위,아래)
	        titleStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
	        titleStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
	        titleStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
	        titleStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
             
            // Cell style(데이터 내용 tb)
            XSSFCellStyle contentStyle = workbook.createCellStyle();
            contentStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.index);
            contentStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            contentStyle.setBorderTop(CellStyle.BORDER_THIN);
            contentStyle.setBorderBottom(CellStyle.BORDER_THIN);
            contentStyle.setBorderLeft(CellStyle.BORDER_THIN);
            contentStyle.setBorderRight(CellStyle.BORDER_THIN);
            contentStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);

            //셀 병합
            //차트 제목
            sheet1.addMergedRegion(new CellRangeAddress(1,1,7,10));//행시작, 행종료, 열시작, 열종료
            sheet1.addMergedRegion(new CellRangeAddress(1,1,11,13));//행시작, 행종료, 열시작, 열종료
            //송신시간
            sheet1.addMergedRegion(new CellRangeAddress(3,3,1,2));
            //수신시간
            sheet1.addMergedRegion(new CellRangeAddress(3,3,5,6));
            
            //차트제목
            objRow = sheet1.createRow(1);
            objCell = objRow.createCell(7);
            objCell.setCellStyle(ctStyle);
            objCell.setCellValue(chartTitle);
            //연월일시
            objCell = objRow.createCell(11);
            objCell.setCellStyle(dtStyle);
            objCell.setCellValue(sndDt);
            
            if(title.equals("tcms")) {
            	
            	/*데이터 부분 내용 삽입*/
                objRow = sheet1.createRow(3);
                
                objCell = objRow.createCell(1);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("송신시간");
                objCell = objRow.createCell(2);
                objCell.setCellStyle(titleStyle);
                
                objCell = objRow.createCell(3);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("열차번호");
                
                objCell = objRow.createCell(4);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("운행번호");
                
                objCell = objRow.createCell(5);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("수신시간");
                objCell = objRow.createCell(6);
                objCell.setCellStyle(titleStyle);
                
                objCell = objRow.createCell(7);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("열차타입");
                
                objCell = objRow.createCell(8);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("열차방향");
                
                objCell = objRow.createCell(9);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("차량무게1");
                objCell = objRow.createCell(10);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("차량무게2");
                objCell = objRow.createCell(11);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("차량무게3");
                objCell = objRow.createCell(12);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("차량무게4");
                objCell = objRow.createCell(13);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("차량무게5");
                objCell = objRow.createCell(14);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("차량무게6");
                objCell = objRow.createCell(15);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("차량무게7");
                objCell = objRow.createCell(16);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("차량무게8");
            	
            	//통계 vo 리스트 데이터 삽입
                for (int i = 0; i < stcTcmsList.size(); i++) {
                	//셀 병합
                	sheet1.addMergedRegion(new CellRangeAddress(i+4,i+4,1,2));
                	sheet1.addMergedRegion(new CellRangeAddress(i+4,i+4,5,6));
                	//평균 혼잡도(kpa)
                    
                    objRow = sheet1.createRow(i+4);
                    //수신시간
                    objCell = objRow.createCell(1);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcTcmsList.get(i).getSndDt());
                    objCell = objRow.createCell(2);
                    objCell.setCellStyle(contentStyle);
                    //열차번로
                    objCell = objRow.createCell(3);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcTcmsList.get(i).getCarNum());
                    //운행번호
                    objCell = objRow.createCell(4);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcTcmsList.get(i).getTrainNum());
                    //수신시간
                    objCell = objRow.createCell(5);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcTcmsList.get(i).getRcvDt());
                    objCell = objRow.createCell(6);
                    objCell.setCellStyle(contentStyle);
                    
                    //엵차타입
                    objCell = objRow.createCell(7);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcTcmsList.get(i).getTrainType());
                    //열차방향
                    objCell = objRow.createCell(8);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcTcmsList.get(i).getTrainDirect());
                    //차량무게 1~8
                    objCell = objRow.createCell(9);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcTcmsList.get(i).getCarWgt1());
                    objCell = objRow.createCell(10);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcTcmsList.get(i).getCarWgt2());
                    objCell = objRow.createCell(11);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcTcmsList.get(i).getCarWgt3());
                    objCell = objRow.createCell(12);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcTcmsList.get(i).getCarWgt4());
                    objCell = objRow.createCell(13);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcTcmsList.get(i).getCarWgt5());
                    objCell = objRow.createCell(14);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcTcmsList.get(i).getCarWgt6());
                    objCell = objRow.createCell(15);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcTcmsList.get(i).getCarWgt7());
                    objCell = objRow.createCell(16);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcTcmsList.get(i).getCarWgt8());
                }
                
                
            }else {//hse

            	/*데이터 부분 내용 삽입*/
                objRow = sheet1.createRow(3);
                
                objCell = objRow.createCell(1);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("송신시간");
                objCell = objRow.createCell(2);
                objCell.setCellStyle(titleStyle);
                
                objCell = objRow.createCell(3);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("열차번호");
                
                objCell = objRow.createCell(4);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("운행번호");
                
                objCell = objRow.createCell(5);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("수신시간");
                objCell = objRow.createCell(6);
                objCell.setCellStyle(titleStyle);
                
                objCell = objRow.createCell(7);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("열차타입");
                
                objCell = objRow.createCell(8);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("열차방향");
                
                objCell = objRow.createCell(9);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("평균혼잡도");
                
                objCell = objRow.createCell(10);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("차량무게1");
                objCell = objRow.createCell(11);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("차량무게2");
                objCell = objRow.createCell(12);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("차량무게3");
                objCell = objRow.createCell(13);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("차량무게4");
                objCell = objRow.createCell(14);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("차량무게5");
                objCell = objRow.createCell(15);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("차량무게6");
                objCell = objRow.createCell(16);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("차량무게7");
                objCell = objRow.createCell(17);
                objCell.setCellStyle(titleStyle);
                objCell.setCellValue("차량무게8");
            	
            	//통계 vo 리스트 데이터 삽입
                for (int i = 0; i < stcHseList.size(); i++) {
                	//셀 병합
                	sheet1.addMergedRegion(new CellRangeAddress(i+4,i+4,1,2));
                	sheet1.addMergedRegion(new CellRangeAddress(i+4,i+4,5,6));
                	//평균 혼잡도(kpa)
                    
                    objRow = sheet1.createRow(i+4);
                    //수신시간
                    objCell = objRow.createCell(1);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcHseList.get(i).getSndDt());
                    objCell = objRow.createCell(2);
                    objCell.setCellStyle(contentStyle);
                    //열차번로
                    objCell = objRow.createCell(3);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcHseList.get(i).getCarNum());
                    //운행번호
                    objCell = objRow.createCell(4);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcHseList.get(i).getTrainNum());
                    //수신시간
                    objCell = objRow.createCell(5);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcHseList.get(i).getRcvDt());
                    objCell = objRow.createCell(6);
                    objCell.setCellStyle(contentStyle);
                    
                    //엵차타입
                    objCell = objRow.createCell(7);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcHseList.get(i).getTrainType());
                    //열차방향
                    objCell = objRow.createCell(8);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcHseList.get(i).getTrainDirect());
                    //차량무게 1~8
                    objCell = objRow.createCell(9);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcHseList.get(i).getCarWgt1());
                    //평균혼잡도
                    objCell = objRow.createCell(10);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcHseList.get(i).getAvgCrwd());
                    
                    objCell = objRow.createCell(11);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcHseList.get(i).getCarWgt2());
                    objCell = objRow.createCell(12);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcHseList.get(i).getCarWgt3());
                    objCell = objRow.createCell(13);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcHseList.get(i).getCarWgt4());
                    objCell = objRow.createCell(14);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcHseList.get(i).getCarWgt5());
                    objCell = objRow.createCell(15);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcHseList.get(i).getCarWgt6());
                    objCell = objRow.createCell(16);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcHseList.get(i).getCarWgt7());
                    objCell = objRow.createCell(17);
                    objCell.setCellStyle(contentStyle);
                    objCell.setCellValue(stcHseList.get(i).getCarWgt8());
                }
                
            }
	    }catch(Exception e) {
	        e.getStackTrace();
	    }
	    
	 // 파일명 설정 ------------------------------------------///
        String fileName = "download";
        if (model.get("fileName") != null && !((String)model.get("fileName")).equals("")) {
            fileName = (String)model.get("fileName");
        }
        
        
        // 브라우저에 따른 파일명 인코딩
        String userAgent = req.getHeader("User-Agent");
        if (userAgent.indexOf("MSIE") > -1) {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        }
        
        //들어온 request에 따른(일 월 연 계) 파일명 분기처리
        
        
        //헤더정보에 파일명 설정
        /*if(title.equals("day")) {
        	res.setHeader("Content-Disposition", "attachment; filename=일일 통계.xlsx"); 
		}else if(title.equals("month")) {
			res.setHeader("Content-Disposition", "attachment; filename=월별 통계.xlsx"); 
		}else if(title.equals("year")) {
			res.setHeader("Content-Disposition", "attachment; filename=연도별 통계.xlsx"); 
		}else {
			res.setHeader("Content-Disposition", "attachment; filename=계절별 통계.xlsx"); 
		}*/
        res.setHeader("Content-Disposition", "attachment; filename="+title+"_"+nowDt+".xlsx"); 
        res.setHeader("Content-Description", "JSP Generated Data"); 
        res.setContentType("application/vnd.ms-excel"); 
        
        ServletOutputStream out= res.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close(); 
	}
	
}
