<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" errorPage="../../../include/error.jsp"%>

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
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>

<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.cms.auth.persistent.Groups" %>

<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>

<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.cms.auth.persistent.Users" %>
<%@ page import="com.trs.components.stat.UserLoginInfo"%>
<%@ page import="com.trs.components.stat.UserLoginInfos"%>
<%@ page import="com.trs.components.stat.UserLoginInfoServiceProvider"%>

<%@ page import="com.trs.infra.persistent.WCMFilter" %>

<%@include file="../../include/public_server.jsp"%>
<%@include file="../include/static_common.jsp"%>

<%
BufferedOutputStream buffos = null;
String excelFile = null;
try{

	//设置时间范围
	String[] sGetTimes= getDateTimeFromParame(currRequestHelper);

	// 1 获取统计数据
	JSPRequestProcessor processor = new JSPRequestProcessor(request,response);	
	UserLoginInfos results ;
	
	String sServiceId = "wcm61_userlogininfo", sMethodName = "query";

	//1.1 获取到指定参数检索的字段和值
	String sSelectItem = currRequestHelper.getString("SelectItem");
	String sSearchValue = currRequestHelper.getString("SearchValue");

	//1.2 如果sSelectItem不为空，则传值sSelectItem和sSearchValue。否则不传
	if(!CMyString.isEmpty(sSearchValue)) {
		processor.setAppendParameters(new String[] {sSelectItem,sSearchValue , "StartTime", sGetTimes[0], "EndTime", sGetTimes[1]});
	}else{
		processor.setAppendParameters(new String[] { "StartTime", sGetTimes[0], "EndTime", sGetTimes[1]});
	}
	results = (UserLoginInfos) processor.excute(sServiceId, sMethodName);

	int nNum = 0;
	if(results != null){
		nNum = results.size();
	}

	//excel文件的sheet名称；
	CMyDateTime cdtStartTime = new CMyDateTime(),cdtEndTime = new CMyDateTime();
	cdtStartTime.setDateTimeWithString(sGetTimes[0]);
	cdtEndTime.setDateTimeWithString(sGetTimes[1]);

	//after to do ...
	String[] saFieldName = new String[]{LocaleServer.getString("export_userlogininfo_stat.num","序号"),LocaleServer.getString("export_userlogininfo_stat.userName","发稿人"),LocaleServer.getString("export_userlogininfo_stat.depName","部门名称"),LocaleServer.getString("export_userlogininfo_stat.logging.time","登录时间"),LocaleServer.getString("export_userlogininfo_stat.address","IP地址")};
	//2 构造Excel基本文件信息

	//(1)获取FilesMan对象
	FilesMan fileManager = FilesMan.getFilesMan();
	//(2)根据文件的类型，向WCM文件管理中心申请有效的文件名，存放在临时文件systemtemp目录中，systemp目录
	excelFile = fileManager.getNextFilePathName(FilesMan.FLAG_SYSTEMTEMP,"xls");
	buffos = new BufferedOutputStream(new FileOutputStream(excelFile));
	WritableWorkbook excelBook = Workbook.createWorkbook(buffos);
	WritableSheet excelSheet = excelBook.createSheet(CMyString.format(LocaleServer.getString("export_userlogininfo_stat.logging.info","用户登录信息表({0}至{1})"),new String[]{cdtStartTime.getDateTimeAsString("yyyy-MM-dd"),cdtEndTime.getDateTimeAsString("yyyy-MM-dd")}), 0);
	 
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
	if (results != null) {

		//构造每一行信息中的每一个单元格的数据，进行输出
		//获取每一条搜索结果，进行输出显示
		UserLoginInfo userLogininfo;// = (UserLoginInfo) results.getAt(0);
		for(int i=0; i<results.size(); i++) {
			userLogininfo = (UserLoginInfo) results.getAt(i);
			
			//获取指定Id的对象
			User user = User.findById(userLogininfo.getUserId());
			Groups groups = user.getGroups();
			String sGroupName = LocaleServer.getString("export_userlogininfo_stat.null","无");
			Group temp = null;
			if(groups.size() > 0){
				sGroupName = "";
				for(int j=0,nsize= groups.size(); j< nsize; j++){
					temp = (Group) groups.getAt(j);
					if(temp == null) continue;
					if(j != nsize-1){
						sGroupName += temp.getName() + " , ";
					}else{
						sGroupName += temp.getName();
					}
				}
			}

			iExcelCol = 0;
			iExcelRow++;
			//往Excel中插入一行数据的各单元格   "序号","发稿人", "部门名称", "登录时间", "IP地址"

			String sFieldValue = "";
			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, i+1);
			excelSheet.addCell(numberCell);

			contentCell = new Label(iExcelCol++, iExcelRow, user.getName());
			excelSheet.addCell(contentCell);

			contentCell = new Label(iExcelCol++, iExcelRow, sGroupName);
			excelSheet.addCell(contentCell);

			contentCell = new Label(iExcelCol++, iExcelRow, userLogininfo.getLoginTime().toString());
			excelSheet.addCell(contentCell);

			contentCell = new Label(iExcelCol++, iExcelRow, userLogininfo.getIpAddress());
			excelSheet.addCell(contentCell);

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