<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" errorPage="../../../include/error.jsp"%>

<%@include file="export_stat_chnl_include.jsp"%>
<%
BufferedOutputStream buffos = null;
String excelFile = null;
try{
	//开始构造excel数据
	//应该用个二维数组了
	//String[] saFieldName = new String[]{"序号","栏目名称","所属站点","总发稿量","文档状态统计","使用情况统计","文档类型统计"};
	String[] saFieldName = new String[]{LocaleServer.getString("doc_stat.num","序号"),LocaleServer.getString("doc_stat.chnl.name","栏目名称"),LocaleServer.getString("doc_stat.site","所属站点"),LocaleServer.getString("doc_stat.total","总发稿量"),LocaleServer.getString("doc_stat.new.doc","新稿"),LocaleServer.getString("doc_stat.check","正审"),LocaleServer.getString("doc_stat.edited","已编"),LocaleServer.getString("doc_stat.signed","已签"),LocaleServer.getString("doc_stat.sent","已发"),LocaleServer.getString("doc_stat.resent","返工"),LocaleServer.getString("doc_stat.sourcedoc","原稿"),LocaleServer.getString("doc_stat.copy","复制"),LocaleServer.getString("doc_stat.quote","引用"),LocaleServer.getString("doc_stat.word","文字型"),LocaleServer.getString("doc_stat.pic","图片型"),LocaleServer.getString("doc_stat.audio","音频型"),LocaleServer.getString("doc_stat.video","视频型")};
	String[] StatusName = {LocaleServer.getString("doc_stat.new.doc","新稿"),LocaleServer.getString("doc_stat.check","正审"),LocaleServer.getString("doc_stat.signed","已签"),LocaleServer.getString("doc_stat.sent","已发"),LocaleServer.getString("doc_stat.back","返工")};
	String[] UseName = {LocaleServer.getString("doc_stat.sourcedoc","原稿"),LocaleServer.getString("doc_stat.copy","复制"),LocaleServer.getString("doc_stat.quote","引用")};
	String[] typeName = {LocaleServer.getString("doc_stat.word","文字型"),LocaleServer.getString("doc_stat.pic","图片型"),LocaleServer.getString("doc_stat.audio","音频型"),LocaleServer.getString("doc_stat.video","视频型")};
	//2 构造Excel基本文件信息
	FilesMan fileManager = FilesMan.getFilesMan();
	excelFile = fileManager.getNextFilePathName(FilesMan.FLAG_SYSTEMTEMP,"xls");
	buffos = new BufferedOutputStream(new FileOutputStream(excelFile));
	WritableWorkbook excelBook = Workbook.createWorkbook(buffos);
	WritableSheet excelSheet = excelBook.createSheet(LocaleServer.getString("doc_stat.chnl","栏目发稿量统计表"), 0);
	 
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
	//for (int i = 0,nLen = saFieldName[4].length-1; i < nLen; i++){
	//	contentCell = new Label(iExcelCol++,iExcelRow+1,saFieldName[column][0], wcfF);
	//}

	//4 构造各个单元格    

	//在每个循环中做的事情
	if (ChnlKeys != null) {
		for (int i = 0; i < ChnlKeys.size(); i++) {
			String sKey = (String) ChnlKeys.get(i);
			int nChnlId = Integer.parseInt(sKey);
			Channel oChannel = Channel.findById(nChnlId);
			if(oChannel==null)continue;
			String sChnlDesc = oChannel.getDesc();
			String sChnlName = oChannel.getName();
			String sSiteDesc = oChannel.getSite().getDesc();
			int nDocCount = result.getResult(1, sKey);
			int nDocCountOfNew = result.getResult(2, new String[]{sKey,String.valueOf(Status.STATUS_ID_NEW)});
			int nDocCountOfVerify = result.getResult(2, new String[]{sKey,String.valueOf(Status.STATUS_ID_VERIFY)});
			int nDocCountOfEdited = result.getResult(2, new String[]{sKey,String.valueOf(Status.STATUS_ID_EDITED)});
			int nDocCountOfSign = result.getResult(2, new String[]{sKey,String.valueOf(Status.STATUS_ID_SIGN)});
			int nDocCountOfPublished = result.getResult(2, new String[]{sKey,String.valueOf(Status.STATUS_ID_PUBLISHED)});
			int nDocCountOfNo = result.getResult(2, new String[]{sKey,String.valueOf(Status.STATUS_ID_NO)});
			int nDocCountOfEntity = result.getResult(3, new String[]{sKey,String.valueOf(CMSConstants.CONTENT_MODAL_ENTITY)});
			int nDocCountOfLinkModal = result.getResult(3, new String[]{sKey,String.valueOf(CMSConstants.CONTENT_MODAL_LINK)});
			int nDocCountOfMirrorModal = result.getResult(3, new String[]{sKey,String.valueOf(CMSConstants.CONTENT_MODAL_MIRROR)});
			int nDocCountOfModal = nDocCountOfLinkModal + nDocCountOfMirrorModal;
			int nDocCountOfCopy = result.getResult(4, sKey);
			//nDocCountOfEntity = nDocCountOfEntity - nDocCountOfCopy;
			int nDocCountOfLitery = result.getResult(5, new String[]{sKey,String.valueOf(Document.DOC_FORM_LITERY)});
			int nDocCountOfPic = result.getResult(5, new String[]{sKey,String.valueOf(Document.DOC_FORM_PIC)});
			int nDocCountOfAudio = result.getResult(5, new String[]{sKey,String.valueOf(Document.DOC_FORM_AUDIO)});
			int nDocCountOfVideo = result.getResult(5, new String[]{sKey,String.valueOf(Document.DOC_FORM_VIDEO)});
			
			iExcelCol = 0;
			iExcelRow++;
			//往Excel中插入一行数据的各单元格
			String sFieldValue = "";
			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, i+1);
			excelSheet.addCell(numberCell);

			contentCell = new Label(iExcelCol++, iExcelRow, sChnlDesc);
			excelSheet.addCell(contentCell);

			contentCell = new Label(iExcelCol++, iExcelRow, sSiteDesc);
			excelSheet.addCell(contentCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, nDocCount);
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, nDocCountOfNew);
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, nDocCountOfVerify);
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, nDocCountOfEdited);
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, nDocCountOfSign);
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, nDocCountOfPublished);
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, nDocCountOfNo);
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, nDocCountOfEntity);
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, nDocCountOfCopy);
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, nDocCountOfModal);
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, nDocCountOfLitery);
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, nDocCountOfPic);
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, nDocCountOfAudio);
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, nDocCountOfVideo);
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