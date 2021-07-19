<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" errorPage="../../../include/error.jsp"%>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@include file="../export_include.jsp"%>
<%@include file="group_hitscount_data_include.jsp"%>
<%
BufferedOutputStream buffos = null;
String excelFile = null;
try{
	//1 构造表头字段名
	String[] saFieldName = new String[]{LocaleServer.getString("export_group_hitscount.num","序号"),LocaleServer.getString("export_group_hitscount.departmentName","部门名称"), LocaleServer.getString("export_group_hitscount.total.hitscount","总点击量")};
	//2 构造Excel基本文件信息
	FilesMan fileManager = FilesMan.getFilesMan();
	excelFile = fileManager.getNextFilePathName(FilesMan.FLAG_SYSTEMTEMP,"xls");	
	buffos = new BufferedOutputStream(new FileOutputStream(excelFile));
	WritableWorkbook excelBook = Workbook.createWorkbook(buffos);
	WritableSheet excelSheet = excelBook.createSheet(LocaleServer.getString("export_group_hitscount.hitscount.chart","部门点击量统计列表"), 0);
	 
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
			if(arGroupIds.size() < i + 1) 
				break;
				
			int nGroupId = Integer.parseInt((String) arGroupIds.get(i));
			Group group = Group.findById(nGroupId);
			if(group == null)
				continue;
			String sGroupName = CMyString.transDisplay(group.getName());

			int nCount = result.getResult(1, String.valueOf(nGroupId));
			
			iExcelCol = 0;
			iExcelRow++;
			//往Excel中插入一行数据的各单元格
			String sFieldValue = "";
			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, i+1);
			excelSheet.addCell(numberCell);
			
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