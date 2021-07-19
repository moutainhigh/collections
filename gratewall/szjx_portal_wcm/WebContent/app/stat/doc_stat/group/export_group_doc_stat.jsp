<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.cms.auth.persistent.Groups" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.cms.auth.persistent.Users" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>

<%@include file="../user/export_include.jsp"%>
<%@include file="group_doc_stat_include.jsp"%>
<%
BufferedOutputStream buffos = null;
String excelFile = null;
try{
	//after to do ...
	String[] saFieldName = new String[]{LocaleServer.getString("group_doc_stat.num","序号"),LocaleServer.getString("group_doc_stat.department", "所属部门"),LocaleServer.getString("group_doc_stat.docnum","发稿量"),LocaleServer.getString("group_doc_stat.newdoc","新稿"),LocaleServer.getString("group_doc_stat.check","正审"),LocaleServer.getString("group_doc_stat.edited","已编"),LocaleServer.getString("group_doc_stat.signed","已签"),LocaleServer.getString("group_doc_stat.sent","已发"),LocaleServer.getString("group_doc_stat.back","返工"),LocaleServer.getString("group_doc_stat.src","原稿"),LocaleServer.getString("group_doc_stat.copy","复制"),LocaleServer.getString("group_doc_stat.quote","引用"),LocaleServer.getString("group_doc_stat.word","文字型"),LocaleServer.getString("group_doc_stat.pic","图片型"),LocaleServer.getString("group_doc_stat.audio","音频型"),LocaleServer.getString("group_doc_stat.video","视频型")};
	//2 构造Excel基本文件信息
	FilesMan fileManager = FilesMan.getFilesMan();
	excelFile = fileManager.getNextFilePathName(FilesMan.FLAG_SYSTEMTEMP,"xls");	
	buffos = new BufferedOutputStream(new FileOutputStream(excelFile));
	WritableWorkbook excelBook = Workbook.createWorkbook(buffos);
	WritableSheet excelSheet = excelBook.createSheet(LocaleServer.getString("group_doc_stat.count","用户发稿量统计列表"), 0);
	 
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
	if (nNum > 0) {  
		for (int i = 0; i < nNum; i++) {
			//反解出构造的key数组
			DocStatHandler4UserDept temp = new DocStatHandler4UserDept();
			String[] sKeyGroup = temp.makeFields((String)arGroupKeys.get(i));	
			String sGroupKey = sKeyGroup[0];

			int nGroupId = Integer.parseInt(sGroupKey);
			Group group = Group.findById(nGroupId);
			String sGroupName = group.getName();

			//实体型文档数目
			int nEntityNum = result.getResult(3,new String[]{sGroupKey,CMSConstants.CONTENT_MODAL_ENTITY + ""});
			int nCopyNum = result.getResult(4, nGroupId + "");
			nEntityNum = nEntityNum - nCopyNum;

			int nLinkNum = result.getResult(3,new String[]{sGroupKey,CMSConstants.CONTENT_MODAL_LINK + ""});
			int nMirrorNum = result.getResult(3,new String[]{sGroupKey,CMSConstants.CONTENT_MODAL_MIRROR + ""});

			iExcelCol = 0;
			iExcelRow++;
			//往Excel中插入一行数据的各单元格
			String sFieldValue = "";
			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, i+1);
			excelSheet.addCell(numberCell);

			contentCell = new Label(iExcelCol++, iExcelRow, sGroupName);
			excelSheet.addCell(contentCell);
			
			//总发稿量
			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, result.getResult(1,sGroupKey));
			excelSheet.addCell(numberCell);

			//按状态
			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, result.getResult(2,new String[]{sGroupKey,Status.STATUS_ID_NEW + ""}));
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, result.getResult(2,new String[]{sGroupKey,Status.STATUS_ID_VERIFY +""}));
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, result.getResult(2,new String[]{sGroupKey,Status.STATUS_ID_EDITED +""}));
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, result.getResult(2,new String[]{sGroupKey,Status.STATUS_ID_SIGN +""}));
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, result.getResult(2,new String[]{sGroupKey,Status.STATUS_ID_PUBLISHED +""}));
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, result.getResult(2,new String[]{sGroupKey,Status.STATUS_ID_AGAIN +""}));
			excelSheet.addCell(numberCell);
			
			//按创作方式
			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, nEntityNum);
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, nCopyNum);
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, nLinkNum + nMirrorNum);
			excelSheet.addCell(numberCell);

			//按类型
			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, result.getResult(5,new String[]{sGroupKey,Document.DOC_FORM_LITERY + ""}));
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, result.getResult(5,new String[]{sGroupKey,Document.DOC_FORM_PIC + ""}));
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, result.getResult(5,new String[]{sGroupKey,Document.DOC_FORM_AUDIO + ""}));
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, result.getResult(5,new String[]{sGroupKey,Document.DOC_FORM_VIDEO + ""}));
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