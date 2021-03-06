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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.report.service.Data05Service;

@Controller
@RequestMapping("data05")
public class Data05Controller {

	@Autowired
	private Data05Service data05Service;

	@RequestMapping("/list")
	@ResponseBody
	public Map getList(String startTime, HttpServletRequest request) throws ParseException {
		Map map = new HashMap();
		if (startTime == null) {
			return null;
		}
		List list = data05Service.getList(startTime);
		List list2 = data05Service.getList2(startTime);

		request.getSession().removeAttribute("time05");
		request.getSession().removeAttribute("list05");
		request.getSession().removeAttribute("list051");

		request.getSession().setAttribute("time05", startTime);
		request.getSession().setAttribute("list05", list);
		request.getSession().setAttribute("list051", list2);

		return map;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/de", method = RequestMethod.GET)
	public String downLoadExcel(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ParseException {
		 DecimalFormat df = new DecimalFormat("#.00");
		// List list = data01Service.getList(null);
		List list = (List) request.getSession().getAttribute("list05");
		List list2 = (List) request.getSession().getAttribute("list051");
		String currentTime = (String) request.getSession().getAttribute("time05");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = currentTime;
		Date dt = sdf.parse(str);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.YEAR, -1);// ?????????1???
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);
		System.out.println(reStr);
		String lastYear = reStr; // ??????????????????

		if (list != null && list.size() > 0) {
			String fileName = "test.xlsx";
			response.setHeader("Content-disposition",
					"attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));// ???????????????????????????
			response.setContentType("APPLICATION/OCTET-STREAM;charset=UTF-8");// ????????????
			response.setHeader("Cache-Control", "no-cache");// ?????????
			response.setDateHeader("Expires", 0);// ???????????????

			XSSFWorkbook workbook = new XSSFWorkbook();

			XSSFSheet sheet = workbook.createSheet();
			sheet.setColumnWidth(4, 20 * 256);
			// CellStyle cellStyle = workbook.createCellStyle();

			// cellStyle.setDataFormat(workbook.createDataFormat().getFormat("yyyy-MM-dd
			// HH:mm:ss"));

			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));// ???????????????
			sheet.addMergedRegion(new CellRangeAddress(13, 13, 0,6));//???14???

			Row row00 = sheet.createRow(0);
			Cell year = row00.createCell(0);
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor((short) 13);// ???????????????
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			year.setCellStyle(cellStyle);
			year.setCellValue(currentTime + "??????");

			Row row0 = sheet.createRow(1);
			// ????????????
			CellStyle cellStyle2 = workbook.createCellStyle();
			cellStyle2.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());// ???????????????
			cellStyle2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			Cell xuhao = row0.createCell(0);
			xuhao.setCellStyle(cellStyle2);
			xuhao.setCellValue("??????");

			Cell title = row0.createCell(1);
			title.setCellStyle(cellStyle2);
			title.setCellValue("??????");

			Cell title1 = row0.createCell(2);
			title1.setCellStyle(cellStyle2);
			title1.setCellValue("?????????");

			Cell title2 = row0.createCell(3);
			title2.setCellStyle(cellStyle2);
			title2.setCellValue("??????");

			Cell title3 = row0.createCell(4);
			title3.setCellStyle(cellStyle2);
			title3.setCellValue("??????????????????");
			
			Cell title4 = row0.createCell(5);
			title4.setCellStyle(cellStyle2);
			title4.setCellValue("??????");
			
			
			Cell title5 = row0.createCell(6);
			title5.setCellStyle(cellStyle2);
			title5.setCellValue("??????");

