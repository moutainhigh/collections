package com.gwssi.report.excelmodel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;


public class JiChaBiao01 {
	@SuppressWarnings("resource")
	public static void testSomeThing() throws FileNotFoundException, IOException{
		// excel模板路径
				File fi = new File("D:\\testExcel\\jichabiao01.xls");
				POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fi));
				// 读取excel模板
				HSSFWorkbook wb = new HSSFWorkbook(fs);
				// 读取了模板内所有sheet内容
				HSSFSheet sheet = wb.getSheetAt(0); 
				// System.out.println(sheet.getRow(13).getCell(5).getRichStringCellValue());
				System.out.println(sheet.getRow(0).getCell(0).getStringCellValue());
				// 在相应的单元格进行赋值
				// HSSFCell cell = sheet.getRow(1).getCell(1);
				// cell.setCellValue("测试");
				// HSSFCell cell2 = sheet.getRow(3).getCell(3);
				// cell2.setCellValue("数据");
				// HSSFCell cell3 = sheet.getRow(0).getCell(0);
				// cell3.setCellValue("大标题");
				// 修改模板内容导出新模板
				/*FileOutputStream out = new FileOutputStream("D:/export.xls");
				wb.write(out);
				out.close();*/
	}

	public HSSFWorkbook getHSSFWorkbook(HttpServletRequest req,String endTime,
			Map<String, Map<String, Map<String, String>>> num) {
		String str = req.getSession().getServletContext().getRealPath("/")
				+ "\\files\\excelmodel\\jichabiao01.xls";
		File fi = new File(str);
		POIFSFileSystem fs;
		String strs="填报单位：（公章）                                        "+endTime.substring(0,4)+" 年   第 "+getJiJie(endTime.substring(5,7))+" 季度          单    位：件";
		String strss=endTime.substring(0,4)+"年第"+getJiJie1(endTime.substring(5, 7))+"季度投诉举报基础数据报送表";
		HSSFWorkbook wb = null;
		try {
			fs = new POIFSFileSystem(new FileInputStream(fi));
			wb = new HSSFWorkbook(fs);
			HSSFSheet at = wb.getSheetAt(0);
			at.getRow(0).getCell(0).setCellValue(strss);
			at.getRow(1).getCell(0).setCellValue(strs);
			at.getRow(4).getCell(4)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("电话").get("食品"))));
			at.getRow(4).getCell(5)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("电话").get("保健食品"))));
			at.getRow(4).getCell(6)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("电话").get("药品"))));
			at.getRow(4).getCell(7)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("电话").get("化妆品"))));
			at.getRow(4).getCell(8)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("电话").get("医疗器械"))));
			at.getRow(4).getCell(9)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("电话").get("合计行"))));
			
			at.getRow(5).getCell(4)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("网络").get("食品"))));
			at.getRow(5).getCell(5)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("网络").get("保健食品"))));
			at.getRow(5).getCell(6)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("网络").get("药品"))));
			at.getRow(5).getCell(7)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("网络").get("化妆品"))));
			at.getRow(5).getCell(8)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("网络").get("医疗器械"))));
			at.getRow(5).getCell(9)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("网络").get("合计行"))));
			
			at.getRow(6).getCell(4)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("信件").get("食品"))));
			at.getRow(6).getCell(5)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("信件").get("保健食品"))));
			at.getRow(6).getCell(6)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("信件").get("药品"))));
			at.getRow(6).getCell(7)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("信件").get("化妆品"))));
			at.getRow(6).getCell(8)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("信件").get("医疗器械"))));
			at.getRow(6).getCell(9)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("信件").get("合计行"))));
			
			at.getRow(7).getCell(4)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("走访").get("食品"))));
			at.getRow(7).getCell(5)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("走访").get("保健食品"))));
			at.getRow(7).getCell(6)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("走访").get("药品"))));
			at.getRow(7).getCell(7)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("走访").get("化妆品"))));
			at.getRow(7).getCell(8)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("走访").get("医疗器械"))));
			at.getRow(7).getCell(9)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("走访").get("合计行"))));
			
			at.getRow(8).getCell(4)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("其他").get("食品"))));
			at.getRow(8).getCell(5)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("其他").get("保健食品"))));
			at.getRow(8).getCell(6)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("其他").get("药品"))));
			at.getRow(8).getCell(7)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("其他").get("化妆品"))));
			at.getRow(8).getCell(8)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("其他").get("医疗器械"))));
			at.getRow(8).getCell(9)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("其他").get("合计行"))));
			
			at.getRow(9).getCell(4)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("合计").get("食品"))));
			at.getRow(9).getCell(5)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("合计").get("保健食品"))));
			at.getRow(9).getCell(6)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("合计").get("药品"))));
			at.getRow(9).getCell(7)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("合计").get("化妆品"))));
			at.getRow(9).getCell(8)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("合计").get("医疗器械"))));
			at.getRow(9).getCell(9)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第一部分").get("合计").get("合计行")))/2);
			
			at.getRow(10).getCell(4)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("投诉举报受理").get("食品"))));
			at.getRow(10).getCell(5)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("投诉举报受理").get("保健食品"))));
			at.getRow(10).getCell(6)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("投诉举报受理").get("药品"))));
			at.getRow(10).getCell(7)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("投诉举报受理").get("化妆品"))));
			at.getRow(10).getCell(8)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("投诉举报受理").get("医疗器械"))));
			at.getRow(10).getCell(9)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("投诉举报受理").get("合计行"))));
			
			at.getRow(11).getCell(4)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("投诉举报不受理").get("食品"))));
			at.getRow(11).getCell(5)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("投诉举报不受理").get("保健食品"))));
			at.getRow(11).getCell(6)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("投诉举报不受理").get("药品"))));
			at.getRow(11).getCell(7)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("投诉举报不受理").get("化妆品"))));
			at.getRow(11).getCell(8)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("投诉举报不受理").get("医疗器械"))));
			at.getRow(11).getCell(9)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("投诉举报不受理").get("合计行"))));
			
			at.getRow(12).getCell(4)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("咨询").get("食品"))));
			at.getRow(12).getCell(5)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("咨询").get("保健食品"))));
			at.getRow(12).getCell(6)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("咨询").get("药品"))));
			at.getRow(12).getCell(7)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("咨询").get("化妆品"))));
			at.getRow(12).getCell(8)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("咨询").get("医疗器械"))));
			at.getRow(12).getCell(9)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("咨询").get("合计行"))));
			
			at.getRow(13).getCell(4)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("建议").get("食品"))));
			at.getRow(13).getCell(5)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("建议").get("保健食品"))));
			at.getRow(13).getCell(6)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("建议").get("药品"))));
			at.getRow(13).getCell(7)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("建议").get("化妆品"))));
			at.getRow(13).getCell(8)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("建议").get("医疗器械"))));
			at.getRow(13).getCell(9)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("建议").get("合计行"))));
			
			at.getRow(14).getCell(4)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("合计").get("食品"))));
			at.getRow(14).getCell(5)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("合计").get("保健食品"))));
			at.getRow(14).getCell(6)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("合计").get("药品"))));
			at.getRow(14).getCell(7)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("合计").get("化妆品"))));
			at.getRow(14).getCell(8)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("合计").get("医疗器械"))));
			at.getRow(14).getCell(9)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第二部分").get("合计").get("合计行")))/2);
			
			at.getRow(15).getCell(4)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("研制").get("食品"))));
			at.getRow(15).getCell(5)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("研制").get("保健食品"))));
			at.getRow(15).getCell(6)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("研制").get("药品"))));
			at.getRow(15).getCell(7)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("研制").get("化妆品"))));
			at.getRow(15).getCell(8)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("研制").get("医疗器械"))));
			at.getRow(15).getCell(9)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("研制").get("合计行"))));
			
			at.getRow(16).getCell(4)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("生产").get("食品"))));
			at.getRow(16).getCell(5)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("生产").get("保健食品"))));
			at.getRow(16).getCell(6)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("生产").get("药品"))));
			at.getRow(16).getCell(7)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("生产").get("化妆品"))));
			at.getRow(16).getCell(8)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("生产").get("医疗器械"))));
			at.getRow(16).getCell(9)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("生产").get("合计行"))));
			
			at.getRow(17).getCell(4)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("流通").get("食品"))));
			at.getRow(17).getCell(5)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("流通").get("保健食品"))));
			at.getRow(17).getCell(6)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("流通").get("药品"))));
			at.getRow(17).getCell(7)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("流通").get("化妆品"))));
			at.getRow(17).getCell(8)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("流通").get("医疗器械"))));
			at.getRow(17).getCell(9)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("流通").get("合计行"))));
			
			at.getRow(18).getCell(4)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("消费").get("食品"))));
			at.getRow(18).getCell(5)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("消费").get("保健食品"))));
			at.getRow(18).getCell(6)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("消费").get("药品"))));
			at.getRow(18).getCell(7)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("消费").get("化妆品"))));
			at.getRow(18).getCell(8)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("消费").get("医疗器械"))));
			at.getRow(18).getCell(9)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("消费").get("合计行"))));
			
			at.getRow(19).getCell(4)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("其他").get("食品"))));
			at.getRow(19).getCell(5)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("其他").get("保健食品"))));
			at.getRow(19).getCell(6)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("其他").get("药品"))));
			at.getRow(19).getCell(7)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("其他").get("化妆品"))));
			at.getRow(19).getCell(8)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("其他").get("医疗器械"))));
			at.getRow(19).getCell(9)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("其他").get("合计行"))));
			
			at.getRow(20).getCell(4)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("合计").get("食品"))));
			at.getRow(20).getCell(5)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("合计").get("保健食品"))));
			at.getRow(20).getCell(6)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("合计").get("药品"))));
			at.getRow(20).getCell(7)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("合计").get("化妆品"))));
			at.getRow(20).getCell(8)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("合计").get("医疗器械"))));
			at.getRow(20).getCell(9)
			.setCellValue(Integer.valueOf(String.valueOf(num.get("第三部分").get("合计").get("合计行")))/2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wb;
	}
	private String getJiJie1(String substring) {
		if ("01".equals(substring)||"02".equals(substring)||"03".equals(substring)) {
			return "一";
		}else if("04".equals(substring)||"05".equals(substring)||"06".equals(substring)){
			return "二";
		}else if("07".equals(substring)||"08".equals(substring)||"09".equals(substring)){
			return "三";
		}else if ("10".equals(substring)||"11".equals(substring)||"12".equals(substring)) {
			return "四";
		}else{
			return "";
		}
	}
	private String getJiJie(String substring) {
		if ("01".equals(substring)||"02".equals(substring)||"03".equals(substring)) {
			return "1";
		}else if("04".equals(substring)||"05".equals(substring)||"06".equals(substring)){
			return "2";
		}else if("07".equals(substring)||"08".equals(substring)||"09".equals(substring)){
			return "3";
		}else if ("10".equals(substring)||"11".equals(substring)||"12".equals(substring)) {
			return "4";
		}else{
			return "";
		}
	
	}

}