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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.report.util.UUIDUtils;

public class Data02ExcelUtil {
	
	
	
	public static void createExcell(List firstRows, List list02NeiZiJiDu,List list02NeiZiAll,List list02SiYinJiDu,List list02SiYinAll,List list02WaiZiJiDu,List list02WaiZiAll,String  currentTime, String fileName) throws ParseException, IOException {
		DecimalFormat df = new DecimalFormat("#.00");
		

		if (firstRows != null && firstRows.size() > 0) {

			XSSFWorkbook workbook = new XSSFWorkbook();

			XSSFSheet sheet = workbook.createSheet();
			
			sheet.setColumnWidth(1, 20 * 256);
			sheet.setColumnWidth(2, 20 * 256);
			sheet.setColumnWidth(3, 20 * 256);
			sheet.setColumnWidth(4, 20 * 256);
			sheet.setColumnWidth(5, 20 * 256);
			sheet.setColumnWidth(6, 20 * 256);
			sheet.setColumnWidth(7, 20 * 256);
			sheet.setColumnWidth(8, 20 * 256);
			sheet.setColumnWidth(9, 20 * 256);
			sheet.setColumnWidth(10, 20 * 256);
			sheet.setColumnWidth(11, 20 * 256);
			sheet.setColumnWidth(12, 20 * 256);
			sheet.setColumnWidth(13, 20 * 256);
			
			// CellStyle cellStyle = workbook.createCellStyle();

			// cellStyle.setDataFormat(workbook.createDataFormat().getFormat("yyyy-MM-dd
			// HH:mm:ss"));

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 13));//???
			sheet.addMergedRegion(new CellRangeAddress(15, 15, 0, 13));//??????
			
			sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));// ??????
			sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 1));//??????
			sheet.addMergedRegion(new CellRangeAddress(1, 2, 2, 2));//?????????
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 5));//??????
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 6, 8));//??????
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 9,11));//??????
			sheet.addMergedRegion(new CellRangeAddress(1, 2, 12,12));//??????
			
			
			


			Row row00 = sheet.createRow(0);
			Cell year = row00.createCell(0);
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor((short) 13);// ???????????????
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			year.setCellStyle(cellStyle);
			// year.setCellValue("2018-10-18");
			year.setCellValue("??????1??????" +currentTime+"?????????");

			
			
			
			
			//?????????
			Row rowLeiJiTitle = sheet.createRow(15);
			Cell leiJiyear = rowLeiJiTitle.createCell(0);
			CellStyle leiJiCellStyle = workbook.createCellStyle();
			leiJiCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());// ???????????????
			leiJiCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			leiJiyear.setCellStyle(leiJiCellStyle);
			// year.setCellValue("2018-10-18");
			leiJiyear.setCellValue("?????????" +currentTime+"?????????");
			
			
			
			
			
			
			//?????????
			Row row0 = sheet.createRow(1);

			// ????????????
			CellStyle cellStyle2 = workbook.createCellStyle();
			//cellStyle2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());// ???????????????,//https://blog.csdn.net/z1074907546/article/details/50544178/
		//	cellStyle2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
			Cell title = row0.createCell(0);
			title.setCellStyle(cellStyle2);
			title.setCellValue("??????");
			
			Cell title1 = row0.createCell(1);
			title1.setCellStyle(cellStyle2);
			title1.setCellValue("??????");
			
			
			Cell title2 = row0.createCell(2);
			title2.setCellStyle(cellStyle2);
			title2.setCellValue("?????????");

			Cell titleNeiZi = row0.createCell(3);
			
			cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			titleNeiZi.setCellStyle(cellStyle2);
			titleNeiZi.setCellValue("??????");
			
			
			
			Cell titleSiYin= row0.createCell(6);
			titleSiYin.setCellStyle(cellStyle2);
			titleSiYin.setCellValue("??????");
			
			Cell titleWaiZi= row0.createCell(9);
			titleWaiZi.setCellStyle(cellStyle2);
			titleWaiZi.setCellValue("??????");
			
			Cell titleZongJi = row0.createCell(12);
			titleZongJi.setCellStyle(cellStyle2);
			titleZongJi.setCellValue("??????");
			
			
			Row row2 = sheet.createRow(2);

			
			
			Cell title3 = row2.createCell(3);
			title3.setCellStyle(cellStyle2);
			title3.setCellValue("??????");

			Cell title4 = row2.createCell(4);
			title4.setCellStyle(cellStyle2);
			title4.setCellValue("??????????????????");

			Cell title5 = row2.createCell(5);
			title5.setCellStyle(cellStyle2);
			title5.setCellValue("??????");
			
			
			
			Cell title6 = row2.createCell(6);
			title6.setCellStyle(cellStyle2);
			title6.setCellValue("??????");

			Cell title7 = row2.createCell(7);
			title7.setCellStyle(cellStyle2);
			title7.setCellValue("??????????????????");

			Cell title8 = row2.createCell(8);
			title8.setCellStyle(cellStyle2);
			title8.setCellValue("??????");
			
			
			
			Cell title9 = row2.createCell(9);
			title9.setCellStyle(cellStyle2);
			title9.setCellValue("??????");

			Cell title10 = row2.createCell(10);
			title10.setCellStyle(cellStyle2);
			title10.setCellValue("??????????????????");

			Cell title11 = row2.createCell(11);
			title11.setCellStyle(cellStyle2);
			title11.setCellValue("??????");


			int rowNum = 3;
			for (int i = 0; i < firstRows.size(); i++) {
				Integer toalBenQi = 0;

				// ????????????
				Map maps = (Map) list02NeiZiJiDu.get(i);

				Row row = sheet.createRow(rowNum);
				
				Cell cellXuHao = row.createCell(0);
				cellXuHao.setCellValue(i+1);

				Cell cell = row.createCell(1);
				// cell.setCellStyle(cellStyle);
				String xiaQu = "";
				if (maps.get("??????") != null) {
					xiaQu = maps.get("??????").toString();
				}
				cell.setCellValue(xiaQu);
				
				
				//????????????
				Cell cell2 = row.createCell(2);
				// cell.setCellStyle(cellStyle);
				String jianGuanSuo = "";
				if (maps.get("?????????") != null) {
					jianGuanSuo = maps.get("?????????").toString();
				}
				cell2.setCellValue(jianGuanSuo);

				Cell cell3 = row.createCell(3);
				// cell1.setCellStyle(cellStyle);
				String shuLiang = "";
				if (maps.get("??????") != null) {
					shuLiang = maps.get("??????").toString();
					toalBenQi+=Integer.valueOf(shuLiang);
				}
				cell3.setCellValue(shuLiang);

				
				Cell cell4 = row.createCell(4);
				// cell2.setCellStyle(cellStyle);
				String zongShu = "";
				if (maps.get("??????") != null) {
					zongShu = maps.get("??????").toString();
				}
				cell4.setCellValue(zongShu);


				Cell cell5 = row.createCell(5);
				// cell4.setCellStyle(cellStyle);
				
				Double bilv = Double.valueOf(shuLiang) / Double.valueOf(zongShu) * 100;
				cell5.setCellValue(df.format(bilv) + "%");
				
				
				
				
				//????????????
				
				
				
				
				//????????????
				
				
				maps = (Map) list02SiYinJiDu.get(i);
				Cell sycell3 = row.createCell(6);
				// cell1.setCellStyle(cellStyle);
				String slshuLiang = "";
				if (maps.get("??????") != null) {
					slshuLiang = maps.get("??????").toString();
					toalBenQi+=Integer.valueOf(slshuLiang);
				}
				sycell3.setCellValue(slshuLiang);
				
				Cell sycell4 = row.createCell(7);
				// cell2.setCellStyle(cellStyle);
				String syzongShu = "";
				if (maps.get("??????") != null) {
					syzongShu = maps.get("??????").toString();
				}
				sycell4.setCellValue(syzongShu);


				Cell sycell5 = row.createCell(8);
				// cell4.setCellStyle(cellStyle);
				Double sybilv = Double.valueOf(slshuLiang) / Double.valueOf(syzongShu) * 100;
				sycell5.setCellValue(df.format(sybilv) + "%");
				//????????????
				
				
				//????????????
				maps = (Map) list02WaiZiJiDu.get(i);
				Cell wzcell3 = row.createCell(9);
				// cell1.setCellStyle(cellStyle);
				String wzshuLiang = "";
				if (maps.get("??????") != null) {
					wzshuLiang = maps.get("??????").toString();
					toalBenQi+=Integer.valueOf(wzshuLiang);
				}
				wzcell3.setCellValue(wzshuLiang);
				
				Cell wzcell4 = row.createCell(10);
				// cell2.setCellStyle(cellStyle);
				String wzzongShu = "";
				if (maps.get("??????") != null) {
					wzzongShu = maps.get("??????").toString();
				}
				wzcell4.setCellValue(wzzongShu);


				Cell wzcell5 = row.createCell(11);
				// cell4.setCellStyle(cellStyle);
				Double wzbilv = Double.valueOf(wzshuLiang) / Double.valueOf(wzzongShu) * 100;
				wzcell5.setCellValue(df.format(wzbilv) + "%");
				
				//????????????
				
				
				
				
				
				Cell cellTotalBenQi = row.createCell(12);
				// cell4.setCellStyle(cellStyle);
				cellTotalBenQi.setCellValue(toalBenQi);
				
				
				
				
				rowNum++;

			}
			
			
			
			
			
			
			
			
			
			
			//????????????
			
			int leiJi = 16;
			for (int i = 0; i < firstRows.size(); i++) {
				Integer totalLeiJi = 0;
			
				// ????????????
				Map maps = (Map) list02NeiZiAll.get(i);

				Row row = sheet.createRow(leiJi);
				
				Cell cellXuHao = row.createCell(0);
				cellXuHao.setCellValue(i+1);

				Cell cell = row.createCell(1);
				// cell.setCellStyle(cellStyle);
				String xiaQu = "";
				if (maps.get("??????") != null) {
					xiaQu = maps.get("??????").toString();
				}
				cell.setCellValue(xiaQu);
				
				
				//????????????
				Cell cell2 = row.createCell(2);
				// cell.setCellStyle(cellStyle);
				String jianGuanSuo = "";
				if (maps.get("?????????") != null) {
					jianGuanSuo = maps.get("?????????").toString();
				}
				cell2.setCellValue(jianGuanSuo);

				Cell cell3 = row.createCell(3);
				// cell1.setCellStyle(cellStyle);
				String shuLiang = "";
				if (maps.get("??????") != null) {
					shuLiang = maps.get("??????").toString();
					totalLeiJi+=Integer.valueOf(shuLiang);
				}
				cell3.setCellValue(shuLiang);

				
				Cell cell4 = row.createCell(4);
				// cell2.setCellStyle(cellStyle);
				String zongShu = "";
				if (maps.get("??????") != null) {
					zongShu = maps.get("??????").toString();
				}
				cell4.setCellValue(zongShu);


				Cell cell5 = row.createCell(5);
				// cell4.setCellStyle(cellStyle);
				
				Double bilv = Double.valueOf(shuLiang) / Double.valueOf(zongShu) * 100;
				cell5.setCellValue(df.format(bilv) + "%");
				
				//????????????
				
				
				
				
				//????????????
				
				
				maps = (Map) list02SiYinAll.get(i);
				Cell sycell3 = row.createCell(6);
				// cell1.setCellStyle(cellStyle);
				String slshuLiang = "";
				if (maps.get("??????") != null) {
					slshuLiang = maps.get("??????").toString();
					totalLeiJi+=Integer.valueOf(slshuLiang);
				}
				sycell3.setCellValue(slshuLiang);
				
				Cell sycell4 = row.createCell(7);
				// cell2.setCellStyle(cellStyle);
				String syzongShu = "";
				if (maps.get("??????") != null) {
					syzongShu = maps.get("??????").toString();
				}
				sycell4.setCellValue(syzongShu);


				Cell sycell5 = row.createCell(8);
				// cell4.setCellStyle(cellStyle);
				Double sybilv = Double.valueOf(slshuLiang) / Double.valueOf(syzongShu) * 100;
				sycell5.setCellValue(df.format(sybilv) + "%");
				//????????????
				
				
				//????????????
				maps = (Map) list02WaiZiAll.get(i);
				Cell wzcell3 = row.createCell(9);
				// cell1.setCellStyle(cellStyle);
				String wzshuLiang = "";
				if (maps.get("??????") != null) {
					wzshuLiang = maps.get("??????").toString();
					totalLeiJi+=Integer.valueOf(wzshuLiang);
				}
				wzcell3.setCellValue(wzshuLiang);
				
				Cell wzcell4 = row.createCell(10);
				// cell2.setCellStyle(cellStyle);
				String wzzongShu = "";
				if (maps.get("??????") != null) {
					wzzongShu = maps.get("??????").toString();
				}
				wzcell4.setCellValue(wzzongShu);


				Cell wzcell5 = row.createCell(11);
				// cell4.setCellStyle(cellStyle);
				Double wzbilv = Double.valueOf(wzshuLiang) / Double.valueOf(wzzongShu) * 100;
				wzcell5.setCellValue(df.format(wzbilv) + "%");
				
				//????????????
				
				
				
				Cell cellTotalBenQi = row.createCell(12);
				// cell4.setCellStyle(cellStyle);
				cellTotalBenQi.setCellValue(totalLeiJi);

				leiJi++;

			}
			

			FileOutputStream fout = new FileOutputStream(fileName);
			workbook.write(fout);
			workbook.close();
			fout.flush();
			fout.close();

		}
	}
}
