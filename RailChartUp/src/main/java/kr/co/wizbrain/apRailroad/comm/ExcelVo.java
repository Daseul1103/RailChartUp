package kr.co.wizbrain.apRailroad.comm;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFCreationHelper;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.util.IOUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class ExcelVo {
	// Excel 만들기.

/*
	public static void main(String[] args) {
		String chartType = "bar";
		ArrayList<CaseInsensitiveMap> alPictureInfo = new ArrayList<CaseInsensitiveMap>();
		ExcelVo excelAndChart = new ExcelVo();
		
		FileOutputStream stream			= null;
		try{
			stream								= new FileOutputStream("D:/dataTitle.xls");
			HSSFWorkbook workBook 		= new HSSFWorkbook();
			HSSFRow row						= null;
			HSSFSheet sheet					= workBook.createSheet("시트이름");
			//HSSFCellStyle headCellStyle 	= excelAndChart.headCellStyle(workBook);
			//HSSFCellStyle bodyCellStyle 	= excelAndChart.bodyCellStyle(workBook);
			
			row 	= sheet.createRow(0);
			row.createCell(0).setCellValue("번호");
			row.createCell(1).setCellValue("제목");
			row.createCell(2).setCellValue("답변1");
			row.createCell(3).setCellValue("답변2");
			row.createCell(4).setCellValue("답변3");
			row.createCell(5).setCellValue("답변4");
			
			ArrayList<String> chartItem 		= new ArrayList<String>();
			ArrayList<Integer> chartValues 	= new ArrayList<Integer>();
			int chartTotalCnt						= 0;
			String chartTitle						= "테스트 데이터";
			
			row 									= sheet.createRow(1);
			row.createCell(0).setCellValue(1);
			row.createCell(1).setCellValue("테스트 데이터");
			row.createCell(2).setCellValue("응답1(5)");
			chartItem.add("응답1");chartValues.add(5);
			row.createCell(3).setCellValue("응답2(4)");
			chartItem.add("응답2");chartValues.add(4);
			row.createCell(4).setCellValue("응답3(7)");
			chartItem.add("응답3");chartValues.add(7);
			row.createCell(5).setCellValue("응답4(8)");
			chartItem.add("응답4");chartValues.add(8);
			
			HSSFCell textCell = row.createCell(넘버);
			textCell.setCellStyle(textCellStyle);//셀의 스타일을 자동 개행이 가능한 스타일로 변경
			textCell.setCellType(HSSFCell.CELL_TYPE_STRING);//개행 문자 적용
			textCell.setCellValue(cellOutString);
			 * 
			
			
			int i=0;
			int additionNum = 1;
			chartTotalCnt = 24;
			String imageFullPath = "D:/testImg.jpeg";
			
			JFreeChart chart 			= null;
			if("bar".equals(chartType)){
				chart 			= excelAndChart.getBarChart(chartItem, chartValues, chartTotalCnt, chartTitle);
			}else{
				chart 			= excelAndChart.getPieChart(chartItem, chartValues, chartTotalCnt, chartTitle);
			}
			
			excelAndChart.createChartImage(chart, imageFullPath, 500, 300, chartType, chartItem.size());
			
			CaseInsensitiveMap cmPictureInfo = new CaseInsensitiveMap();
			cmPictureInfo.put("imageFullPath", imageFullPath);
			cmPictureInfo.put("loopNum", i);
			cmPictureInfo.put("additionNum", additionNum);
			alPictureInfo.add(cmPictureInfo);
			
			excelAndChart.insertImageIntoExcel(workBook, sheet, imageFullPath, i, additionNum);
			
			for(int x=0; x<6; x++){
				sheet.autoSizeColumn((short)x);
				sheet.setColumnWidth(x, (sheet.getColumnWidth(x))+512 );
			}
			excelAndChart.insertImageIntoExcel(workBook, sheet, alPictureInfo);
			workBook.write(stream);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(stream!=null)try{stream.close();}catch(Exception e){}
		}
	}
	
	public JFreeChart getPieChart(ArrayList<String> titles, ArrayList<Integer> values, int totalCnt, String chartTitle) {
		DefaultPieDataset data 		= new DefaultPieDataset();
		JFreeChart chart 				= null;
		if(titles!=null && titles.size()>0){
			int iLoop = titles.size();
			for(int i=0; i<iLoop; i++){
				data.setValue(titles.get(i)+"("+100*values.get(i)/totalCnt+"%)", 100*values.get(i)/totalCnt);
			}
			chart = ChartFactory.createPieChart(chartTitle, data, true, true, false);
		}
		return chart;
	}
	
	public JFreeChart getBarChart(ArrayList<String> titles, ArrayList<Integer> values, int totalCnt, String chartTitle){
		DefaultCategoryDataset data 		= new DefaultCategoryDataset();
		JFreeChart chart 						= null;
		if(titles!=null && titles.size()>0){
			int iLoop = titles.size();
			for(int i=0; i<iLoop; i++){
				data.addValue(values.get(i), titles.get(i)+" ("+values.get(i)+")", titles.get(i));
			}
			chart = ChartFactory.createBarChart(chartTitle, "", "", data, PlotOrientation.VERTICAL, true,true, false);
		}
		return chart;
	}
	
	public boolean createChartImage(JFreeChart chart, String imageFullPath, int imageWidth, int imageHeight, String chartType, int titlesSize){
		boolean isSucess = true;
		
		if("bar".equals(chartType)){
			chart.getTitle().setFont(new Font("나눔고딕", Font.BOLD, 20));//제목
	        chart.getLegend().setItemFont(new Font("나눔고딕", Font.BOLD, 15));//범례
	        chart.setBackgroundPaint(Color.WHITE);//배경색상
	        
	        CategoryPlot plot = chart.getCategoryPlot();
	        plot.setNoDataMessageFont(new Font("나눔고딕", Font.BOLD, 15));//데이터 없을때 나오는 메시지
	        
	        StandardCategoryItemLabelGenerator stdCateGen = new StandardCategoryItemLabelGenerator();
	        BarRenderer barRender = new BarRenderer();
	        barRender.setItemLabelGenerator(stdCateGen);
	        plot.setRenderer(barRender);
	        plot.setRangeGridlinesVisible(true);
	        plot.setRangeGridlinePaint(Color.GRAY);
	        plot.setBackgroundPaint(Color.WHITE);
	        
	        //Y축 데이터 정수로 출력 하게
	        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	        rangeAxis.setUpperMargin(0.40);
	        
	        CategoryItemRenderer renderer = plot.getRenderer();
	        for(int i=0; i<titlesSize; i++){
	        	renderer.setSeriesItemLabelsVisible(i, Boolean.TRUE);
	        }
	        
	        CategoryAxis domainAxis = plot.getDomainAxis();
	        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD); 
	        domainAxis.setLabelFont(new Font("나눔고딕", Font.BOLD, 15));//x축 라벨
	        domainAxis.setTickLabelFont(new Font("나눔고딕", Font.BOLD, 15));//x축 도메인
		}else{
			chart.getTitle().setFont(new Font("나눔고딕", Font.BOLD, 20));
			chart.getLegend().setItemFont(new Font("나눔고딕", Font.BOLD, 15));
			chart.setBackgroundPaint(Color.WHITE);
			PiePlot piePlot 					= (PiePlot)chart.getPlot();
		    piePlot.setLabelFont(new Font("나눔고딕", Font.BOLD, 13));
		    piePlot.setBackgroundPaint(Color.WHITE);
		}
	    
	    ChartRenderingInfo info 	= new ChartRenderingInfo(new StandardEntityCollection());
	    try{
	    	ChartUtilities.saveChartAsJPEG(new File(imageFullPath), chart, imageWidth, imageHeight, info);
	    }catch(IOException e){
	    	isSucess = false;
	    }
		
		return isSucess;
	}
	
	public void insertImageIntoExcel(HSSFWorkbook workBook, HSSFSheet sheet, ArrayList<CaseInsensitiveMap> alPictureInfo){
		if(alPictureInfo!=null && alPictureInfo.size()>0){
			int iLoop = alPictureInfo.size();
			for(int i=0; i<iLoop; i++){
				CaseInsensitiveMap cmPictureInfo = alPictureInfo.get(i);
				String imageFullPath 		= (String)cmPictureInfo.get("imageFullPath");
				Integer loopNum 			= (Integer)cmPictureInfo.get("loopNum");
				Integer additionNum 		= (Integer)cmPictureInfo.get("additionNum");
				insertImageIntoExcel(workBook, sheet, imageFullPath, loopNum, additionNum);
			}
		}
	}
	
	public boolean insertImageIntoExcel(HSSFWorkbook workBook, HSSFSheet sheet, String imageFullPath, int i, int additionNum){
		boolean isSuccess = true;
		
		InputStream inputStream = null;
		try{
			inputStream 	= new FileInputStream(imageFullPath);
			byte[] bytes 	= IOUtils.toByteArray(inputStream);
			int pictureIdx 	= workBook.addPicture(bytes, HSSFWorkbook.PICTURE_TYPE_JPEG);
			
			HSSFCreationHelper helper 	= workBook.getCreationHelper();
	        HSSFPatriarch patriarch 		= sheet.createDrawingPatriarch();
	        HSSFClientAnchor anchor 		= helper.createClientAnchor();
	        
	        anchor.setCol1(0);
	        anchor.setRow1(i+additionNum+2);
	        
	        HSSFPicture picture = patriarch.createPicture(anchor, pictureIdx);
	        picture.resize();
		}catch(Exception e){
			isSuccess = false;
		}finally{
			if(inputStream!=null)try{inputStream.close();}catch(Exception e){}
		}
		
		return isSuccess;
	}
	
	public HSSFCellStyle headCellStyle(HSSFWorkbook workBook){
		HSSFCellStyle cellStyle = workBook.createCellStyle();
		
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);         
	    cellStyle.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());   
	    cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);   
	    cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
	    cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
	    cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);   
	    
	    HSSFFont font = workBook.createFont();   
	    font.setBoldweight((short)700);        
	    cellStyle.setFont(font);   
		
		return cellStyle;
	}
	
	public HSSFCellStyle bodyCellStyle(HSSFWorkbook workBook){
		HSSFCellStyle cellStyle = workBook.createCellStyle();
		
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);   
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);   
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);   
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
		
		return cellStyle;
	}
	
	public HSSFCellStyle textCellStyle(HSSFWorkbook workBook){
		HSSFCellStyle cellStyle = workBook.createCellStyle();
		//내용이 길어지면 자동 줄바꿈
		cellStyle.setWrapText(true);
		
		return cellStyle;
	}*/
	
	
}