			int rowNum = 2;
			for (int i = 0; i < list.size(); i++) {

				// ????????????
				System.out.println(list.get(i));
				Map maps = (Map) list.get(i);

				Row row = sheet.createRow(rowNum);

				Cell xuHaoNum = row.createCell(0);
				// cell.setCellStyle(cellStyle);
				xuHaoNum.setCellValue(i+1);

				Cell cell = row.createCell(1);
				// cell.setCellStyle(cellStyle);
				String xiaQu = "";
				if (maps.get("??????") != null) {
					xiaQu = maps.get("??????").toString();
				}
				cell.setCellValue(xiaQu);

				Cell cell1 = row.createCell(2);
				// cell1.setCellStyle(cellStyle);
				String hangYe = "";
				if (maps.get("?????????") != null) {
					hangYe = maps.get("?????????").toString();
				}
				cell1.setCellValue(hangYe);

				Cell cell2 = row.createCell(3);
				// cell2.setCellStyle(cellStyle);
				String num = "";
				if (maps.get("??????") != null) {
					num = maps.get("??????").toString();
				}
				cell2.setCellValue(num);

				Cell cell3 = row.createCell(4);
				// cell3.setCellStyle(cellStyle);
				String total = "";
				if (maps.get("??????") != null) {
					total = maps.get("??????").toString();
				}
				cell3.setCellValue(total);
				
				
				Cell cell5 = row.createCell(5);
				//cell6.setCellStyle(cellStyle);
				Double bilv = Double.valueOf(num)/Double.valueOf(total)*100;
				cell5.setCellValue(df.format(bilv)+"%");
				
				
				Cell cell6 = row.createCell(6);
				//cell6.setCellStyle(cellStyle);
				cell6.setCellValue(total);

				// ???????????????

				rowNum++;

			}

			
			
			
			// ?????????
			Row leiJiDate = sheet.createRow(13);
			Cell leiJiCellDate = leiJiDate.createCell(0);
			CellStyle leijiCellStyle = workbook.createCellStyle();
			leijiCellStyle.setFillForegroundColor((short) 13);// ???????????????
			leijiCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			leiJiCellDate.setCellStyle(leijiCellStyle);
			leiJiCellDate.setCellValue("???????????????" + currentTime + "?????????????????????");
			
			
			Row leijiAll = sheet.createRow(14);
			CellStyle leijiTitle = workbook.createCellStyle();
			leijiTitle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());// ???????????????
			leijiTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			Cell leijiXuhao = leijiAll.createCell(0);
			leijiXuhao.setCellStyle(cellStyle2);
			leijiXuhao.setCellValue("??????");

			Cell leiJiTitle = leijiAll.createCell(1);
			leiJiTitle.setCellStyle(cellStyle2);
			leiJiTitle.setCellValue("??????");

			Cell leiJiJianGuan = leijiAll.createCell(2);
			leiJiJianGuan.setCellStyle(cellStyle2);
			leiJiJianGuan.setCellValue("?????????");

			Cell leiJiShuLian = leijiAll.createCell(3);
			leiJiShuLian.setCellStyle(cellStyle2);
			leiJiShuLian.setCellValue("??????");

			Cell leiJiZhongShu = leijiAll.createCell(4);
			leiJiZhongShu.setCellStyle(cellStyle2);
			leiJiZhongShu.setCellValue("??????????????????");
			

			Cell leiJiBiZhong = leijiAll.createCell(5);
			leiJiBiZhong.setCellStyle(cellStyle2);
			leiJiBiZhong.setCellValue("??????");
			
			
			Cell leiJIZongJi = leijiAll.createCell(6);
			leiJIZongJi.setCellStyle(cellStyle2);
			leiJIZongJi.setCellValue("??????");
			
			int leiJi = 15;
			for (int i = 0; i < list2.size(); i++) {

				// ????????????
				System.out.println(list2.get(i));
				Map maps = (Map) list2.get(i);

				Row row = sheet.createRow(leiJi);

				Cell xuHaoNum = row.createCell(0);
				// cell.setCellStyle(cellStyle);
				xuHaoNum.setCellValue(i+1);

				Cell cell = row.createCell(1);
				// cell.setCellStyle(cellStyle);
				String xiaQu = "";
				if (maps.get("??????") != null) {
					xiaQu = maps.get("??????").toString();
				}
				cell.setCellValue(xiaQu);

				Cell cell1 = row.createCell(2);
				// cell1.setCellStyle(cellStyle);
				String hangYe = "";
				if (maps.get("?????????") != null) {
					hangYe = maps.get("?????????").toString();
				}
				cell1.setCellValue(hangYe);

				Cell cell2 = row.createCell(3);
				// cell2.setCellStyle(cellStyle);
				String num = "";
				if (maps.get("??????") != null) {
					num = maps.get("??????").toString();
				}
				cell2.setCellValue(num);

				Cell cell3 = row.createCell(4);
				// cell3.setCellStyle(cellStyle);
				String total = "";
				if (maps.get("??????") != null) {
					total = maps.get("??????").toString();
				}
				cell3.setCellValue(total);

				
				Cell cell5 = row.createCell(5);
				//cell6.setCellStyle(cellStyle);
				Double bilv = Double.valueOf(num)/Double.valueOf(total)*100;
				cell5.setCellValue(df.format(bilv)+"%");
				
				
				Cell cell6 = row.createCell(6);
				//cell6.setCellStyle(cellStyle);
				cell6.setCellValue(total);
				
				// ?????????

				leiJi++;

			}

			workbook.write(response.getOutputStream());

			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
		return null;
	}
}
