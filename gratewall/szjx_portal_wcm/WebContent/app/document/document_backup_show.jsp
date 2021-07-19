<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="../include/error.jsp"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.trs.components.wcm.content.persistent.DocBak" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.Appendix" %>
<%@ page import="com.trs.components.wcm.content.persistent.Appendixes" %>
<%@ page import="com.trs.components.wcm.content.persistent.Relation" %>
<%@ page import="com.trs.components.wcm.content.persistent.Relations" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.ajaxservice.ContentExtendValue" %>
<%@ page import="com.trs.ajaxservice.ContentExtendValues" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.infra.util.ExceptionNumber"%>
<%@ page import="com.trs.infra.common.WCMTypes"%>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@include file="../include/public_server.jsp"%>
<%
	//1 接受页面参数
	int nDocumentId = currRequestHelper.getInt("DocumentId",0);
	int nVersionId = currRequestHelper.getInt("Version",0);
	// 2 初始化工作
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	String sServiceId = "wcm6_documentBak", sMethodName = "findbyid";
	// 3 直接指定参数模式
	HashMap parameters = new HashMap();
	parameters.put("DocumentId", nDocumentId + "");
	parameters.put("Version", nVersionId + "");
	//3.1 文档相关信息
	parameters.put("ObjectId", nVersionId + "");
	DocBak result = (DocBak) processor.excute(sServiceId,
			sMethodName, parameters);
	int nChannelId = result.getPropertyAsInt("DOCCHANNEL",0);
	Channel currChannel = Channel.findById(nChannelId);
	if(currChannel == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found", "没有找到指定的{1} [ID={0}]."), new String[]{String.valueOf(nChannelId),WCMTypes.getLowerObjName(Channel.OBJ_TYPE)}));
	}
	//3.2 相关文档
	Relation currRelation = null;
	sMethodName = "queryRelations";
	Relations DocRelations = (Relations) processor.excute(sServiceId,
			sMethodName, parameters);
	//3.3 附件
	Appendix currAppendix = null;
	sMethodName = "queryAppendixes";
	parameters.put("AppendixType", 20 + "");
	Appendixes ImgAppendixs = (Appendixes) processor.excute(sServiceId,
			sMethodName, parameters);
	parameters.put("AppendixType", 10 + "");
	Appendixes DocAppendixs = (Appendixes) processor.excute(sServiceId,
		sMethodName, parameters);
	parameters.put("AppendixType", 40 + "");
	Appendixes LinkAppendixs = (Appendixes) processor.excute(sServiceId,
		sMethodName, parameters);
	//3.4 扩展字段
	ContentExtendValue currExtValue = null;
	sMethodName = "queryExtendFields";
	ContentExtendValues ExtValues = (ContentExtendValues) processor.excute(sServiceId,
			sMethodName, parameters);
	
%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:TRS="" xmlns:TRS_UI="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title WCMAnt:param="documnet_backup_show.jsp.title">TRS WCM 文档版本显示页面:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</title>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/document.js"></script>
<!--wcm-dialog start-->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!--wcm-dialog end-->
<!--AJAX-->
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<!--<script src="document_backup_show.js" label="PageScope"></script>-->
<link href="./document_detail.css" rel="stylesheet" type="text/css" WCMAnt:locale="./document_detail_$locale$.css"/>
<style type="text/css">
	.docbak_recover{
		border:1px solid gray;
		height:24px;
		line-height:24px;
		padding-right:3px;
		padding-left:20px;
		margin-left:5px;
		background-image:url(../images/document/docbak_recover.gif);
		background-repeat:no-repeat;
		background-position:3px center;
		cursor:pointer;
	}
	.docbak_delete{
		height:24px;
		border:1px solid gray;
		line-height:24px;
		padding-right:3px;
		padding-left:20px;
		margin-left:5px;
		background-image:url(../images/document/docbak_delete.gif);
		background-repeat:no-repeat;
		background-position:3px center;
		cursor:pointer;
	}
	.document_detail_body{
		text-align:left;
	}
	.p_body td{
		font-size:14px;
		line-height:150%;
	}
	.p_body {
		font-size: 14px;
	/*	line-height: 16px;*/
		color: #000000;
		padding-top: 2px;
		padding-right: 5px;
		padding-bottom: 5px;
		padding-left: 5px;
	}
	.document_detail_head{
		padding-top:10px;
		padding-bottom:4px;
		border-bottom:0px solid gray;
		margin-bottom:10px;
	}
	.document_detail_body{
		padding-top:0px;
		margin:0px;
	}
	/*标题*/
	.document_detail_title {
		font-size: 14px;
		color: #000000;
		width: 100%;
		font-weight: bold;
		text-align: center;
		margin-bottom: 10px;
	}
	/*描述信息*/
	.document_detail_desc {
		font-size: 14px;
		line-height: 20px;
		color: #666666;
		padding-top: 5px;
		padding-right: 5px;
		padding-bottom: 5px;
		padding-left: 0px;
		font-family: "宋体";
	}
