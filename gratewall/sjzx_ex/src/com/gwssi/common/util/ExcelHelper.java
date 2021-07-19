package com.gwssi.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.log4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;


public class ExcelHelper {

	private static Logger log = Logger.getLogger(ExcelHelper.class);

	public static ExcelHelper excelHelper;
	
	private static final int EXCEL_MAX_COLS = 3;//�������ַ�������󳤶�
	private static final int EXCEL_SCALE_Y = 26;//�������λ��

	private ExcelHelper() {

	}

	public static ExcelHelper getInstance() {

		if (excelHelper == null) {
			excelHelper = new ExcelHelper();
		}

		return excelHelper;
	}

	public File getFile(String filename) {
		File file = new File(filename);
		return file;
	}

	public Workbook getWorkbook(InputStream is) throws Exception {
		Workbook rwb ;
		try {
			rwb = Workbook.getWorkbook(is);
		} catch (BiffException e) {
			throw e;
			//throw new GwhcException("���ݸ�ʽ�Ƿ���", e);
		} catch (IOException e) {
			//throw new GwhcException("IO�쳣��", e);
			throw e;
		}
		return rwb;
	}

	public Workbook getWorkbook(String filename) {
		Workbook w = null;

		try {
			w = Workbook.getWorkbook(getFile(filename));
		} catch (BiffException e) {
			log.info(e.getMessage());
		} catch (IOException e) {
			log.info(e.getMessage());
		}
		return w;
	}

	// �������ǰ��Ŀո���Ŀ
	public int getSpaceNumbers(String cpmc) {
		if (cpmc.equals("")) {
			return 0;
		}
		String currentStr = " ";
		int i = 0;
		while (currentStr.equals(" ")) {
			try {
				currentStr = cpmc.substring(i, i + 1);
			} catch (StringIndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			i++;
		}
		// System.out.println(cpmc.trim()+"�ո���"+(i-1)+":");
		return i - 1;
	}

	// �ѵ�Ԫд���ļ������
	public void doCell(String cpdm, String cpmc, String jldw,
			String parentCpdm, String parentPrefix, FileOutputStream out)
			throws IOException {

		String str = cpdm + "," + cpmc.trim() + "," + jldw + "," + parentCpdm
				+ "\n";
		out.write(str.getBytes());
	}

	public static void writeExcel(OutputStream out, String xmlString) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); // ����һ�������������������һ������Ľ���������
		DocumentBuilder db = null;

		try {
			db = dbf.newDocumentBuilder(); // ���һ��DocumentBuilder���������������˾����DOM������

		} catch (ParserConfigurationException pce) {
			System.err.println(pce.getMessage()); // ���쳣ʱ����쳣��Ϣ��Ȼ���˳�����ͬ
		}

