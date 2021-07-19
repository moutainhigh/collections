<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="include/error.jsp"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="java.util.HashMap" %>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
	//接受页面参数
	int nPhotoId = currRequestHelper.getInt("PhotoId",0);
	String sPicFile = currRequestHelper.getString("PicName");
	String fields = "DocTitle,CrUser,CrTime,DocKeywords,DocChannel";
    Document photodoc = Document.findById(nPhotoId, fields);

%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title WCMAnt:param="photo_sysPic_showdetail.jsp.title">图片查看</title>
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
	</style>
	<script src="../../app/js/runtime/myext-debug.js"></script>
	<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
	<script src="../../app/js/data/locale/photo.js"></script>
	<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
	<!--AJAX-->
	<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
	<script src="photo_syspic_showdetail.js"></script>	
</head>
<body style="overflow:auto;width:330px;height:400px;">
<div style="text-align:center;margin-top:10px">
	<img src=""id="picshower" onload="resizeIfNeed(this);" align="absmiddle">
</div>
<div style="width:330px;height:40px;">&nbsp;</div>
<div style="width:360px;margin-left:20px;">
	<div id="imageprops">
		<table border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed;border-collapse:collapse;">
		<tbody>
			<tr class="attr_row">
				<td class="attr_name" WCMAnt:param="photo_sysPic_showdetail.jsp.picStyle">图片类型:</td>
				<td class="attr_value"><span id="pictype"></span></td>
			</tr>
			<tr class="attr_row">
				<td class="attr_name" WCMAnt:param="photo_sysPic_showdetail.jsp.subTitle">标题:</td>
				<td class="attr_value"><%=photodoc.getTitle()%></td>
			</tr>
			<tr class="attr_row">
				<td class="attr_name" WCMAnt:param="photo_sysPic_showdetail.jsp.crtime">创建时间:</td>
				<td class="attr_value"><%=photodoc.getCrTime()%></td>
			</tr>
			<tr class="attr_row">
				<td class="attr_name" WCMAnt:param="photo_sysPic_showdetail.jsp.cruser">发稿人:</td>
				<td class="attr_value"><%=photodoc.getCrUser()%></td>
			</tr>
			<tr class="attr_row">
				<td class="attr_name" WCMAnt:param="photo_sysPic_showdetail.jsp.keyWords">关键词:</td>
				<td class="attr_value"><%=CMyString
                .showNull(photodoc.getKeywords(), "")%></td>
			</tr>
			<tr class="attr_row">
				<td class="attr_name" WCMAnt:param="photo_sysPic_showdetail.jsp.position">所在位置:</td>
				<td class="attr_value"><%=getChnlPath(photodoc.getChannel())%></td>
			</tr>	
		</tbody>
		</table>
	</div>
</div>
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
	
%>