package kr.co.wizbrain.apRailroad.statistic.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.wizbrain.apRailroad.comm.SessionListener;
import kr.co.wizbrain.apRailroad.statistic.service.StatisticService;
import kr.co.wizbrain.apRailroad.statistic.vo.StatisticVO;
import kr.co.wizbrain.apRailroad.statistic.vo.StkAreaVO;
import kr.co.wizbrain.apRailroad.terminal.vo.TerminalVO;
import kr.co.wizbrain.apRailroad.statistic.vo.LogDataVO;
import kr.co.wizbrain.apRailroad.statistic.vo.MainStVo;
import kr.co.wizbrain.apRailroad.statistic.vo.MainYTVo;
import kr.co.wizbrain.apRailroad.statistic.vo.ScatterVO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.apache.commons.codec.binary.Base64;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder.BorderSide;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;


/**
 * statistic 컨트롤러 클래스
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
public class StatisticController {
	
	public static final Logger logger = LoggerFactory.getLogger(StatisticController.class);
	
	@Resource(name="statisticService")
	private StatisticService statisticService;
	public String url="";
	
	List<StatisticVO> svoList =null;
	
	//주소에 맞게 매핑
	@RequestMapping(value="/statistic/**/*.do")
	public String statisticUrlMapping(HttpServletRequest request) throws Exception{
		url = request.getRequestURI().substring(request.getContextPath().length()).split(".do")[0];
		logger.debug("▶▶▶▶▶▶▶.보내려는 url : "+url);
		try {
			HttpSession chkSession = request.getSession(false);
			logger.debug("▶▶▶▶▶▶▶.체크세션 : "+chkSession);
			logger.debug("▶▶▶▶▶▶▶.체크세션 id: "+chkSession.getId());
			// 이미 접속한 아이디인지 체크
            // 현재 접속자들 보여주기
            SessionListener.getInstance().printloginUsers();
		} catch (Exception e) {
			System.out.println("에러메시지"+e.getMessage());
		}
		return url;
	}
	
	//전/금일 현시간 주의혼잡(메인화면)
	@RequestMapping(value="/statistic/mainYesToday.ajax")
	public @ResponseBody ModelAndView mainYesToday( @RequestParam(required=false, value="timeRange", defaultValue= "1")String timeRange) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.전/금일 현시간 주의혼잡(메인화면)");
		ModelAndView mav = new ModelAndView("jsonView");
		MainYTVo stList= null;
		MainStVo svo = new MainStVo();
		try {
			stList = statisticService.mainYesToday(timeRange);
			logger.debug("▶▶▶▶▶▶▶.시험코드 결과값들:"+stList);
			
			mav.addObject("stList", stList);
		} catch (Exception e) {
			logger.debug("▶▶▶▶▶▶▶.에러메시지 : "+e);
		}
		return mav;
	}

	//단일편성 테이블 목록
	@RequestMapping(value="/statistic/trainTable.ajax")
	public @ResponseBody ModelAndView trainTable(@RequestParam(required=false, value="sDate")String sDate, HttpServletRequest request) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.전/금일 현시간 주의혼잡(메인화면)");
		ModelAndView mav = new ModelAndView("jsonView");
		List<TerminalVO> tbList= null;
		try {
			tbList = statisticService.trainTable(sDate);
			logger.debug("▶▶▶▶▶▶▶.시험코드 결과값들:"+tbList);
			
			mav.addObject("tbList", tbList);
		} catch (Exception e) {
			logger.debug("▶▶▶▶▶▶▶.에러메시지 : "+e);
		}
		return mav;
	}
	
	//일일 통계
	@RequestMapping(value="/statistic/dayTermByRange.ajax")
	public @ResponseBody ModelAndView dayTermByRange( @RequestParam(required=false, value="sDate")String sDate,@RequestParam(required=false, value="trainNum")String trainNum) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.일일통계");
		ModelAndView mav = new ModelAndView("jsonView");
		svoList = new ArrayList<StatisticVO>();
		try {
			svoList = statisticService.dayTermByRange(sDate,trainNum);
			logger.debug("▶▶▶▶▶▶▶.시험코드 결과값들:"+svoList);
			
			mav.addObject("data", svoList);
		} catch (Exception e) {
			logger.debug("▶▶▶▶▶▶▶.에러메시지 : "+e);
		}
		return mav;
	}
	
	//월간(일별) 통계
	@RequestMapping(value="/statistic/mdTermByRange.ajax")
	public @ResponseBody ModelAndView mdTermByRange( @RequestParam(required=false, value="sDate")String sDate) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.월간(일별) 통계");
		ModelAndView mav = new ModelAndView("jsonView");
		svoList = new ArrayList<StatisticVO>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		try {
			Date date1 = new Date();
			String nowDt1 = sdf.format(date1);
			logger.debug("▶▶▶▶▶▶▶.월별통계 조회 전 시간:"+nowDt1);
			
			svoList = statisticService.mdTermByRange(sDate);
			logger.debug("▶▶▶▶▶▶▶.시험코드 결과값들:"+svoList);
			mav.addObject("data", svoList);
			
		} catch (Exception e) {
			logger.debug("▶▶▶▶▶▶▶.에러메시지 : "+e);
		}
		Date date2 = new Date();
		String nowDt2 = sdf.format(date2);
		logger.debug("▶▶▶▶▶▶▶.월별통계 조회 후 시간:"+nowDt2);
		return mav;
	}

	//월별 통계
	@RequestMapping(value="/statistic/monTermByRange.ajax")
	public @ResponseBody ModelAndView monTermByRange( @RequestParam(required=false, value="sDate")String sDate) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.월별통계");
		ModelAndView mav = new ModelAndView("jsonView");
		svoList = new ArrayList<StatisticVO>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date date1 = new Date();
		String nowDt1 = sdf.format(date1);
		logger.debug("▶▶▶▶▶▶▶.월별통계 조회 전 시간:"+nowDt1);
		try {
			svoList = statisticService.monTermByRange(sDate);
			logger.debug("▶▶▶▶▶▶▶.시험코드 결과값들:"+svoList);
			mav.addObject("data", svoList);
		} catch (Exception e) {
			logger.debug("▶▶▶▶▶▶▶.에러메시지 : "+e);
		}
		Date date2 = new Date();
		String nowDt2 = sdf.format(date2);
		logger.debug("▶▶▶▶▶▶▶.월별통계 조회 후 시간:"+nowDt2);
		return mav;
	}

	//연도별 통계
	@RequestMapping(value="/statistic/yearTermByRange.ajax")
	public @ResponseBody ModelAndView yearTermByRange( 
	  @RequestParam(required=false, value="sDate")String sDate, 
	  @RequestParam(required=false, value="eDate")String eDate) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.연도별통계");
		ModelAndView mav = new ModelAndView("jsonView");
		svoList = new ArrayList<StatisticVO>();
		try {
			svoList = statisticService.yearTermByRange(sDate,eDate);
			logger.debug("▶▶▶▶▶▶▶.시험코드 결과값들:"+svoList);
			
			mav.addObject("data", svoList);
		} catch (Exception e) {
			logger.debug("▶▶▶▶▶▶▶.에러메시지 : "+e);
		}
		return mav;
	}

	//계절별 통계
	@RequestMapping(value="/statistic/seaTermByRange.ajax")
	public @ResponseBody ModelAndView seaTermByRange( @RequestParam(required=false, value="sDate")String sDate) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.계절별통계");
		ModelAndView mav = new ModelAndView("jsonView");
		svoList = new ArrayList<StatisticVO>();
		try {
			svoList = statisticService.seaTermByRange(sDate);
			logger.debug("▶▶▶▶▶▶▶.시험코드 결과값들:"+svoList);
			
			mav.addObject("data", svoList);
		} catch (Exception e) {
			logger.debug("▶▶▶▶▶▶▶.에러메시지 : "+e);
		}
		return mav;
	}
	

	//그래프 캡쳐(div) 및 통계 데이터 다운로드
	@RequestMapping(value = "/capture/*.do", method = RequestMethod.POST)
	public void slip(Map<String, Object> model, XSSFWorkbook workbook, 
			HttpServletRequest req, HttpServletResponse res
			//,@RequestParam(required=false, value="imgVal")String imgVal
			)
            throws Exception {
		
		url = req.getRequestURI().substring(req.getContextPath().length()).split(".do")[0].split("/")[2];
		
		//일월연계 분기
		String title=url.split("_")[0];
		//받아온 일시
		String sndDt=url.split("_")[1];
		//다운로드 파일명
		String chartTitle="";
		//x축 항목명
		String xAxisC ="";
		//y축 항목명
		String yAxisC ="각\r\n량\r\n별\r\n\r\n혼\r\n잡\r\n도\r\n차\r\n량\r\n의\r\n\r\n갯\r\n수\r\n\r\n(단위:천)";
		
		String xDay="시간";
		String y1="차량 수";
		String y2="무게";
		String y3="데이터";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String nowDt = sdf.format(date);
		
		
		if(title.equals("DAY")) {
			chartTitle="일일 통계";
			xAxisC="기준(출근/주간/퇴근/야간)";
		}else if(title.equals("MONTH")) {
			chartTitle="월별 통계";
			xAxisC="기준(월)";
			sndDt=sndDt.replace(")", "년)");
		}else if(title.equals("YEAR")) {
			chartTitle="연도별 통계";
			xAxisC="기준(연도)";
			sndDt=sndDt.replace("~", " ~ ");
		}else if(title.equals("SEA")){
			chartTitle="계절별 통계";
			xAxisC="기준(계절)";
			sndDt=sndDt.replace(")", "년)");
		}else {
			chartTitle="월간(일별) 통계";
			xAxisC="기준(일)";
			sndDt=sndDt.replace(")", "월)");
		}
			
		res.setCharacterEncoding("UTF-8");
		
		XSSFSheet sheet1 = workbook.createSheet("image_sheet");
		
		logger.debug("▶▶▶▶▶▶▶.web으로부터 차트이미지 전송받음 : "+title);
	    try {
	    	//이미지 시작
	    	//데이터링크를 스트링으로
	    	//일일통계 ( 그림 4개) 의 경우
	    	if (req.getParameter("img_val")==null) {
				for (int i = 0; i < 4; i++) {
					String idx = Integer.toString(i);
					String paramName = "img_val"+idx;
					String data0 = req.getParameter(paramName);
					//String data = imgVal;
			        //데이터 헤더부분 자름
			        data0 = data0.replaceAll("data:image/png;base64,", "");
			        //바이트로 그림데이터 치환
			        byte[] bytes = Base64.decodeBase64(data0.getBytes());
			        /////////////////////
			        
			        int pictureIdx = workbook.addPicture(bytes, XSSFWorkbook.PICTURE_TYPE_JPEG);
			        XSSFCreationHelper helper = workbook.getCreationHelper();
		            XSSFDrawing drawing = sheet1.createDrawingPatriarch();
		            XSSFClientAnchor anchor = helper.createClientAnchor();
		            
		         // 이미지를 출력할 CELL 위치 선정
		            if (i==0) {
		            	anchor.setCol1(2);//오른쪽으로 3칸 시작점
		                anchor.setRow1(3);//아래로 3칸 시작점
					} else if(i==1){
						anchor.setCol1(10);//오른쪽으로 11칸 시작점
		                anchor.setRow1(3);//아래로 3칸 시작점
					} else if(i==2){
						anchor.setCol1(2);//오른쪽으로 4칸 시작점
		                anchor.setRow1(12);//아래로 3칸 시작점
					}else {
						anchor.setCol1(10);//오른쪽으로 4칸 시작점
		                anchor.setRow1(12);//아래로 3칸 시작점
					}
	            	//anchor.setCol1(3);//오른쪽으로 4칸 시작점
	                //anchor.setRow1(2);//아래로 3칸 시작점
		            // 이미지 그리기
		            XSSFPicture pict = drawing.createPicture(anchor, pictureIdx);
		            // 이미지 사이즈 비율 설정
		            pict.resize(6,7);//행 13칸,열 15칸
		            
				}
	            
			} else {
				String data = req.getParameter("img_val");
		    	//String data = imgVal;
		        //데이터 헤더부분 자름
		        data = data.replaceAll("data:image/png;base64,", "");
		        //바이트로 그림데이터 치환
		        byte[] bytes = Base64.decodeBase64(data.getBytes());
		        /////////////////////
		        
		        int pictureIdx = workbook.addPicture(bytes, XSSFWorkbook.PICTURE_TYPE_JPEG);
		        XSSFCreationHelper helper = workbook.getCreationHelper();
	            XSSFDrawing drawing = sheet1.createDrawingPatriarch();
	            XSSFClientAnchor anchor = helper.createClientAnchor();
	            
	            // 이미지를 출력할 CELL 위치 선정
	            if(title.equals("MONTH") || title.equals("DATE")) {//월일 경우
	            	anchor.setCol1(2);//오른쪽으로 3칸 시작점
	                anchor.setRow1(3);//아래로 4칸 시작점
	            }else {
	            	anchor.setCol1(3);//오른쪽으로 4칸 시작점
	                anchor.setRow1(2);//아래로 3칸 시작점
	            }
	            
	            // 이미지 그리기
	            XSSFPicture pict = drawing.createPicture(anchor, pictureIdx);
	            
	            // 이미지 사이즈 비율 설정
	            if(title.equals("MONTH") || title.equals("DATE")) {//월일 경우
	            	pict.resize(16,13);//행 13칸,열 15칸
	            }else {
	            	pict.resize(13,15);//행 13칸,열 15칸
	            }
	            
			}
            //이미지 끝
            
            //표 시작
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
            
            //일일통계 각 항목별 소제목
            XSSFCellStyle dayTt = workbook.createCellStyle();
	        //정렬
            dayTt.setAlignment(CellStyle.ALIGN_CENTER); //가운데 정렬
            dayTt.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //높이 가운데 정렬
	        XSSFFont daytFont = workbook.createFont();
	        daytFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	        dayTt.setFont(daytFont);
            // Cell style(x축 부분)
            
            XSSFCellStyle xStyle = workbook.createCellStyle();
            //정렬
            xStyle.setAlignment(CellStyle.ALIGN_CENTER); //가운데 정렬
            xStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //높이 가운데 정렬
            XSSFFont xFont = workbook.createFont();
            xFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
            xFont.setFontHeight((short)(12*20));//12포인트
            xStyle.setFont(xFont);
            
            // Cell style(y축 부분)
            XSSFCellStyle yStyle = workbook.createCellStyle();
            //정렬
            yStyle.setAlignment(CellStyle.ALIGN_CENTER); //가운데 정렬
            yStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //높이 가운데 정렬
            yStyle.setWrapText(true);//개행문자 \r\n을 사용하여 줄바꿈 허용
            XSSFFont yFont = workbook.createFont();
            yFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
            yFont.setFontHeight((short)(12*20));//12포인트
            yStyle.setFont(yFont);
            
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
            //XSSFFont whiteFont = workbook.createFont();
            //whiteFont.setColor(IndexedColors.WHITE.getIndex());
            //contentStyle.setFont(whiteFont);

            //셀 병합
            //차트 제목
            sheet1.addMergedRegion(new CellRangeAddress(1,1,7,10));//행시작, 행종료, 열시작, 열종료
            sheet1.addMergedRegion(new CellRangeAddress(1,1,11,12));//행시작, 행종료, 열시작, 열종료
            if(!(title.equals("DAY"))) {
            	//x축
                sheet1.addMergedRegion(new CellRangeAddress(17,17,8,10));
                //y축
                if(title.equals("MONTH") || title.equals("DATE")) {//월일 경우
                	sheet1.addMergedRegion(new CellRangeAddress(2,16,1,1));
                }else {
                	sheet1.addMergedRegion(new CellRangeAddress(2,16,2,2));
                }
            }else {
            	sheet1.addMergedRegion(new CellRangeAddress(2,2,3,6));
            	sheet1.addMergedRegion(new CellRangeAddress(2,2,11,14));
            	sheet1.addMergedRegion(new CellRangeAddress(11,11,3,6));
            	
            	
            	
            	objRow = sheet1.createRow(2);
            	objCell = objRow.createCell(3);
            	objCell.setCellStyle(dayTt);
                objCell.setCellValue("시간대별 혼잡도");
                objCell = objRow.createCell(11);
                objCell.setCellStyle(dayTt);
                objCell.setCellValue("량별 무게값 분포도");
                
            	objRow = sheet1.createRow(11);
                objCell = objRow.createCell(3);
                objCell.setCellStyle(dayTt);
                objCell.setCellValue("누적 데이터 집계");

            }
            
            
            //기준
            sheet1.addMergedRegion(new CellRangeAddress(20,21,1,1));
            //혼잡도 변화 건수(량당)
            sheet1.addMergedRegion(new CellRangeAddress(20,20,2,5));
            //평균 혼잡도(kpa)
            sheet1.addMergedRegion(new CellRangeAddress(20,21,6,7));
            //최소
            sheet1.addMergedRegion(new CellRangeAddress(20,20,8,12));
            //최대
            sheet1.addMergedRegion(new CellRangeAddress(20,20,13,17));
            //최소시간
            sheet1.addMergedRegion(new CellRangeAddress(21,21,10,12));
            //최소시간
            sheet1.addMergedRegion(new CellRangeAddress(21,21,15,17));
            
            
            
            /*차트제목 xy축 부분 내용 삽입*/
            //차트제목
            objRow = sheet1.createRow(1);
            objCell = objRow.createCell(7);
            objCell.setCellStyle(ctStyle);
            objCell.setCellValue(chartTitle);
            //연월일시
            objCell = objRow.createCell(11);
            objCell.setCellStyle(dtStyle);
            objCell.setCellValue(sndDt);
            
            //x축
            if(!(title.equals("DAY"))) {
            	objRow = sheet1.createRow(17);
                objCell = objRow.createCell(8);
                objCell.setCellStyle(xStyle);
                objCell.setCellValue(xAxisC);
            }else {
            	
            	objRow = sheet1.createRow(9);
                objCell = objRow.createCell(8);
                objCell.setCellValue("시간");
                
                objCell = objRow.createCell(16);
                objCell.setCellValue("시간");
                
                objRow = sheet1.createRow(18);
                objCell = objRow.createCell(8);
                objCell.setCellValue("시간");
            }
            
            //y축
            if(!(title.equals("DAY"))) {
            	if(title.equals("MONTH") || title.equals("DATE")) {
                	objRow = sheet1.createRow(2);
                    objCell = objRow.createCell(1);
                    objCell.setCellStyle(yStyle);
                    objCell.setCellValue(yAxisC);
                }else {
                	objRow = sheet1.createRow(2);
                    objCell = objRow.createCell(2);
                    objCell.setCellStyle(yStyle);
                    objCell.setCellValue(yAxisC);
                }
            }else {
            	
            	
            	objRow = sheet1.createRow(3);
                objCell = objRow.createCell(1);
                objCell.setCellValue("차량 수");
                
                objCell = objRow.createCell(9);
                objCell.setCellValue("무게");
                
                objRow = sheet1.createRow(12);
                objCell = objRow.createCell(1);
                objCell.setCellValue("데이터량");
            }
            
            /*데이터 부분 내용 삽입*/
            objRow = sheet1.createRow(20);
            
            objCell = objRow.createCell(1);
            objCell.setCellStyle(titleStyle);
            objCell.setCellValue("기준");
            
            objCell = objRow.createCell(2);
            objCell.setCellStyle(titleStyle);
            objCell.setCellValue("혼잡도 변화 건수(량당)");
            objCell = objRow.createCell(3);
            objCell.setCellStyle(titleStyle);
            objCell = objRow.createCell(4);
            objCell.setCellStyle(titleStyle);
            objCell = objRow.createCell(5);
            objCell.setCellStyle(titleStyle);
            
            objCell = objRow.createCell(6);
            objCell.setCellStyle(titleStyle);
            objCell.setCellValue("평균 혼잡도 (KPA)");
            objCell = objRow.createCell(7);
            objCell.setCellStyle(titleStyle);
            
            objCell = objRow.createCell(8);
            objCell.setCellStyle(titleStyle);
            objCell.setCellValue("최소");
            objCell = objRow.createCell(9);
            objCell.setCellStyle(titleStyle);
            objCell = objRow.createCell(10);
            objCell.setCellStyle(titleStyle);
            objCell = objRow.createCell(11);
            objCell.setCellStyle(titleStyle);
            objCell = objRow.createCell(12);
            objCell.setCellStyle(titleStyle);
            
            objCell = objRow.createCell(13);
            objCell.setCellStyle(titleStyle);
            objCell.setCellValue("최대");
            objCell = objRow.createCell(14);
            objCell.setCellStyle(titleStyle);
            objCell = objRow.createCell(15);
            objCell.setCellStyle(titleStyle);
            objCell = objRow.createCell(16);
            objCell.setCellStyle(titleStyle);
            objCell = objRow.createCell(17);
            objCell.setCellStyle(titleStyle);
            
            
            objRow = sheet1.createRow(21);
            
            objCell = objRow.createCell(1);
            objCell.setCellStyle(titleStyle);
            objCell = objRow.createCell(2);
            objCell.setCellStyle(titleStyle);
            objCell.setCellValue("여유");
            
            objCell = objRow.createCell(3);
            objCell.setCellStyle(titleStyle);
            objCell.setCellValue("보통");
            
            objCell = objRow.createCell(4);
            objCell.setCellStyle(titleStyle);
            objCell.setCellValue("주의");
            
            objCell = objRow.createCell(5);
            objCell.setCellStyle(titleStyle);
            objCell.setCellValue("혼잡");
            
            objCell = objRow.createCell(6);
            objCell.setCellStyle(titleStyle);
            objCell = objRow.createCell(7);
            objCell.setCellStyle(titleStyle);
            
            objCell = objRow.createCell(8);
            objCell.setCellStyle(titleStyle);
            objCell.setCellValue("중량");
            
            objCell = objRow.createCell(9);
            objCell.setCellStyle(titleStyle);
            objCell.setCellValue("열번");
            
            objCell = objRow.createCell(10);
            objCell.setCellStyle(titleStyle);
            objCell.setCellValue("시간");
            
            objCell = objRow.createCell(11);
            objCell.setCellStyle(titleStyle);
            objCell = objRow.createCell(12);
            objCell.setCellStyle(titleStyle);
            
            objCell = objRow.createCell(13);
            objCell.setCellStyle(titleStyle);
            objCell.setCellValue("중량");
            
            objCell = objRow.createCell(14);
            objCell.setCellStyle(titleStyle);
            objCell.setCellValue("열번");
            
            objCell = objRow.createCell(15);
            objCell.setCellStyle(titleStyle);
            objCell.setCellValue("시간");
            
            objCell = objRow.createCell(16);
            objCell.setCellStyle(titleStyle);
            objCell = objRow.createCell(17);
            objCell.setCellStyle(titleStyle);
            
            //통계 vo 리스트 데이터 삽입
            for (int i = 0; i < svoList.size(); i++) {
            	//셀 병합
            	//평균 혼잡도(kpa)
                sheet1.addMergedRegion(new CellRangeAddress(i+22,i+22,6,7));
                //최소시간
                sheet1.addMergedRegion(new CellRangeAddress(i+22,i+22,10,12));
                //최소시간
                sheet1.addMergedRegion(new CellRangeAddress(i+22,i+22,15,17));
                
                objRow = sheet1.createRow(i+22);
                
                objCell = objRow.createCell(1);
                objCell.setCellStyle(contentStyle);
                objCell.setCellValue(svoList.get(i).getTitle());
                
                objCell = objRow.createCell(2);
                objCell.setCellStyle(contentStyle);
                objCell.setCellValue(svoList.get(i).getRlxCnt());
                
                objCell = objRow.createCell(3);
                objCell.setCellStyle(contentStyle);
                objCell.setCellValue(svoList.get(i).getUsCnt());
                
                objCell = objRow.createCell(4);
                objCell.setCellStyle(contentStyle);
                objCell.setCellValue(svoList.get(i).getCareCnt());
                
                objCell = objRow.createCell(5);
                objCell.setCellStyle(contentStyle);
                objCell.setCellValue(svoList.get(i).getCwdCnt());
                
                objCell = objRow.createCell(6);
                objCell.setCellStyle(contentStyle);
                objCell.setCellValue(svoList.get(i).getAvgCwd());
                objCell = objRow.createCell(7);
                objCell.setCellStyle(contentStyle);
                
                //최소
                objCell = objRow.createCell(8);
                objCell.setCellStyle(contentStyle);
                objCell.setCellValue(svoList.get(i).getMinWgt());
                
                objCell = objRow.createCell(9);
                objCell.setCellStyle(contentStyle);
                objCell.setCellValue(svoList.get(i).getMinTnum());
                
                objCell = objRow.createCell(10);
                objCell.setCellStyle(contentStyle);
                objCell.setCellValue(svoList.get(i).getMinDt());
                objCell = objRow.createCell(11);
                objCell.setCellStyle(titleStyle);
                objCell = objRow.createCell(12);
                objCell.setCellStyle(titleStyle);
                
                //최대
                objCell = objRow.createCell(13);
                objCell.setCellStyle(contentStyle);
                objCell.setCellValue(svoList.get(i).getMaxWgt());
                
                objCell = objRow.createCell(14);
                objCell.setCellStyle(contentStyle);
                objCell.setCellValue(svoList.get(i).getMaxTnum());
                
                objCell = objRow.createCell(15);
                objCell.setCellStyle(contentStyle);
                objCell.setCellValue(svoList.get(i).getMaxDt());
                
                objCell = objRow.createCell(16);
                objCell.setCellStyle(titleStyle);
                objCell = objRow.createCell(17);
                objCell.setCellStyle(titleStyle);
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

	//로그데이터 관리 목록 화면
	@RequestMapping(value="/statistic/dataLogList/*.do")
	public @ResponseBody ModelAndView dataLogList( @ModelAttribute("LogDataVO") LogDataVO logDataVO,HttpServletRequest req) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.데이터로그 관리 목록");
		String flg = req.getRequestURI().substring(req.getContextPath().length()).split(".do")[0].split("/")[3];
		ModelAndView mav = new ModelAndView("jsonView");
		List<LogDataVO> ldList = new ArrayList<LogDataVO>();
		try {
			if (flg.equals("tcms")) {
				ldList = statisticService.dataLogListTcms();
			} else {
				ldList = statisticService.dataLogListHse();
			}
			logger.debug("▶▶▶▶▶▶▶.시험코드 결과값들:"+ldList);
			mav.addObject("data", ldList);
		} catch (Exception e) {
			logger.debug("▶▶▶▶▶▶▶.에러메시지 : "+e);
		}
		return mav;
	}

	//로그데이터 삭제
	@RequestMapping(value="/statistic/dataLogDelete/*.ajax")
	public @ResponseBody ModelAndView dataLogDelete( @RequestParam(value="idArr[]")List<String> ldArr,HttpServletRequest req) throws Exception{
		
		String flg = req.getRequestURI().substring(req.getContextPath().length()).split(".ajax")[0].split("/")[3];
		logger.debug("▶▶▶▶▶▶▶.로그 정보 삭제!!!!!!!!!!!!!!!! : "+flg);
		
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			
			if (flg.equals("tcms")) {
				statisticService.dataLogDeleteTcms(ldArr);
			} else {
				statisticService.dataLogDeleteHse(ldArr);
			}
			
			
		} catch (Exception e) {
			logger.debug("에러메시지 : "+e);
			mav.addObject("msg","에러가 발생하였습니다");
		}
		return mav;
	}
	
	//산점도 차트 
	@RequestMapping(value="/statistic/scatterChart.ajax")
	public @ResponseBody ModelAndView scatterChart( 
		 @RequestParam(required=false, value="sDate")String sDate
		,@RequestParam(required=false, value="trainNum")String trainNum
		,@RequestParam(required=false, value="carWgt")String carWgt
	) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.산점도 그래프");
		ModelAndView mav = new ModelAndView("jsonView");
		List<ScatterVO> sctList = new ArrayList<ScatterVO>();
		try {
			sctList = statisticService.scatterChart(sDate,trainNum,carWgt);
			logger.debug("▶▶▶▶▶▶▶.시험코드 결과값들:"+sctList);
			
			mav.addObject("data", sctList);
		} catch (Exception e) {
			logger.debug("▶▶▶▶▶▶▶.에러메시지 : "+e);
		}
		return mav;
	}
	
	//누적영역 차트 
	@RequestMapping(value="/statistic/stkAreaChart.ajax")
	public @ResponseBody ModelAndView stkAreaChart( 
		 @RequestParam(required=false, value="sDate")String sDate
		,@RequestParam(required=false, value="trainNum")String trainNum
	) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.누적영역 그래프");
		ModelAndView mav = new ModelAndView("jsonView");
		List<StkAreaVO> sctList = new ArrayList<StkAreaVO>();
		try {
			sctList = statisticService.stkAreaChart(sDate,trainNum);
			logger.debug("▶▶▶▶▶▶▶.시험코드 결과값들:"+sctList);
			
			mav.addObject("data", sctList);
		} catch (Exception e) {
			logger.debug("▶▶▶▶▶▶▶.에러메시지 : "+e);
		}
		return mav;
	}
	//메인 1. 금일 5분전 현재 열차별 여보주혼
	@RequestMapping(value="/statistic/stkBarTod.ajax")
	public @ResponseBody ModelAndView stkBarTod(
			@RequestParam(required=false, value="nowTR")String nowTR
			) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.누적영역 그래프");
		ModelAndView mav = new ModelAndView("jsonView");
		List<StatisticVO> sctList = new ArrayList<StatisticVO>();
		try {
			sctList = statisticService.stkBarTod(nowTR);
			logger.debug("▶▶▶▶▶▶▶.시험코드 결과값들:"+sctList);
			
			mav.addObject("data", sctList);
		} catch (Exception e) {
			logger.debug("▶▶▶▶▶▶▶.에러메시지 : "+e);
		}
		return mav;
	}
	//메인 2. 선택한 열차의 1~6량의 각 량별 실제 혼잡도 수치
	@RequestMapping(value="/statistic/oneTrAllRyang.ajax")
	public @ResponseBody ModelAndView oneTrAllRyang( 
			@RequestParam(required=false, value="cnum")String cnum
			) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.누적영역 그래프");
		ModelAndView mav = new ModelAndView("jsonView");
		List<StatisticVO> sctList = new ArrayList<StatisticVO>();
		try {
			sctList = statisticService.oneTrAllRyang(cnum);
			logger.debug("▶▶▶▶▶▶▶.시험코드 결과값들:"+sctList);
			
			mav.addObject("data", sctList);
		} catch (Exception e) {
			logger.debug("▶▶▶▶▶▶▶.에러메시지 : "+e);
		}
		return mav;
	}
	//메인 3. 전/금일 주혼 누적그래프 비교
	@RequestMapping(value="/statistic/yesTodStkAr.ajax")
	public @ResponseBody ModelAndView yesTodStkAr( 
			) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.누적영역 그래프");
		ModelAndView mav = new ModelAndView("jsonView");
		List<StkAreaVO> sctList = new ArrayList<StkAreaVO>();
		try {
			sctList = statisticService.yesTodStkAr();
			logger.debug("▶▶▶▶▶▶▶.시험코드 결과값들:"+sctList);
			
			mav.addObject("data", sctList);
		} catch (Exception e) {
			logger.debug("▶▶▶▶▶▶▶.에러메시지 : "+e);
		}
		return mav;
	}
	
	//현재 시간대
	@RequestMapping(value="/statistic/whenNowTime.ajax")
	public @ResponseBody ModelAndView whenNowTime( 
			) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.누적영역 그래프");
		ModelAndView mav = new ModelAndView("jsonView");
		String tt = null;
		try {
			tt = statisticService.whenNowTime();
			logger.debug("▶▶▶▶▶▶▶.시험코드 결과값들:"+tt);
			
			mav.addObject("data", tt);
		} catch (Exception e) {
			logger.debug("▶▶▶▶▶▶▶.에러메시지 : "+e);
		}
		return mav;
	}
	
	//현재 시간대
	@RequestMapping(value="/statistic/bunmo.ajax")
	public @ResponseBody ModelAndView bunmo( 
			) throws Exception{
		logger.debug("▶▶▶▶▶▶▶.누적영역 그래프");
		ModelAndView mav = new ModelAndView("jsonView");
		int tt = 0;
		try {
			tt = statisticService.bunmo();
			logger.debug("▶▶▶▶▶▶▶.시험코드 결과값들:"+tt);
			
			mav.addObject("data", tt);
		} catch (Exception e) {
			logger.debug("▶▶▶▶▶▶▶.에러메시지 : "+e);
		}
		return mav;
	}
}
