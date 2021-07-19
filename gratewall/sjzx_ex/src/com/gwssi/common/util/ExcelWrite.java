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
		String[][] numthree; // ����һ��float���͵�2ά����
		numthree = new String[5][8]; // Ϊ������5��8�еĿռ��С
		numthree[0][0] = "1.1f"; // ͨ���±�����ȥ���� 1��1��=1.1
		numthree[1][0] = "1.2f"; // 2��1��=1.2
		numthree[2][0] = "1.3f"; // 3��1��=1.3
		numthree[3][0] = "1.4f"; // 4��1��=1.4
		numthree[4][0] = "1.5f"; // 5��1��=1.5
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
		String[] Titles = { "��1", "��2", "��3", "��4", "��5", "��6", "��7", "��8", };

		ew.expordExcel(os, "����", Titles, numthree);

		System.out.println(numthree[0][0]); // ��ӡ��������
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

		// һЩ��ʱ����������д��excel��
		Label l = null;
		jxl.write.Number n = null;
		jxl.write.DateTime d = null;

		// Ԥ�����һЩ����͸�ʽ��ͬһ��Excel����ò�Ҫ��̫���ʽ
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

		NumberFormat nf = new NumberFormat("0.00000"); // ����Number�ĸ�ʽ
		WritableCellFormat priceFormat = new WritableCellFormat(detFont, nf);

		DateFormat df = new DateFormat("yyyy-MM-dd");// �������ڵ�
		WritableCellFormat dateFormat = new WritableCellFormat(detFont, df);

		// ʣ�µ����飬��������������ݺ͸�ʽ����һЩ��Ԫ���ټӵ�sheet��
		l = new Label(0, 0, "���ڲ��Ե�Excel�ļ�", headerFormat);
		sheet.addCell(l);

		// add Title
		int column = 0;
		l = new Label(column++, 2, "����", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "����", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "����", titleFormat);
		sheet.addCell(l);
		l = new Label(column++, 2, "�۸�", titleFormat);
		sheet.addCell(l);

		// add detail
		int i = 0;
		column = 0;
		l = new Label(column++, i + 3, "���� " + i, detFormat);
		sheet.addCell(l);
		d = new DateTime(column++, i + 3, new java.util.Date(), dateFormat);
		sheet.addCell(d);
		l = new Label(column++, i + 3, "CNY", detFormat);
		sheet.addCell(l);
		n = new jxl.write.Number(column++, i + 3, 5.678, priceFormat);
		sheet.addCell(n);

		i++;
		column = 0;
		l = new Label(column++, i + 3, "���� " + i, detFormat);
		sheet.addCell(l);
		d = new DateTime(column++, i + 3, new java.util.Date(), dateFormat);
		sheet.addCell(d);
		l = new Label(column++, i + 3, "SGD", detFormat);
		sheet.addCell(l);
		n = new jxl.write.Number(column++, i + 3, 98832, priceFormat);
		sheet.addCell(n);

		// �����еĿ��
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

		WritableWorkbook wbook = Workbook.createWorkbook(os); // ����excel�ļ�
		// ����excel����
		WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				Colour.BLACK);
		WritableCellFormat wcfFC = new WritableCellFormat(wfont);
		wfont = new jxl.write.WritableFont(WritableFont.ARIAL, 14,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				Colour.BLACK);
		wcfFC = new WritableCellFormat(wfont);

		if (Contents.length < 10000) {
			// �ж�����������65000����ʹ�ô���sheet���ƣ���������������
			WritableSheet wsheet = wbook.createSheet(name, 0); // sheet����

			// ��ʼ������������
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
				WritableSheet wsheet = wbook.createSheet("��"+k+"ҳ", 0); // sheet����
				// ��ʼ������������
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
				System.out.println("��"+k+"ҳ");
			}
			
			
		}
		// �����������ɽ���
		wbook.write(); // д���ļ�
		wbook.close();
		os.close();
		return "success";
	}

}