		Document doc = null;
		try {

			StringReader rSource = new StringReader(xmlString);
			xmlString = xmlString.replaceAll("utf-8", "gb2312");
			xmlString = new String(xmlString.getBytes(), "gb2312");
			org.xml.sax.InputSource insrc = new org.xml.sax.InputSource(rSource);
			insrc.setEncoding("gb2312");

			try {
				doc = db.parse(insrc);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // ����һ��XML�ĵ�����Ϊ�������������һ��Document�������Document����ʹ�����һ��XML�ĵ�����ģ��
		} catch (DOMException dom) {
			System.err.println(dom.getMessage());
		} catch (IOException ioe) {

		}

		jxl.write.WritableWorkbook wwb = null;
		try {
			wwb = Workbook.createWorkbook(out);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			wwb.setColourRGB(jxl.format.Colour.GRAY_25, 230, 230, 230);

			WritableFont blue = new WritableFont(WritableFont.ARIAL, 12,
					WritableFont.NO_BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.DARK_BLUE);
			WritableCellFormat twoBorders = new WritableCellFormat(blue);
			WritableCellFormat threeBorders = new WritableCellFormat(blue);
			WritableCellFormat blallThin = new WritableCellFormat(blue);
			try {
				blallThin.setBorder(jxl.format.Border.ALL,
						jxl.format.BorderLineStyle.THIN,
						jxl.format.Colour.BLACK);
			} catch (WriteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				blallThin.setWrap(true);
			} catch (WriteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			blallThin.setBackground(jxl.format.Colour.GRAY_25);
			blallThin.setAlignment(jxl.format.Alignment.CENTRE);
			blallThin.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			WritableCellFormat zlallThin = new WritableCellFormat(blue);
			zlallThin.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
			zlallThin.setWrap(true);
			zlallThin.setBackground(jxl.format.Colour.GRAY_25);
			zlallThin.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			twoBorders.setBorder(jxl.format.Border.TOP,
					jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
			twoBorders.setBorder(jxl.format.Border.LEFT,
					jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
			twoBorders.setWrap(true);
			twoBorders.setBackground(jxl.format.Colour.GRAY_25);
			twoBorders.setAlignment(jxl.format.Alignment.CENTRE);
			twoBorders
					.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			threeBorders.setBorder(jxl.format.Border.TOP,
					jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
			threeBorders.setBorder(jxl.format.Border.LEFT,
					jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
			threeBorders.setBorder(jxl.format.Border.RIGHT,
					jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
			threeBorders.setWrap(true);
			threeBorders.setBackground(jxl.format.Colour.GRAY_25);
			threeBorders.setAlignment(jxl.format.Alignment.CENTRE);
			threeBorders
					.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			WritableCellFormat zlbottomBorders = new WritableCellFormat(blue);
			zlbottomBorders.setBorder(jxl.format.Border.BOTTOM,
					jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
			zlbottomBorders.setWrap(true);
			zlbottomBorders.setBackground(jxl.format.Colour.GRAY_25);
			zlbottomBorders
					.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			WritableCellFormat zlBackground = new WritableCellFormat(blue);
			zlBackground.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
			zlBackground.setWrap(true);
			zlBackground.setBackground(jxl.format.Colour.GRAY_25);
			zlBackground
					.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			WritableCellFormat dataallThin = new WritableCellFormat(blue);
			dataallThin.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
			dataallThin
					.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			WritableCellFormat data2allThin = new WritableCellFormat(blue);
			data2allThin.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
			data2allThin.setWrap(true);
			data2allThin
					.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			WritableFont arial18ptBoldItalic = new WritableFont(
					WritableFont.ARIAL, 14, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.DARK_BLUE);
			WritableCellFormat arialBoldItalice = new WritableCellFormat(
					arial18ptBoldItalic);
			arialBoldItalice.setAlignment(jxl.format.Alignment.CENTRE);
			arialBoldItalice
					.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			arialBoldItalice.setWrap(true);

			int i = 0;
			int z = 0;
			int bcount[] = new int[5000];
			int blshowrow[] = new int[5000];
			int blshowcol[] = new int[5000];
			int zlshowrow[] = new int[5000];
			int zlshowcol[] = new int[5000];
			int zlrow[] = new int[5000];
			int rsrow[] = new int[5000];

			int height[][] = new int[5000][10];
			int weight[][] = new int[5000][10];
			int pubshowcol[] = new int[5000];
			for (int rownum = 0; rownum < 5000; rownum++) {
				bcount[rownum] = 0;
				blshowrow[rownum] = 0;
				blshowcol[rownum] = 0;
				zlshowrow[rownum] = 0;
				zlshowcol[rownum] = 0;
				zlrow[rownum] = 0;
				rsrow[rownum] = 0;

				pubshowcol[rownum] = 0;
				for (int colnum = 0; colnum < 10; colnum++) {
					height[rownum][colnum] = 1;
					weight[rownum][colnum] = 1;
				}
			}

			int rscol = 0; // �����ʼ�к�

			Element root1 = doc.getDocumentElement();
			// file://ȡ"report"Ԫ���б�
			NodeList reports = root1.getElementsByTagName("report");
			int sheetcount = reports.getLength() - 1;
			String sheetid[] = new String[sheetcount + 2];

			String zlmc[][] = new String[sheetcount + 2][5000];
			String zldw[][] = new String[sheetcount + 2][5000];
			String zldm[][] = new String[sheetcount + 2][5000];
			for (int rownum = 0; rownum < sheetcount + 2; rownum++) {
				sheetid[i] = "";
				for (int colnum = 0; colnum < 5000; colnum++) {
					zlmc[rownum][colnum] = "";
					zldw[rownum][colnum] = "";
					zldm[rownum][colnum] = "";
				}
			}

			for (i = 0; i < reports.getLength(); i++) {
				// file://����ȡÿ��"report"Ԫ��
				Element report = (Element) reports.item(i);
				String reportCode = report.getAttribute("code");
				String reportType = report.getAttribute("type");
				jxl.write.WritableSheet ws = wwb.createSheet(reportCode, i);
				sheetid[i] = reportCode;
				ws.setColumnView(0, 30);
				// ȡ"title"Ԫ��
				NodeList reportTitle = report.getElementsByTagName("title");
				rsrow[i] = rsrow[i] + 1 + reportTitle.getLength();
				if (reportTitle.getLength() == 1) {
					Element e = (Element) reportTitle.item(0);
					Text t = (Text) e.getFirstChild();

					String t1[] = t.getNodeValue().split(",");
					jxl.write.Label labela = new jxl.write.Label(0, 0, t1[0],
							arialBoldItalice);
					ws.addCell(labela);
					log.info(t1[0]);
				}
				// ȡ"subtitle"Ԫ��
				/*
				NodeList reportsubTitle = report
						.getElementsByTagName("subtitle");
				rsrow[i] = rsrow[i] + 1 + reportsubTitle.getLength();
				if (reportsubTitle.getLength() == 1) {
					Element e = (Element) reportsubTitle.item(0);
					Text t = (Text) e.getFirstChild();
					System.out.println(t.getNodeValue());
					String t2[] = t.getNodeValue().split(",");
					jxl.write.Label labelC = new jxl.write.Label(0, 2, t2[0],
							arialBoldItalice);
					ws.addCell(labelC);
				}
				*/
				// ȡ"public"Ԫ���б�
				int pubstartrow = rsrow[i]; // ��������ʼ����
				NodeList publics = report.getElementsByTagName("public");
				int pubcount = publics.getLength() - 1; // ������ָ�����
				String pubname[] = new String[pubcount];
				String zlbz[] = new String[pubcount];
				String blbz[] = new String[pubcount];
				for (int j = 0; j < pubcount; j++) {
					pubname[j] = "";
					zlbz[j] = "";
					blbz[j] = "";
					// ����ȡÿ��"public"Ԫ��
					Element e = (Element) publics.item(j);
					pubname[j] = e.getAttribute("name");

					zlbz[j] = e.getAttribute("zl");
					blbz[j] = e.getAttribute("bl");
					log.info(zlbz[j]);
				}

				// ȡ����Ԫ���б�
				blshowcol[i] = rscol; // ��������ʼ�к�
				blshowrow[i] = rsrow[i]; // ��������ʼ�к�
				NodeList bls = report.getElementsByTagName("bl");
				int blcount = bls.getLength(); // ����ָ�����
				bcount[i] = blcount;
				String bldata[][] = new String[5000][blcount + 1];
				String dm[] = new String[blcount];
				String dw[] = new String[blcount];
				String blxm = "";
				for (int colnum = 0; colnum < blcount; colnum++) {
					for (int blrow = 0; blrow < 5000; blrow++)

						bldata[blrow][colnum] = "";
					dw[colnum] = "";
					dm[colnum] = "";
				}

				int pubcol = 0; // ������������
				int row = 0; // �����Ĳ���
				for (int colnum = 0; colnum < blcount; colnum++) {
					// ����ȡÿ������ָ��
					Element e = (Element) bls.item(colnum);
					blxm = e.getAttribute("xm");
					dm[colnum] = e.getAttribute("dm");
					dw[colnum] = e.getAttribute("dw");

					String bld[] = blxm.split("/");

					row = bld.length;
					log.info(blxm);

					// jxl.write.Number n11=new jxl.write.Number(0,30,row);
					// ws.addCell(n11);

					for (int rownum = 0; rownum < row; rownum++) {
						bldata[rownum][colnum] = bld[rownum];
					}
				}

				height[i][0] = row;
				int pubrow = 0; // ����������
				pubshowcol[i] = rscol;
				int pubcount1 = pubcount;

				for (int colnum = 0; colnum < pubcount; colnum++) {

					if (zlbz[colnum].equalsIgnoreCase("no")
							&& (!pubname[colnum].equalsIgnoreCase("��Ŀ"))) {
						weight[i][colnum] = 0;
						pubcount1 = pubcount1 - 1;
					}
					if (zlbz[colnum].equalsIgnoreCase("no")
							&& (pubname[colnum].equalsIgnoreCase("��Ŀ"))) {
						weight[i][colnum] = 0;
						pubcount1 = pubcount1 - 1;
					}
					if (blbz[colnum].equalsIgnoreCase("no")
							&& (!pubname[colnum].equalsIgnoreCase("��Ŀ")))
						height[i][colnum] = 0;
					if (blbz[colnum].equalsIgnoreCase("no")
							&& (pubname[colnum].equalsIgnoreCase("��Ŀ"))) {
						height[i][colnum] = 0;
						z = 1;
					}
					jxl.write.Label label1 = new Label(pubshowcol[i],
							pubstartrow, pubname[colnum], blallThin);
					ws.addCell(label1);

					pubshowcol[i] = pubshowcol[i] + weight[i][colnum];
					blshowcol[i] = blshowcol[i] + weight[i][colnum]; // ��������ʼ�к�
					pubcol = pubcol + weight[i][colnum];
					pubrow = pubrow + height[i][colnum]; // ����������

				}

				int mcolcount[] = new int[row];
				for (int rownum = 0; rownum < row; rownum++) {
					mcolcount[rownum] = 0;
				}
				// �ϲ�������
				int tablecol = pubcol + blcount - 1;
				ws.mergeCells(0, 0, tablecol, 1);
//				ws.mergeCells(0, 2, tablecol, 3);
				pubcol = pubcount; // ������ָ�����
				for (int colnum = 0; colnum < pubcount1; colnum++)
					ws.mergeCells(colnum, pubstartrow, colnum, pubstartrow
							+ pubrow - 1);

				// jxl.write.Number n11=new
				// jxl.write.Number(0,18,pubcount1);
				// ws.addCell(n11);
				// jxl.write.Number n12=new jxl.write.Number(0,19,pubrow);
				// ws.addCell(n12);
				if (z == 0) {

					for (int rownum = 0; rownum < row; rownum++)

						for (int colnum = 0; colnum < blcount; colnum++) {
							// ��������д�����ָ��
							jxl.write.Label label = new Label(blshowcol[i]
									+ colnum, rsrow[i] + rownum,
									bldata[rownum][colnum], twoBorders);
							ws.addCell(label);
							if (colnum == (blcount - 1)) {
								jxl.write.Label label1 = new Label(blshowcol[i]
										+ colnum, rsrow[i] + rownum,
										bldata[rownum][colnum], threeBorders);
								ws.addCell(label1);

							}

						}

					// �ϲ�������Ԫ��
					int blstartrow = 0;
					int blstartcol = 0;
					int hbrow = 0;
					for (int colnum = 0; colnum < blcount; colnum++) {
						for (int rownum = 0; rownum < row - 1; rownum++) {
							// int hbrow = 1;
							hbrow = 1;
							while (true) {
								if (rownum + hbrow > row - 1)
									break;
								if (!((bldata[rownum][colnum]
										.equalsIgnoreCase(bldata[rownum + hbrow][colnum])))
										&& (!bldata[rownum + hbrow][colnum]
												.equalsIgnoreCase("")))
									break;
								hbrow++;
							}
							blstartrow = rownum;
							hbrow = hbrow - 1;

							if (hbrow > 0)
								ws.mergeCells(colnum + blshowcol[i], blstartrow
										+ blshowrow[i], colnum + blshowcol[i],
										blstartrow + blshowrow[i] + hbrow);

						}

					}

					for (int rownum = 0; rownum < row; rownum++) {
						for (int colnum = 0; colnum < blcount - 1; colnum++) {
							int hbcol = 1;
							while (true) {
								if (colnum + hbcol > blcount - 1)
									break;
								if (!(bldata[rownum][colnum]
										.equalsIgnoreCase(bldata[rownum][colnum
												+ hbcol])))
									break;
								hbcol++;
							}
							blstartcol = colnum;
							hbcol = hbcol - 1;
							if (hbcol > 0)
								ws.mergeCells(blstartcol + blshowcol[i], rownum
										+ blshowrow[i], blstartcol + hbcol
										+ blshowcol[i], rownum + blshowrow[i]);

						}
					}

				}
				// ����д�����͵�λ
				for (int colnum = 0; colnum < blcount; colnum++) {
					jxl.write.Label label1 = new Label(colnum + blshowcol[i],
							rsrow[i] + height[i][0], dm[colnum], twoBorders);
					ws.addCell(label1);
					jxl.write.Label label2 = new Label(colnum + blshowcol[i],
							rsrow[i] + height[i][0] + height[i][1], dw[colnum],
							twoBorders);
					ws.addCell(label2);
					if (colnum == (blcount - 1)) {
						jxl.write.Label label3 = new Label(colnum
								+ blshowcol[i], rsrow[i] + height[i][0],
								dm[colnum], threeBorders);
						ws.addCell(label3);
						jxl.write.Label label4 = new Label(colnum
								+ blshowcol[i], rsrow[i] + height[i][0]
								+ height[i][1], dw[colnum], threeBorders);
						ws.addCell(label4);

					}

				}

				// ȡ����Ԫ���б�
				NodeList zls = report.getElementsByTagName("zl");
				int zlcount = zls.getLength(); // ����ָ�����
				String[][] zldata = new String[zlcount][99];
				for (int k = 0; k < 99; k++)
					for (int rownum = 0; rownum < zlcount; rownum++)
						zldata[rownum][k] = "";

				zlrow[i] = zlcount; // ����������
				zlshowcol[i] = rscol; // ��������ʼ�к�
				zlshowrow[i] = rsrow[i] + pubrow; // ��������ʼ�к�
				// jxl.write.Number n1=new
				// jxl.write.Number(0,20,zlshowrow[i]);
				// ws.addCell(n1);

				int col[] = new int[zlrow[i] + 1];
				for (int k = 0; k < zlrow[i]; k++)
					col[k] = 0;

				for (int rownum = 0; rownum < zlrow[i]; rownum++) {
					// ����ȡÿ������ָ��

					Element e = (Element) zls.item(rownum);
					String zlxm = e.getAttribute("xm");
					zldm[i][rownum] = e.getAttribute("dm");
					zldw[i][rownum] = e.getAttribute("dw");
					zlmc[i][rownum] = "";

					int zlstartrow = pubstartrow + row;
					int pubstartcol = 0;
					int colnum = 0;
					// ������������"/���ֲ�
					String zld[] = zlxm.split("/");
					col[rownum] = zld.length;
					for (colnum = 0; colnum < col[rownum]; colnum++)
						zldata[rownum][colnum] = zld[colnum];
				}

				int zlcs = col[0];
				for (int rownum = 0; rownum < zlrow[i] - 1; rownum++)
					zlcs = java.lang.Math.max(zlcs, col[rownum + 1]);

				// �ϲ�����ָ��
				for (int colnum = 0; colnum < zlcs; colnum++)
					for (int rownum1 = 0; rownum1 < zlrow[i] - 1; rownum1++)
						for (int rownum2 = rownum1 + 1; rownum2 < zlrow[i]; rownum2++) {
							if (!(zldata[rownum1][colnum]
									.equalsIgnoreCase(zldata[rownum2][colnum])))
								break;
							zldata[rownum2][colnum] = "  ";
						}

				// ��������ָ��
				for (int rownum = 0; rownum < zlrow[i]; rownum++)
					for (int colnum = 0; colnum < zlcs; colnum++)
						zlmc[i][rownum] = zlmc[i][rownum]
								+ zldata[rownum][colnum];

				// ���Ƕ�����ͽ�����ָ��д�����
				if (reportType.equalsIgnoreCase("������"))
					for (int rownum = 0; rownum < zlrow[i]; rownum++) {
						// for (int colnum = 0; colnum < zlcs; colnum++)
						// {
						jxl.write.Label label3 = new Label(zlshowcol[i],
								zlshowrow[i] + rownum, zlmc[i][rownum],
								zlallThin);
						ws.addCell(label3);
						// }

						jxl.write.Label label5 = new Label(zlshowcol[i]
								+ weight[i][0], zlshowrow[i] + rownum,
								zldm[i][rownum], zlallThin);
						ws.addCell(label5);
						jxl.write.Label label6 = new Label(zlshowcol[i]
								+ weight[i][0] + weight[i][1], zlshowrow[i]
								+ rownum, zldw[i][rownum], zlallThin);
						ws.addCell(label6);

					}
				// ȡ�����б�
				NodeList datarows = report.getElementsByTagName("row");
				int datarow = datarows.getLength(); // ������ָ�����
				String data[] = {};
				for (int rownum = 0; rownum < datarow; rownum++) {
					// ����ȡÿ������
					Element e = (Element) datarows.item(rownum);
					String d = e.getAttribute("data");
					String datadm = e.getAttribute("dm");
					d = d.replaceAll(",", ", ");
					data = d.split(",");

					for (int rownum1 = 0; rownum1 < zlrow[i]; rownum1++) {
						if (zldm[i][rownum1].equalsIgnoreCase(datadm))
							for (int colnum = 0; colnum < data.length; colnum++) {
								jxl.write.Label label8 = new Label(blshowcol[i]
										+ colnum, zlshowrow[i] + rownum1,data[colnum], dataallThin);
								ws.addCell(label8);
							}
					}
				}
			}

			try {
				wwb.write();
				log.info(wwb.getSheet(0).getName());
				wwb.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public static String CharToNum(int i){
		String str="";
		char finalC;
		
		int baseLevel = 0;
		baseLevel = i/EXCEL_SCALE_Y;
		int strInt = 65+i%EXCEL_SCALE_Y;
		finalC=(char)strInt;
        
        for(int k=0;k<baseLevel;k++){
        	str+="A";
        }
        str+=finalC;
		return str;
	}

	public static void main(String a[]) {
		
		System.out.print(ExcelHelper.CharToNum(1)+155);
	}
}
