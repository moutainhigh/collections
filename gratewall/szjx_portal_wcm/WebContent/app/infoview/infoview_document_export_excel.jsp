<%
/** Title:			infoview_document_export_excel.jsp
 *  Description:
 *		WCM5.2 自定义表单，导出自定义表单数据为EXCEL文件(csv)
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			FCR
 *  Created:		2006.06.22
 *  Vesion:			1.0
 *	Update Logs:
 *	History				Who				What
 *	2007-01-08			wenyh			修正导出的时间及编号显示的问题
 *
 *  Parameters:
 *		see infoview_document_export_excel.xml
 *
 */
%>

<%@ page contentType="text/html;charset=GBK" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%-- ----- WCM IMPORTS BEGIN ---------- --%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.Documents" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.presentation.wcm.content.DocumentsPageHelper" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="com.trs.components.infoview.persistent.InfoView" %>
<%@ page import="com.trs.components.infoview.helper.InfoViewNewQueryHelper" %>
<%@ page import="com.trs.components.infoview.InfoViewHelper" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewDocuments" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewDocument" %>
<%-- ----- WCM IMPORTS END ---------- --%>

<%@ page import="jxl.write.WritableWorkbook" %>
<%@ page import="jxl.write.WritableSheet" %>
<%@ page import="jxl.write.WritableFont" %>
<%@ page import="jxl.write.WritableCellFormat" %>
<%@ page import="jxl.write.Label" %>
<%@ page import="jxl.Workbook" %>
<%@ page import="java.io.BufferedOutputStream" %>
<%@ page import="java.io.FileOutputStream" %>
<%@ page import="com.trs.infra.util.CMyZip" %>
<%@ page import="com.trs.cms.content.HTMLContent" %>
<%@ page import="com.trs.infra.util.CMyException" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewField" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewFields" %>
<%@ page import="java.io.File"%>
<%@include file="./infoview_public_include.jsp"%>
<%--  页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../../include/validate_publish.jsp"%>