</style>
</head>

<body style="margin:0;padding:5px">

<table style="width:70%;height:100%;" align="center" border=0 cellpadding=0 cellspacing=0 id="docDetail" class="view_area">
    <tr bgcolor="F3F1F1"><td style="height:45px;" valign="bottom">
        <div class="document_top_nav" id="document_top_nav">
            <div style="float:right;padding:1px 15px;cursor:hand;font-size:12px;">
                <span _function="recoverBak" class="docbak_recover" onclick='javaScript:window.opener.recover(<%=nDocumentId%>,"<%=CMyString.filterForJs(result.getPropertyAsString("DOCTITLE"))%>",<%=nVersionId%>,"true");window.close()' WCMAnt:param="documnet_backup_show.jsp.recoverVersion">
                    恢复当前版本
                </span>
                <span _function="deleteBak"  class="docbak_delete"
				onclick='javaScript:window.opener.deleteVersion(<%=nDocumentId%>,<%=nVersionId%>);window.close()' WCMAnt:param="documnet_backup_show.jsp.deleteVersion" >
                    删除当前版本
                </span>
            </div>
        </div>
    </td></tr>
    <tr><td style="margin:0; padding:0px;">
        <table border=0 cellspacing=0 cellpadding=0 style="width:100%; height:100%;">
        	<tr style="height:1px;" bgcolor="F3F1F1">
        		<td>
                     <div id="document_head" class="document_detail_head">
						  <center>
							  <div class="document_detail_title" style="font-size:24px;"><%=CMyString.transDisplay(result.getPropertyAsString("DOCTITLE"))%></div>
							  <div class="info"><span class="info_name" WCMAnt:param="document_backup_show.jsp.channel">栏目:</span><span class="info_name"><%=CMyString.transDisplay(currChannel.getName())%></span><span class="info_name" WCMAnt:param="document_backup_show.jsp.author">  作者:</span><span class="info_name"><%=CMyString.transDisplay(result.getPropertyAsString("DOCAUTHOR"))%></span><span class="info_name" WCMAnt:param="document_backup_show.jsp.crtime"> 撰写时间:</span><span class="info_name"><%=CMyString.transDisplay(result.getPropertyAsString("DOCRELTIME"))%></span><span class="info_name" WCMAnt:param="document_backup_show.jsp.pubtime">  发布时间:</span><span class="info_name"><%=CMyString.transDisplay((result.getPropertyAsString("DOCPUBTIME")== null ? "" :result.getPropertyAsString("DOCPUBTIME")))%></span><span class="info_name" WCMAnt:param="document_backup_show.jsp.version"> 版本号:</span><span class="info_name"><%=result.getPropertyAsInt("DOCVersion",0)+1%></span></div>
						  </center>
					 </div>               
                </td>
        	</tr>
        	<tr valign="top">
        		<td>
					<div id="document_body">						
						<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="content_outer table_fixed">
						<tr>
						<td class="content_inner">
						<%
						String sHtmlCon = showContent(result,result.getPropertyAsInt("DOCTYPE",0),result.getPropertyAsString("DOCLINK"),result.getPropertyAsString("DOCFILENAME"));
						%>
						 <%=filterForTRS(sHtmlCon)%>
						</td>
						 </tr>
						</table>
					</div>
					<div id="document_abstract">
						<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="p_sep table_fixed">
						<tr>
						  <td class="p_title" WCMAnt:param="documnet_backup_show.jsp.documentAbstract">文档摘要</td>
						</tr>
						<tr>
						  <td class="p_content">
							<table border=0 cellspacing=0 cellpadding=0>
								<tr><td class="abstract_content">								<%=formatDocAbstract(CMyString.filterForHTMLValue(result.getPropertyAsString("DOCABSTRACT")))%>
								</td></tr>
							</table>
						  </td>
						</tr>
					  </table>
					</div>
					<div id="document_extendfields">
						<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="p_sep table_fixed">
					<tr>
					  <td class="p_title" WCMAnt:param="documnet_backup_show.jsp.documentExtend">扩展字段</td>
					</tr>
					<tr>
					  <td class="p_content">
						<table border=0 cellspacing=0 cellpadding=0>
							<tr><td class="p_content">
								<%
									if(ExtValues.size() > 0){
										for(int i = 0 ; i < ExtValues.size(); i++){
											currExtValue = (ContentExtendValue) ExtValues.getAt(i);
											String sExtendName = CMyString.filterForHTMLValue(currExtValue.getPropertyAsString("name"));
											String sExtendValue = CMyString.filterForHTMLValue(currExtValue.getPropertyAsString("value"));
											String []enumValue = sExtendValue.split("~");
											if(enumValue[0].trim().equals("") || enumValue[0].trim().equals("null")) continue;
											if(sExtendValue.indexOf("\n") != -1){
								%>
								<tr class="extend_name"><td><%=CMyString.transDisplay(sExtendName)%>:&nbsp;</td><td></td></tr>
								<tr><td></td><td>
								<table border=0 cellspacing=0 cellpadding=0>
									<tr><td class="abstract_content">
									<%=formatDocAbstract(enumValue[0])%>
									</td></tr>
								</table>
								</td></tr>
							<%
									}else{
										if(enumValue.length >=2 && enumValue[1].trim().equals("2")){
							%>
								<tr class="extend_name"><td><%=CMyString.transDisplay(sExtendName)%>:&nbsp;</td><td><input type="password" value="<%=CMyString.filterForHTMLValue(format(enumValue[0]))%>"/ style="border:0;" readonly></td></tr>
							<%
										}else{
							%>
								<tr class="extend_name"><td><%=CMyString.transDisplay(sExtendName)%>:&nbsp;</td><td><%=CMyString.transDisplay(format(enumValue[0]))%></td></tr>
							<%
											}
										}
									}
								}		
							%>
									</td>
									</tr>
								</table>
							   </td>
							</tr>
						  </table>
					</div>
								<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="p_sep">
				<tr>
					<td colspan="2" class="p_title" WCMAnt:param="document_backup_show.jsp.appendix">附件</td>
				</tr>
				<tr>
					<td valign="top" class="p_content p_contentname" WCMAnt:param="document_backup_show.jsp.picAppendix">图片附件：</td>
					<td class="p_content">
						<div id="document_image_appendixes">
							<%
									if(ImgAppendixs.size() > 0){
										for(int i = 0 ; i < ImgAppendixs.size(); i++){
											currAppendix = (Appendix) ImgAppendixs.getAt(i);
								%>
									<div style="height:90px; width:120px; float:left; margin-right:5px;">
										<a href="/wcm/file/read_image.jsp?FileName=<%=CMyString.URLEncode(currAppendix.getFile())%>" target="about:blank"><img src="<%=CMyString.filterForHTMLValue(mapRollImage(currAppendix.getFile()))%>" border=0 alt="<%=CMyString.filterForHTMLValue(currAppendix.getPropertyAsString("APPLINKALT"))%>" style="height:90px; width:120px;" title="<%=CMyString.filterForHTMLValue(currAppendix.getPropertyAsString("APPLINKALT"))%>"></a>
									</div>
								<%
										}
									}
								%>           
						</div>
					</td>
				</tr>
				<tr>
					<td valign="top" class="p_content p_contentname" WCMAnt:param="document_backup_show.jsp.docAppendix">文档附件：</td>
					<td>
						<div id="document_doc_appendixes">
							<table width="100%" border="0" cellspacing="2" cellpadding="2">
							<tr>
								<td class="p_content">
								<ul class="ul_content">
								<%
									if(DocAppendixs.size() > 0){
										for(int i = 0 ; i < DocAppendixs.size(); i++){
											currAppendix = (Appendix) DocAppendixs.getAt(i);
								%>
										<div>
											<a href="/wcm/file/read_file.jsp?FileName=<%=CMyString.URLEncode(currAppendix.getFile())%>" style="color:black;" title="<%=CMyString.filterForHTMLValue(currAppendix.getPropertyAsString("APPLINKALT"))%>"><%=CMyString.transDisplay(currAppendix.getDesc())%></a>
										</div> 
								<%
										}
									}
								%> 
								</ul>
								</td>
							</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td valign="top" class="p_content p_contentname" WCMAnt:param="document_backup_show.jsp.linkAppendix">链接附件：</td>
				<td>
					<div id="document_link_appendixes">
						<table width="100%" border="0" cellspacing="2" cellpadding="2">
						<tr>
							<td class="p_content">
							<ul class="ul_content">						
							<%
								if(LinkAppendixs.size() > 0){
									for(int i = 0 ; i < LinkAppendixs.size(); i++){
										currAppendix = (Appendix) LinkAppendixs.getAt(i);
							%>
								 <div>
									<a href="<%=CMyString.URLEncode(currAppendix.getDesc())%>" target="about:blank" style="color:black;" title="<%=CMyString.filterForHTMLValue(currAppendix.getPropertyAsString("APPLINKALT"))%>"><%=CMyString.transDisplay(currAppendix.getDesc())%></a>
								</div>     
							<%
									}
								}
							%>          
							</ul>
							</td>
						</tr>
						</table>
					</div>
				</td>
				</tr>
			</table>	
			<div id="document_relations">
				<table width="95%" border="0" class="table_fixed" align="center" cellpadding="0" cellspacing="0">
					<tr>
					  <td class="p_title" WCMAnt:param="document_backup_show.jsp.relation">相关文档</td>
					</tr>
					<tr>
						<td class="p_content">
						<ul class="ul_content" style="margin-left:30px;">
					<%
						if(DocRelations.size() > 0){
							for(int i = 0 ; i < DocRelations.size(); i++){
								currRelation = (Relation) DocRelations.getAt(i);
								if(currRelation == null)continue;
								int nDocId = currRelation.getRelDocId();
								Document oDocument = Document.findById(nDocId);
								if(oDocument==null)continue;
								int nChnlId = oDocument.getChannelId();
								ChnlDoc chnlDoc = ChnlDoc.findByDocAndChnl(nDocId, nChnlId);
								int nRecId = (chnlDoc!=null)?chnlDoc.getPropertyAsInt("RECID", 0):0;
								boolean bCanDetail = hasRight(loginUser,oDocument,32);
					%>
						<li><a href="document_show.jsp?DocumentId=<%=nDocId%>&ChannelId=<%=nChnlId%>&ChnlDocId=<%=nRecId%>&ReadOnly=1" style="color:black;" target="about:blank" onclick="if(!<%=bCanDetail%>)return false;"><div><%=i+1%>.<%=CMyString.filterForHTMLValue(getDocTitle(currRelation.getRelDocId()))%></div></a></li>
					<%
							}
						}
					%>  
					</ul>
						</td>
					</tr>
				</table>
				</div>
				<div style="padding-bottom:5px;"></div>
                </td>
        	</tr>
        </table>
    </td></tr>
