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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
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

@Controller
@RequestMapping("data01")
public class Data01Controller {

	@Autowired
	private Data01Service data01Service;

	//https://www.cnblogs.com/henuyuxiang/p/6149466.html
	//https://www.cnblogs.com/linkstar/p/5910916.html
	//https://blog.csdn.net/a919423654/article/details/68066294
	//https://blog.csdn.net/qq_36834487/article/details/78933133
	//https://blog.csdn.net/liqingwei168/article/details/79162359?utm_source=blogxgwz0
	// https://blog.csdn.net/liqingwei168/article/details/79162359
	@RequestMapping("/list")
	@ResponseBody
	public Map  getList(String startTime,HttpServletRequest request) throws ParseException {
		Map map = new HashMap();
		if (startTime == null) {
			return null;
		}
		List list = data01Service.getList(startTime);
		List list2 = data01Service.getList2(startTime);
		
		request.getSession().removeAttribute("time1");
		request.getSession().removeAttribute("list1");
		request.getSession().removeAttribute("list2");
		
		
		request.getSession().setAttribute("time1", startTime);
		request.getSession().setAttribute("list1", list);
		request.getSession().setAttribute("list2", list2);
		
		return map;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/de", method = RequestMethod.GET)
	public String downLoadExcel(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
		//List list = data01Service.getList(null);
		List list = (List) request.getSession().getAttribute("list1");
		List list2 = (List) request.getSession().getAttribute("list2");
		String currentTime =  (String) request.getSession().getAttribute("time1");
		
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = currentTime;
		Date dt = sdf.parse(str);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.YEAR, -1);// 日期减1年
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);
		System.out.println(reStr);
		String lastYear = reStr; // 去年这个日期
		
		
		
		if (list != null && list.size() > 0) {
			String fileName = "test.xlsx";
			response.setHeader("Content-disposition","attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));// 设置文件头编码格式
			response.setContentType("APPLICATION/OCTET-STREAM;charset=UTF-8");// 设置类型
			response.setHeader("Cache-Control", "no-cache");// 设置头
			response.setDateHeader("Expires", 0);// 设置日期头

			XSSFWorkbook workbook = new XSSFWorkbook();

			XSSFSheet sheet = workbook.createSheet();
			//CellStyle cellStyle = workbook.createCellStyle();

			//cellStyle.setDataFormat(workbook.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));

			
			
			sheet.setColumnWidth(1, 20 * 256);
			sheet.setColumnWidth(4, 20 * 256);
			
			
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,4));//第一行数据
			sheet.addMergedRegion(new CellRangeAddress(0,0,5,9));//第一行数据，显示第二个跨列数据
			
			
			for (int i = 3; i < list.size()+2; i++) {
				if(i%3==0) {
					//sheet.addMergedRegion(new CellRangeAddress(2,4,0,0));//第一行数据
					sheet.addMergedRegion(new CellRangeAddress((i-1),(i+1),0,0));//第一行数据
					sheet.addMergedRegion(new CellRangeAddress((i-1),(i+1),5,5));//第一行数据(去年数据)
				}
			}
			
			
			Row row00 = sheet.createRow(0);
			Cell year = row00.createCell(0);
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor((short) 13);// 设置背景色
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			year.setCellStyle(cellStyle);
			//year.setCellValue("2018-10-18");
			year.setCellValue(currentTime);
			
