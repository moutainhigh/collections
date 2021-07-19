package com.ye.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Data01ExcelUtil {

	public static void createExcell(List thisYear, List lastYear, String month,String fileSavePathWithName) throws Exception {
		    HSSFWorkbook wb = new HSSFWorkbook();  
	        HSSFSheet sheet = wb.createSheet("深圳各区优势产业分布"+month);  
	        HSSFRow row =  null;  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        
	        
	        String[] excelHeader = { "辖区", "行业", "数量", "总数","比重"};  
	        row = sheet.createRow(0);
	        for (int i = 0; i < excelHeader.length; i++) {  
	        	
	            HSSFCell cell = row.createCell(i);  
	            cell.setCellValue(excelHeader[i]);  
	            cell.setCellStyle(style);  
	            sheet.autoSizeColumn(i);  
	            sheet.setColumnWidth(i, 20 * 256);
	            //sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 17 / 10);
	        }  
	    	/*sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));// 第一行数据
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 5, 9));// 第一行数据，显示第二个跨列数据
	        */
	        
	        
	        int rowNum = 2;
	        
	        for (int i = 0; i < thisYear.size(); i++) {  
	            row = sheet.createRow(rowNum);  
	            Map  maps = (Map) thisYear.get(i);  
	            
	            row = sheet.createRow(rowNum);
	            
	            
	            String xiaQu = "";
				if (maps.get("辖区") != null) {
					xiaQu = maps.get("辖区").toString();
				}
				String hangYe = "";
				if (maps.get("行业") != null) {
					hangYe = maps.get("行业").toString();
				}
	          
				String num = "";
				if (maps.get("数量") != null) {
					num = maps.get("数量").toString();
				}
				
				String total = "";
				if (maps.get("总数") != null) {
					total = maps.get("总数").toString();
				}
				/*String biZhong = "";
				if (maps.get("比重") != null) {
					biZhong = maps.get("比重").toString();
				}*/
				DecimalFormat df = new DecimalFormat("#.00");
				Double bilv = Double.valueOf(num) / Double.valueOf(total) * 100;
				
				
				
	            row.createCell(0).setCellValue(xiaQu);  
	            row.createCell(1).setCellValue(hangYe);  
	            row.createCell(2).setCellValue(num);  
	            row.createCell(3).setCellValue(total);  
	            row.createCell(4).setCellValue(df.format(bilv) + "%");  
	            
	            rowNum++;
	        }
	       
	    	FileOutputStream fout = new FileOutputStream(fileSavePathWithName);
	    	wb.write(fout);
	    	wb.close();
			fout.flush();
			fout.close();
	}
}
