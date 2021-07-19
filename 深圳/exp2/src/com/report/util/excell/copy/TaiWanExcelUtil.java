package com.report.util.excell.copy;

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

public class TaiWanExcelUtil {

	public static void createExcell(List list, String thisYear, String fileName) throws ParseException, IOException {
		DecimalFormat df = new DecimalFormat("#.00");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (list != null && list.size() > 0) {

			XSSFWorkbook workbook = new XSSFWorkbook();

			XSSFSheet sheet = workbook.createSheet();
			sheet.setColumnWidth(4, 20 * 256);
			// CellStyle cellStyle = workbook.createCellStyle();

			// cellStyle.setDataFormat(workbook.createDataFormat().getFormat("yyyy-MM-dd
			// HH:mm:ss"));
			
			
			
			//sheet.setColumnWidth(0, 40 * 256);
			sheet.setColumnWidth(1, 40 * 256);
			sheet.setColumnWidth(2, 80 * 256);
			sheet.setColumnWidth(3, 20 * 256);
			sheet.setColumnWidth(4, 20 * 256);
			sheet.setColumnWidth(5, 100 * 256);
			sheet.setColumnWidth(6, 60 * 256);
			sheet.setColumnWidth(7, 60 * 256);
			sheet.setColumnWidth(8, 60 * 256);
			sheet.setColumnWidth(9, 60 * 256);
			sheet.setColumnWidth(11, 60 * 256);
			sheet.setColumnWidth(12, 120 * 256);
			sheet.setColumnWidth(13, 60 * 256);
			sheet.setColumnWidth(14, 60 * 256);


			//sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));// 第一行数据
			//sheet.addMergedRegion(new CellRangeAddress(13, 13, 0, 6));// 第14行

			Row row00 = sheet.createRow(0);
			Cell year = row00.createCell(0);
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor((short) 13);// 设置背景色
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			year.setCellStyle(cellStyle);
			year.setCellValue(thisYear + "所在季度");

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
			title.setCellValue("个体工商户名称");

			Cell title1 = row0.createCell(2);
			title1.setCellStyle(cellStyle2);
			title1.setCellValue("经营场所");

			Cell title2 = row0.createCell(3);
			title2.setCellStyle(cellStyle2);
			title2.setCellValue("组成形式");

			Cell title3 = row0.createCell(4);
			title3.setCellStyle(cellStyle2);
			title3.setCellValue("注册资本(万元)");

			Cell title4 = row0.createCell(5);
			title4.setCellStyle(cellStyle2);
			title4.setCellValue("经营范围及方式");

			Cell title5 = row0.createCell(6);
			title5.setCellStyle(cellStyle2);
			title5.setCellValue("工商注册号");
			
			
			Cell title7 = row0.createCell(7);
			title7.setCellStyle(cellStyle2);
			title7.setCellValue("统一社会信用代码");
			
			
			
			Cell title8 = row0.createCell(8);
			title8.setCellStyle(cellStyle2);
			title8.setCellValue("工商注册日期");
			
			
			
			Cell title9 = row0.createCell(9);
			title9.setCellStyle(cellStyle2);
			title9.setCellValue("存续状态");
			
			
			Cell title10 = row0.createCell(10);
			title10.setCellStyle(cellStyle2);
			title10.setCellValue("从业人数");
			
			
			Cell title11 = row0.createCell(11);
			title11.setCellStyle(cellStyle2);
			title11.setCellValue("法定代表人(经营者)姓名");
			
			
			Cell title12 = row0.createCell(12);
			title12.setCellStyle(cellStyle2);
			title12.setCellValue("法定(经营者)户籍住址(台湾)");
			
			
			
			
			Cell title13 = row0.createCell(13);
			title13.setCellStyle(cellStyle2);
			title13.setCellValue("电话号码");
			
			
			Cell title14 = row0.createCell(14);
			title14.setCellStyle(cellStyle2);
			title14.setCellValue("备注(证件号码)");
			
			

			int rowNum = 2;
			for (int i = 0; i < list.size(); i++) {

				System.out.println(list.get(i));
				Map maps = (Map) list.get(i);

				Row row = sheet.createRow(rowNum);

				Cell xuHaoNum = row.createCell(0);
				// cell.setCellStyle(cellStyle);
				xuHaoNum.setCellValue(i + 1);

				Cell cell = row.createCell(1);
				// cell.setCellStyle(cellStyle);
				String xiaQu = "";
				if (maps.get("个体工商户名称") != null) {
					xiaQu = maps.get("个体工商户名称").toString();
				}
				cell.setCellValue(xiaQu);

				Cell cell1 = row.createCell(2);
				// cell1.setCellStyle(cellStyle);
				String hangYe = "";
				if (maps.get("经营场所") != null) {
					hangYe = maps.get("经营场所").toString();
				}
				cell1.setCellValue(hangYe);

				Cell cell2 = row.createCell(3);
				// cell2.setCellStyle(cellStyle);
				String num = "";
				if (maps.get("组成形式") != null) {
					num = maps.get("组成形式").toString();
				}
				cell2.setCellValue(num);

				Cell cell3 = row.createCell(4);
				// cell3.setCellStyle(cellStyle);
				String total = "";
				if (maps.get("注册资本") != null) {
					total = maps.get("注册资本").toString();
				}
				cell3.setCellValue(total);

				
				
				
				
				Cell cell5 = row.createCell(5);
				// cell3.setCellStyle(cellStyle);
				String opscope = "";
				if (maps.get("经营范围及方式") != null) {
					opscope = maps.get("经营范围及方式").toString();
				}
				cell5.setCellValue(opscope);

				
				
				Cell cell6 = row.createCell(6);
				// cell3.setCellStyle(cellStyle);
				String regno = "";
				if (maps.get("工商注册号") != null) {
					regno = maps.get("工商注册号").toString();
				}
				cell6.setCellValue(regno);

				
				
				Cell cell7 = row.createCell(7);
				// cell3.setCellStyle(cellStyle);
				String uniscid = "";
				if (maps.get("统一社会信用代码") != null) {
					uniscid = maps.get("统一社会信用代码").toString();
				}
				cell7.setCellValue(uniscid);

				Cell cell8 = row.createCell(8);
				// cell3.setCellStyle(cellStyle);
				String estdate = "";
				if (maps.get("工商注册日期") != null) {
					estdate = maps.get("工商注册日期").toString();
				}
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				long nowTime = Long.valueOf(estdate);
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(nowTime);
				String dateStr =  dateFormat.format(cal.getTime());
				cell8.setCellValue(dateStr);

				Cell cell9= row.createCell(9);
				// cell3.setCellStyle(cellStyle);
				String regstate_cn = "";
				if (maps.get("存续状态") != null) {
					regstate_cn = maps.get("存续状态").toString();
				}
				cell9.setCellValue(regstate_cn);

				Cell cell10 = row.createCell(10);
				// cell3.setCellStyle(cellStyle);
				String empnum = "";
				if (maps.get("从业人数") != null) {
					empnum = maps.get("从业人数").toString();
				}
				cell10.setCellValue(empnum);

				Cell cell11 = row.createCell(11);
				// cell3.setCellStyle(cellStyle);
				String name = "";
				if (maps.get("法定代表人(经营者)姓名") != null) {
					name = maps.get("法定代表人(经营者)姓名").toString();
				}
				cell11.setCellValue(name);

				Cell cell12 = row.createCell(12);
				// cell3.setCellStyle(cellStyle);
				String dom = "";
				if (maps.get("法定(经营者)户籍住址(台湾)") != null) {
					dom = maps.get("法定(经营者)户籍住址(台湾)").toString();
				}
				cell12.setCellValue(dom);

				Cell cell13 = row.createCell(13);
				// cell3.setCellStyle(cellStyle);
				String tel = "";
				if (maps.get("电话号码") != null) {
					tel = maps.get("电话号码").toString();
				}
				cell13.setCellValue(tel);

				
				Cell cell14 = row.createCell(14);
				// cell3.setCellStyle(cellStyle);
				String cerno = "";
				if (maps.get("备注(证件号码)") != null) {
					cerno = maps.get("备注(证件号码)").toString();
				}
				cell14.setCellValue(cerno);
				
				
				
				rowNum++;

			}

			


			FileOutputStream fout = new FileOutputStream(fileName);
			workbook.write(fout);
			workbook.close();
			fout.flush();
			fout.close();
		}
	}
}
