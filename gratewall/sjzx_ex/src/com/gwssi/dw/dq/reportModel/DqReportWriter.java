package com.gwssi.dw.dq.reportModel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class DqReportWriter
{

	private DqReport				dqReport;
	
	private final static int FONT_BASE_PIX = 6;//字母或空格对应像素基数

	public DqReportWriter(DqReport dqReport)
	{
		this.dqReport = dqReport;
		calcDqReport();
	}

	/**
	 * 
	 */
	private void calcDqReport()
	{
		this.dqReport.calculate();
	}
	
	public  String getHtml(){
		DqMetaArea zlMetaArea = this.dqReport.getZlMetaArea();
		DqMetaArea blMetaArea = this.dqReport.getBlMetaArea();
		int zlLevels = zlMetaArea.getAreaLevels();
		int blLevels = blMetaArea.getAreaLevels();
		
		int zlCounts = zlMetaArea.getCounts();
		int blCounts = blMetaArea.getCounts();
		
		int[] zlMaxTextLength = zlMetaArea.getLevelsMaxLength();
		int[] blMaxTextLength = blMetaArea.getCountsMaxLength();
		
		String[][] zlTextArray = zlMetaArea.getTextArray();
		String[][] blTextArray = blMetaArea.getTextArray();
		
		StringBuffer htmlBuf = new StringBuffer("");
		htmlBuf.append("<div class=\"pub\"></div>");
		htmlBuf.append(this.getAreaHtml(blTextArray,blLevels, blCounts,blMaxTextLength,false));
		htmlBuf.append(this.getAreaHtml(zlTextArray,zlCounts, zlLevels,zlMaxTextLength,true));
		htmlBuf.append(this.getDataHtml(zlCounts,blCounts,blMaxTextLength));
		
		return htmlBuf.toString();
	}
	/**
	 * @param dqReport
	 * @param out
	 */
	public void writeHtml(OutputStream out)
	{
		try {
			out.write(this.getHtml().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				
			}
		}
	}
	
	private String getDataHtml(int zlCounts, int blCounts,int[] maxTextLength) {
		StringBuffer dataHtml = new StringBuffer("<div class=\"body\">");
		dataHtml.append("<table tableLayout=\"fixed\" cellspacing=\"0\" cellpadding=\"0\">");
		for(int row=0;row<zlCounts;row++){
			dataHtml.append("<tr>");
			for(int col = 0;col<blCounts;col++){
				dataHtml.append("<td");
				if(row==0){
					dataHtml.append(" width=\""+Math.max(maxTextLength[col]*FONT_BASE_PIX, 90)+"\"");
				}
				dataHtml.append(">&nbsp;</td>");
			}
			dataHtml.append("</tr>");
		}
		dataHtml.append("</table></div>");
		return dataHtml.toString();
	}

	private String getAreaHtml(String[][] textArray,int rows,int cols,int[] maxTextLength,boolean isZl){
		String text;
		int tableWidth = 0;
		StringBuffer tableInnerHtml = new StringBuffer();
		boolean hideRows[][] = new boolean[rows][cols];
		boolean showCode = this.dqReport.getZlMetaArea().isShowCode();
		if(isZl)System.out.println(isZl+"----"+showCode);
		for(int row = 0;row<rows;row++){
			tableInnerHtml.append("<tr>");
			boolean hideCols[] = new boolean[cols];
			for(int col = 0;col<cols;col++){
				tableInnerHtml.append("<td nowrap ");
				int rowspan = 1;
				int colspan = 1;
				
				if(isZl){
					text = textArray[col][row];
					if(row<rows-1&&col!=cols-1){//行合并
						for(int mergeRowIndex = row+1;mergeRowIndex<rows;mergeRowIndex++){
							if(text==textArray[col][mergeRowIndex]){
								rowspan++;
								hideRows[mergeRowIndex][col] = true;
							}else{
								break;
							}
						}
					}
					if(hideRows[row][col]==true){
						tableInnerHtml.append(" style=\"display:none;\" ");
					}else if(rowspan>1){
						tableInnerHtml.append(" rowspan=\""+rowspan+"\" ");
					}
					
					if(row==0){//定位主栏列宽度
						int width = Math.max(maxTextLength[col]*FONT_BASE_PIX, 90);//计算列宽度
						if(showCode)width = width+20;
						tableWidth+=(width+1);
						if(!showCode){
							tableInnerHtml.append(" width=\""+width+"\" ");
						}
					}
				}else{
					text = textArray[row][col];
					if(col<cols-1&&row!=rows-1){//列合并
						for(int mergeColIndex=col+1;mergeColIndex<cols;mergeColIndex++){
							if(text==textArray[row][mergeColIndex]){
								colspan++;
								hideCols[mergeColIndex] = true;
							}else{
								break;
							}
						}
					}
					if(hideCols[col]==true){
						tableInnerHtml.append(" style=\"display:none;\" ");
					}else{
						if(colspan>1)tableInnerHtml.append(" colspan=\""+colspan+"\" ");
					}
				}
				if(text.equals("")){
					tableInnerHtml.append(" style=\"border-top:0;\" ");
				}
				tableInnerHtml.append(">");
				if(!isZl){
					text = text.replaceAll("&nbsp;", "");//宾栏不做缩进
				}else{
					//主栏代码列切分
					if(text.startsWith("(")&&showCode){
						int rIndex = text.indexOf(")");
						text = "&nbsp;"+text.substring(1,rIndex)+"</td><td>"+text.substring(rIndex+1);
					}
				}
				tableInnerHtml.append((!text.equals(""))?text:"&nbsp;");
				tableInnerHtml.append("</td>");
				
				if(isZl&&col==cols-1){
					if(row==0)tableWidth+=31;
					tableInnerHtml.append("<td style=\"text-align:center;\" width=\"30\">"+(row+1)+"</td>");
				}
			}
			tableInnerHtml.append("</tr>");
			
			if(row==rows-1&&!isZl){//宾栏定位行
				tableInnerHtml.append("<tr>");
				for(int col = 0;col<cols;col++){
					int width = Math.max(maxTextLength[col]*FONT_BASE_PIX, 90);//计算列宽度
					tableInnerHtml.append("<td width=\""+width+"\">"+(col+1)+"</td>");
					tableWidth+=(width+1);
				}
				tableInnerHtml.append("</tr>");
			}
		}
		StringBuffer areaHtml = new StringBuffer("<div class=\""+(isZl?"zl":"bl")+"\">");
		areaHtml.append("<table width=\""+tableWidth+"\" tableLayout=\"fixed\" cellspacing=\"0\" cellpadding=\"0\">");
		
		areaHtml.append(tableInnerHtml);
		areaHtml.append("</table></div>");
		return areaHtml.toString();
	}

	public String headHtml()
	{
		return null;
	}

	/**
	 * @param dqReport
	 * @param out
	 * @throws WriteException 
	 */
	public void writeExcel(OutputStream out) throws WriteException
	{
		WritableWorkbook workbook  = null;
		WritableSheet dataSheet;
		try {
			workbook = Workbook.createWorkbook(out);
		} catch (IOException e) {
			
		}
		String title;

		title = this.dqReport.getTitle();
		
		dataSheet = workbook.createSheet(title, 0);
		dataSheet.getSettings().setShowGridLines(false);//不显示网格线
		
		WritableCellFormat zlMetaFormat = this.getZlFormat(true,false);//单元格写入样式,设置
		WritableCellFormat blMetaFormat = this.getBlFormat(false,true);//单元格写入样式,设置
		
		WritableCellFormat noTopBlMetaFormat = this.getBlFormat(true,true);//单元格写入样式,设置
		WritableCellFormat format;//单元格写入样式,设置
		
		DqMetaArea zlMetaArea = this.dqReport.getZlMetaArea();
		DqMetaArea blMetaArea = this.dqReport.getBlMetaArea();
		int zlLevels = zlMetaArea.getAreaLevels();
		int blLevels = blMetaArea.getAreaLevels();
		
		int zlCounts = zlMetaArea.getCounts();
		int blCounts = blMetaArea.getCounts();
		
		int[] zlMaxTextLength = zlMetaArea.getLevelsMaxLength();
		int[] blMaxTextLength = blMetaArea.getCountsMaxLength();
		
		String[][] zlTextArray = zlMetaArea.getTextArray();
		String[][] blTextArray = blMetaArea.getTextArray();
		
		//
		String text;
		Label label;
		
		int zlCalRows = 0;//主栏计算增加行数
		int blCalCols = 0;//主栏计算增加列数
		
		List merges = new ArrayList();//合并单元格位置
		
		merges.add(new MergeArea(0,1,0,zlLevels+1+blCounts+blCalCols));//记录标题区域合并位置
		merges.add(new MergeArea(1,blLevels+1,0,zlLevels+1));//记录公共区域合并位置
		//标题
		label = new Label(0,0,title,this.getTitleFormat());//构建可写的EXCEL单元格
		this.addLable(dataSheet, label);
		//宾栏区域
		for(int row=0;row<blLevels;row++){
			boolean hideCols[] = new boolean[blCounts];//记录隐藏的定位
			for(int col=0;col<blCounts;col++){
				text = blTextArray[row][col];
				int colspan = 1;
				if(col<blCounts-1&&row!=blLevels-1){//列合并
					for(int mergeColIndex=col+1;mergeColIndex<blCounts;mergeColIndex++){
						if(text==blTextArray[row][mergeColIndex]){
							colspan++;
							hideCols[mergeColIndex] = true;
						}else{
							break;
						}
					}
				}
				if(hideCols[col]==false&&colspan>1){
					merges.add(new MergeArea(row+1,1,col+zlLevels+1,colspan));//记录宾栏区域合并位置
				}
				if(text.equals("")){
					format = noTopBlMetaFormat;
					if(col==blCounts-1){
						format = this.getBlFormat(true, false);
					}
				}else{
					format = blMetaFormat;
					if(col==blCounts-1){
						format = this.getBlFormat(false, false);
					}
				}
				
				label = new Label(col+zlLevels+1,row+1,text,format);//构建可写的EXCEL单元格
				this.addLable(dataSheet, label);
			}
			if(row==blLevels-1){
				for(int col=0;col<blCounts;col++){
					format = blMetaFormat;
					if(col==blCounts-1){
						format = this.getBlFormat(false, false);
					}
					label = new Label(col+zlLevels+1,row+1+1,""+(col+1),format);//构建可写的EXCEL单元格
					this.addLable(dataSheet, label);
					int width =  getXlsCellWidth(blMaxTextLength[col]*2);//Math.max(blMaxTextLength[col]*2,10);//最小设置10
					dataSheet.setColumnView(col+zlLevels+1,width);
				}
			}
		}
		//主栏区域
		boolean hideRows[][] = new boolean[zlCounts][zlLevels];
		for(int row=0;row<zlCounts;row++){
			for(int col = 0;col<zlLevels;col++){
				text = zlTextArray[col][row];
				int rowspan = 1;
				if(row<zlCounts-1&&col!=zlLevels-1){//行合并
					for(int mergeRowIndex = row+1;mergeRowIndex<zlCounts;mergeRowIndex++){
						if(text==zlTextArray[col][mergeRowIndex]){
							rowspan++;
							hideRows[mergeRowIndex][col] = true;
						}else{
							break;
						}
					}
				}
				
				if(hideRows[row][col]==true&&rowspan>1){
					merges.add(new MergeArea(row+blLevels+1+1,rowspan,col,1));//记录主栏区域合并位置
				}
				//
				format = zlMetaFormat;
				if(row==zlCounts-1){
					format = this.getZlFormat(false,false);
				}
				label = new Label(col,row+blLevels+2,text,format);//构建可写的EXCEL单元格
				this.addLable(dataSheet, label);
				if(col==zlLevels-1){
					label = new Label(col+1,row+blLevels+2,""+(row+1),format);//构建可写的EXCEL单元格
					this.addLable(dataSheet, label);
					//
				}
				
				if(row==0){//定位主栏列宽度
					int width = this.getXlsCellWidth(zlMaxTextLength[col]);//计算列宽度
					dataSheet.setColumnView(col,width);
					if(col==zlLevels-1){
						dataSheet.setColumnView(col+1,4);
					}
				}
			}
			
		}
		//数据区域
		//this.dqReport.getDatas();
		CellFormat dataFormat = this.getDataFormat();
		for(int row=0;row<zlCounts;row++){
			for(int col=0;col<blCounts;col++){
				label = new Label(col+zlLevels+1,row+blLevels+2,"",dataFormat);//构建可写的EXCEL单元格
				this.addLable(dataSheet, label);
			}
		}
		//
		dataSheet.setRowView(0, 400);
		//合并单元格
		MergeArea mergeArea;
		for(int i=0;i<merges.size();i++){
			mergeArea = (MergeArea)merges.get(i);
			dataSheet.mergeCells(mergeArea.getCol(), 
					mergeArea.getRow(), 
					mergeArea.getCol()+mergeArea.getMergeCols()-1, 
					mergeArea.getRow()+mergeArea.getMergeRows()-1);
		}
		try {
			//dataSheet.insertColumn(3);
			workbook.write();
		} catch (IOException e) {
			
		}finally{
			try {
				workbook.close();
			} catch (jxl.write.WriteException e) {
				
			} catch (IOException e) {
				
			}
		}
		
	}
	
	private WritableCellFormat getTitleFormat() throws WriteException
	{
		WritableCellFormat metaFormat = new WritableCellFormat();//单元格写入样式,设置
		metaFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		metaFormat.setAlignment(Alignment.CENTRE);
		metaFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		metaFormat.setIndentation(1);
		return metaFormat;
	}

	private int getXlsCellWidth(int width){
		return Math.max(width, 10);
	}
	
	private WritableCellFormat getZlFormat(boolean noBottom,boolean isCenter) throws WriteException{
		WritableCellFormat metaFormat = new WritableCellFormat();//单元格写入样式,设置
		metaFormat.setBorder(Border.LEFT, BorderLineStyle.THIN);
		metaFormat.setBorder(Border.TOP, BorderLineStyle.THIN);
		if(!noBottom)metaFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
		if(isCenter)metaFormat.setAlignment(Alignment.CENTRE);
		return metaFormat;
	}
	
	private WritableCellFormat getBlFormat(boolean noTop,boolean noRight) throws WriteException{
		WritableCellFormat metaFormat = new WritableCellFormat();//单元格写入样式,设置
		metaFormat.setBorder(Border.LEFT, BorderLineStyle.THIN);
		if(!noTop)metaFormat.setBorder(Border.TOP, BorderLineStyle.THIN);
		if(!noRight)metaFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN);
		metaFormat.setAlignment(Alignment.CENTRE);
		return metaFormat;
	}
	
	private WritableCellFormat getDataFormat() throws WriteException{
		WritableCellFormat metaFormat = new WritableCellFormat();//单元格写入样式,设置
		metaFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		metaFormat.setAlignment(Alignment.RIGHT);
		return metaFormat;
	}
	
	private void addLable(WritableSheet dataSheet,Label label){
		try {
			dataSheet.addCell(label);//把单元格加入sheet中
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (jxl.write.WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		OutputStream fileOut;
		try {
			fileOut = new FileOutputStream("D:/test.xls");
		} catch (FileNotFoundException e) {
			return;
		}
		DqReport dqReport = DqReportReader.getInstance().readFromMap(DqReportReader.exampleReportMap());
		DqReportWriter dqReportWriter = new DqReportWriter(dqReport);
		
		Map map = DqReportReader.exampleReportMap();
		map.put("html",dqReportWriter.getHtml());
//		
		System.out.println(JSONObject.fromObject(map).toString());
//		try {
//			dqReportWriter.writeExcel(fileOut);
//		} catch (WriteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		dqReportWriter.writeHtml(out);
		//
		System.out.println(out.toString());
	}
}

class MergeArea{
	private int row;
	
	private int mergeRows;
	
	private int col;
	
	private int mergeCols;
	
	public MergeArea(int row, int mergeRows, int col, int mergeCols)
	{
		super();
		this.row = row;
		this.mergeRows = mergeRows;
		this.col = col;
		this.mergeCols = mergeCols;
	}

	/**
	 * @return the row
	 */
	public int getRow()
	{
		return row;
	}

	/**
	 * @param row the row to set
	 */
	public void setRow(int row)
	{
		this.row = row;
	}

	/**
	 * @return the mergeRows
	 */
	public int getMergeRows()
	{
		return mergeRows;
	}

	/**
	 * @param mergeRows the mergeRows to set
	 */
	public void setMergeRows(int mergeRows)
	{
		this.mergeRows = mergeRows;
	}

	/**
	 * @return the col
	 */
	public int getCol()
	{
		return col;
	}

	/**
	 * @param col the col to set
	 */
	public void setCol(int col)
	{
		this.col = col;
	}

	/**
	 * @return the mergeCols
	 */
	public int getMergeCols()
	{
		return mergeCols;
	}

	/**
	 * @param mergeCols the mergeCols to set
	 */
	public void setMergeCols(int mergeCols)
	{
		this.mergeCols = mergeCols;
	}
	
	public String toString(){
		return "row:"+this.row+",mergeRows:"+this.mergeRows+",col:"+this.col+",mergeCols:"+this.mergeCols;
	}
}
