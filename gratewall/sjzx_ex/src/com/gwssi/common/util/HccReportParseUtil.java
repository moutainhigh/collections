package com.gwssi.common.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

public class HccReportParseUtil {
	Document reportsDoc  = null;
	
	private static String DEFAULT_ROW_HEIGHT = "20";
	private static String DEFAULT_COL_WIDTH = "80"; 
	
	public HccReportParseUtil(Document reportsDoc){
		this.reportsDoc = reportsDoc;
		this.reportsDoc.setXMLEncoding("gb2312");
	}
	
	public String toStyleSheet(){
		reportsDoc.selectNodes("Reports/Report/Style/CellStyles/CellStyle");
		return null;
	}
	
	public void wrapWithHtml(){
		List reportList = new ArrayList();//report����
		reportList = this.reportsDoc.selectNodes("Reports/Report");
		for(int i = 0; i < reportList.size(); i ++){
			Element reportElement = (Element) reportList.get(i);
			this.addHtmlNode(reportElement);
		}
	}
	
	private void addHtmlNode(Element reportElement){
		StringBuffer htmlBuf = new StringBuffer();
		List colList = new ArrayList();//�нڵ㼯��
		List cellStyleList = new ArrayList();//��ʽ�ڵ㼯��
		List rowList = new ArrayList();//��ʽ�ڵ㼯��
		List cellList;
		
		String rowNo,rowHeight;//
		/*
		 * ��Ԫ������
		 * -------------------------------------------------------------------------------------------------------------
		 * |		 	   	|��������			|˵��																		|
		 * |----------------|-------------------------------------------------------------------------------------------
		 * |colNo       	|NO				|�ж�λ,��1��ʼ																|
		 * |spanRow 	   	|SpanRow		|�ϲ�������																	|
		 * |spanCol     	|SpanCol		|�ϲ�������																	|
		 * |styleId	   		|StyleId		|��ʽ��־																		|
		 * |readOnly	   	|ReadOnly		|�Ƿ��־��Ԫ��Ϊֻ��															|
		 * |dataType	   	|DataType		|��Ԫ����������																|
		 * |formula			|Formular		|���㹫ʽ																		|
		 * |auditFormula 	|AuditFormula	|��˹�ʽ		
		 * |selectCode		|SelectCode		|�������־												|
		 * -------------------------------------------------------------------------------------------------------------
		 * ��Ԫ���ı� cellText 
		 */
		String colNo,spanRow,spanCol,styleId,readOnly,dataType,formula,auditFormula,selectCode;
		String cellText;//�ı�
		/*
		 * 
		 */
		colList = reportElement.selectNodes("Style/Table/Col");
		cellStyleList = reportElement.selectNodes("Style/CellStyles/CellStyle");
		rowList =  reportElement.selectNodes("Style/Table/Row");
		
		Hashtable hiddenCells = new Hashtable();
		//
		Hashtable tableRowHash = new Hashtable();
		Hashtable tableCellHash;
		 
		Hashtable widthHash = new Hashtable();
		Hashtable heightHash = new Hashtable();
		
		TableRow tableRow;
		TableCell tableCell;
		
		String cellWidth;
		String cellHeight;
		//�����п��
		for(int i = 0; i < colList.size(); i++){
			Element colElement = (Element) colList.get(i);
			colNo = colElement.attributeValue("NO");
			cellWidth = colElement.attributeValue("Width");
			widthHash.put(colNo,cellWidth);
		}
		//������ʽ
		
		//����Ԫ��
		int maxRow=0,maxCol=0;
		maxCol = colList.size();
		String firstRowNo = null;
		for(int i = 0; i < rowList.size(); i++){
			Element rowElement = (Element) rowList.get(i);
			rowNo = rowElement.attributeValue("NO");
			if(firstRowNo==null)firstRowNo = rowNo;
			rowHeight = rowElement.attributeValue("Height");
			
			rowHeight =(rowHeight==null)?HccReportParseUtil.DEFAULT_ROW_HEIGHT:rowHeight;
			
			maxRow = Math.max(Integer.parseInt(rowNo),maxRow);
			cellList = new ArrayList();
			cellList = rowElement.selectNodes("Cell");
			
			
			tableCellHash = new Hashtable();
			
			for(int j = 0; j < cellList.size(); j++){
				Element cellElement = (Element) cellList.get(j);
				colNo = cellElement.attributeValue("NO");
				if(rowNo.equals(firstRowNo)){
					maxCol = Math.max(Integer.parseInt(colNo),maxCol);
				}
				spanRow = cellElement.attributeValue("SpanRow");
				spanCol = cellElement.attributeValue("SpanCol");
				styleId = cellElement.attributeValue("StyleId");
				readOnly = cellElement.attributeValue("ReadOnly");
				dataType = cellElement.attributeValue("DataType");
				formula = cellElement.attributeValue("Formula");
				auditFormula = cellElement.attributeValue("AuditFormula");
				selectCode = cellElement.attributeValue("SelectCode");
				
				if(spanRow!=null&&spanCol!=null){
					hiddenCells.putAll(this.getMergeCellsIndex(rowNo,colNo,spanRow,spanCol));
				}
				spanRow = (spanRow==null)?"1":spanRow;
				spanCol = (spanCol==null)?"1":spanCol;
				cellText = cellElement.getText();
				if(cellText==null||cellText.equals("")||cellText.equals("\n")){
					cellText = " ";
				}
				tableCell = new TableCell();
				//colNo,spanRow,spanCol,styleId,readOnly,dataType,formula,auditFormula
				tableCell.setColNo(colNo);
				tableCell.setSpanRow(spanRow);
				tableCell.setSpanCol(spanCol);
				tableCell.setStyleId(styleId);
				tableCell.setReadOnly(readOnly);
				tableCell.setDataType(dataType);
				tableCell.setFormula(formula);
				tableCell.setAuditFormula(auditFormula);
				tableCell.setCellText(cellText);
				tableCell.setSelectCode(selectCode);
				
				tableCellHash.put(colNo,tableCell);
				cellWidth = null;
				cellHeight = null;
				styleId = null;
				spanRow = null;
				spanCol = null;
				readOnly = null;
				dataType = null;
				formula = null;
				auditFormula = null;
				cellText = null;
				selectCode = null;
			}
			tableRow = new TableRow(rowNo,rowHeight,tableCellHash);
			tableRowHash.put(rowNo,tableRow);
			rowNo = null;
			rowHeight = null;
			tableRow = null;
		}
		
		Element htmlElement = reportElement.addElement("html");
		Element tableElement = htmlElement.addElement("table");
		Element tableRowElement;
		Element tableCellElement;
		
		tableElement.addAttribute("class","HccReport_body_table");
		tableElement.addAttribute("border","0");
		tableElement.addAttribute("cellSpacing","0");
		tableElement.addAttribute("tableLayout","fixed");
		tableElement.addAttribute("cellPadding","0");
		/*
		 * ���е�����Ԫ�ظ߶�Ϊ0 HccReport_body_cell_colContral��HccReport_body_cell_pubContral
		 */
		tableRowElement = tableElement.addElement("tr");//������һ�п���Ԫ��
		//������һ��Ԫ�� cell_pubContral
		tableRowElement.addAttribute("rowIndex","0");
		tableCellElement = tableRowElement.addElement("td");
		tableCellElement.addAttribute("class","HccReport_body_cell_pubContral");
		tableCellElement.addAttribute("rowIndex","0");
		tableCellElement.addAttribute("colIndex","0");
		int sumWidth = 0;//
		int colWidth = 0;
		for(int col=1;col<=maxCol;col++){
			String key = new Integer(col).toString();
			cellWidth = (String) widthHash.get(key);
			cellWidth = (cellWidth==null)?DEFAULT_COL_WIDTH:cellWidth;
			tableCellElement = tableRowElement.addElement("td");
			tableCellElement.addAttribute("class","HccReport_body_cell_colContral");
			tableCellElement.addAttribute("style","width:"+cellWidth+"px;");
			tableCellElement.addAttribute("rowIndex","0");
			tableCellElement.addAttribute("colIndex",new Integer(col).toString());
			try {
				colWidth = Integer.parseInt(cellWidth);
			} catch (NumberFormatException e) {
				colWidth = 0;
			}
			sumWidth+= colWidth;
			cellWidth = null;
		}
		tableElement.addAttribute("sumWidth",new Integer(sumWidth).toString());

		for(int row=1;row<=maxRow;row++){
			rowNo = new Integer(row).toString();
			tableRowElement = tableElement.addElement("tr");//������Ԫ��
			tableRowElement.addAttribute("rowIndex",new Integer(row).toString());
			//�п���Ԫ��
			tableCellElement = tableRowElement.addElement("td");
			tableCellElement.addAttribute("class","HccReport_body_cell_rowContral");
			tableCellElement.addAttribute("rowIndex",new Integer(row).toString());
			tableCellElement.addAttribute("colIndex","0");
			//�����Ԫ����ֵ
			if(tableRowHash.containsKey(rowNo)){
				tableRow = (TableRow) tableRowHash.get(rowNo);
				tableCellHash = new Hashtable();
				rowHeight = tableRow.getHeight();
				tableCellElement.addAttribute("style","height:"+rowHeight+"px;");
				tableCellHash = tableRow.getTableCellHash();
				tableRowElement.addAttribute("height",rowHeight);
				
				for(int col=1;col<=maxCol;col++){
					colNo = new Integer(col).toString();
					
					tableCellElement = tableRowElement.addElement("td");
					tableCellElement.addAttribute("rowIndex",new Integer(row).toString());
					tableCellElement.addAttribute("colIndex",new Integer(col).toString());
					//System.out.println(row+","+col);
					if(tableCellHash.containsKey(colNo)){
						
						tableCell =  (TableCell) tableCellHash.get(colNo);
						
						styleId = tableCell.getStyleId();
						spanRow = tableCell.getSpanRow();
						spanCol = tableCell.getSpanCol();
						cellText = tableCell.getCellText();
						readOnly = tableCell.getReadOnly();
						dataType = tableCell.getDataType();
						selectCode = tableCell.getSelectCode();
						
						tableCellElement.addAttribute("class",styleId+" HccReport_body_cell");
						tableCellElement.addAttribute("readOnly",readOnly);
						tableCellElement.addAttribute("dataType",dataType);
						tableCellElement.addAttribute("selectCode",selectCode);
						if(styleId!=null){
							tableCellElement.addAttribute("styleId",styleId);
						}
						//tableCellElement.addAttribute("readOnly",readOnly);
						
						if(spanRow.equals("1")&&spanCol.equals("1")){
							//cellWidth = widthHash.get(colNo);
							//cellHeight = rowHeight;
						}else{
							tableCellElement.addAttribute("rowSpan",spanRow);
							tableCellElement.addAttribute("colSpan",spanCol);
						//	cellWidth = this.getMergeCellWidth(colList,colNo,spanCol);
						//	cellHeight = this.getMergeCellHeight(rowList,rowNo,spanRow);
							
						}
						//tableCellElement.addAttribute("style","width:"+cellWidth+"px;height:"+cellHeight+"px;");
						//tableCellElement.addAttribute("width",cellWidth+"px");
						//tableCellElement.addAttribute("height",cellHeight+"px");
						tableCellElement.addText(cellText);
						tableCellElement.addAttribute("title",cellText);
						cellWidth = null;
						cellHeight = null;
						styleId = null;
						spanRow = null;
						spanCol = null;
						readOnly = null;
						dataType = null;
						formula = null;
						auditFormula = null;
						cellText = null;
						selectCode = null;
					}else{
						//this.addDefaultCell(tableRowElement,row,col);
					}
					colNo = null;//
				}
			}else{//��Ĭ����
				this.addDefaultRow(tableRowElement,row,maxCol);
			}
			rowHeight = null;
			rowNo = null;
		}
		tableElement.addAttribute("maxRow",new Integer(maxRow).toString());
		tableElement.addAttribute("maxCol",new Integer(maxCol).toString());
		
		Enumeration mergeCellEnum = hiddenCells.keys();
		String key,rowIndex,colIndex;
		while(mergeCellEnum.hasMoreElements()){
			key = (String) mergeCellEnum.nextElement();
			rowIndex = key.split(",")[0];
			colIndex = key.split(",")[1];
			Element cellElement = (Element)tableElement.selectSingleNode("tr/td[@rowIndex="+rowIndex+"][@colIndex="+colIndex+"]");
			if(cellElement!=null){
				cellElement.addAttribute("style","display:none;width:0;");
				//cellElement.getParent().remove(cellElement);
			}
		}
		//String key = row+","+col;
		//System.out.println(key);
		//if(hiddenCells.containsKey(key)){
		//	tableCellElement.addAttribute("style","dispaly:none;");
		//}
	}
	
