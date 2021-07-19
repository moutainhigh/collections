<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="include/error.jsp"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.wcm.photo.IImageLibMgr" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.Documents" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="java.util.HashMap" %>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
	//接受页面参数
	int nPhotoId = currRequestHelper.getInt("PhotoId",0);
	String sPicFile = currRequestHelper.getString("PicName");
	//String fields = "DocTitle,CrUser,CrTime,DocKeywords,DocChannel,DOCSTATUS,DOCPUBTIME,DOCCONTENT";
    Document photodoc = Document.findById(nPhotoId);
	if(photodoc == null){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, CMyString.format(LocaleServer.getString("photo_show_editor.jsp.pic_notfound", "没有找到ID为[{0}]的图片!"), new int[]{nPhotoId}));
	}
	String sRelWords = photodoc.getRelateWords();
	String sDefault = photodoc.getAttributeValue("srcfile");
	String sFileName = mapfileName(sRelWords, 1, sDefault);	
	//构造getQuoteDocs方法，提取参数
	IImageLibMgr m_libManager = (IImageLibMgr) DreamFactory
                .createObjectById("IImageLibMgr");
	Documents docs = m_libManager.getDocumentsQuoteImage(nPhotoId);
	Document doc = null;

%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title WCMAnt:param="photo_show_editor.jsp.title">图片查看</title>
	<style>
		.template{
			display:none;
		}
		.attr_row{
			margin-left:10px;
			font-size:12px;	
			float:left;
			width:330px;			
		}
		.attr_name{
			font-weight:bold;				
			width:25%;
			text-align:right;
			padding-right:15px;
			margin:5px;
			padding:5px;
			empty-cells:show;
			border-bottom:1px dashed lightgrey;
		}
		.attr_value{
			padding:5px;
			margin:5px;
			width:75%;
			clear:both;
			empty-cells:show;
			border-bottom:1px dashed lightgrey;
			break-word:break-all;
			word-wrap:break-word;
		}
	   .ext-ie .bodyStyle{
			overflow:auto;
			width:330px;
			height:400px;
		}
		.ext-gecko .bodyStyle{
			overflow:auto;
			width:100%;
			height:100%;
		}
	</style>
	<script src="../../app/js/runtime/myext-debug.js"></script>
	<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
	<script src="../../app/js/data/locale/photo.js"></script>
	<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
	<!--AJAX-->
	<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
	<script src="photo_show_editor.js"></script>	
</head>
<body class="bodyStyle">
<div style="text-align:center;margin-top:10px;padding:5px 0;">
	<img src="<%=sFileName%>" id="picshower" onload="resizeIfNeed(this);">
</div>
<div style="width:330px;height:40px;">&nbsp;</div>
<div style="width:360px;margin-left:20px;">
	<div id="imageprops">
		<table border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed;border-collapse:collapse;">
		<tbody>
			<tr class="attr_row">
				<td class="attr_name" WCMAnt:param="photo_show_editor.jsp.picStyle">图片类型:</td>
				<td class="attr_value"><span id="pictype"></span></td>
			</tr>
			<tr class="attr_row">
				<td class="attr_name" WCMAnt:param="photo_show_editor.jsp.subTitle">标题:</td>
				<td class="attr_value"><%=photodoc.getTitle()%></td>
			</tr>
			<tr class="attr_row">
				<td class="attr_name" WCMAnt:param="photo_show_editor.jsp.describe">描述:</td>
				<td class="attr_value"><%=photodoc.getContent()%></td>
			</tr>
			<tr class="attr_row">
				<td class="attr_name" WCMAnt:param="photo_show_editor.jsp.createuser">创建人:</td>
				<td class="attr_value"><%=photodoc.getCrUser()%></td>
			</tr>
			<tr class="attr_row">
				<td class="attr_name" WCMAnt:param="photo_show_editor.jsp.crtime">创建时间:</td>
				<td class="attr_value"><%=photodoc.getCrTime()%></td>
			</tr>
			<%
				if(photodoc.getStatusId() == 10){
			%>
				<tr class="attr_row">
					<td class="attr_name" WCMAnt:param="photo_findbyid.jsp.pubTime">发布时间：</td>
					<td class="attr_value" title="<%=photodoc.getPubTime()%>"><%=photodoc.getPubTime()%></td>
				</tr>
			<%
				}
			%>
		</tbody>
		</table>
	</div>
</div>
<table>
	<%
		if(docs.size() >0){
	%>
	<tr>
		<td class="attr_name"  style="border:0px;" align="right" WCMAnt:param="photo_show_editor.jsp.beQuotedByDoc">被以下文档引用：</td>
		<td>&nbsp;</td>
	</tr>	
	<%
		}
	%>
	<%
		 for (int i = 0, size = docs.size(); i < size; i++) {
			doc = (Document) docs.getAt(i);
			if (doc != null) {
	%>
	<tr>
		<td class="attr_name" style="border:0px;" >&nbsp;</td>
		<td class="attr_value status_<%=String.valueOf(doc.getStatusId())%>" style="border:0px;">[<%=i
		+1%>].&nbsp;&nbsp;<%=doc.getTitle()%>&nbsp;&nbsp;[<%=getChnlPath(doc.getChannel())%>]</td>
	</tr>					
	<%
			}
		}
	%>
	<tr height="10">
		<td>&nbsp;</td>
	</tr>
</table>
</body>
</html>
<%!
	private String getChnlPath(Channel _channel) throws WCMException {
        if (_channel == null) {
            return "";
        }

        StringBuffer buff = new StringBuffer(128);
        buff.append(_channel.getSite().getDesc());
        buff.append("&nbsp;>&nbsp;");
        Channel parent = _channel.getParent();
        while (parent != null) {
            buff.append(parent.getDesc());
            parent = parent.getParent();
            buff.append("&nbsp;>&nbsp;");
        }

        buff.append(_channel.getDesc());

        return buff.toString();
    }
	private String mapfileName(String sFileName,int nIndex,String sDefault) throws WCMException {
		if(sFileName == null || (sFileName.trim()).equals("")){
				return "../images/photo/pic_notfound.gif";
			}
		String [] fg = sFileName.split(",");
		String r = "";
		if(fg.length <= nIndex){
			r = "../../file/read_image.jsp?FileName=" +  sDefault;
			return r;
		}
		r = fg[fg.length -1];
		return "/webpic/" + r.substring(0,8) + "/" + r.substring(0,10) + "/" + r;	
	}
%>