<%!boolean IS_DEBUG = false;%>
<%
BufferedOutputStream buffos = null;
String excelFile = null;
try{
//4.初始化（获取数据）
	int	nChannelId		= currRequestHelper.getInt("ChannelId", 0);
	//ge gfc add @ 2008-1-8 兼容从V6链过来的情况
	if(nChannelId == 0) {
		nChannelId		= currRequestHelper.getInt("channelid", 0);
		currRequestHelper.setValue("ChannelId", String.valueOf(nChannelId));
	}
	Channel currChannel = Channel.findById(nChannelId);
	if(currChannel == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("infoview_document_export_excel.channel.failed","获取ID为[{0}]的栏目失败！"),new int[]{nChannelId}));
	}
	InfoView infoview = InfoViewHelper.getInfoView(currChannel);
	if(infoview == null){
		throw new WCMException(LocaleServer.getString("infoview_document_export_excel.ont.fit","该栏目不是自定义表单类型的栏目或者该栏目没有配置有效的自定义表单，无法编辑其下的文档！"));
	}
	InfoViewFields allFields = InfoViewFields.findBy(infoview);
	String sExportFields = currRequestHelper.getString("ExportFields");

	if(CMyString.isEmpty(sExportFields)){
		sExportFields = CMyString.showNull(currChannel.getOutlineFields(), CMyString.showNull(infoview.getOutlineFields(), m_sDEFAULT_OUTLINE_FILEDS_NAME));
		//导出当前视图
		currRequestHelper.setValue("ExportFields", sExportFields);
	}

	String[] saFieldName = InfoViewNewQueryHelper.getFieldNames(infoview, sExportFields);
	String[] saFieldDicplay = InfoViewNewQueryHelper.getFieldsDisplay(saFieldName);

	String[] aDBFieldNames = InfoViewHelper.getDBFieldsByFields(infoview,
			saFieldName, false);

	//Excel
	FilesMan fileManager = FilesMan.getFilesMan();
	excelFile = fileManager.getNextFilePathName(FilesMan.FLAG_SYSTEMTEMP,"xls");	
	buffos = new BufferedOutputStream(new FileOutputStream(excelFile));
	WritableWorkbook excelBook = Workbook.createWorkbook(buffos);
	WritableSheet excelSheet = excelBook.createSheet(LocaleServer.getString("infoview.document.export.excel" ,"WCM自定义表单文档数据"), 0);
	
	//表头		
	int iExcelCol = 0;
	int iExcelRow = 0;
	Label contentCell = null;
	WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false);
	WritableCellFormat wcfF = new WritableCellFormat(wf);
	for (int column = 0; column < saFieldName.length; column++) {
	    if (isExportField(saFieldName[column])) {
			if(m_sDEFAULT_OUTLINE_FILEDS_OUTLINENAME.indexOf(saFieldDicplay[column]) >= 0){
				contentCell = new Label(iExcelCol++,iExcelRow,saFieldDicplay[column], wcfF);
			}else if(saFieldDicplay[column].equalsIgnoreCase("POSTIP")){
				contentCell = new Label(iExcelCol++,iExcelRow,LocaleServer.getString("export_excel.ip","发稿人IP"), wcfF);
			}else 
				contentCell = new Label(iExcelCol++,iExcelRow,saFieldDicplay[column]);
				excelSheet.addCell(contentCell);
	    }
	}

	int nPageSize = currRequestHelper.getInt("PageItemCount", 0);
	if(nPageSize == 0){
		nPageSize = -1;
		currRequestHelper.setValue("PageItemCount", "-1");
	}
	CMyZip oMyZip = new CMyZip();
	String sXlsFilePath = fileManager.mapFilePath(excelFile, FilesMan.PATH_LOCAL);
	int nLastPos = excelFile.lastIndexOf('.');
    String sZipFile = excelFile.substring(0, nLastPos) + ".zip";
	oMyZip.setZipFileName(sZipFile);

	InfoViewDocuments oInfoViewDocuments = InfoViewNewQueryHelper.queryData(currRequestHelper);
	CPager currPager = new CPager(nPageSize);
	currPager.setItemCount(oInfoViewDocuments.size());
	currPager.setCurrentPageIndex(currRequestHelper.getInt("PageIndex", 1));
	for (int i = currPager.getFirstItemIndex(); i <= currPager
			.getLastItemIndex(); i++) {
		try {
			InfoViewDocument oInfoViewDoc = (InfoViewDocument) oInfoViewDocuments
					.getAt(i - 1);
			if (oInfoViewDoc == null) {
				continue;
			}
			iExcelCol = 0;
			iExcelRow++;
			for (int column = 0; column < aDBFieldNames.length; column++) {
				String sFieldName = aDBFieldNames[column];
				if (!isExportField(sFieldName)) {
				    continue;
				}
				String sFieldValue = CMyString.showNull(getFieldValue(oInfoViewDoc, sFieldName));
				contentCell = new Label(iExcelCol++,iExcelRow, sFieldValue);						 
				excelSheet.addCell(contentCell);
				InfoViewField oField = null;
				for(int k=0; k < allFields.size(); k++){
					InfoViewField currField = (InfoViewField)allFields.getAt(k);
					String sTrueFieldName = currField.getTrueFieldName();
					if(!CMyString.isEmpty(sTrueFieldName) && sTrueFieldName.equals(sFieldName)){
						oField = currField;
						break;
					}	
				}
				if("".equals(sFieldValue) || oField == null || oField.getFieldType() == null){
					continue;
				}
				if("FileAttachment".equals(oField.getFieldType()) || "InlineImage".equals(oField.getFieldType())){
					getAppendixFieldFiles(sFieldValue);
				}
				if(oField.getDataType() == java.sql.Types.CLOB){
					getClobFieldFiles(sFieldValue);
				}
			}
		} catch (Exception ex) {
            throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("infoview.doc.export.error1","获取第[{0}]篇文档失败！"),new int[]{i}), ex);
		}
	}// end for