	private String  getMergeCellHeight(List rowList,String rowNo,String spanRow){
		int height = 0;
		int rowFrom = Integer.parseInt(rowNo);
		int rowTo = rowFrom + Integer.parseInt(spanRow);
		
		String cRowNo,cWidth;
		int cRow = 0;
		for(int i = 0; i < rowList.size(); i++){
			Element rowElement = (Element) rowList.get(i);
			cRowNo  = rowElement.attributeValue("NO");
			cRow = Integer.parseInt(cRowNo);
			
			if(cRow>=rowFrom&&cRow<rowTo){
				height += Integer.parseInt(rowElement.attributeValue("Height"));
			}
		}
		return new Integer(height).toString();
	}
	
	private String getMergeCellWidth(List colList,String colNo,String spanCol){
		int width = 0;
		int colFrom = Integer.parseInt(colNo);
		int colTo = colFrom + Integer.parseInt(spanCol);
		
		String cColNo,cWidth;
		int cCol = 0;
		for(int i = 0; i < colList.size(); i++){
			Element rowElement = (Element) colList.get(i);
			cColNo  = rowElement.attributeValue("NO");
			cCol = Integer.parseInt(cColNo);
			
			if(cCol>=colFrom&&cCol<colTo){
				width += Integer.parseInt(rowElement.attributeValue("Width"));
			}
		}
		return new Integer(width).toString();
	}
	
