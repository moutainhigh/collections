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

public class Data03ExcelUtil {

	public static void createExcell(List list, List list2, List jianNianList, List list4, String currentYearStr,String lastYearStr, String fileName)
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
			sheet.addMergedRegion(new CellRangeAddress(33, 33, 0, 6));// 第14行
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 9, 15));// 第1行9到15列
			sheet.addMergedRegion(new CellRangeAddress(33, 33, 9, 15));// 第14行，9到15列

			for (int i = 3; i < list.size() + 2; i++) {
				if (i % 3 == 0) {
					// sheet.addMergedRegion(new
					// CellRangeAddress(2,4,0,0));//第一行数据
					// sheet.addMergedRegion(new
					// CellRangeAddress((i-1),(i+2),1,1));//第一行数据
					// sheet.addMergedRegion(new
					// CellRangeAddress((i-1),(i+2),5,5));//第一行数据(去年数据)
					sheet.addMergedRegion(new CellRangeAddress((i - 1), (i + 1), 1, 1));// 第一行数据
					sheet.addMergedRegion(new CellRangeAddress((i - 1), (i + 1), 10, 10));// 第一行数据

					sheet.addMergedRegion(new CellRangeAddress((i - 1 + 33), (i + 1 + 33), 1, 1));// 第一行数据
					sheet.addMergedRegion(new CellRangeAddress((i - 1 + 33), (i + 1 + 33), 10, 10));// 第一行数据

				}
			}

			// sheet.addMergedRegion(new CellRangeAddress(2, 4, 1, 1));// 第一行数据

			Row row00 = sheet.createRow(0);
			Cell year = row00.createCell(0);
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor((short) 13);// 设置背景色
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			year.setCellStyle(cellStyle);
			year.setCellValue(currentYearStr + "月数据统计");

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

			// 去年本季度
			year = row00.createCell(9);
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor((short) 13);// 设置背景色
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			year.setCellStyle(cellStyle);
			year.setCellValue(lastYearStr + "月数据统计");

			cellStyle2 = workbook.createCellStyle();
			cellStyle2.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());// 设置背景色
			cellStyle2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			xuhao = row0.createCell(9);
			xuhao.setCellStyle(cellStyle2);
			xuhao.setCellValue("序号");

			title = row0.createCell(10);
			title.setCellStyle(cellStyle2);
			title.setCellValue("辖区");

			title1 = row0.createCell(11);
			title1.setCellStyle(cellStyle2);
			title1.setCellValue("监管所");

			title2 = row0.createCell(12);
			title2.setCellStyle(cellStyle2);
			title2.setCellValue("数量");

			title3 = row0.createCell(13);
			title3.setCellStyle(cellStyle2);
			title3.setCellValue("该辖区局数量");

			title4 = row0.createCell(14);
			title4.setCellStyle(cellStyle2);
			title4.setCellValue("比重");

			title5 = row0.createCell(15);
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
				xuHaoNum.setCellValue(i + 1);

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
				// cell6.setCellStyle(cellStyle);
				Double bilv = Double.valueOf(num) / Double.valueOf(total) * 100;
				cell5.setCellValue(df.format(bilv) + "%");

				Cell cell6 = row.createCell(6);
				// cell6.setCellStyle(cellStyle);
				cell6.setCellValue(total);

				// 去年今天的这个月份所在季度的

				maps = (Map) list2.get(i);

				xuHaoNum = row.createCell(9);
				// cell.setCellStyle(cellStyle);
				xuHaoNum.setCellValue(i + 1);

				cell = row.createCell(10);
				// cell.setCellStyle(cellStyle);
				xiaQu = "";
				if (maps.get("辖区") != null) {
					xiaQu = maps.get("辖区").toString();
				}
				cell.setCellValue(xiaQu);

				cell1 = row.createCell(11);
				// cell1.setCellStyle(cellStyle);
				hangYe = "";
				if (maps.get("监管所") != null) {
					hangYe = maps.get("监管所").toString();
				}
				cell1.setCellValue(hangYe);
				cell2 = row.createCell(12);
				// cell2.setCellStyle(cellStyle);
				num = "";
				if (maps.get("数量") != null) {
					num = maps.get("数量").toString();
				}
				cell2.setCellValue(num);

				cell3 = row.createCell(13);
				// cell3.setCellStyle(cellStyle);
				total = "";
				if (maps.get("总数") != null) {
					total = maps.get("总数").toString();
				}
				cell3.setCellValue(total);

				cell5 = row.createCell(14);
				// cell6.setCellStyle(cellStyle);
				bilv = Double.valueOf(num) / Double.valueOf(total) * 100;
				cell5.setCellValue(df.format(bilv) + "%");

				cell6 = row.createCell(15);
				// cell6.setCellStyle(cellStyle);
				cell6.setCellValue(total);

				// 今年数据完

				rowNum++;

			}

			// 累计的，今年一季度到本季度的
			Row leiJiDate = sheet.createRow(33);
			Cell leiJiCellDate = leiJiDate.createCell(0);
			CellStyle leijiCellStyle = workbook.createCellStyle();
			leijiCellStyle.setFillForegroundColor((short) 13);// 设置背景色
			leijiCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			leiJiCellDate.setCellStyle(leijiCellStyle);
			leiJiCellDate.setCellValue("今年1月到" + currentYearStr + "月");

			Row leijiAll = sheet.createRow(34);
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

			// 去年一季度以来的
			leiJiCellDate = leiJiDate.createCell(9);
			leijiCellStyle = workbook.createCellStyle();
			leijiCellStyle.setFillForegroundColor((short) 13);// 设置背景色
			leijiCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			leiJiCellDate.setCellStyle(leijiCellStyle);
			leiJiCellDate.setCellValue("去年1月到" + lastYearStr + "月(去年同期)");

			leijiTitle = workbook.createCellStyle();
			leijiTitle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());// 设置背景色
			leijiTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			leijiXuhao = leijiAll.createCell(9);
			leijiXuhao.setCellStyle(cellStyle2);
			leijiXuhao.setCellValue("序号");

			leiJiTitle = leijiAll.createCell(10);
			leiJiTitle.setCellStyle(cellStyle2);
			leiJiTitle.setCellValue("辖区");

			leiJiJianGuan = leijiAll.createCell(11);
			leiJiJianGuan.setCellStyle(cellStyle2);
			leiJiJianGuan.setCellValue("监管所");

			leiJiShuLian = leijiAll.createCell(12);
			leiJiShuLian.setCellStyle(cellStyle2);
			leiJiShuLian.setCellValue("数量");

			leiJiZhongShu = leijiAll.createCell(13);
			leiJiZhongShu.setCellStyle(cellStyle2);
			leiJiZhongShu.setCellValue("该辖区局数量");

			leiJiBiZhong = leijiAll.createCell(14);
			leiJiBiZhong.setCellStyle(cellStyle2);
			leiJiBiZhong.setCellValue("比重");

			leiJIZongJi = leijiAll.createCell(15);
			leiJIZongJi.setCellStyle(cellStyle2);
			leiJIZongJi.setCellValue("总计");

			int leiJi = 35;
			for (int i = 0; i < list2.size(); i++) {

				// 今年数据
				System.out.println(jianNianList.get(i));
				Map maps = (Map) jianNianList.get(i);

				Row row = sheet.createRow(leiJi);

				Cell xuHaoNum = row.createCell(0);
				// cell.setCellStyle(cellStyle);
				xuHaoNum.setCellValue(i + 1);

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
				// cell6.setCellStyle(cellStyle);
				Double bilv = Double.valueOf(num) / Double.valueOf(total) * 100;
				cell5.setCellValue(df.format(bilv) + "%");

				Cell cell6 = row.createCell(6);
				// cell6.setCellStyle(cellStyle);
				cell6.setCellValue(total);

				maps = (Map) list4.get(i);
				xuHaoNum = row.createCell(9);
				// cell.setCellStyle(cellStyle);
				xuHaoNum.setCellValue(i + 1);

				cell = row.createCell(10);
				// cell.setCellStyle(cellStyle);
				xiaQu = "";
				if (maps.get("辖区") != null) {
					xiaQu = maps.get("辖区").toString();
				}
				cell.setCellValue(xiaQu);

				cell1 = row.createCell(11);
				// cell1.setCellStyle(cellStyle);
				hangYe = "";
				if (maps.get("监管所") != null) {
					hangYe = maps.get("监管所").toString();
				}
				cell1.setCellValue(hangYe);

				cell2 = row.createCell(12);
				// cell2.setCellStyle(cellStyle);
				num = "";
				if (maps.get("数量") != null) {
					num = maps.get("数量").toString();
				}
				cell2.setCellValue(num);

				cell3 = row.createCell(13);
				// cell3.setCellStyle(cellStyle);
				total = "";
				if (maps.get("总数") != null) {
					total = maps.get("总数").toString();
				}
				cell3.setCellValue(total);

				cell5 = row.createCell(14);
				// cell6.setCellStyle(cellStyle);
				bilv = Double.valueOf(num) / Double.valueOf(total) * 100;
				cell5.setCellValue(df.format(bilv) + "%");

				cell6 = row.createCell(15);
				// cell6.setCellStyle(cellStyle);
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
