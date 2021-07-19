<%
/** Title:			default_xsl.jsp
 *  Description:
 *		WCM5.2 文件上传页面。
 *		用于文档导入时上传XSL文件
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			NZ
 *  Created:		2005-7-1
 *  Vesion:			1.0
 *  Last EditTime:	
 *	Update Logs:
 *		NZ@2005-7-1 created file
 *	History			Who			What
 *	2009-05-17		wenyh		修正js错误
 *
 *  Parameters:
 *		see default_xsl.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>

<!--- 页面状态设定,登录校验,参数获取,都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtField" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtFields" %>
<%@ page import="com.trs.components.wcm.content.persistent.DocumentFieldConfig" %>
<%@ page import="com.trs.infra.config.XMLConfigServer" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.presentation.documetimport.xml.DocumentImportXmlHelper" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="com.trs.service.ServiceHelper" %>
<%@ page import="com.trs.service.impl.DocumentService" %>
<!------- WCM IMPORTS END ------------>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

<%
//4.初始化(获取数据)vb 
	int nChannelId = currRequestHelper.getInt("ChannelId", 0);
	Channel currChannel = Channel.findById(nChannelId);
	String strFileName = currRequestHelper.getString("FileName");
	if(CMyString.isEmpty(strFileName)){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, LocaleServer.getString("document_addedit_label_11","文件名为空."));
	}
	//安全性问题的处理
	if (strFileName.indexOf("/") >= 0 || strFileName.indexOf("\\") >= 0) {
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,"传入的文件名不合法！");
	}

//5.权限校验

//6.业务代码	
	IChannelService currChannelService = ServiceHelper.createChannelService();
	ContentExtFields currExtendedFields = null;
	if(currChannel!=null){
		currExtendedFields = currChannelService.getExtFields(currChannel, null);
	}

	DocumentService currDocumentService = (DocumentService)DreamFactory.createObjectById("IDocumentService");
	String strDefaultXslPath = currDocumentService.getMyDocumentImportSourceFilePath() + strFileName;

	String[][] arrayImportSrc = DocumentImportXmlHelper.getImportSources(strDefaultXslPath);
//7.结束
	out.clear();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title WCMAnt:param="document_trs_mapping_edit.jsp.title">修改映射文件</TITLE>
<!--css-->
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-list-common.css">
<link rel="stylesheet" type="text/css" href="../../app/js/resource/widget.css"/>
<link href="../../app/css/list-widget.css" rel="stylesheet" type="text/css" />

<style>
	body,td{
		font-size:12px;
	}
	.grid_row{
		background-color:#FFF;
	}
	.sp{
		width:80px;
		display:inline-block;
		text-align:right;
	}
	/*strict*/
	.ext-strict .layout_center_container{top:0;bottom:0;}
	.ext-strict .layout_center{left:0;right:0px;}
	.ext-strict .layout_center_innercontainer{top:50px;bottom:0px;}
	.ext-strict .layout_innercenter{left:0;right:0;}
	/*ie6*/
	.ext-ie6 .layout_container{padding-top:0;padding-bottom:0;}
	.ext-ie6 .layout_center_container{padding-left:0;padding-right:2px;}
	.ext-ie6 .layout_innercontainer{padding-top:50px;padding-bottom:0px;}
	.ext-ie6 .layout_center_innercontainer{padding-left:0;padding-right:0;}
	/*all*/
	.layout_north{height:50px;}
	.layout_south{display:none;}
	.layout_east{display:none;}
</style>
</head>
<BODY topmargin="0" leftmargin="0" style="margin:0px">
<script src="../../app/js/easyversion/cssrender.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
	//存储SQL字段名,用作查重
	var historySQLField = "";
	var arSQLFields = [];	