	private Hashtable getMergeCellsIndex(String rowNo,String colNo,String spanRow,String spanCol){
		Hashtable mergeCellsIndexHash = new Hashtable();
		String key;
		int rowFrom=0;
		int colFrom=0;
		int rowTo=0;
		int colTo=0;
		
		String rowStr,colStr;
		
		rowFrom = Integer.parseInt(rowNo);
		colFrom = Integer.parseInt(colNo);
		
		rowTo = rowFrom+Integer.parseInt(spanRow);
		colTo = colFrom+Integer.parseInt(spanCol);
		
		for(int row=rowFrom;row<rowTo;row++){
			rowStr = new Integer(row).toString();
			for (int col=colFrom;col<colTo;col++){
				if(row==rowFrom&&col==colFrom){
					
				}else{
					colStr = new Integer(col).toString();
					key = rowStr+","+colStr;
					mergeCellsIndexHash.put(key,key);
					colStr = null;
				}
			}
			rowStr = null;
		}
		return mergeCellsIndexHash;
	}
	
	private void addDefaultRow(Element tableRowElement,int row,int maxCol){
		for(int col=1;col<=maxCol;col++){
			this.addDefaultCell(tableRowElement,row,col);
		}
	}
	
	private void addDefaultCell(Element tableRowElement,int row, int col){
		Element tableCellElement = tableRowElement.addElement("td");
		tableCellElement.addAttribute("class","HccReport_body_cell ");
		tableCellElement.addAttribute("rowIndex",new Integer(row).toString());
		tableCellElement.addAttribute("colIndex",new Integer(col).toString());
	}
	
