package com.report.controller.temp;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.rmi.CORBA.Tie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.report.service.Data01Service;
import com.report.service.Data02Service;

@Controller
@RequestMapping("data02")
public class Data02Controller {

	@Autowired
	private Data02Service data02Service;

	@ResponseBody
	@RequestMapping("/list")
	public void getList(String startTime, HttpServletRequest request) throws ParseException {
		Map map = new HashMap();
		if (startTime == null) {
			return ;
		}

		List list1 = data02Service.getSumJiDu(startTime);
		List list2 = data02Service.getSumAll(startTime);

		List list3 = data02Service.getNeiZiJiDu(startTime);
		List list4 = data02Service.getNeiZiAll(startTime);

		List list5 = data02Service.getSiYinJiDu(startTime);
		List list6 = data02Service.getSiYinAll(startTime);

		List list7 = data02Service.getWaiZiJiDu(startTime);
		List list8 = data02Service.getWaiZiAll(startTime);
		
		
		

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/de", method = RequestMethod.GET)
	public String downLoadExcel(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ParseException {
		
		
		List list = (List) request.getSession().getAttribute("list04");
		List list2 = (List) request.getSession().getAttribute("list041");

		List list3 = (List) request.getSession().getAttribute("list042");
		List list4 = (List) request.getSession().getAttribute("list0421");
		String currentTime = (String) request.getSession().getAttribute("time04");

		DecimalFormat df = new DecimalFormat("#.00");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = currentTime;
		Date dt = sdf.parse(str);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.YEAR, -1);// 日期减1年
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);
		System.out.println(reStr);

		if (list != null && list.size() > 0) {
			String fileName = "test.xlsx";
			response.setHeader("Content-disposition",
					"attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));// 设置文件头编码格式
			response.setContentType("APPLICATION/OCTET-STREAM;charset=UTF-8");// 设置类型
			response.setHeader("Cache-Control", "no-cache");// 设置头
			response.setDateHeader("Expires", 0);// 设置日期头

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

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 13));//年
			sheet.addMergedRegion(new CellRangeAddress(15, 15, 0, 13));//累计
			
			sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));// 序号
			sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 1));//辖区
			sheet.addMergedRegion(new CellRangeAddress(1, 2, 2, 2));//监管所
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 5));//内资
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 6, 8));//私营
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 9,11));//外资
			sheet.addMergedRegion(new CellRangeAddress(1, 2, 12,12));//总计
			
			
			


			Row row00 = sheet.createRow(0);
			Cell year = row00.createCell(0);
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor((short) 13);// 设置背景色
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			year.setCellStyle(cellStyle);
			// year.setCellValue("2018-10-18");
			year.setCellValue("本年一季度到" +currentTime+"月所在季度统计");

			
			
			
			
			//累计的
			Row rowLeiJiTitle = sheet.createRow(15);
			Cell leiJiyear = rowLeiJiTitle.createCell(0);
			CellStyle leiJiCellStyle = workbook.createCellStyle();
			leiJiCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());// 设置背景色
			leiJiCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			leiJiyear.setCellStyle(leiJiCellStyle);
			// year.setCellValue("2018-10-18");
			leiJiyear.setCellValue("累计到" +currentTime+"月统计");
			
			
			
			
			
			
			//第二行
			Row row0 = sheet.createRow(1);

			// 今年开始
			CellStyle cellStyle2 = workbook.createCellStyle();
			//cellStyle2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());// 设置背景色,//https://blog.csdn.net/z1074907546/article/details/50544178/
		//	cellStyle2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
			Cell title = row0.createCell(0);
			title.setCellStyle(cellStyle2);
			title.setCellValue("序号");
			
			Cell title1 = row0.createCell(1);
			title1.setCellStyle(cellStyle2);
			title1.setCellValue("辖区");
			
			
			Cell title2 = row0.createCell(2);
			title2.setCellStyle(cellStyle2);
			title2.setCellValue("监管所");

			Cell titleNeiZi = row0.createCell(3);
			XSSFCellStyle setBorder = workbook.createCellStyle();
			
			cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			titleNeiZi.setCellStyle(cellStyle2);
			titleNeiZi.setCellValue("内资");
			
			
			
			Cell titleSiYin= row0.createCell(6);
			titleSiYin.setCellStyle(cellStyle2);
			titleSiYin.setCellValue("私营");
			
			Cell titleWaiZi= row0.createCell(9);
			titleWaiZi.setCellStyle(cellStyle2);
			titleWaiZi.setCellValue("外资");
			
			Cell titleZongJi = row0.createCell(12);
			titleZongJi.setCellStyle(cellStyle2);
			titleZongJi.setCellValue("总计");
			
			
			Row row2 = sheet.createRow(2);

			
			
			Cell title3 = row2.createCell(3);
			title3.setCellStyle(cellStyle2);
			title3.setCellValue("数量");

			Cell title4 = row2.createCell(4);
			title4.setCellStyle(cellStyle2);
			title4.setCellValue("该辖区局数量");

			Cell title5 = row2.createCell(5);
			title5.setCellStyle(cellStyle2);
			title5.setCellValue("比重");
			
			
			
			Cell title6 = row2.createCell(6);
			title6.setCellStyle(cellStyle2);
			title6.setCellValue("数量");

			Cell title7 = row2.createCell(7);
			title7.setCellStyle(cellStyle2);
			title7.setCellValue("该辖区局数量");

			Cell title8 = row2.createCell(8);
			title8.setCellStyle(cellStyle2);
			title8.setCellValue("比重");
			
			
			
			Cell title9 = row2.createCell(9);
			title9.setCellStyle(cellStyle2);
			title9.setCellValue("数量");

			Cell title10 = row2.createCell(10);
			title10.setCellStyle(cellStyle2);
			title10.setCellValue("该辖区局数量");

			Cell title11 = row2.createCell(11);
			title11.setCellStyle(cellStyle2);
			title11.setCellValue("比重");


			int rowNum = 3;
			for (int i = 0; i < list.size(); i++) {

				// 今年数据
				Map maps = (Map) list.get(i);

				Row row = sheet.createRow(rowNum);
				
				Cell cellXuHao = row.createCell(0);
				cellXuHao.setCellValue(i+1);

				Cell cell = row.createCell(1);
				// cell.setCellStyle(cellStyle);
				String xiaQu = "";
				if (maps.get("辖区") != null) {
					xiaQu = maps.get("辖区").toString();
				}
				cell.setCellValue(xiaQu);
				
				
				//内资开始
				Cell cell2 = row.createCell(2);
				// cell.setCellStyle(cellStyle);
				String jianGuanSuo = "";
				if (maps.get("监管所") != null) {
					jianGuanSuo = maps.get("监管所").toString();
				}
				cell2.setCellValue(jianGuanSuo);

				Cell cell3 = row.createCell(3);
				// cell1.setCellStyle(cellStyle);
				String shuLiang = "";
				if (maps.get("数量") != null) {
					shuLiang = maps.get("数量").toString();
				}
				cell3.setCellValue(shuLiang);

				
				Cell cell4 = row.createCell(4);
				// cell2.setCellStyle(cellStyle);
				String zongShu = "";
				if (maps.get("总数") != null) {
					zongShu = maps.get("总数").toString();
				}
				cell4.setCellValue(zongShu);


				Cell cell5 = row.createCell(5);
				// cell4.setCellStyle(cellStyle);
				
				Double bilv = Double.valueOf(shuLiang) / Double.valueOf(zongShu) * 100;
				cell5.setCellValue(df.format(bilv) + "%");
				
				//内资结束
				
				
				
				
				//私营开始
				
				
				maps = (Map) list.get(i);
				Cell sycell3 = row.createCell(6);
				// cell1.setCellStyle(cellStyle);
				String slshuLiang = "";
				if (maps.get("数量") != null) {
					slshuLiang = maps.get("数量").toString();
				}
				sycell3.setCellValue(shuLiang);
				
				Cell sycell4 = row.createCell(7);
				// cell2.setCellStyle(cellStyle);
				String syzongShu = "";
				if (maps.get("总数") != null) {
					syzongShu = maps.get("总数").toString();
				}
				sycell4.setCellValue(syzongShu);


				Cell sycell5 = row.createCell(8);
				// cell4.setCellStyle(cellStyle);
				Double sybilv = Double.valueOf(slshuLiang) / Double.valueOf(syzongShu) * 100;
				sycell5.setCellValue(df.format(sybilv) + "%");
				//私营结束
				
				
				//外资开始
				
				Cell wzcell3 = row.createCell(9);
				// cell1.setCellStyle(cellStyle);
				String wzshuLiang = "";
				if (maps.get("数量") != null) {
					wzshuLiang = maps.get("数量").toString();
				}
				wzcell3.setCellValue(wzshuLiang);
				
				Cell wzcell4 = row.createCell(10);
				// cell2.setCellStyle(cellStyle);
				String wzzongShu = "";
				if (maps.get("总数") != null) {
					wzzongShu = maps.get("总数").toString();
				}
				wzcell4.setCellValue(wzzongShu);


				Cell wzcell5 = row.createCell(11);
				// cell4.setCellStyle(cellStyle);
				Double wzbilv = Double.valueOf(slshuLiang) / Double.valueOf(wzzongShu) * 100;
				wzcell5.setCellValue(df.format(wzbilv) + "%");
				
				//外资结束
				
				
				
				rowNum++;

			}
			
			
			
			
			
			
			
			
			
			
			
			
			int leiJi = 16;
			for (int i = 0; i < list.size(); i++) {

				// 今年数据
				Map maps = (Map) list.get(i);

				Row row = sheet.createRow(leiJi);
				
				Cell cellXuHao = row.createCell(0);
				cellXuHao.setCellValue(i+1);

				Cell cell = row.createCell(1);
				// cell.setCellStyle(cellStyle);
				String xiaQu = "";
				if (maps.get("辖区") != null) {
					xiaQu = maps.get("辖区").toString();
				}
				cell.setCellValue(xiaQu);
				
				
				
				Cell cell2 = row.createCell(2);
				// cell.setCellStyle(cellStyle);
				String jianGuanSuo = "";
				if (maps.get("监管所") != null) {
					jianGuanSuo = maps.get("监管所").toString();
				}
				cell2.setCellValue(jianGuanSuo);

				Cell cell3 = row.createCell(3);
				// cell1.setCellStyle(cellStyle);
				String shuLiang = "";
				if (maps.get("数量") != null) {
					shuLiang = maps.get("数量").toString();
				}
				cell3.setCellValue(shuLiang);

				
				Cell cell4 = row.createCell(4);
				// cell2.setCellStyle(cellStyle);
				String zongShu = "";
				if (maps.get("总数") != null) {
					zongShu = maps.get("总数").toString();
				}
				cell4.setCellValue(zongShu);


				Cell cell5 = row.createCell(5);
				// cell4.setCellStyle(cellStyle);
				Double bilv = Double.valueOf(shuLiang) / Double.valueOf(zongShu) * 100;
				cell5.setCellValue(df.format(bilv) + "%");

				leiJi++;

			}
			
			
			
			
			

			workbook.write(response.getOutputStream());

			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
		return null;
	}

}
