package com.gwssi.common.rodimus.report.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class PoiStyleUtil {
	
	public static HSSFCellStyle getHeaderStyle(HSSFWorkbook wb){
		HSSFFont font = wb.createFont();
	    font.setFontHeightInPoints((short)16);
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    HSSFCellStyle titleStyle = wb.createCellStyle();
	    titleStyle.setFont(font);
	    titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    return titleStyle;
	}
	
	public static HSSFCellStyle getTitleStyle(HSSFWorkbook wb){
		HSSFCellStyle headerStyle = wb.createCellStyle();
	    headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    short BLACK = new HSSFColor.BLACK().getIndex();
	    headerStyle.setBottomBorderColor(BLACK);
	    headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    headerStyle.setLeftBorderColor(BLACK);
	    headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    headerStyle.setRightBorderColor(BLACK);
	    headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    headerStyle.setTopBorderColor(BLACK);
	    HSSFFont headerFont = wb.createFont();
	    headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    headerStyle.setFont(headerFont);
	    headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    return headerStyle;
	}
	
	public static HSSFCellStyle getLeftCellStyle(HSSFWorkbook wb,long level,long exportDataLevel){
		short BLACK = new HSSFColor.BLACK().getIndex();
		HSSFCellStyle leftStyle = wb.createCellStyle();
	    leftStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    leftStyle.setBottomBorderColor(BLACK);
	    leftStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    leftStyle.setLeftBorderColor(BLACK);
	    leftStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    leftStyle.setRightBorderColor(BLACK);
	    leftStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    leftStyle.setTopBorderColor(BLACK);
	    leftStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	    
	    if(level<3 && (exportDataLevel!=1)){
	    	HSSFFont font = wb.createFont();
	    	font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    	leftStyle.setFont(font);
	    }
	    return leftStyle;
	}
	
	public static HSSFCellStyle getRightCellStyle(HSSFWorkbook wb){
		short BLACK = new HSSFColor.BLACK().getIndex();
	    HSSFCellStyle rightStyle = wb.createCellStyle();
	    rightStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    rightStyle.setBottomBorderColor(BLACK);
	    rightStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    rightStyle.setLeftBorderColor(BLACK);
	    rightStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    rightStyle.setRightBorderColor(BLACK);
	    rightStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    rightStyle.setTopBorderColor(BLACK);
	    rightStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
	    return rightStyle;
	}
	

	static short WITHE = new HSSFColor.WHITE().getIndex();
	static short GREY = new HSSFColor.GREY_25_PERCENT().getIndex();
	public static HSSFCellStyle getBackgroundColorByLevel(HSSFCellStyle style,long level,boolean hasChild){
		short bg = WITHE;
		if(level==1 && hasChild){
			bg = GREY;
		}
		style.setFillForegroundColor(bg);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		return style;
	}
}
