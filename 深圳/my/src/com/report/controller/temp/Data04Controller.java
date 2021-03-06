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

import com.report.service.Data04Service;
import com.report.service.Data05Service;

@Controller
@RequestMapping("data04")
public class Data04Controller {

	@Autowired
	private Data04Service data04Service;

	@RequestMapping("/list")
	@ResponseBody
	public Map getList(String startTime, HttpServletRequest request) throws ParseException {
		Map map = new HashMap();
		if (startTime == null) {
			return null;
		}

		List list1 = data04Service.list2BenQi(startTime);
		List list2 = data04Service.list2_QuNianBenQi(startTime);

		List list3 = data04Service.list1_JinNianLeiJi(startTime);
		List list4 = data04Service.list1_QuNianLeiJi(startTime);

		request.getSession().removeAttribute("time04");
		request.getSession().removeAttribute("list04");
		request.getSession().removeAttribute("list041");
		request.getSession().removeAttribute("list042");
		request.getSession().removeAttribute("list0421");

		request.getSession().setAttribute("time04", startTime);
		request.getSession().setAttribute("list04", list1);
		request.getSession().setAttribute("list041", list2);

		request.getSession().setAttribute("list042", list3);
		request.getSession().setAttribute("list0421", list4);

		return map;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/de", method = RequestMethod.GET)
	public String downLoadExcel(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ParseException {
		DecimalFormat df = new DecimalFormat("#.00");
		// List list = data01Service.getList(null);
		List list = (List) request.getSession().getAttribute("list04");
		List list2 = (List) request.getSession().getAttribute("list041");

		List list3 = (List) request.getSession().getAttribute("list042");
		List list4 = (List) request.getSession().getAttribute("list0421");
		String currentTime = (String) request.getSession().getAttribute("time04");

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
			String fileName = "4.??????????????????????????????????????????????????????"+currentTime+".xlsx";
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
			sheet.addMergedRegion(new CellRangeAddress(33, 33, 0, 6));// ???14???
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 9, 15));// ???1???9???15???
			sheet.addMergedRegion(new CellRangeAddress(33, 33, 9, 15));// ???14??????9???15???
			
			
			
			for (int i = 3; i < list.size()+2; i++) {
				if(i%3==0) {
					//sheet.addMergedRegion(new CellRangeAddress(2,4,0,0));//???????????????
					//sheet.addMergedRegion(new CellRangeAddress((i-1),(i+2),1,1));//???????????????
					//sheet.addMergedRegion(new CellRangeAddress((i-1),(i+2),5,5));//???????????????(????????????)
					sheet.addMergedRegion(new CellRangeAddress((i-1), (i+1), 1, 1));// ???????????????
					sheet.addMergedRegion(new CellRangeAddress((i-1), (i+1), 10, 10));// ???????????????
					
					sheet.addMergedRegion(new CellRangeAddress((i-1+33), (i+1+33), 1, 1));// ???????????????
					sheet.addMergedRegion(new CellRangeAddress((i-1+33), (i+1+33), 10, 10));// ???????????????

				}
			}
			
		//	sheet.addMergedRegion(new CellRangeAddress(2, 4, 1, 1));// ???????????????
			

			Row row00 = sheet.createRow(0);
			Cell year = row00.createCell(0);
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor((short) 13);// ???????????????
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			year.setCellStyle(cellStyle);
			year.setCellValue(currentTime + "???????????????????????????");

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
			
			
			
			//???????????????
		    year = row00.createCell(9);
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor((short) 13);// ???????????????
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			year.setCellStyle(cellStyle);
			year.setCellValue(lastYear + "??????");
			
			 cellStyle2= workbook.createCellStyle();
			 cellStyle2.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());// ???????????????
			 cellStyle2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			 xuhao = row0.createCell(9);
			xuhao.setCellStyle(cellStyle2);
			xuhao.setCellValue("??????");

			 title = row0.createCell(10);
			 title.setCellStyle(cellStyle2);
			 title.setCellValue("??????");

			title1 = row0.createCell(11);
			title1.setCellStyle(cellStyle2);
			title1.setCellValue("?????????");

			title2 = row0.createCell(12);
			title2.setCellStyle(cellStyle2);
			title2.setCellValue("??????");

			title3 = row0.createCell(13);
			title3.setCellStyle(cellStyle2);
			title3.setCellValue("??????????????????");

			 title4 = row0.createCell(14);
			title4.setCellStyle(cellStyle2);
			title4.setCellValue("??????");

			title5 = row0.createCell(15);
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
				xuHaoNum.setCellValue(i + 1);

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
				// cell6.setCellStyle(cellStyle);
				Double bilv = Double.valueOf(num) / Double.valueOf(total) * 100;
				cell5.setCellValue(df.format(bilv) + "%");

				Cell cell6 = row.createCell(6);
				// cell6.setCellStyle(cellStyle);
				cell6.setCellValue(total);

				// ??????????????????????????????????????????

				maps = (Map) list2.get(i);

				xuHaoNum = row.createCell(9);
				// cell.setCellStyle(cellStyle);
				xuHaoNum.setCellValue(i + 1);

