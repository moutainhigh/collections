<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.ChannelContentLink" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChannelContentLinks" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@include file="../include/public_server.jsp"%>
<%
	//接受页面参数
	int nObjectId = currRequestHelper.getInt("ObjectId",0);
	int nChannelId = currRequestHelper.getInt("ChannelId",0);
	int nSiteId = currRequestHelper.getInt("SiteId",0);
	int nTempId = currRequestHelper.getInt("ContainsSite",0);
	boolean bContainsSite = nTempId == 1;
	ChannelContentLink currChannelContentLink = null,newChannelContentLink = null;
	ChannelContentLinks currChannelContentLinks = null;
	if(nObjectId == 0){
		currChannelContentLink = ChannelContentLink.createNewInstance();
	}else{
		currChannelContentLink = ChannelContentLink.findById(nObjectId);
		//参数校验
		if(currChannelContentLink == null){
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, CMyString.format(LocaleServer.getString("channelcontentlink_add_edit.jsp.notfindchnlkeyword","没有找到ID为{0}的栏目热词"),new int[]{nObjectId}));
		}
	} 
	WCMFilter filter = new WCMFilter("", "", "linkorder desc","");
	if(nChannelId > 0){
		filter.setWhere("channelid=?");
		filter.addSearchValues(0, nChannelId);
	}else{
		filter.setWhere("siteid=?");
		filter.addSearchValues(0, nSiteId);
	}
	try{
		currChannelContentLinks = ChannelContentLinks.openWCMObjs(ContextHelper.getLoginUser(),filter);
	}catch(Exception e){
		throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, LocaleServer.getString("channelcontentlink_add_edit.jsp.getkeywords","获取热词集合！"), e);
	}
	int nSize = currChannelContentLinks.size();
%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:TRS=""  xmlns:TRS_UI="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<style type="text/css">
	.input_text{
		width:280px;
		height:20px;
		border:1px solid black;		
		font-size:14px;
	}
	.attrname{		
		text-align:right;
		font-size:12px;
	}
	.input_textarea{
		width:280px;
		border:1px solid black;			
	}
	td{
		padding:2px;
	}
</style>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/channelcontentlink.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<!--FloatPanel Inner Start-->
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
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
<!-- Validator-->
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<link href="../../app/js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
<!--locker-->
<SCRIPT src="../../app/js/source/wcmlib/core/LockUtil.js"></SCRIPT>
<script language="JavaScript" src="channelcontentlink_add_edit.js"></script>
<script language="javascript">
	window.m_fpCfg = {
		m_arrCommands : [{
			cmd : 'SaveContentLink',
			name : wcm.LANG.CHANNELCONTENTLINK_SURE || '确定'
		}],
		size : [450, 230]
	};
</script>
</head>
<body>
<div style="width:380px;;margin-top:10px;margin-left:10px;">
<form id="addEditForm" onSubmit="return false;">
<input type="hidden" id="OldLinkName" name="OldLinkName" value="<%=(nObjectId==0?"":currChannelContentLink.getName())%>">
<input type="hidden" id="linkid" name="linkid" value="<%=(nObjectId==0?"":String.valueOf(currChannelContentLink.getId()))%>">
<input type="hidden" id="LinkOrder" name="LinkOrder" value="<%=(nObjectId==0?"":String.valueOf(currChannelContentLink.getOrder()))%>">
<table border="0" cellspacing="0" cellpadding="0">
<tbody>
	<tr>
		<td class="attrname" valign="top" WCMAnt:param="channelcontentlink_add_edit.jsp.title">名称：</td>
		<td class="attrvalue">
			<input id="linkname" name="linkname" type="text" class="input_text" validation="type:'string',required:'true',max_len:'30',rpc:'exitsSimilar',showid:'validation_tip'" value="<%=(nObjectId==0?"":currChannelContentLink.getName())%>">
		</td>
	</tr>
	<tr>
		<td class="attrname"  valign="top" WCMAnt:param="channelcontentlink_add_edit.jsp.describe">描述：</td>
		<td class="attrvalue">
			<textarea type="text" id="linktitle" name="linktitle" class="input_textarea" scroll="auto" cols="20" rows="4" validation="type:'string',max_len:'100',showid:'validation_tip'"><%=(nObjectId==0?"":CMyString.showNull(currChannelContentLink.getLinkTitle()))%></textarea>
		</td>
	</tr>
	<tr>
		<td class="attrname"  valign="top" WCMAnt:param="channelcontentlink_add_edit.jsp.link">链接：</td>
		<td class="attrvalue">
			<input type="text" id="linkurl" name="linkurl" class="input_text" validation="type:'uri',required:'true',max_len:'200',showid:'validation_tip'" value="<%=(nObjectId==0?"":currChannelContentLink.getLinkUrl())%>">
		</td>
	</tr>
	<%
		if(nChannelId > 0 && currChannelContentLink.getSiteId() == 0){
	%>
	<tr>
		<td class="attrname"  valign="top"></td>
		<td class="attrvalue">
			<input type="checkbox" name="bUsedInChildren" id="bUsedInChildren" value="" isboolean="1" <%=currChannelContentLink.isUsedInChildren()?"checked":""%>/><label for="bUsedInChildren" WCMAnt:param="channelcontentlink_add_edit.jsp.wetherapplytochildchnl">是否应用到子栏目</label>
		</td>
	</tr>
	<%	
		}	
	%>
	<tr>
		<td class="attrname" valign="top" WCMAnt:param="channelcontentlink_add_edit.jsp.former">前一个：</td>
		<td class="attrvalue">
			<select id="sellinkorder" <%=bContainsSite ? "disabled":""%>>
				<option value="-1" WCMAnt:param="channelcontentlink_add_edit.jsp.top">最前面</option>
				<%
					if(nSize > 0){
						for(int i=0;i < nSize;i++){
							newChannelContentLink =(ChannelContentLink) currChannelContentLinks.getAt(i);		
				%>
					<option value="<%=newChannelContentLink.getOrder()%>"><%=CMyString.filterForHTMLValue(newChannelContentLink.getName())%></option>
				<%
						}
					}
				%>
			</select>
		</td>
	</tr>
</tbody>
</table>
<div style="float:right;">
<span id="validation_tip" style="magin-right:10px;"></span>
<span style="width:20px;"></span>
</div>
<input type="hidden" id="ChannelId" name="ChannelId" value="">
</form>
</div>
<%if(nSiteId == 0 &&currChannelContentLink.getSiteId() > 0){%>
<script>
Event.observe(window,'load',function(){	
	FloatPanel.setTitle('<%=LocaleServer.getString("channelcontentlink_add_edit.jsp.title","修改站点热词")%>');
});
</script>
<%}%>
</body>
</html>