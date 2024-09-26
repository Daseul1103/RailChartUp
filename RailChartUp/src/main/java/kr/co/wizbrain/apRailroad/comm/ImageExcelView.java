package kr.co.wizbrain.apRailroad.comm;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import org.springframework.web.servlet.view.document.AbstractExcelView;
 
 
public class ImageExcelView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/*@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		 XSSFSheet sheet1 = workbook.createSheet("image_sheet");
		 
	        try {
	            // 이미지 파일 로드
	            InputStream inputStream = new FileInputStream("filepath");
	            byte[] bytes = IOUtils.toByteArray(inputStream);
	            int pictureIdx = workbook.addPicture(bytes, XSSFWorkbook.PICTURE_TYPE_JPEG);
	            inputStream.close();
	            
	            XSSFCreationHelper helper = workbook.getCreationHelper();
	            XSSFDrawing drawing = sheet1.createDrawingPatriarch();
	            XSSFClientAnchor anchor = helper.createClientAnchor();
	            
	            // 이미지를 출력할 CELL 위치 선정
	            anchor.setCol1(idx);
	            anchor.setRow1(rowNum+imageIdx);
	            
	            // 이미지 그리기
	            XSSFPicture pict = drawing.createPicture(anchor, pictureIdx);
	            
	            // 이미지 사이즈 비율 설정
	            pict.resize();
	        } catch (Exception e) {
	        }
	}
 */
    
}

 