	public Document wrapWithHtmlNode(){
		return  reportsDoc;
	}
	

	public Document getReportsDoc() {
		return reportsDoc;
	}

	public void setReportsDoc(Document reportsDoc) {
		this.reportsDoc = reportsDoc;
	}
	
}

class TableRow{
	private String height;
	
	private String rowNo;
	
	private Hashtable tableCellHash = new Hashtable();
	
	public TableRow(String rowNo,String height,Hashtable tableCellHash){
		this.rowNo = rowNo;
		this.height = height;
		this.tableCellHash = tableCellHash;
	}
	public Hashtable getTableCellHash() {
		return tableCellHash;
	}
	public void setTableCellHash(Hashtable tableCellHash) {
		this.tableCellHash = tableCellHash;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getRowNo() {
		return rowNo;
	}
	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}
	
}

class TableCell{
	private String styleId;
	private String rowNo;
	private String colNo;
	private String spanRow;
	private String spanCol;
	private String cellText;
	private String  readOnly;
	private String  dataType;
	private String formula;
	private String auditFormula;
	private String selectCode;
	
	public String getSelectCode() {
		return selectCode;
	}

	public void setSelectCode(String selectCode) {
		this.selectCode = selectCode;
	}

	public TableCell(){
		
	}
	
	public String getAuditFormula() {
		return auditFormula;
	}

	public void setAuditFormula(String auditFormula) {
		this.auditFormula = auditFormula;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	public String getColNo() {
		return colNo;
	}

	public void setColNo(String colNo) {
		this.colNo = colNo;
	}

	public String getRowNo() {
		return rowNo;
	}

	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}

	public String getSpanCol() {
		return spanCol;
	}

	public void setSpanCol(String spanCol) {
		this.spanCol = spanCol;
	}

	public String getSpanRow() {
		return spanRow;
	}

	public void setSpanRow(String spanRow) {
		this.spanRow = spanRow;
	}

	public String getStyleId() {
		return styleId;
	}

	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	public String getCellText() {
		return cellText;
	}

	public void setCellText(String cellText) {
		this.cellText = cellText;
	}
	
}
