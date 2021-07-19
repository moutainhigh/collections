package com.report.util.excell;

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

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.report.util.UUIDUtils;

public class CYRYUtil {

	public static void createExcell(List list, String fileName) throws ParseException, IOException {

		if (list != null && list.size() > 0) {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet();
			sheet.setColumnWidth(0, 5 * 256);
			sheet.setColumnWidth(1, 30 * 256);
			sheet.setColumnWidth(2, 40 * 256);
		/*	sheet.setColumnWidth(3, 20 * 256);
			sheet.setColumnWidth(4, 20 * 256);
			sheet.setColumnWidth(5, 20 * 256);
			sheet.setColumnWidth(6, 40 * 256);
			sheet.setColumnWidth(7, 20 * 256);
*/
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));// 第一行数据

			/*for (int i = 3; i < list.size() + 2; i++) {
				if (i % 3 == 0) {
					// sheet.addMergedRegion(new
					// CellRangeAddress(2,4,0,0));//第一行数据
					sheet.addMergedRegion(new CellRangeAddress((i - 1), (i + 1), 0, 0));// 第一行数据
					sheet.addMergedRegion(new CellRangeAddress((i - 1), (i + 1), 5, 5));// 第一行数据(去年数据)
				}
			}*/

			
			//sheet.addMergedRegion(new CellRangeAddress(12, 12, 0, 2));
			
			
			Row row00 = sheet.createRow(0);
			Cell year = row00.createCell(0);
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor((short) 13);// 设置背景色
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			year.setCellStyle(cellStyle);
			year.setCellValue("数据截止时间：2018年底");
			
			
			
			CellStyle myCenter = workbook.createCellStyle();
			myCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			myCenter.setFillForegroundColor((short) 13);// 设置背景色  
			myCenter.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
			
			
			
			CellStyle myCenter2 = workbook.createCellStyle();
			myCenter2.setAlignment(HSSFCellStyle.ALIGN_CENTER);



			Row row0 = sheet.createRow(1);

			// 今年开始
			CellStyle cellStyle2 = workbook.createCellStyle();
			cellStyle2.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());// 设置背景色
			cellStyle2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			Cell title = row0.createCell(0);
			title.setCellStyle(cellStyle2);
			title.setCellValue("序号");

			Cell title1 = row0.createCell(1);
			title1.setCellStyle(cellStyle2);
			title1.setCellValue("辖区");

			Cell title2 = row0.createCell(2);
			title2.setCellStyle(cellStyle2);
			title2.setCellValue("监管所");

			Cell title3 = row0.createCell(3);
			title3.setCellStyle(cellStyle2);
			title3.setCellValue("业务数据");


			
			int rownum = 2;
			for (int i = 0; i < list.size(); i++) {
				
				Map maps = (Map) list.get(i);
				System.out.println(maps);
				
				Row row2 = sheet.createRow(rownum);
				title2 = row2.createCell(0);
				title2.setCellStyle(myCenter2);
				title2.setCellValue(rownum-1);
			
				title2 = row2.createCell(1);
				String org = "";
				if (maps.get("REGORG_CN") != null) {
					org = maps.get("REGORG_CN").toString();
				}else {
					org = "无分局的商事主体数量";
				}
				title2.setCellStyle(myCenter2);
				title2.setCellValue(org);
				
				
				title2 = row2.createCell(2);
				if (maps.get("DEP_NAME") != null) {
					org = maps.get("DEP_NAME").toString();
				}else {
					org ="该分局下面商事主体无监管所企业数量";
				}
				title2.setCellStyle(myCenter2);
				title2.setCellValue(org);
				
				title2 = row2.createCell(3);
				if (maps.get("COUNT(1)") != null) {
					org = maps.get("COUNT(1)").toString();
				}
				title2.setCellStyle(myCenter2);
				title2.setCellValue(org);
				
				rownum++;
			}
			
			/*
			Row rowxj = sheet.createRow(12);
			Cell xj = rowxj.createCell(0);
		
			xj.setCellStyle(myCenter);
			xj.setCellValue("小计");*/
			
			
			
			
			
		
			
			
			FileOutputStream fout = new FileOutputStream(fileName);
			workbook.write(fout);
			workbook.close();
			fout.flush();
			fout.close();

		}
	}
}