</table>
<div id="divNoDocumentFound" style="display:none;">
    <div class="no_object_found"></div>
</div>

<div id="divNoRights" style="display:none; width:100%; height:100%;">
    <div style="font-size:14px; color:red; padding-left:50px;padding-top:100px;" WCMAnt:param="document_backup_show.jsp.noRight">没有查看此篇文档的权限</div>
</div>
<script language="javascript">
<!--
	var iRand = new Date().getTime()%5+1;
	document.getElementById('docDetail').style.backgroundImage = 'url(../images/document/document_show/bg_tu'+iRand+'.gif)';
	//PageContext.LoadDocumentBak();
-->
</script>
</body>
</html>
<%!
	private String filterForTRS(String _sContent){
		return _sContent.replaceAll(
                "(?is)<TRS_COMMENT([^>]*)>(.*?)</TRS_COMMENT>", "<SPAN class=\"fck_comment\" _trscomment=\"true\" contentEditable=\"false\" onresizestart=\"return false\"$1>$2</SPAN>").replaceAll("<trs_page_separator></trs_page_separator>",
				"<table border=0 cellspacing=0 cellpadding=0 width=\"100%\"><tbody><tr><td style=\"font-size:8px;line-height:8px;\" height=\"8\" align=\"center\" valign=\"middle\"><span style=\"width:40%;height:1px;overflow:hidden;background:gray;\"></span>"+LocaleServer.getString("document_detail_label_sep","分页符")
			+"<span style=\"width:40%;height:1px;overflow:hidden;background:gray;\"></span></td></tr></tbody></table>");
	}
	private int getChannelId(int _nDocId) throws WCMException{
		Document currDocument = Document.findById(_nDocId);
		if(currDocument == null){
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,CMyString.format(LocaleServer.getString("object.not.found_a", "没有找到指定ID为[{0}]的对象!"), new int[]{_nDocId}));
		}
		return currDocument.getChannelId();
	}
	
	private String getDocTitle(int _nDocId) throws WCMException{
		Document currDocument = Document.findById(_nDocId);
		if(currDocument == null){
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,CMyString.format(LocaleServer.getString("object.not.found_a", "没有找到指定ID为[{0}]的对象!"), new int[]{_nDocId}));
		}
		return currDocument.getTitle();
	}

	private String formatDocAbstract(String _sAbstract) throws WCMException{
		return _sAbstract.replaceAll("\n", "<br>");
	}
	private String format(String _sAbstract) throws WCMException{
		if(_sAbstract.trim().equals("null")) return "";
		return _sAbstract.replaceAll(",", ", ");
	}
	private String showContent(Document _currDocument,int _nType,String _sDocLink,String _sDocFileName) throws WCMException{
		if(_nType == 30){//链接文档和文件文档单独处理			
			return "<a href=" + _sDocLink + " target='_blank' style='color:black;'>" + _sDocLink + "</a>";
		}else if(_nType == 40){
			return "<a href=/wcm/file/read_file.jsp?FileName=" + _sDocFileName + " target='_blank' style='color:black;'>" + _sDocFileName + "</a>";
		}else if(_nType == 10){ //普通文档
			return CMyString.transDisplay(_currDocument
                    .getContent(), false) ;
		}
		return _currDocument.getHtmlContentWithImgFilter(
                    null, false);
	}
	private String mapRollImage(String _fns) throws WCMException{
			return "/wcm/file/read_image.jsp?FileName=" + _fns;
	}

	private boolean hasRight(User _currUser, CMSObj _objCurrent,int _nRightIndex) throws WCMException{
		if(_objCurrent instanceof Document){
			return DocumentAuthServer.hasRight(_currUser,((Document)_objCurrent).getChannel(),(Document)_objCurrent,_nRightIndex);
		}
		return AuthServer.hasRight(_currUser,_objCurrent,_nRightIndex);
	}
%>