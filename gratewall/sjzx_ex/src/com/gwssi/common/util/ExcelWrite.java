package com.gwssi.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelWrite
{

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		ExcelWrite ew = new ExcelWrite();
		File tempFile = new File("d:/temp/output3.xls");

		OutputStream os = null;
		try {
			os = new FileOutputStream(tempFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[][] numthree; // 定义一个float类型的2维数组
		numthree = new String[5][8]; // 为它分配5行8列的空间大小
		numthree[0][0] = "1.1f"; // 通过下标索引去访问 1行1列=1.1
		numthree[1][0] = "1.2f"; // 2行1列=1.2
		numthree[2][0] = "1.3f"; // 3行1列=1.3
		numthree[3][0] = "1.4f"; // 4行1列=1.4
		numthree[4][0] = "1.5f"; // 5行1列=1.5
		numthree[0][1] = "2.1f";
		numthree[1][1] = "2.2f";
		numthree[2][1] = "2.3f";
		numthree[3][1] = "2.4f";
		numthree[4][1] = "2.5f";
		numthree[0][2] = "3.1f";
		numthree[1][2] = "3.2f";
		numthree[2][2] = "3.3f";
		numthree[3][2] = "3.4f";
		numthree[4][2] = "3.5f";
		numthree[0][3] = "4.1f";
		String[] Titles = { "列1", "列2", "列3", "列4", "列5", "列6", "列7", "列8", };

		ew.expordExcel(os, "测试", Titles, numthree);

		System.out.println(numthree[0][0]); // 打印换行输出喽
		System.out.println(numthree[1][0]);
		System.out.println(numthree[2][0]);
		System.out.println(numthree[3][0]);
		System.out.println(numthree[4][0]);
		System.out.println(numthree.length);
		System.out.println(numthree[0].length);
	}

	public String toexcel() throws Exception
	{

		File tempFile = new File("d:/temp/output.xls");
		WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
		WritableSheet sheet = workbook.createSheet("TestCreateExcel", 0);

		// 一些临时变量，用于写到excel中
		Label l = null;
		jxl.write.Number n = null;
		jxl.write.DateTime d = null;

		// 预定义的一些字体和格式，同一个Excel中最好不要有太多格式
		WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 12,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLUE);
		WritableCellFormat headerFormat = new WritableCellFormat(headerFont);

		WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.RED);
		WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

		WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLACK);
		WritableCellFormat detFormat = new WritableCellFormat(detFont);

		NumberFormat nf = new NumberFormat("0.00000"); // 用于Number的格式
		WritableCellFormat priceFormat = new WritableCellFormat(detFont, nf);

		DateFormat df = new DateFormat("yyyy-MM-dd");// 用于日期的
		WritableCellFormat dateFormat = new WritableCellFormat(detFont, df);

		// 剩下的事情，就是用上面的内容和格式创建一些单元格，再加到sheet中
		l = new Label(0, 0, "用于测试的Excel文件", headerFormat);
		sheet.addCell(l);

		// add Title
		int column = 0;
		l = new Label(column++, 2, "标题", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "日期", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "货币", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "价格", titleFormat);
		sheet.addCell(l);

		// add detail
		int i = 0;
		column = 0;
		l = new Label(column++, i + 3, "标题 " + i, detFormat);
		sheet.addCell(l);
		d = new DateTime(column++, i + 3, new java.util.Date(), dateFormat);
		sheet.addCell(d);
		l = new Label(column++, i + 3, "CNY", detFormat);
		sheet.addCell(l);
		n = new jxl.write.Number(column++, i + 3, 5.678, priceFormat);
		sheet.addCell(n);

		i++;
		column = 0;
		l = new Label(column++, i + 3, "标题 " + i, detFormat);
		sheet.addCell(l);
		d = new DateTime(column++, i + 3, new java.util.Date(), dateFormat);
		sheet.addCell(d);
		l = new Label(column++, i + 3, "SGD", detFormat);
		sheet.addCell(l);
		n = new jxl.write.Number(column++, i + 3, 98832, priceFormat);
		sheet.addCell(n);

		// 设置列的宽度
		column = 0;
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 20);
		sheet.setColumnView(column++, 10);
		sheet.setColumnView(column++, 20);

		workbook.write();
		workbook.close();

		return null;

	}

	public String expordExcel(OutputStream os, String name, String[] Titles,
			String[][] Contents) throws Exception
	{

		WritableWorkbook wbook = Workbook.createWorkbook(os); // 建立excel文件
		// 设置excel标题
		WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				Colour.BLACK);
		WritableCellFormat wcfFC = new WritableCellFormat(wfont);
		wfont = new jxl.write.WritableFont(WritableFont.ARIAL, 14,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				Colour.BLACK);
		wcfFC = new WritableCellFormat(wfont);

		if (Contents.length < 10000) {
			// 判断数据量低于65000条则使用传入sheet名称，超过则按数字命名
			WritableSheet wsheet = wbook.createSheet(name, 0); // sheet名称

			// 开始生成主体内容
			for (int i = 0; i < Titles.length; i++) {
				wsheet.addCell(new Label(i, 0, Titles[i], wcfFC));
			}

			for (int i = 1; i < Contents.length + 1; i++) {
				for (int j = 0; j < Contents[i - 1].length; j++) {
					wsheet.addCell(new Label(j, i, Contents[i - 1][j]));
				}
			}
		}
		else
		{
			System.out.println("------BIG-----");
			int sheetSum=30000;
			int sheetNum = Contents.length/30000 + 1;
			for(int k=1;k<sheetNum+1;k++)
			{
				WritableSheet wsheet = wbook.createSheet("第"+k+"页", 0); // sheet名称
				// 开始生成主体内容
				for (int i = 0; i < Titles.length; i++) {
					wsheet.addCell(new Label(i, 0, Titles[i], wcfFC));
				}
				int x=((Contents.length<k*sheetSum)?Contents.length:(k*sheetSum)) + 1;
				System.out.println(x);
				System.out.println((k-1)*sheetSum+1);
				for (int i = (k-1)*sheetSum+1; i < ((Contents.length<k*sheetSum)?Contents.length:(k*sheetSum)) + 1; i++) {

					for (int j = 0; j < Contents[i - 1].length; j++) {
						wsheet.addCell(new Label(j, i-(k-1)*sheetSum, Contents[i - 1][j]));
					}
				}
				System.out.println("第"+k+"页");
			}
			
			
		}
		// 主体内容生成结束
		wbook.write(); // 写入文件
		wbook.close();
		os.close();
		return "success";
	}

}
