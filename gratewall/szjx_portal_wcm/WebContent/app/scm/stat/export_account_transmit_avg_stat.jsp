<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" errorPage="../../../include/error.jsp"%>

<%@ page import="jxl.write.WritableWorkbook" %>
<%@ page import="jxl.write.WritableSheet" %>
<%@ page import="jxl.write.WritableFont" %>
<%@ page import="jxl.write.WritableCellFormat" %>
<%@ page import="jxl.write.Label" %>
<%@ page import="jxl.write.Number" %>
<%@ page import="jxl.Workbook" %>

<%@ page import="java.io.BufferedOutputStream" %>
<%@ page import="java.io.FileOutputStream" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.infra.util.CMyFile"%>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>

<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.cms.auth.persistent.Users" %>

<%@ page import="com.trs.infra.persistent.WCMFilter" %>

<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="com.trs.components.stat.IStatResultsFilter" %>
<%@ page import="java.util.HashMap" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>

<%@ page import="com.trs.scm.persistent.Accounts" %>
<%@ page import="com.trs.scm.persistent.SCMGroup" %>
<%@ page import="com.trs.scm.persistent.SCMGroups" %>
<%@ page import="com.trs.scm.persistent.SCMMicroContent" %>
<%@ page import="com.trs.components.stat.IStatResults" %>
<%@ page import="com.trs.components.stat.StatAnalysisTool" %>
<%@ page import="com.trs.components.stat.TimeRange" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.scm.persistent.Account" %>
<%@ page import="com.trs.scm.sdk.factory.PlatformFactory" %>
<%@ page import="com.trs.scm.sdk.model.Platform" %>
<%@ include file="../../include/public_server.jsp"%>
<%@ include file="static_common.jsp"%>
<%@ include file="public_stat_include.jsp"%>
<%
BufferedOutputStream buffos = null;
String excelFile = null;
try{
%>
<%@ include file="query_transmit_avg_include.jsp"%>
<%
	//after to do ...
	String[] saFieldName = new String[12];
	saFieldName[0]="帐号/月份";
	List oXLabels = oStatResults.getXlabels();
	if(oXLabels == null){
		return;
	}
	List oNewList = transXLabels(oXLabels);
	int nSize = oNewList.size();
	for(int i= 0; i< nSize; i++){
		saFieldName[i+1] = oNewList.get(i).toString();
	}
	//2 构造Excel基本文件信息

	//(1)获取FilesMan对象
	FilesMan fileManager = FilesMan.getFilesMan();
	//(2)根据文件的类型，向WCM文件管理中心申请有效的文件名，存放在临时文件systemtemp目录中，systemp目录
	excelFile = fileManager.getNextFilePathName(FilesMan.FLAG_SYSTEMTEMP,"xls");
	buffos = new BufferedOutputStream(new FileOutputStream(excelFile));
	WritableWorkbook excelBook = Workbook.createWorkbook(buffos);
	WritableSheet excelSheet = excelBook.createSheet(sTitle,0);
	 
	//3 设置表头
	int iExcelCol = 0;
	int iExcelRow = 0;
	Label contentCell = null;
	WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false);
	WritableCellFormat wcfF = new WritableCellFormat(wf);
	for (int column = 0; column < saFieldName.length; column++) {
		if(!CMyString.isEmpty(saFieldName[column])){
			contentCell = new Label(iExcelCol++,iExcelRow,saFieldName[column], wcfF);
			excelSheet.addCell(contentCell); 
		}
	}
	//4 构造各个单元格    
	if (oAccountList != null) {
		//构造每一行信息中的每一个单元格的数据，进行输出
		for(int i=0; i<oAccountList.size(); i++) {
			//获取指定Id的对象
			String sAccount = (String)oAccountList.get(i);
			List oList = (List)oAccountDataMap.get(sAccount);

			iExcelCol = 0;
			iExcelRow++;
			//往Excel中插入一行数据的各单元格   "帐号","1月", "2月"....的数据

			contentCell = new jxl.write.Label(iExcelCol++, iExcelRow, sAccount);
			excelSheet.addCell(contentCell);
			if(oList == null){
				continue;
			}
			for(int j=0; j< oList.size() && j < nSize; j++){
				Float fTempData = (Float)oList.get(j);
				contentCell = new Label(iExcelCol++, iExcelRow, fTempData.toString());
				excelSheet.addCell(contentCell);
			}
		}
	}
	
	//5.资源释放
	excelBook.write();
	excelBook.close();
	if(buffos!=null){
		buffos.close();
	}
	
	//6.返回值的构造
	String sXslFileName = CMyFile.extractFileName(excelFile);
	String result = "";
	result = "<excelfile>"+ sXslFileName + "</excelfile>";
	out.clear();
	out.print(result);
	return;
}finally{
	if(buffos!=null){
		buffos.close();
	}
}
%>