//6.资源释放
	excelBook.write();
	excelBook.close();
	if(buffos!=null){
		buffos.close();
	}
	try{
		oMyZip = mergeAppFiles(oMyZip, m_arAppFiles);
		if (CMyFile.fileExists(excelFile)) {
			oMyZip.addToZip(excelFile, false);
		}
		oMyZip.done();
		
		String sXslFileName = CMyFile.extractFileName(excelFile);
		String sZipFileName = sXslFileName.substring(0, sXslFileName.lastIndexOf('.'));
		String result = "";
		if(m_arAppFiles.size() > 0)
			result = "<excelfile>"+ sZipFileName + ".zip"+"</excelfile>";
		else 
			result = "<excelfile>"+ sXslFileName + "</excelfile>";
		out.clear();
		out.print(result);
	}catch(Exception ex){
		 throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, LocaleServer.getString("infoview_document_export_excel.reduce.failed","将文件压入zip包失败！"), ex);
	}
}
finally{
	if(buffos!=null){
		buffos.close();
	}
}
//7.不输出后面的无意义的空行
	if (true) {
		return;
	}
%>

<%!
	private ArrayList m_arAppFiles = new ArrayList();
	private final static String m_sDEFAULT_OUTLINE_FILEDS_NAME = "_EDIT,DOCTITLE,_PREVIEW,CRTIME,CRUSER,POSTIP,_DOCSTATUS";
	private final static String m_sDEFAULT_OUTLINE_FILEDS_OUTLINENAME =  LocaleServer.getString("infoview_document_export_excel.jsp.label.doc_state","文档标题,创建时间,发稿人,发稿人IP,文档状态");
    private boolean isExportField(String sFieldName) throws WCMException {
    	if (sFieldName.startsWith("_")) {
        	if ("_EDIT".equals(sFieldName) || "_PREVIEW".equals(sFieldName)) {
        	    return false;
        	}
    	}
	    return true;
	}
	private String getFieldValue(InfoViewDocument _infoviewDoc, String _sFieldName)
		throws WCMException {
		if(_sFieldName.equalsIgnoreCase("POSTIP")){
			Document currDocument = _infoviewDoc.getDocument();
			return CMyString.showNull(currDocument.getAttributeValue("IP"));
		}
		if(_sFieldName.equalsIgnoreCase("_DocStatus")){
			return _infoviewDoc.getStatus().getDisp();
		}
		if(_sFieldName.equalsIgnoreCase("DocChannel")){
			return _infoviewDoc.getChannel().getName();
		}
		int nIndexDot = _sFieldName.indexOf(".");
		if(nIndexDot!=-1){
			_sFieldName = _sFieldName.substring(nIndexDot+1);
		}
		return _infoviewDoc.getPropertyAsString(_sFieldName, _infoviewDoc.getChnlDocProperty(_sFieldName));
	}
	 
	private void getClobFieldFiles(String _currValue){
		try{
			HTMLContent htmlContent = new HTMLContent(_currValue.toString());
			m_arAppFiles.addAll(htmlContent.getWCMFiles());
		}catch(Exception ex){
			//do nothing
		}
	}
	private void getAppendixFieldFiles(String _currValue){
		m_arAppFiles.add(_currValue.toString());
	}
	private CMyZip mergeAppFiles(CMyZip _oMyZip, ArrayList _arAppFiles)
            throws WCMException, CMyException {
        FilesMan filesMan = FilesMan.getFilesMan();
        for (int i = 0, nSize = _arAppFiles.size(); i < nSize; i++) {
            String sAppFileName = (String) _arAppFiles.get(i);

            if (CMyString.isEmpty(sAppFileName))
                continue;
            sAppFileName = filesMan.mapFilePath(sAppFileName,
                    FilesMan.PATH_LOCAL)
                    + sAppFileName;
            if (!CMyFile.fileExists(sAppFileName)) {
                continue;
            }
            _oMyZip.addToZip(sAppFileName, false);
        }
		return _oMyZip;
    }
%>