			Cell lastYearCell = row00.createCell(5);
			CellStyle lastYearCellStyle = workbook.createCellStyle();
			lastYearCellStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());// 设置背景色
			lastYearCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			lastYearCell.setCellStyle(lastYearCellStyle);
			//lastYearCell.setCellValue("2017-10-18");
			lastYearCell.setCellValue(lastYear);
			
			
			
			
			Row row0 = sheet.createRow(1);
			
			//今年开始
			CellStyle cellStyle2 = workbook.createCellStyle();
			cellStyle2.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());// 设置背景色
			cellStyle2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			Cell title = row0.createCell(0);
			title.setCellStyle(cellStyle2);
			title.setCellValue("辖区");
			
			Cell title1 = row0.createCell(1);
			title1.setCellStyle(cellStyle2);
			title1.setCellValue("行业");
			
			Cell title2 = row0.createCell(2);
			title2.setCellStyle(cellStyle2);
			title2.setCellValue("数量");
			
			Cell title3 = row0.createCell(3);
			title3.setCellStyle(cellStyle2);
			title3.setCellValue("总数");
			
			Cell title4 = row0.createCell(4);
			title4.setCellStyle(cellStyle2);
			title4.setCellValue("比重");
			//今年结束
			
			//去年开始
			CellStyle lastYearCell02 = workbook.createCellStyle();
			lastYearCell02.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());// 设置背景色
			lastYearCell02.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			Cell lastYearTitle = row0.createCell(5);
			lastYearTitle.setCellStyle(lastYearCell02);
			lastYearTitle.setCellValue("辖区");
			
			Cell lastYearTitle1 = row0.createCell(6);
			lastYearTitle1.setCellStyle(lastYearCell02);
			lastYearTitle1.setCellValue("行业");
			
			Cell lastYearTitle2 = row0.createCell(7);
			lastYearTitle2.setCellStyle(lastYearCell02);
			lastYearTitle2.setCellValue("数量");
			
			Cell lastYearTitle3 = row0.createCell(8);
			lastYearTitle3.setCellStyle(lastYearCell02);
			lastYearTitle3.setCellValue("总数");
			
			Cell lastYearTitle4 = row0.createCell(9);
			lastYearTitle4.setCellStyle(lastYearCell02);
			lastYearTitle4.setCellValue("比重");
			
			
			//去年结束
			
			
			
			
			
			
			
			int rowNum = 2;
			for (int i = 0; i < list.size(); i++) {
				
				//今年数据
				System.out.println(list.get(i));
				Map maps =  (Map) list.get(i);
				
				Row row = sheet.createRow(rowNum);
				Cell cell = row.createCell(0);
				//cell.setCellStyle(cellStyle);
				String xiaQu = "";
				if(maps.get("辖区")!=null) {
					xiaQu = maps.get("辖区").toString();
				}
				cell.setCellValue(xiaQu);
				
				
				Cell cell1 = row.createCell(1);
				//cell1.setCellStyle(cellStyle);
				String hangYe = "";
				if(maps.get("行业")!=null) {
					hangYe = maps.get("行业").toString();
				}
				cell1.setCellValue(hangYe);
				
				
				Cell cell2 = row.createCell(2);
				//cell2.setCellStyle(cellStyle);
				String num = "";
				if(maps.get("数量")!=null) {
					num = maps.get("数量").toString();
				}
				cell2.setCellValue(num);
				
				Cell cell3 = row.createCell(3);
				//cell3.setCellStyle(cellStyle);
				String total = "";
				if(maps.get("总数")!=null) {
					total = maps.get("总数").toString();
				}
				cell3.setCellValue(total);
				
				Cell cell4 = row.createCell(4);
				//cell4.setCellStyle(cellStyle);
				 DecimalFormat df = new DecimalFormat("#.00");
				Double bilv = Double.valueOf(num)/Double.valueOf(total)*100;
				cell4.setCellValue(df.format(bilv)+"%");
				//今年数据完
				
				
				
				
				//显示去年数据
				System.out.println(list.get(i));
				Map lastYearmaps =  (Map) list2.get(i);
				
				Cell lastYearcell = row.createCell(5);
				//cell.setCellStyle(cellStyle);
				String lastYearXiaQu = "";
				if(lastYearmaps.get("辖区")!=null) {
					lastYearXiaQu = lastYearmaps.get("辖区").toString();
				}
				lastYearcell.setCellValue(lastYearXiaQu);
				
				
				Cell lastYearcell1 = row.createCell(6);
				//cell1.setCellStyle(cellStyle);
				String lasthangYe = "";
				if(lastYearmaps.get("行业")!=null) {
					lasthangYe = lastYearmaps.get("行业").toString();
				}
				lastYearcell1.setCellValue(lasthangYe);
				
				
				Cell lastYearcell2 = row.createCell(7);
				//cell2.setCellStyle(cellStyle);
				String lastYearnum = "";
				if(lastYearmaps.get("数量")!=null) {
					lastYearnum = lastYearmaps.get("数量").toString();
				}
				lastYearcell2.setCellValue(lastYearnum);
				
				Cell lastYearcell3 = row.createCell(8);
				//cell3.setCellStyle(cellStyle);
				String lastYeartotal = "";
				if(lastYearmaps.get("总数")!=null) {
					lastYeartotal = lastYearmaps.get("总数").toString();
				}
				lastYearcell3.setCellValue(lastYeartotal);
				
				Cell lastYearcell4 = row.createCell(9);
				//cell4.setCellStyle(cellStyle);
				Double lastYearbilv = Double.valueOf(lastYearnum)/Double.valueOf(lastYeartotal)*100;
				lastYearcell4.setCellValue(df.format(lastYearbilv)+"%");
				
				
				rowNum++;
				
				
			}
			
	         
			workbook.write(response.getOutputStream());

			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
		return null;
	}

}