//-->
</SCRIPT>
<div class="layout_container">
	<div class="layout_center_container">
		<div class="layout_center">
			<div class="layout_container layout_innercontainer">
				<div class="layout_north">
					<TABLE style="height:40px" border="0" cellpadding="0" cellspacing="0">
				<TR>
				  <TD>
					<input type="hidden" id="XMLField" name="XMLField" value="">
					<span WCMAnt:param="document_trs_mapping_edit.jsp.field" class="sp">TRS字段：</span>
					<input type="text" id="TRSField" name="TRSField">
				  </TD>
				</TR>
				<TR>
				  <TD>
					<span WCMAnt:param="document_trs_mapping_edit.jsp.DBfield_a" class="sp">数据库字段：</span>
					<select id="SQLField" name="SQLField" onchange="CheckNewAdd();">
					  <option value="0" WCMAnt:param="document_trs_mapping_edit.jsp.toSelect">请选择...</option>
					  <option value="10" WCMAnt:param="document_trs_mapping_edit.jsp.documentAppendix">文档附件</option>
					<%
					List list = XMLConfigServer.getInstance().getConfigObjects(DocumentFieldConfig.class);
					StringBuffer sbSelected = new StringBuffer();
					List listSqlFields = new ArrayList(10);
					DocumentFieldConfig currDocumentFieldConfig = null;
					HashMap docMaps = new HashMap(); //字段名称和字段描述的映射
					for(java.util.Iterator it=list.iterator(); it.hasNext(); ) {
						currDocumentFieldConfig = (DocumentFieldConfig)it.next();
						if(currDocumentFieldConfig == null || currDocumentFieldConfig.getImport() == 0)
							continue;
						docMaps.put(currDocumentFieldConfig.getFields(), currDocumentFieldConfig.getDesc());
					%>
					  <option value="<%=currDocumentFieldConfig.getFields()%>"><%=currDocumentFieldConfig.getDesc()%></option>
					<%
					}
					ContentExtField currExtendedField = null;
					for(int i=0;currExtendedFields!=null&& i<currExtendedFields.size(); i++) {
						try{
							currExtendedField = (ContentExtField)currExtendedFields.getAt(i);
						} catch(Exception ex){
							throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("document_addedit_label_16","获取第[{0}]个扩展字段失败!"),new int[]{i}),ex);
						}
						if(currExtendedField==null) continue;
						docMaps.put(currExtendedField.getName(), currExtendedField.getDesc());
					%>
						<option value="<%=currExtendedField.getName()%>"><%=currExtendedField.getDesc()%></option>
					<%
					}
					%>
					</select>&nbsp;
					<input type="button" id="btn_add"  value="添加" class="input_btn" disabled WCMAnt:paramattr="value:document_trs_mapping_edit.jsp.add">&nbsp;
					<input type="button" id="btn_edit"  value="修改" class="input_btn" disabled WCMAnt:paramattr="value:document_trs_mapping_edit.jsp.edit">
				  </TD>
				</TR>
			</TABLE>
				</div>
				<div class="layout_center_container layout_center_innercontainer ">
					<div class="layout_center layout_innercenter">

						<div class="layout_container table_grid">
							<div id='wcm_table_grid' class="layout_center_container table_grid_data" style="width:100%!important">
								<table cellspacing=0 border="1" cellpadding=0  class="grid_table" borderColor="gray" id="showFieldMaps" name="showFieldMaps" style="width:100%">
									<tr class="grid_head" id="grid_head">
										<td WCMAnt:param="document_trs_mapping.jsp_edit.trsname" style="width:120px;height:24px;line-height:24px;overflow:hidden;" WCMAnt:param="document_trs_mapping_edit.jsp.TRSfield">TRS字段</td>
										<td WCMAnt:param="document_trs_mapping_edit.jsp.DBfield">数据库字段</td>
										<td WCMAnt:param="document_trs_mapping_edit.jsp.edit" style="width:40px">修改</td>
										<td WCMAnt:param="document_trs_mapping_edit.jsp.delete" style="width:40px">删除</td>
									</tr>
									<tbody class="grid_body" id="grid_body">
									<%
										String strTempValue,strTempKey,strShowKey,strFlag;
										String[] tmpArray;
										for(int i=0; i<arrayImportSrc.length; i++) {
											tmpArray = arrayImportSrc[i];
											strTempKey = tmpArray[0];
											strTempValue = tmpArray[1];
											strFlag = tmpArray[2];
											String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
											if("APPFLAG".equals(strTempKey))
												continue;
											if("10".equals(strFlag)) {
												strShowKey = LocaleServer.getString("document_trs_mapping_edit.label.docAppendix", "文档附件");
												strTempKey = strFlag;
											}
											else{ 
												strShowKey = (String)docMaps.get(strTempKey);
											}
											listSqlFields.add(strTempKey);
									%>									
										<TR height="24" id="<%=strTempValue%>" name="<%=strTempValue%>" class="grid_row <%=sRowClassName%>" key="<%=strTempKey%>" rowid="<%=(i+1)%>">
											<TD align="center"><span title="<%=strTempValue%>" ><%=strTempValue%></span></TD>
											<TD align="center"><%=strShowKey%></TD>
											<TD align="center"><a href="#" grid_function="edit"  WCMAnt:param="document_trs_mapping_edit.jsp.edit">修改</a></TD>
											<TD align="center">
											<%if(!"DOCTITLE".equals(strTempKey) && !"DOCCONTENT".equals(strTempKey)) {%>
											<a href="#" grid_function="delete"  WCMAnt:param="document_trs_mapping_edit.jsp.delete">删除</a>
											<%}else{%>
											&nbsp;
											<%}%>
											</TD>
										</TR>
									<%
										}
									%>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<input type="hidden" id="input" value="<%=CMyString.filterForHTMLValue(strFileName)%>">
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/easyversion/extrender.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script src="../../app/js/easyversion/basicdatahelper.js"></script>
<script src="../../app/js/easyversion/web2frameadapter.js"></script>
<script src="../../app/js/easyversion/elementmore.js"></script>
<script src="../../app/js/easyversion/list.js"></script>
<script src="../js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/document.js"></script>
<script src="../js/source/wcmlib/core/CMSObj.js"></script>
<script src="../js/source/wcmlib/core/AuthServer.js"></script>
<script src="../js/source/wcmlib/pagecontext/PageContext.js"></script>
<script src="../js/source/wcmlib/pagecontext/PageNav.js"></script>
<script src="../js/source/wcmlib/pagecontext/PageGrid.js"></script>
<script src="../js/source/wcmlib/pagecontext/PageLiterator.js"></script>
<script src="../../app/js/source/wcmlib/pagecontext/AbsListInner.js"></script>
<script src="../js/source/wcmlib/pagecontext/BubblePanel.js"></script>

<!--wcm-dialog start-->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></SCRIPT>

<script src="document_trs_mapping_edit.js"></script>
<script language="javascript">
<!--
	try{
		wcm.Grid.init();
	}catch(err){
		//Just skip it.
	}	//PageContext.LoadPage();

	<%
		//wenyh@2009-05-17:因为js放到了后面,所以不能在循环过程中直接调用add
		for(int i =0,size=listSqlFields.size();i<size;i++){
	%>
		add('<%=listSqlFields.get(i)%>')
	<%
		}
		listSqlFields.clear();
	%>
//-->
</script>
</BODY>
</HTML>