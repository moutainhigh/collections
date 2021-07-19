package com.gwssi.report.excelmodel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.util.CellRangeAddress;

public class JiaGeJuBaoXinXi {

	public static void main(String[] args) throws IOException {
		HSSFWorkbook wb=new HSSFWorkbook();//excel表格对象
		HSSFSheet st = wb.createSheet();//sheet对象
		HSSFRow row = st.createRow(100);
		HSSFCell cell = row.createCell(9);
		st.addMergedRegion(new CellRangeAddress(0,0, 0, 8));
		
	}
	public void testSomeThing() throws FileNotFoundException, IOException{
		// excel模板路径
				File fi = new File("D:\\testExcel\\test.xls");
				POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fi));
				// 读取excel模板
				HSSFWorkbook wb = new HSSFWorkbook(fs);
				// 读取了模板内所有sheet内容
				HSSFSheet sheet = wb.getSheetAt(0);
				// System.out.println(sheet.getRow(13).getCell(5).getRichStringCellValue());
				System.out.println(sheet.getRow(13).getCell(5).getNumericCellValue());
				// 在相应的单元格进行赋值
				// HSSFCell cell = sheet.getRow(1).getCell(1);
				// cell.setCellValue("测试");
				// HSSFCell cell2 = sheet.getRow(3).getCell(3);
				// cell2.setCellValue("数据");
				// HSSFCell cell3 = sheet.getRow(0).getCell(0);
				// cell3.setCellValue("大标题");
				// 修改模板内容导出新模板
				FileOutputStream out = new FileOutputStream("D:/export.xls");
				wb.write(out);

				out.close();
	} 

	public HSSFWorkbook getHSSFWorkbook(HttpServletRequest req,
			Map<String, Map> num) {
		String str = req.getSession().getServletContext().getRealPath("/")
				+ "\\files\\excelmodel\\jiagejubaoxinxicaiji.xls";
		File fi = new File(str);
		POIFSFileSystem fs;
		HSSFWorkbook wb = null;
		try {
			fs = new POIFSFileSystem(new FileInputStream(fi));
			wb = new HSSFWorkbook(fs);
			HSSFSheet at = wb.getSheetAt(0);
			String banJieLv = null;
			String heji = num.get("合计").get("数量").toString();
			String banjie = num.get("办结件数").get("数量").toString();
			if (heji != null && !"0".equals(heji) && heji.length() > 0) {
				Double d = Double.valueOf(banjie) / Double.valueOf(heji) * 100;
				BigDecimal   b   =   new   BigDecimal(d);
				banJieLv = String.valueOf(b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue())+"%";
			} else {
				banJieLv = "0%";
			}
			// at.getRow(2).getCell(4).setCellValue("价格政策咨询数量");
			at.getRow(2).getCell(4)
					.setCellValue(num.get("价格政策咨询").get("数量").toString());
			// at.getRow(2).getCell(5).setCellValue("价格政策咨询同比");
			at.getRow(2).getCell(5)
					.setCellValue(num.get("价格政策咨询").get("同比").toString());
			// at.getRow(3).getCell(4).setCellValue("违法行为举报数量");
			at.getRow(3).getCell(4)
					.setCellValue(num.get("违法行为举报").get("数量").toString());
			// at.getRow(3).getCell(5).setCellValue("违法行为举报同比");
			at.getRow(3).getCell(5)
					.setCellValue(num.get("违法行为举报").get("同比").toString());
			// at.getRow(4).getCell(4).setCellValue("合计数量");
			at.getRow(4).getCell(4)
					.setCellValue(num.get("合计").get("数量").toString());
			// at.getRow(4).getCell(5).setCellValue("合计同比");
			at.getRow(4).getCell(5)
					.setCellValue(num.get("合计").get("同比").toString());
			// at.getRow(5).getCell(4).setCellValue("来信数量");
			at.getRow(5).getCell(4)
					.setCellValue(num.get("来信").get("数量").toString());
			// at.getRow(5).getCell(5).setCellValue("来信同比");
			at.getRow(5).getCell(5)
					.setCellValue(num.get("来信").get("同比").toString());
			// at.getRow(6).getCell(4).setCellValue("来电数量");
			at.getRow(6).getCell(4)
					.setCellValue(num.get("来电").get("数量").toString());
			// at.getRow(6).getCell(5).setCellValue("来电同比");
			at.getRow(6).getCell(5)
					.setCellValue(num.get("来电").get("同比").toString());
			// at.getRow(7).getCell(4).setCellValue("电子邮件数量");
			at.getRow(7).getCell(4)
					.setCellValue(num.get("电子邮件").get("数量").toString());
			// at.getRow(7).getCell(5).setCellValue("电子邮件同比");
			at.getRow(7).getCell(5)
					.setCellValue(num.get("电子邮件").get("同比").toString());
			// at.getRow(8).getCell(4).setCellValue("来访数量");
			at.getRow(8).getCell(4)
					.setCellValue(num.get("来访").get("数量").toString());
			// at.getRow(8).getCell(5).setCellValue("来访同比");
			at.getRow(8).getCell(5)
					.setCellValue(num.get("来访").get("同比").toString());
			// at.getRow(9).getCell(4).setCellValue("上级交办数量");
			at.getRow(9).getCell(4)
					.setCellValue(num.get("上级交办").get("数量").toString());
			// at.getRow(9).getCell(5).setCellValue("上级交办同比");
			at.getRow(9).getCell(5)
					.setCellValue(num.get("上级交办").get("同比").toString());
			// at.getRow(10).getCell(4).setCellValue("部门转办数量");
			at.getRow(10).getCell(4)
					.setCellValue(num.get("部门转办").get("数量").toString());
			// at.getRow(10).getCell(5).setCellValue("部门转办同比");
			at.getRow(10).getCell(5)
					.setCellValue(num.get("部门转办").get("同比").toString());
			// at.getRow(11).getCell(4).setCellValue("办结件数数量");
			at.getRow(11).getCell(4)
					.setCellValue(num.get("办结件数").get("数量").toString());
			// at.getRow(11).getCell(5).setCellValue("办结件数同比");
			at.getRow(11).getCell(5)
					.setCellValue(num.get("办结件数").get("同比").toString());
			at.getRow(12).getCell(4).setCellValue(banJieLv);
			at.getRow(12).getCell(5).setCellValue("0%");
			// at.getRow(13).getCell(4).setCellValue("咨询办结件数量");
			at.getRow(13).getCell(4)
					.setCellValue(num.get("咨询办结件数").get("数量").toString());
			// at.getRow(13).getCell(5).setCellValue("咨询办结件同比");
			at.getRow(13).getCell(5)
					.setCellValue(num.get("咨询办结件数").get("同比").toString());
			// at.getRow(14).getCell(4).setCellValue("举报办结件数量");
			at.getRow(14).getCell(4)
					.setCellValue(num.get("举报办结件数").get("数量").toString());
			// at.getRow(14).getCell(5).setCellValue("举报办结件同比");
			at.getRow(14).getCell(5)
					.setCellValue(num.get("举报办结件数").get("同比").toString());
			// at.getRow(15).getCell(4).setCellValue("协调办结案件数量");
			at.getRow(15).getCell(4)
					.setCellValue(num.get("协调办结案件").get("数量").toString());
			// at.getRow(15).getCell(5).setCellValue("协调办结案件同比");
			at.getRow(15).getCell(5)
					.setCellValue(num.get("协调办结案件").get("同比").toString());
			// at.getRow(16).getCell(4).setCellValue("查处办结案件数量");
			at.getRow(16).getCell(4)
					.setCellValue(num.get("查处办结结案").get("数量").toString());
			// at.getRow(16).getCell(5).setCellValue("查处办结案件同比");
			at.getRow(16).getCell(5)
					.setCellValue(num.get("查处办结结案").get("同比").toString());
			// at.getRow(17).getCell(4).setCellValue("退还金额数量");
			// at.getRow(17).getCell(5).setCellValue("退还金额同比");
			// at.getRow(18).getCell(4).setCellValue("没收违法所得数量");
			// at.getRow(18).getCell(5).setCellValue("没收违法所得同比");
			// at.getRow(19).getCell(4).setCellValue("罚款数量");
			// at.getRow(19).getCell(5).setCellValue("罚款同比");
			// at.getRow(20).getCell(4).setCellValue("经济制裁总额数量");
			// at.getRow(20).getCell(5).setCellValue("经济制裁总额同比");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wb;
	}

}