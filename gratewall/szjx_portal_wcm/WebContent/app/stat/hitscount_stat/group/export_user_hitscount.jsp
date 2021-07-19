<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" errorPage="../../../include/error.jsp"%>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@include file="../export_include.jsp"%>
<%@include file="user_hitscount_data_include.jsp"%>
<%
BufferedOutputStream buffos = null;
String excelFile = null;
try{
	//1 构造表头字段名
	String[] saFieldName = saFieldName = new String[]{LocaleServer.getString("export_user_hitscount.num","序号"),LocaleServer.getString("export_user_hitscount.user","发稿人"),LocaleServer.getString("export_user_hitscount.departmentName","部门名称"),LocaleServer.getString("export_user_hitscount.total.hitscount","总点击量")};	
	//2 构造Excel基本文件信息
	FilesMan fileManager = FilesMan.getFilesMan();
	excelFile = fileManager.getNextFilePathName(FilesMan.FLAG_SYSTEMTEMP,"xls");	
	buffos = new BufferedOutputStream(new FileOutputStream(excelFile));
	WritableWorkbook excelBook = Workbook.createWorkbook(buffos);
	WritableSheet excelSheet = excelBook.createSheet(LocaleServer.getString("export_user_hitscount.total.user","用户点击量统计列表"), 0);
	 
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

	//循环
	if (nNum > 0) {  
		for (int i = 0; i < nNum; i++) {
			if(arUserNames.size() < i + 1) 
				break;
				
			String sUserName = (String) arUserNames.get(i);
			User oUser = User.findByName(sUserName);
			if("".equals(sUserName))
				continue;
			 int nCount = result.getResult(1, sUserName);
			 if(nCount == 0)
				 continue;
			 String sGroupName = LocaleServer.getString("export_user_hitscount.null","无");
			 Groups groups = oUser.getGroups();
			 Group temp = null;
			 if(groups.size() > 0){
				sGroupName = "";
				for(int j=0,nsize= groups.size(); j< nsize; j++){
					temp = (Group) groups.getAt(j);
					if(temp == null) continue;
					if(j != nsize-1){
						sGroupName += temp.getName() + ",";
					}else{
						sGroupName += temp.getName();
					}
				}
			 }
			
			iExcelCol = 0;
			iExcelRow++;
			//往Excel中插入一行数据的各单元格
			String sFieldValue = "";
			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, i+1);
			excelSheet.addCell(numberCell);
			
			contentCell = new Label(iExcelCol++, iExcelRow, sUserName);
			excelSheet.addCell(contentCell);
			
			contentCell = new Label(iExcelCol++, iExcelRow, sGroupName);
			excelSheet.addCell(contentCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, nCount);
			excelSheet.addCell(numberCell);
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
	String sResult = "";
	sResult = "<excelfile>"+ sXslFileName + "</excelfile>";
	out.clear();
	out.print(sResult);
	return;
}finally{
	if(buffos!=null){
		buffos.close();
	}
}
%>