				cell = row.createCell(10);
				// cell.setCellStyle(cellStyle);
				xiaQu = "";
				if (maps.get("??????") != null) {
					xiaQu = maps.get("??????").toString();
				}
				cell.setCellValue(xiaQu);

				cell1 = row.createCell(11);
				// cell1.setCellStyle(cellStyle);
				hangYe = "";
				if (maps.get("?????????") != null) {
					hangYe = maps.get("?????????").toString();
				}
				cell1.setCellValue(hangYe);
				cell2 = row.createCell(12);
				// cell2.setCellStyle(cellStyle);
				num = "";
				if (maps.get("??????") != null) {
					num = maps.get("??????").toString();
				}
				cell2.setCellValue(num);

				cell3 = row.createCell(13);
				// cell3.setCellStyle(cellStyle);
				total = "";
				if (maps.get("??????") != null) {
					total = maps.get("??????").toString();
				}
				cell3.setCellValue(total);

				cell5 = row.createCell(14);
				// cell6.setCellStyle(cellStyle);
				bilv = Double.valueOf(num) / Double.valueOf(total) * 100;
				cell5.setCellValue(df.format(bilv) + "%");

				cell6 = row.createCell(15);
				// cell6.setCellStyle(cellStyle);
				cell6.setCellValue(total);

				// ???????????????

				rowNum++;

			}

			// ??????????????????????????????????????????
			Row leiJiDate = sheet.createRow(33);
			Cell leiJiCellDate = leiJiDate.createCell(0);
			CellStyle leijiCellStyle = workbook.createCellStyle();
			leijiCellStyle.setFillForegroundColor((short) 13);// ???????????????
			leijiCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			leiJiCellDate.setCellStyle(leijiCellStyle);
			leiJiCellDate.setCellValue("????????????????????????" + currentTime + "?????????????????????");

			Row leijiAll = sheet.createRow(34);
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
			
			
			
			
			
			
			
			
			//????????????????????????
			leiJiCellDate = leiJiDate.createCell(9);
			leijiCellStyle = workbook.createCellStyle();
			leijiCellStyle.setFillForegroundColor((short) 13);// ???????????????
			leijiCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			leiJiCellDate.setCellStyle(leijiCellStyle);
			leiJiCellDate.setCellValue("????????????????????????" + lastYear + "?????????????????????(????????????)");

			leijiTitle = workbook.createCellStyle();
			leijiTitle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());// ???????????????
			leijiTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			 leijiXuhao = leijiAll.createCell(9);
			leijiXuhao.setCellStyle(cellStyle2);
			leijiXuhao.setCellValue("??????");

			leiJiTitle = leijiAll.createCell(10);
			leiJiTitle.setCellStyle(cellStyle2);
			leiJiTitle.setCellValue("??????");

			 leiJiJianGuan = leijiAll.createCell(11);
			leiJiJianGuan.setCellStyle(cellStyle2);
			leiJiJianGuan.setCellValue("?????????");

			 leiJiShuLian = leijiAll.createCell(12);
			leiJiShuLian.setCellStyle(cellStyle2);
			leiJiShuLian.setCellValue("??????");

			 leiJiZhongShu = leijiAll.createCell(13);
			leiJiZhongShu.setCellStyle(cellStyle2);
			leiJiZhongShu.setCellValue("??????????????????");

			 leiJiBiZhong = leijiAll.createCell(14);
			leiJiBiZhong.setCellStyle(cellStyle2);
			leiJiBiZhong.setCellValue("??????");

			 leiJIZongJi = leijiAll.createCell(15);
			leiJIZongJi.setCellStyle(cellStyle2);
			leiJIZongJi.setCellValue("??????");
			
			
			

			int leiJi = 35;
			for (int i = 0; i < list2.size(); i++) {

				// ????????????
				System.out.println(list3.get(i));
				Map maps = (Map) list3.get(i);

				Row row = sheet.createRow(leiJi);

				Cell xuHaoNum = row.createCell(0);
				// cell.setCellStyle(cellStyle);
				xuHaoNum.setCellValue(i + 1);

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
				if (maps.get("??????") != null) {
					xiaQu = maps.get("??????").toString();
				}
				cell.setCellValue(xiaQu);

				cell1 = row.createCell(11);
				// cell1.setCellStyle(cellStyle);
				 hangYe = "";
				if (maps.get("?????????") != null) {
					hangYe = maps.get("?????????").toString();
				}
				cell1.setCellValue(hangYe);

				cell2 = row.createCell(12);
				// cell2.setCellStyle(cellStyle);
				 num = "";
				if (maps.get("??????") != null) {
					num = maps.get("??????").toString();
				}
				cell2.setCellValue(num);

				 cell3 = row.createCell(13);
				// cell3.setCellStyle(cellStyle);
				 total = "";
				if (maps.get("??????") != null) {
					total = maps.get("??????").toString();
				}
				cell3.setCellValue(total);

				 cell5 = row.createCell(14);
				// cell6.setCellStyle(cellStyle);
				bilv = Double.valueOf(num) / Double.valueOf(total) * 100;
				cell5.setCellValue(df.format(bilv) + "%");

				 cell6 = row.createCell(15);
				// cell6.setCellStyle(cellStyle);
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
