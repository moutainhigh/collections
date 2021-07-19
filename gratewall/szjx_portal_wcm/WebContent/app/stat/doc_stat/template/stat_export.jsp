<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" errorPage="../../../include/error.jsp"%>
<%@include file="sitesource_stat_include.jsp"%>
<%@ page import="jxl.write.WritableWorkbook" %>
<%@ page import="jxl.write.WritableSheet" %>
<%@ page import="jxl.write.WritableFont" %>
<%@ page import="jxl.write.WritableCellFormat" %>
<%@ page import="jxl.write.Label" %>
<%@ page import="jxl.write.Number" %>
<%@ page import="jxl.Workbook" %>

<%@ page import="java.util.List" %>
<%@ page import="java.io.BufferedOutputStream" %>
<%@ page import="java.io.FileOutputStream" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.infra.util.CMyFile"%>
<%
BufferedOutputStream buffos = null;
String excelFile = null;
WritableWorkbook excelBook = null;
try{
	//开始构造excel数据. TODO 主要列名
	String[] saFieldName = new String[]{"序号","来源名称","本期","累计"};

	//2 构造Excel基本文件信息
	FilesMan fileManager = FilesMan.getFilesMan();
	excelFile = fileManager.getNextFilePathName(FilesMan.FLAG_SYSTEMTEMP,"xls");
	buffos = new BufferedOutputStream(new FileOutputStream(excelFile));
	excelBook = Workbook.createWorkbook(buffos);
	//TODO 设置sheet名.
	WritableSheet excelSheet = excelBook.createSheet(currSite.getDesc() + "来源发稿统计", 0);
	 
	//3 设置表头
	int iExcelCol = 0;
	int iExcelRow = 0;
	Label contentCell = null;
	jxl.write.Number numberCell = null; 
	WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false);
	WritableCellFormat wcfF = new WritableCellFormat(wf);
	for (int column = 0; column < saFieldName.length; column++) {
		contentCell = new Label(iExcelCol++,iExcelRow,saFieldName[column], wcfF);
		excelSheet.addCell(contentCell); 
	}

	//4 构造各个单元格    

	//在每个循环中做的事情
	if (StatKeys != null) {  
		for (int i = 0, size=StatKeys.size(); i < size; i++) {
			String sKey = (String) StatKeys.get(i);
			
			iExcelCol = 0;
			iExcelRow++;
		
			//往Excel中插入一行数据的各单元格
			String sFieldValue = "";
			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, i+1);
			excelSheet.addCell(numberCell);

			contentCell = new Label(iExcelCol++, iExcelRow, sKey);
			excelSheet.addCell(contentCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, result.getResult(1, sKey));
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, result.getResult(2, sKey));
			excelSheet.addCell(numberCell);
		}
	}
	
	//5.资源释放
	excelBook.write();
	excelBook.close();
	
	//6.返回值的构造
	String sXslFileName = CMyFile.extractFileName(excelFile);
	String sResult = "";
	sResult = "<excelfile>"+ sXslFileName + "</excelfile>";
	out.clear();
	out.print(sResult);
	return;
}finally{
	if(excelBook!=null){
		try{
			excelBook.close();
		}catch(Exception ex){}
	}
	if(buffos!=null){
		try{
			buffos.close();
		}catch(Exception ex){}
	}
}
%>