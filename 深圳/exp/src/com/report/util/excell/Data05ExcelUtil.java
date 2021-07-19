package com.report.util.excell;

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

public class Data05ExcelUtil {

	public static void createExcell(List list, List list2, String thisYear,String lastYear, String fileName)
			throws ParseException, IOException {
	 DecimalFormat df = new DecimalFormat("#.00");

		

		if (list != null && list.size() > 0) {

			XSSFWorkbook workbook = new XSSFWorkbook();

			XSSFSheet sheet = workbook.createSheet();
			sheet.setColumnWidth(4, 20 * 256);
			// CellStyle cellStyle = workbook.createCellStyle();

			// cellStyle.setDataFormat(workbook.createDataFormat().getFormat("yyyy-MM-dd
			// HH:mm:ss"));

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));// 第一行数据
			sheet.addMergedRegion(new CellRangeAddress(13, 13, 0,6));//第14行

			Row row00 = sheet.createRow(0);
			Cell year = row00.createCell(0);
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor((short) 13);// 设置背景色
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			year.setCellStyle(cellStyle);
			year.setCellValue("年初到" +lastYear + "月");

			Row row0 = sheet.createRow(1);
			// 今年开始
			CellStyle cellStyle2 = workbook.createCellStyle();
			cellStyle2.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());// 设置背景色
			cellStyle2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			Cell xuhao = row0.createCell(0);
			xuhao.setCellStyle(cellStyle2);
			xuhao.setCellValue("序号");

			Cell title = row0.createCell(1);
			title.setCellStyle(cellStyle2);
			title.setCellValue("辖区");

			Cell title1 = row0.createCell(2);
			title1.setCellStyle(cellStyle2);
			title1.setCellValue("监管所");

			Cell title2 = row0.createCell(3);
			title2.setCellStyle(cellStyle2);
			title2.setCellValue("数量");

			Cell title3 = row0.createCell(4);
			title3.setCellStyle(cellStyle2);
			title3.setCellValue("该辖区局数量");
			
			Cell title4 = row0.createCell(5);
			title4.setCellStyle(cellStyle2);
			title4.setCellValue("比重");
			
			
			Cell title5 = row0.createCell(6);
			title5.setCellStyle(cellStyle2);
			title5.setCellValue("总计");

			int rowNum = 2;
			for (int i = 0; i < list.size(); i++) {

				// 今年数据
				System.out.println(list.get(i));
				Map maps = (Map) list.get(i);

				Row row = sheet.createRow(rowNum);

				Cell xuHaoNum = row.createCell(0);
				// cell.setCellStyle(cellStyle);
				xuHaoNum.setCellValue(i+1);

				Cell cell = row.createCell(1);
				// cell.setCellStyle(cellStyle);
				String xiaQu = "";
				if (maps.get("辖区") != null) {
					xiaQu = maps.get("辖区").toString();
				}
				cell.setCellValue(xiaQu);

				Cell cell1 = row.createCell(2);
				// cell1.setCellStyle(cellStyle);
				String hangYe = "";
				if (maps.get("监管所") != null) {
					hangYe = maps.get("监管所").toString();
				}
				cell1.setCellValue(hangYe);

				Cell cell2 = row.createCell(3);
				// cell2.setCellStyle(cellStyle);
				String num = "";
				if (maps.get("数量") != null) {
					num = maps.get("数量").toString();
				}
				cell2.setCellValue(num);

				Cell cell3 = row.createCell(4);
				// cell3.setCellStyle(cellStyle);
				String total = "";
				if (maps.get("总数") != null) {
					total = maps.get("总数").toString();
				}
				cell3.setCellValue(total);
				
				
				Cell cell5 = row.createCell(5);
				//cell6.setCellStyle(cellStyle);
				Double bilv = Double.valueOf(num)/Double.valueOf(total)*100;
				cell5.setCellValue(df.format(bilv)+"%");
				
				
				Cell cell6 = row.createCell(6);
				//cell6.setCellStyle(cellStyle);
				cell6.setCellValue(total);

				// 今年数据完

				rowNum++;

			}

			
			
			
			// 累计的
			Row leiJiDate = sheet.createRow(13);
			Cell leiJiCellDate = leiJiDate.createCell(0);
			CellStyle leijiCellStyle = workbook.createCellStyle();
			leijiCellStyle.setFillForegroundColor((short) 13);// 设置背景色
			leijiCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			leiJiCellDate.setCellStyle(leijiCellStyle);
			leiJiCellDate.setCellValue("累计到" + lastYear + "月");
			
			
			Row leijiAll = sheet.createRow(14);
			CellStyle leijiTitle = workbook.createCellStyle();
			leijiTitle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());// 设置背景色
			leijiTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			Cell leijiXuhao = leijiAll.createCell(0);
			leijiXuhao.setCellStyle(cellStyle2);
			leijiXuhao.setCellValue("序号");

			Cell leiJiTitle = leijiAll.createCell(1);
			leiJiTitle.setCellStyle(cellStyle2);
			leiJiTitle.setCellValue("辖区");

			Cell leiJiJianGuan = leijiAll.createCell(2);
			leiJiJianGuan.setCellStyle(cellStyle2);
			leiJiJianGuan.setCellValue("监管所");

			Cell leiJiShuLian = leijiAll.createCell(3);
			leiJiShuLian.setCellStyle(cellStyle2);
			leiJiShuLian.setCellValue("数量");

			Cell leiJiZhongShu = leijiAll.createCell(4);
			leiJiZhongShu.setCellStyle(cellStyle2);
			leiJiZhongShu.setCellValue("该辖区局数量");
			

			Cell leiJiBiZhong = leijiAll.createCell(5);
			leiJiBiZhong.setCellStyle(cellStyle2);
			leiJiBiZhong.setCellValue("比重");
			
			
			Cell leiJIZongJi = leijiAll.createCell(6);
			leiJIZongJi.setCellStyle(cellStyle2);
			leiJIZongJi.setCellValue("总计");
			
			int leiJi = 15;
			for (int i = 0; i < list2.size(); i++) {

				// 今年数据
				System.out.println(list2.get(i));
				Map maps = (Map) list2.get(i);

				Row row = sheet.createRow(leiJi);

				Cell xuHaoNum = row.createCell(0);
				// cell.setCellStyle(cellStyle);
				xuHaoNum.setCellValue(i+1);

				Cell cell = row.createCell(1);
				// cell.setCellStyle(cellStyle);
				String xiaQu = "";
				if (maps.get("辖区") != null) {
					xiaQu = maps.get("辖区").toString();
				}
				cell.setCellValue(xiaQu);

				Cell cell1 = row.createCell(2);
				// cell1.setCellStyle(cellStyle);
				String hangYe = "";
				if (maps.get("监管所") != null) {
					hangYe = maps.get("监管所").toString();
				}
				cell1.setCellValue(hangYe);

				Cell cell2 = row.createCell(3);
				// cell2.setCellStyle(cellStyle);
				String num = "";
				if (maps.get("数量") != null) {
					num = maps.get("数量").toString();
				}
				cell2.setCellValue(num);

				Cell cell3 = row.createCell(4);
				// cell3.setCellStyle(cellStyle);
				String total = "";
				if (maps.get("总数") != null) {
					total = maps.get("总数").toString();
				}
				cell3.setCellValue(total);

				
				Cell cell5 = row.createCell(5);
				//cell6.setCellStyle(cellStyle);
				Double bilv = Double.valueOf(num)/Double.valueOf(total)*100;
				cell5.setCellValue(df.format(bilv)+"%");
				
				
				Cell cell6 = row.createCell(6);
				//cell6.setCellStyle(cellStyle);
				cell6.setCellValue(total);
				
				// 数据完

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
